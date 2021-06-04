package com.tilitili.common.mysqlautocode.server;


import com.tilitili.common.mysqlautocode.AutocodeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ControllerAutocode {
    private static Logger logger = LoggerFactory.getLogger(ControllerAutocode.class);
    public static void run() throws IOException {

        File file = new File(AutocodeHelper.controllerPath+ Table2Domain.domainName+"Controller.java");
        if(file.exists()) {
            file.delete();
        }

        OutputStream outStream = new FileOutputStream(file, true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outStream));

        StringBuffer controllerBuffer = new StringBuffer();

        String mapperDefine   = Table2Domain.domainName+"Mapper" + " " + Table2Domain.domainAttrName+"Mapper";
        String mapperAttr     = Table2Domain.domainAttrName+"Mapper";
        String domainDefine   = Table2Domain.domainName + " " + Table2Domain.domainAttrName;


        controllerBuffer.append("package ").append(AutocodeHelper.basePackageName).append(".controller;").append("\n");

        controllerBuffer.append("import java.text.SimpleDateFormat;").append("\n");
        controllerBuffer.append("import java.util.Map;").append("\n");
        controllerBuffer.append("import java.util.HashMap;").append("\n");
        controllerBuffer.append("import java.util.List;").append("\n");
        controllerBuffer.append("import javax.annotation.Resource;").append("\n");
        controllerBuffer.append("import javax.servlet.http.HttpServletRequest;").append("\n");
        controllerBuffer.append("import javax.servlet.http.HttpServletResponse;").append("\n");
        controllerBuffer.append("import org.apache.commons.lang3.StringUtils;").append("\n");
        controllerBuffer.append("import org.slf4j.Logger;").append("\n");
        controllerBuffer.append("import org.slf4j.LoggerFactory;").append("\n");
        controllerBuffer.append("import org.springframework.stereotype.Controller;").append("\n");
        controllerBuffer.append("import org.springframework.web.bind.annotation.RequestMapping;").append("\n");
        controllerBuffer.append("import org.springframework.web.bind.annotation.RestController;").append("\n");
        controllerBuffer.append("import org.springframework.web.bind.annotation.RestController;").append("\n");
        controllerBuffer.append("import org.springframework.web.bind.annotation.RequestBody;").append("\n");
        controllerBuffer.append("import com.taofen8.admin.scaffold.dto.BaseRequest;").append("\n");
        controllerBuffer.append("import com.taofen8.admin.scaffold.dto.BaseResponse;").append("\n");
        controllerBuffer.append("import com.taofen8.admin.scaffold.dto.ResultCode;").append("\n");
        controllerBuffer.append("import com.taofen8.admin.scaffold.dto.ResultUtil;").append("\n");

        controllerBuffer.append("import ").append(AutocodeHelper.basePackageName).append(".dto.").append("PageBean").append(";\n");
        controllerBuffer.append("import ").append(AutocodeHelper.basePackageName).append(".entity.").append(Table2Domain.domainName).append(";\n");
//        controllerBuffer.append("import ").append(AutocodeHelper.basePackageName).append(".dao.").append(Table2Domain.domainName).append("DAO").append(";\n");
        controllerBuffer.append("import ").append(AutocodeHelper.basePackageName).append(".dao.").append(Table2Domain.domainName).append("Mapper").append(";\n\n\n");


        controllerBuffer.append("@RequestMapping(\"/api/").append(Table2Domain.domainAttrName).append("\")").append("\n");
        controllerBuffer.append("@RestController").append("\n");
        controllerBuffer.append("@Controller").append("\n");
        controllerBuffer.append("public class ").append(Table2Domain.domainName).append("Controller {").append("\n\n");

        controllerBuffer.append("\t").append("private Logger logger = LoggerFactory.getLogger(").append(Table2Domain.domainName).append("Controller.class);").append("\n\n");

        controllerBuffer.append("\t").append("@Resource").append("\n")
                .append("\t").append("private ").append(mapperDefine).append(";\n");

//        controllerBuffer.append("\t").append("@Resource").append("\n")
//                .append("\t").append("private ").append(Table2Domain.domainName).append("DAO").append(" ").append(Table2Domain.domainAttrName).append("DAO").append(";\n\n");

        //insert
        controllerBuffer.append("\t").append("@RequestMapping(\"/save\")").append("\n")
                .append("\t").append("public BaseResponse<?> save").append("(@RequestBody ").append(domainDefine).append(") throws Exception {").append("\n")
                .append("\t\t").append("if(").append(Table2Domain.domainAttrName).append(".getId() !=null && ").append(Table2Domain.domainAttrName).append(".getId() >0) {").append("\n")
                .append("\t\t\t").append(mapperAttr).append(".").append("update").append(Table2Domain.domainName).append("(").append(Table2Domain.domainAttrName).append(");").append("\n")
                .append("\t\t").append("}else{").append("\n")
                .append("\t\t\t").append(mapperAttr).append(".").append("add").append(Table2Domain.domainName).append("(").append(Table2Domain.domainAttrName).append(");\n")
                .append("\t\t").append("}").append("\n")
                .append("\t\t").append("return ResultUtil.success()").append(";\n")
                .append("\t").append("} ").append("\n\n");

//        //update
//        controllerBuffer.append("\t").append("@RequestMapping(\"/update\")").append("\n")
//                .append("\t").append("public BaseResponse<?> update").append("(").append(domainDefine).append(") throws Exception {").append("\n")
//                .append("\t\t").append(mapperAttr).append(".").append("update").append(Table2Domain.domainName).append("(").append(Table2Domain.domainAttrName).append(");").append("\n")
//                .append("\t\t").append("return ResultUtil.success()").append(";\n")
//                .append("\t").append("} ").append("\n\n");


        controllerBuffer.append("\t").append("@RequestMapping(\"/get\")").append("\n")
                .append("\t").append("public BaseResponse<?> get").append("(").append(domainDefine).append(") throws Exception {").append("\n")
                .append("\t").append("int totalNum =")
                    .append(mapperAttr).append(".").append("count").append(Table2Domain.domainName).append("ByCondition(").append(Table2Domain.domainAttrName).append(");\n")
                .append("\t").append("List<").append(Table2Domain.domainName).append("> list = ")
                    .append(mapperAttr).append(".").append("get").append(Table2Domain.domainName).append("ByCondition(").append(Table2Domain.domainAttrName).append(");\n")
                .append("\t").append("\n")
                .append("\t\t").append("int pageNo=").append(Table2Domain.domainAttrName).append(".getPageNo()").append(";\n")
                .append("\t\t").append("int pageSize=").append(Table2Domain.domainAttrName).append(".getPageSize()").append(";\n")
                .append("\t\t").append("PageBean<"+ Table2Domain.domainName+"> pageBean = new PageBean<"+ Table2Domain.domainName+">(pageSize, pageNo, totalNum);").append("\n")
                .append("\t\t").append("pageBean.setList(list);").append("\n")
                .append("\t\t").append("return ResultUtil.success(pageBean)").append(";\n")
                .append("\t").append("} ").append("\n\n");

        controllerBuffer.append("}");

        bw.write(controllerBuffer.toString());
        bw.close();
        logger.error("成功生成文件"+ AutocodeHelper.daoPath+ Table2Domain.domainName+"Controller.java");

    }
}

    //PageBean<OrderTrack> pageBean = new PageBean<OrderTrack>(pageSize, pageNo, totalNum);
    //startNum = pageBean.getStartNum();