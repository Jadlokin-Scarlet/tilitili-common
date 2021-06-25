package com.tilitili.common.mysqlautocode.server;


import com.google.gson.Gson;
import com.tilitili.common.mysqlautocode.AutocodeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import static com.microsoft.sqlserver.jdbc.StringUtils.isNumeric;
import static io.lettuce.core.LettuceStrings.isNotEmpty;

public class Table2Domain {

    private static Logger logger = LoggerFactory.getLogger(Table2Domain.class);

    public static Map<String, String> columnPropertyMap = new HashMap<String, String>();
    static {
        columnPropertyMap.put("BIGINT", "Long");
        columnPropertyMap.put("BIGINT UNSIGNED", "Long");
        columnPropertyMap.put("INT", "Integer");
        columnPropertyMap.put("INT UNSIGNED", "Integer");
        columnPropertyMap.put("FLOAT", "Float");
        columnPropertyMap.put("DOUBLE", "Double");
        columnPropertyMap.put("DATE", "Date");
        columnPropertyMap.put("DATETIME", "Date");
        columnPropertyMap.put("TIMESTAMP", "Date");
        columnPropertyMap.put("TINYINT", "Integer");
        columnPropertyMap.put("TEXT", "String");
    }

    public static Map<String, FieldPair> fieldPairMap = new LinkedHashMap<>();
    public static Map<String, IndexPair> indexPairMap = new LinkedHashMap<>();

    public static String PrimaryColumn = "";
    public static String PrimaryPropertyName = "";
    public static String PrimaryPropertyType = "";

    public static String CreateTimeColumn = "create_time";
    public static String updateTimeColumn = "update_time";

    public static String domainName = null;
    public static String domainAttrName = null;
    public static StringBuffer columnNameBuffer = new StringBuffer();


    public static String getColumnProperty(String columnType) {
        if(columnPropertyMap.get(columnType) != null) {
            return columnPropertyMap.get(columnType.toUpperCase());
        }else{
            return "String";
        }
    }

    public static void init(String tableName) throws IOException, SQLException {

        Connection conn = null;
        Statement stmt  = null;
        try {
            String sql = "select * from "+tableName+" where 1=2";
            Class.forName(AutocodeHelper.driver).newInstance();
            conn = DriverManager.getConnection(AutocodeHelper.url, AutocodeHelper.user, AutocodeHelper.password);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();


            if(domainName == null) {
                Integer idx = tableName.indexOf(".");
                if(idx>0) {
                    domainName = JdbcUtils.convertUnderscoreNameToPropertyName(tableName.substring(idx+1, tableName.length()));
                }else {
                    domainName = JdbcUtils.convertUnderscoreNameToPropertyName(tableName);
                }
                domainName = domainName.substring(0, 1).toUpperCase() + domainName.substring(1, domainName.length());
            }

            domainName = domainName.replaceAll("Lx","");

            domainAttrName = domainName.substring(0, 1).toLowerCase() + domainName.substring(1, domainName.length());

            parseColumn(conn, rsmd);
            parseIndex(conn);
            parseComment(stmt);

        }catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void parseColumn(Connection conn, ResultSetMetaData rsmd) throws SQLException {
        for (int index = 1; index <= rsmd.getColumnCount(); index++) {
            String columnName = JdbcUtils.lookupColumnName(rsmd, index);
            String columnType = rsmd.getColumnTypeName(index);
            String propertyName = JdbcUtils.convertUnderscoreNameToPropertyName(columnName);
            String propertyType = getColumnProperty(columnType.toUpperCase());
            String propertyClassName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1, propertyName.length());

            fieldPairMap.put(columnName, new FieldPair(columnName, columnType, propertyName, propertyType, propertyClassName));

            if(columnNameBuffer.length()>0) {
                columnNameBuffer.append(",");
            }
            columnNameBuffer.append("`"+columnName+"`");
        }

        //如果存在主键,框架要求必须有主键
        ResultSet primaryKeyResultSet = conn.getMetaData().getPrimaryKeys(null,null, AutocodeHelper.tableName);
        if(primaryKeyResultSet.next()){
            String primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME");
            FieldPair fieldPair = fieldPairMap.get(primaryKeyColumnName);
            fieldPair.setPrimaryKey(true);
            PrimaryColumn       = fieldPair.getColumnName();
            PrimaryPropertyName = fieldPair.getPropertyName();
            PrimaryPropertyType = fieldPair.getPropertyType();
        }
    }

