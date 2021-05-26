package com.tilitili.common.autocode.client;


import com.tilitili.common.autocode.AutocodeHelper;
import com.tilitili.common.autocode.server.FieldPair;
import com.tilitili.common.autocode.server.Filter;
import com.tilitili.common.autocode.server.Table2Domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

public class ModalFormAutocode {

    private static Logger logger = LoggerFactory.getLogger(ModalFormAutocode.class);

    public static void run() throws IOException {

        File directory = new File(AutocodeHelper.antdesignPath+ Table2Domain.domainName+File.separator);
        if(!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(AutocodeHelper.antdesignPath+ Table2Domain.domainName+File.separator+"form.jsx");
        if(file.exists()) {
            file.delete();
        }

        OutputStream outStream = new FileOutputStream(file, true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outStream));

        BufferedReader br = new BufferedReader(new FileReader(new File(AutocodeHelper.antdesignPath+"modalForm.template")));
        StringBuffer antDesignTableTemplate = new StringBuffer();

        String line ="";
        while ((line = br.readLine()) !=null) {
            antDesignTableTemplate.append(line).append("\n");
        }
        br.close();

        String formItem = getFormItem();

        bw.write(antDesignTableTemplate.toString().replaceAll("Modal_Form_Placeholder", formItem).replaceAll("TableName", Table2Domain.domainName));
        bw.close();
        logger.error("成功生成文件"+ AutocodeHelper.antdesignPath+ Table2Domain.domainName+File.separator+"form.jsx");
    }

    public static String getFormItem() {
        StringBuffer buffer = new StringBuffer();

        for(Map.Entry<String, FieldPair> entry: Table2Domain.fieldPairMap.entrySet()) {
            FieldPair fieldPair = entry.getValue();
            String columnName        = fieldPair.columnName;
            String columnType        = fieldPair.columnType;
            String propertyName      = fieldPair.propertyName;
            String propertyType      = fieldPair.propertyType;
            String propertyClassName = fieldPair.propertyClassName;
            String displayName       = fieldPair.displayName;

            String nameLabel = String.format("name='%s' label='%s'", propertyName, displayName);
            if ("DATE".equals(columnType) || "DATETIME".equals(columnType)) {
                buffer.append("<Form.Item ").append(nameLabel).append(" initialValue={moment(selectedRow.").append(propertyName).append(")}>")
                        .append("<DatePicker showTime />").append("\n")
                        .append("</Form.Item>").append("\n")
                        .append("\n");
            }
            else if(isNotEmpty(fieldPair.filters)) {
                buffer.append("<Form.Item ").append(nameLabel).append(" initialValue={selectedRow.").append(propertyName).append(" || ''}>").append("\n");
                buffer.append("<Select>").append("\n");

                for(Filter filter: fieldPair.filters) {
                    String option = String.format("<Option value = '%s'> %s </Option>", filter.value, filter.text);
                    buffer.append(option).append("\n");
                }
                buffer.append("</Select>").append("\n");
                buffer.append("</Form.Item>").append("\n");

            }else {
                buffer.append("<Form.Item ").append(nameLabel).append(" initialValue={selectedRow.").append(propertyName).append(" || ''}>").append("\n")
                        .append("<Input/>").append("\n")
                        .append("</Form.Item>").append("\n");
            }
        }

//        System.out.println(buffer.toString());
        return buffer.toString();
    }
}

