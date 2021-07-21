package com.tilitili.common.autocode.server;


import com.tilitili.common.autocode.AutocodeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static com.tilitili.common.autocode.AutocodeHelper.CONFIG;

public class MapperAutocode {
    private static final Logger logger = LoggerFactory.getLogger(MapperAutocode.class);

    public static void run() throws IOException {
        String domainName = CONFIG.updateDomain()? Table2Domain.domainName: CONFIG.getReplaceDomainName();
        String domainAttrName = domainName.substring(0, 1).toLowerCase() + domainName.substring(1);

        StringBuilder buffer = new StringBuilder();
        buffer.append("package ").append(AutocodeHelper.basePackageName).append(".mapper;").append("\n\n");
        buffer.append("import javax.annotation.Resource;").append("\n\n");
        buffer.append("import java.util.List;").append("\n");
        buffer.append("import java.util.Map;").append("\n");
        buffer.append("import java.util.HashMap;").append("\n");
        buffer.append("import java.util.Date;").append("\n");
        buffer.append("import org.springframework.stereotype.Component;").append("\n");
        buffer.append("import org.apache.ibatis.annotations.Param;").append("\n");
        buffer.append("import ").append(AutocodeHelper.basePackageName).append(".entity.").append(domainName).append(";\n\n");

        buffer.append("@Component").append("\n");
        buffer.append("public interface "+ domainName+"Mapper {").append("\n");

        //insert
        buffer.append("\t").append("Long insert"+domainName+"("+ domainName+" " +domainAttrName+");").append("\n");

        //update
        buffer.append("\t").append("void update"+domainName+"("+ domainName+" " +domainAttrName+");").append("\n");

        //count
        buffer.append("\t").append("int count"+domainName+"ByCondition ("+ domainName+" " +domainAttrName+");").append("\n");

        //get
        buffer.append("\t").append("List<").append(domainName).append(">").append(" list"+domainName+"ByCondition ("+ domainName+" " +domainAttrName+");").append("\n");

        buffer.append("}");

        File file = new File(AutocodeHelper.daoPath+ domainName+"Mapper.java");
        if(file.exists()) {
            file.delete();
        }

        OutputStream outStream = new FileOutputStream(file, true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outStream));
        bw.write(buffer.toString());
        bw.close();
        logger.error("成功生成文件"+ AutocodeHelper.daoPath+ domainName+"Mapper.java");
    }
}
