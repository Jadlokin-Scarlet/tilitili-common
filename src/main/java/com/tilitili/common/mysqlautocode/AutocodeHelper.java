package com.tilitili.common.mysqlautocode;

import com.tilitili.common.mysqlautocode.server.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import java.io.File;

public class AutocodeHelper {

    private static Logger logger = LoggerFactory.getLogger(AutocodeHelper.class);

    //数据库名+表名
    public final static String dbName    ="base";
    public final static String tableName = "subscription";
    public final static String schema    = JdbcUtils.convertUnderscoreNameToPropertyName(dbName);

    public final static String driver = "com.mysql.cj.jdbc.Driver";
    public final static String url="jdbc:mysql://47.100.66.36:3306/"+dbName;
    public final static String user="root";
    public final static String password="a65588227";

    public static String basePackageName ="";
    public static String projectPath    = "";
    public static String controllerPath = "";
    public static String daoPath        = "";
    public static String domainPath     = "";
    public static String mapperPath     = "";
    public static String antdesignPath  = "";
    //项目文件生成路径位置，请自行调整
    static {
        String packageName = AutocodeHelper.class.getPackage().getName();
        basePackageName = packageName.substring(0, ordinalIndexOf(packageName, ".", 3));
        projectPath = AutocodeHelper.class.getResource("").getPath();
        projectPath = projectPath.substring(0, projectPath.indexOf("target"));

        String packagePath = basePackageName.replaceAll("\\.",File.separator);
        controllerPath = projectPath+"src/main/java/"+packagePath+"/controller/";
        daoPath        = projectPath+"src/main/java/"+packagePath+"/mapper/";
        domainPath     = projectPath+"src/main/java/"+packagePath+"/entity/";
        mapperPath     = projectPath+"src/main/resources/mybatis/";
        //ant design模板和输出目录
        antdesignPath  = projectPath+"src/main/resources/antdesign/";
    }

    private static int ordinalIndexOf(String str, String searchStr, int ordinal) {
        if (str != null && searchStr != null && ordinal > 0) {
            if (searchStr.length() == 0) {
                return 0;
            } else {
                int found = 0;
                int index = -1;

                do {
                    index = str.indexOf(searchStr, index + 1);

                    if (index < 0) {
                        return index;
                    }

                    ++found;
                } while(found < ordinal);

                return index;
            }
        } else {
            return -1;
        }
    }


    public static void main(String args[]) {
//        Connection conn = null;
//        Statement stmt  = null;
        try {
//            Class.forName(driver).newInstance();
//            conn = DriverManager.getConnection(url, user, password);
//            stmt = conn.createStatement();
            Table2Domain.init(tableName);
//            ModalFormAutocode.run();
//            SimpleTableAutocode.run();
            DomainAutoCode.run();
//            DAOAutocode.run();
            MapperAutocode.run();
            SqlMapperAutocode.run();
//            ControllerAutocode.run();

        }catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
//        finally {
//            try {
//                stmt.close();
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
    }

}