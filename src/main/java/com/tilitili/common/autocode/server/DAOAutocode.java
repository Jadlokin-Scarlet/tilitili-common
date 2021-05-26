package com.tilitili.common.autocode.server;


import com.tilitili.common.autocode.AutocodeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class DAOAutocode {
    private static Logger logger = LoggerFactory.getLogger(DAOAutocode.class);

    public static void run() throws IOException {

        String abstractDBhelperPackageName = AutocodeHelper.class.getPackage().getName().replaceAll("autocode","");
        StringBuffer buffer = new StringBuffer();
        buffer.append("package ").append(AutocodeHelper.basePackageName).append(".dao.mapper;").append("\n\n");
        buffer.append("import ").append(abstractDBhelperPackageName).append("AbstractDBhelper;").append("\n");
        buffer.append("import org.springframework.jdbc.core.JdbcTemplate;").append("\n");
        buffer.append("import org.springframework.stereotype.Component;").append("\n");
        buffer.append("import javax.annotation.Resource;").append("\n\n");
        buffer.append("import java.util.List;").append("\n");
        buffer.append("import java.util.Map;").append("\n");
        buffer.append("import java.util.HashMap;").append("\n");
        buffer.append("import java.util.Date;").append("\n");
        buffer.append("import ").append(AutocodeHelper.basePackageName).append(".domain.").append(Table2Domain.domainName).append(";\n\n");

        buffer.append("@Component").append("\n");
        buffer.append("public class "+ Table2Domain.domainName+"DAO extends AbstractDBhelper<"+ Table2Domain.domainName+"> {").append("\n");
        buffer.append("\n");
        buffer.append("\t").append("@Resource(name = \"jdbc"+ AutocodeHelper.schema.substring(0, 1).toUpperCase() + AutocodeHelper.schema.substring(1, AutocodeHelper.schema.length())+"\")").append("\n");
        buffer.append("\t").append("JdbcTemplate jdbcTemplate;").append("\n");
        buffer.append("\n");
        buffer.append("\t").append("@Override").append("\n");
        buffer.append("\t").append("public JdbcTemplate getJdbcTemplate() {").append("\n");
        buffer.append("\t\t").append("return this.jdbcTemplate;").append("\n");
        buffer.append("\t").append("}").append("\n");
        buffer.append("\n");

        buffer.append("\t").append("public String columnName=\""+ Table2Domain.columnNameBuffer.toString()+"\";").append("\n\n");

        String attrName = Table2Domain.domainName.substring(0, 1).toLowerCase() + Table2Domain.domainName.substring(1, Table2Domain.domainName.length());

        String capitalFirstPropertyName = Table2Domain.PrimaryPropertyName.substring(0, 1).toUpperCase() + Table2Domain.PrimaryPropertyName.substring(1, Table2Domain.PrimaryPropertyName.length());

        //save
        buffer.append("\t").append("public void save("+ Table2Domain.domainName+" " +attrName+") {").append("\n");
        buffer.append("\t\t").append("super.save("+attrName+", \""+ AutocodeHelper.tableName+"\");").append("\n");
        buffer.append("\t").append("}").append("\n").append("\n");

        //query
        buffer.append("\t").append("public "+ Table2Domain.domainName+" query"+ Table2Domain.domainName+"By"+capitalFirstPropertyName+"("+ Table2Domain.PrimaryPropertyType +" "+ Table2Domain.PrimaryPropertyName +") {").append("\n");
        buffer.append("\t\t").append("Map<String,Object> paramMap = new HashMap<String, Object>();").append("\n");
        buffer.append("\t\t").append("paramMap.put(\""+ Table2Domain.PrimaryPropertyName +"\", "+ Table2Domain.PrimaryPropertyName +");").append("\n");
        buffer.append("\t\t").append("return super.query(\"select \"+columnName+\" from "+ AutocodeHelper.tableName+" where "+ Table2Domain.PrimaryColumn +"=:"+ Table2Domain.PrimaryPropertyName +"\", paramMap);").append("\n");
        buffer.append("\t").append("}").append("\n").append("\n");

        //update
        buffer.append("\t").append("public void update"+ Table2Domain.domainName+"By"+capitalFirstPropertyName+"("+ Table2Domain.PrimaryPropertyType +" "+ Table2Domain.PrimaryPropertyName +") {").append("\n");
        buffer.append("\t\t").append("Map<String,Object> paramMap = new HashMap<String, Object>();").append("\n");
        buffer.append("\t\t").append("paramMap.put(\""+ Table2Domain.PrimaryPropertyName +"\", "+ Table2Domain.PrimaryPropertyName +");").append("\n");
        buffer.append("\t\t").append("super.update(\"update "+ AutocodeHelper.tableName+" set update_time=now() where "+ Table2Domain.PrimaryColumn +"=:"+ Table2Domain.PrimaryPropertyName +"\", paramMap);").append("\n");
        buffer.append("\t").append("}").append("\n").append("\n");

        //in
        buffer.append("\t").append("public List<"+ Table2Domain.domainName+"> query"+ Table2Domain.domainName+"In"+capitalFirstPropertyName+"List(List<"+ Table2Domain.PrimaryPropertyType +"> "+ Table2Domain.PrimaryPropertyName +"List) {").append("\n");
        buffer.append("\t\t").append("Map<String,Object> paramMap = new HashMap<String, Object>();").append("\n");
        buffer.append("\t\t").append("paramMap.put(\""+ Table2Domain.PrimaryPropertyName +"List\", "+ Table2Domain.PrimaryPropertyName +"List);").append("\n");
        buffer.append("\t\t").append("return super.queryList(\"select \"+columnName+\" from "+ AutocodeHelper.tableName+" where "+ Table2Domain.PrimaryColumn +" in (:"+ Table2Domain.PrimaryPropertyName +"List)\", paramMap);").append("\n");
        buffer.append("\t").append("}").append("\n").append("\n");

        //queryByIndex
        buffer.append(generateIndexQuery());

        buffer.append("}").append("\n");


        File file = new File(AutocodeHelper.daoPath+ Table2Domain.domainName+"DAO.java");
        if(file.exists()) {
            file.delete();
        }

        OutputStream outStream = new FileOutputStream(file, true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outStream));
        bw.write(buffer.toString());
        bw.close();
        logger.error("成功生成文件"+ AutocodeHelper.daoPath+ Table2Domain.domainName+"DAO.java");
    }

    public static String generateIndexQuery() {
        StringBuffer indexQueryMethod = new StringBuffer();
        for (Map.Entry<String, IndexPair> entry : Table2Domain.indexPairMap.entrySet()) {
            String indexName = entry.getKey();
            IndexPair indexPair = entry.getValue();

            StringBuffer paramDefinitionBuffer = new StringBuffer();
            StringBuffer paramMapBuffer = new StringBuffer();
            StringBuffer paramWhereBuffer = new StringBuffer();
            for (FieldPair fieldPair : indexPair.getFieldPairList()) {
                    if (paramDefinitionBuffer.length() > 0) {
                            paramDefinitionBuffer.append(" , ");
                            paramWhereBuffer.append(" and ");
                            paramMapBuffer.append("\n");
                    }

                    paramDefinitionBuffer.append(fieldPair.getPropertyType() + " " + fieldPair.getPropertyName());
                    paramMapBuffer.append("\t\t").append("paramMap.put(").append("\"").append(fieldPair.getPropertyName()).append("\"").append(" , ").append(fieldPair.getPropertyName()).append(");");
                    paramWhereBuffer.append(fieldPair.getColumnName()).append("=:").append(fieldPair.getPropertyName());
            }

            indexQueryMethod.append("\t").append("public List<" + Table2Domain.domainName + "> query" + Table2Domain.domainName + "By" + indexName + "(" + paramDefinitionBuffer.toString() + ") {").append("\n");
            indexQueryMethod.append("\t\t").append("Map<String,Object> paramMap = new HashMap<String, Object>();").append("\n");
            indexQueryMethod.append(paramMapBuffer.toString()).append("\n");
            indexQueryMethod.append("\t\t").append("return super.queryList(\"select \"+columnName+\" from " + AutocodeHelper.tableName + " where " + paramWhereBuffer.toString() + "\", paramMap);").append("\n");
            indexQueryMethod.append("\t").append("}").append("\n\n");

        }
        return indexQueryMethod.toString();
    }
}