    public static void parseIndex(Connection conn) throws SQLException {

        ResultSet indexResultSet = conn.getMetaData().getIndexInfo(null, null, AutocodeHelper.tableName, false, false);

        while(indexResultSet.next()) {
            String indexName = indexResultSet.getString("INDEX_NAME");
            String columnName = indexResultSet.getString("COLUMN_NAME");
            if (indexName == null || columnName == null) {
                continue;
            }
            FieldPair fieldPair = fieldPairMap.get(columnName);
            fieldPair.isIndex = true;

            indexName = JdbcUtils.convertUnderscoreNameToPropertyName(indexName);
            indexName = indexName.substring(0, 1).toUpperCase() + indexName.substring(1, indexName.length());

            if("PRIMARY".equalsIgnoreCase(indexName)) {
                continue;
            }

            if(indexPairMap.containsKey(indexName)) {
                IndexPair indexPair = indexPairMap.get(indexName);
                indexPair.getFieldPairList().add(fieldPair);
            }else{
                //索引名字
                IndexPair indexPair = new IndexPair();
                indexPair.setIndexName(indexName);

                //索引对应的字段
                List<FieldPair> fieldPairList = new ArrayList<FieldPair>();
                fieldPairList.add(fieldPair);
                indexPair.setFieldPairList(fieldPairList);
                indexPairMap.put(indexName, indexPair);
            }

            //TODO 主外键
        }
    }


    public static void parseComment(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery(String.format("select * from information_schema.columns t where t.table_name='%s';", AutocodeHelper.tableName));
        while (rs.next()) {
            String comment    = rs.getString("COLUMN_NAME");
            String columnName = rs.getString("COLUMN_NAME");

            FieldPair fieldPair = fieldPairMap.get(columnName);
            fieldPair.displayName = fieldPair.getPropertyName();

            if(isNotEmpty(comment)) {
                fieldPair.displayName = comment.trim();
                if(comment.contains(":") || comment.contains("：")) {

                    //"是否隐藏; 0:否; 1:是";
                    comment = comment.replaceAll("；", ";").replaceAll("：",":");
                    String [] commentData = comment.split(";");

                    List<Filter> filterList = new ArrayList<>();
                    for(int i=0; i<commentData.length; i++) {
                        if(i==0) {
                            fieldPair.displayName = commentData[i];
                        }else {
                            String [] comm = commentData[i].split(":");
                            if(comm.length ==2) {
                                if(isNumeric(comm[0].trim())) {
                                    Filter filter = new Filter(comm[0].trim(), "'" + comm[1].trim() + "'");
                                    filterList.add(filter);
                                }else{
                                    Filter filter = new Filter("'"+comm[0].trim()+"'", "'" + comm[1].trim() + "'");
                                    filterList.add(filter);
                                }
                            }
                        }
                        fieldPair.filters = filterList;
                    }
                }
            }
        }
    }

    public static String appendDefinition(String propertyType, String propertyName, String displayName) {
        return "\t" + "private " + propertyType + " " + propertyName + ";\n";
    }

    public static String appendGetterAndSetter(String propertyType, String propertyName, String propertyCapitalName) {
        String get = String.format("\tpublic %s get%s() {return %s;}\n", propertyType, propertyCapitalName, propertyName);
        String set = String.format("\tpublic void set%s(%s %s) {this.%s = %s;}\n", propertyCapitalName, propertyType, propertyName, propertyName, propertyName);
        return get + set;
    }

    public static String append2String() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\t").append("public String toString() {").append("\n")
                .append("\t\t").append("return new Gson().toJson(this);").append("\n")
                .append("\t").append("}").append("\n\n");
        return buffer.toString();
    }

    public static void main(String[] args) {
//        String ret ="是否隐藏；0:否;1：是";
        String ret ="1234";
        ret = ret.replaceAll("；", ";").replaceAll("：",":");

        String [] commentData = ret.split(";");
        List<Filter> filters = new ArrayList<>();

        for(int i=0; i<commentData.length; i++) {
            if(i==0) {
                System.out.println(commentData[i]);
            }else {
                String [] comment = commentData[i].split(":");
                Filter filter = new Filter(comment[0], comment[1]);
                filters.add(filter);
            }
        }
        System.out.println(new Gson().toJson(filters));

    }
}


