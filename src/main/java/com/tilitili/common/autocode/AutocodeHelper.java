package com.tilitili.common.autocode;

import com.tilitili.common.autocode.server.DomainAutoCode;
import com.tilitili.common.autocode.server.MapperAutocode;
import com.tilitili.common.autocode.server.SqlMapperAutocode;
import com.tilitili.common.autocode.server.Table2Domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AutocodeHelper {

    private static Logger logger = LoggerFactory.getLogger(AutocodeHelper.class);

    //数据库名+表名
    public final static String dbName    ="bilibili";
    public final static String tableName = "dynamic";
    public final static String schema    = JdbcUtils.convertUnderscoreNameToPropertyName(dbName);

    public final static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public final static String url="jdbc:sqlserver://122.51.147.107:9999;database="+dbName;
    public final static String user="suika";
    public final static String password="Ls654321";

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


    public static void main(String[] args) throws IOException, SQLException {
        Table2Domain.init(tableName);
//        MapperAutocode.run();
        DomainAutoCode.run();
        SqlMapperAutocode.run();
    }

}