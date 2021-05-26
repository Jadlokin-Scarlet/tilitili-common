package com.tilitili.common.autocode.server;


import com.tilitili.common.autocode.AutocodeHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Map;

@Slf4j
public class DomainAutoCode {

    public static void run() throws IOException {
        File file = new File(AutocodeHelper.domainPath+ Table2Domain.domainName+".java");
        if(file.exists()) {
            file.delete();
        }
        OutputStream outStream = new FileOutputStream(file, true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outStream));

        StringBuilder head = new StringBuilder();
        StringBuilder define = new StringBuilder();
        StringBuilder getterSetter = new StringBuilder();

        head.append("package ").append(AutocodeHelper.basePackageName).append(".entity;").append("\n");
        head.append("import lombok.Data;").append("\n");
        head.append("import lombok.experimental.Accessors;").append("\n");
//        head.append("import java.util.List;").append("\n");
//        head.append("import java.util.Map;").append("\n\n");s


        for(Map.Entry<String, FieldPair> entry: Table2Domain.fieldPairMap.entrySet()) {

            String columnName          = entry.getValue().columnName;
            String columnType          = entry.getValue().columnType;
            String propertyName        = entry.getValue().propertyName;
            String propertyType        = entry.getValue().propertyType;
            String propertyClassName   = entry.getValue().propertyClassName;
            String displayName         = entry.getValue().displayName;

            if ("datetime".equals(columnType)) {
                if (head.indexOf("import java.util.Date") < 0) {
                    head.append("import java.util.Date;").append("\n");
                }
            }

            define.append(Table2Domain.appendDefinition(propertyType, propertyName, displayName));
//            getterSetter.append(Table2Domain.appendGetterAndSetter(propertyType, propertyName, propertyClassName));

        }
        head.append("\n");
        head.append("@Data").append("\n");
        head.append("@Accessors(chain = true)").append("\n");
        head.append("public class ").append(Table2Domain.domainName).append(" {").append("\n");

        bw.write(head.toString());
        bw.write(define.toString());
//        bw.write(getterSetter.toString());
//        bw.write(Table2Domain.append2String());
        bw.write("}");
        bw.close();

        log.error("成功生成文件"+ AutocodeHelper.domainPath+ Table2Domain.domainName+".java");
    }
}