package com.tilitili.common.autocode.server;


import com.tilitili.common.autocode.AutocodeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class MapperAutocode {
    private static Logger logger = LoggerFactory.getLogger(MapperAutocode.class);

    public static void run() throws IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append("package ").append(AutocodeHelper.basePackageName).append(".mapper;").append("\n\n");
        buffer.append("import javax.annotation.Resource;").append("\n\n");
        buffer.append("import java.util.List;").append("\n");
        buffer.append("import java.util.Map;").append("\n");
        buffer.append("import java.util.HashMap;").append("\n");
        buffer.append("import java.util.Date;").append("\n");
        buffer.append("import org.springframework.stereotype.Component;").append("\n");
        buffer.append("import org.apache.ibatis.annotations.Param;").append("\n");
        buffer.append("import ").append(AutocodeHelper.basePackageName).append(".entity.").append(Table2Domain.domainName).append(";\n\n");

        buffer.append("@Component").append("\n");
        buffer.append("public interface "+ Table2Domain.domainName+"Mapper {").append("\n");

        //insert
        buffer.append("\t").append("Long insert"+Table2Domain.domainName+"("+ Table2Domain.domainName+" " +Table2Domain.domainAttrName+");").append("\n");

        //update
        buffer.append("\t").append("void update"+Table2Domain.domainName+"("+ Table2Domain.domainName+" " +Table2Domain.domainAttrName+");").append("\n");

        //count
        buffer.append("\t").append("int count"+Table2Domain.domainName+"ByCondition ("+ Table2Domain.domainName+" " +Table2Domain.domainAttrName+");").append("\n");

        //get
        buffer.append("\t").append("List<").append(Table2Domain.domainName).append(">").append(" list"+Table2Domain.domainName+"ByCondition ("+ Table2Domain.domainName+" " +Table2Domain.domainAttrName+");").append("\n");

        buffer.append("}");

        File file = new File(AutocodeHelper.daoPath+ Table2Domain.domainName+"Mapper.java");
        if(file.exists()) {
            file.delete();
        }

        OutputStream outStream = new FileOutputStream(file, true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outStream));
        bw.write(buffer.toString());
        bw.close();
        logger.error("成功生成文件"+ AutocodeHelper.daoPath+ Table2Domain.domainName+"Mapper.java");
    }
}
