package com.tilitili.common.autocode.server;


import com.tilitili.common.autocode.AutocodeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.SQLException;
import java.util.Map;

import static com.tilitili.common.autocode.AutocodeHelper.CONFIG;

public class SqlMapperAutocode {
    private static Logger logger = LoggerFactory.getLogger(SqlMapperAutocode.class);

    public static void run() throws SQLException, IOException {

        String fullDomainName = AutocodeHelper.mapperPath+ Table2Domain.domainName+"Mapper.xml";

        File file = new File(fullDomainName);
        if(file.exists()) {
            file.delete();
        }

        OutputStream outStream = new FileOutputStream(fullDomainName, true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outStream));
        StringBuffer head = new StringBuffer();
        StringBuffer resultMap = new StringBuffer();
        StringBuffer insertHead = new StringBuffer();
        StringBuffer insertMid = new StringBuffer();
        StringBuffer insertTail = new StringBuffer();
        StringBuffer insertField = new StringBuffer();
        StringBuffer insertValue = new StringBuffer();
        StringBuffer update = new StringBuffer();
        StringBuffer countByCondition = new StringBuffer();
        StringBuffer countField = new StringBuffer();
        StringBuffer getByCondition = new StringBuffer();
        StringBuffer getField = new StringBuffer();
        //Additional拓展

        String parameterType = AutocodeHelper.basePackageName + ".entity." + Table2Domain.domainName;

        head.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n")
                .append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >").append("\n");

        //1.namespace
        head.append("<mapper namespace=\"").append(AutocodeHelper.basePackageName).append(".mapper.").append(Table2Domain.domainName).append("Mapper\">").append("\n");
        //2.resultMap
        resultMap.append("<resultMap id=\"").append(Table2Domain.domainName).append("\" type=\"").append(parameterType).append("\">").append("\n");
        //3.insert
        insertHead.append("<insert id=\"insert").append(Table2Domain.domainName).append("\" parameterType=\""+parameterType+"\" keyProperty=\"id\" useGeneratedKeys=\"true\">").append("\n");
        insertHead.append("INSERT INTO ").append(CONFIG.getTableName()).append("\n")
                .append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append("\n");
        //4.update
        update.append("<update id=\"update").append(Table2Domain.domainName).append("\"  parameterType=\""+parameterType+"\">").append("\n");
        update.append("update ").append(CONFIG.getTableName()).append("\n").append("<set>").append("\n");
        //5.countByCondition
        countByCondition.append("<select id=\"count").append(Table2Domain.domainName).append("ByCondition").append("\"  parameterType=\""+parameterType+"\"")
                .append(" resultType=\"java.lang.Integer\">").append("\n")
                .append("\t select count(1) from ").append(CONFIG.getTableName()).append("\n").append("<where>").append("\n");
        //6.getByCondition
        getByCondition.append("<select id=\"list").append(Table2Domain.domainName).append("ByCondition").append("\"  parameterType=\""+parameterType+"\"")
                .append(" resultMap=\"").append(Table2Domain.domainName).append("\">").append("\n")
                .append("select <include refid=\"Base_Column_List\" />").append("\n");


        for(Map.Entry<String, FieldPair> entry: Table2Domain.fieldPairMap.entrySet()) {

            String columnName          = entry.getValue().columnName;
            String columnType          = entry.getValue().columnType;
            String propertyName        = entry.getValue().propertyName;
            String propertyType        = entry.getValue().propertyType;
            String propertyClassName = entry.getValue().propertyClassName;

            resultMap.append("\t<result  property=\"").append(propertyName).append("\"  column=\"").append(columnName).append("\"/>").append("\n");

            //主键, 创建时间不更新，更新时间固定getdate()
            if (columnName.equals(Table2Domain.updateTimeColumn)) {
                update.append("\t").append(columnName).append("=").append("getdate()").append(",").append("\n");
            }else if(!columnName.equals(Table2Domain.PrimaryColumn) && !columnName.equals(Table2Domain.CreateTimeColumn)){
                update.append("\t<if test=\"").append(propertyName).append("!=null\">").append("\n").append("\t\t").append(columnName).append("=").append("#{").append(propertyName).append("}").append(",").append("\n").append("\t</if>").append("\n");
            }
            //主键不插入, 创建时间，更新时间固定getdate()
            if (columnName.equals(Table2Domain.updateTimeColumn) || columnName.equals(Table2Domain.CreateTimeColumn)) {
                insertField.append("\t").append(columnName).append(",").append("\n");
                insertValue.append("\t").append("getdate()").append(",").append("\n");
            }else if(!columnName.equals(Table2Domain.PrimaryColumn)){
                insertField.append("\t<if test=\"").append(propertyName).append("!= null\" >").append(columnName).append(",").append("</if>").append("\n");
                insertValue.append("\t<if test=\"").append(propertyName).append("!= null\" >").append("#{").append(propertyName).append("}").append(",").append("</if>").append("\n");
            }
            //float要加上round(col,2)才能匹配
            if ("FLOAT".equals(columnType)) {
                countField.append("\t<if test=\"").append(propertyName).append("!=null\">").append("\n\t\t").append(" and ").append("round(").append(columnName).append(",2)=").append("#{").append(propertyName).append("}").append("\n").append("\t</if>").append("\n");
                getField.append("\t<if test=\"").append(propertyName).append("!=null\">").append("\n\t\t").append(" and ").append("round(").append(columnName).append(",2)=").append("#{").append(propertyName).append("}").append("\n").append("\t</if>").append("\n");
            }else{
                countField.append("\t<if test=\"").append(propertyName).append("!=null\">").append("\n\t\t").append(" and ").append(columnName).append("=").append("#{").append(propertyName).append("}").append("\n").append("\t</if>").append("\n");
                getField.append("\t<if test=\"").append(propertyName).append("!=null\">").append("\n\t\t").append(" and ").append(columnName).append("=").append("#{").append(propertyName).append("}").append("\n").append("\t</if>").append("\n");
            }
        }
        resultMap.append("</resultMap>").append("\n\n\n");
        insertMid.append("</trim>").append("\n").append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">").append("\n");
        insertTail.append("</trim>").append("\n")
                .append("</insert>").append("\n\n\n");
        update.append("</set>").append("\n").append("where ").append(Table2Domain.PrimaryColumn).append("=").append("#{").append(Table2Domain.PrimaryColumn).append("}").append("\n").append("</update>").append("\n\n\n");
        countField.append("</where>").append("\n").append("</select>").append("\n\n\n");
        getByCondition.append(" from ").append(CONFIG.getTableName()).append("\n").append("<where>").append("\n");
        getField.append("</where>").append("\n")
                .append("order by id desc ").append("\n")
                .append("<if test=\"startNum != null and pageSize != null\">limit #{startNum}, #{pageSize}</if>").append("\n")
                .append("</select>").append("\n\n\n");

        StringBuffer baseColumnBuffer = new StringBuffer();
        baseColumnBuffer.append("<sql id=\"Base_Column_List\">").append(Table2Domain.columnNameBuffer).append("</sql>").append("\n\n");

        bw.write(head.toString());
        bw.write(resultMap.toString());
        bw.write(baseColumnBuffer.toString());
        bw.write(insertHead.toString());
        bw.write(insertField.toString());
        bw.write(insertMid.toString());
        bw.write(insertValue.toString());
        bw.write(insertTail.toString());
        bw.write(update.toString());
        bw.write(countByCondition.toString());
        bw.write(countField.toString());
        bw.write(getByCondition.toString());
        bw.write(getField.toString());
        bw.write("</mapper>");
        bw.close();

        logger.error("成功生成文件"+AutocodeHelper.mapperPath + Table2Domain.domainName + ".xml successful");
    }

}
