package com.tilitili.common.autocode.client;


import com.google.gson.Gson;
import com.tilitili.common.autocode.server.FieldPair;
import com.tilitili.common.autocode.server.Filter;
import com.tilitili.common.autocode.server.Table2Domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

public class ColumnAutocode {

    private static Logger logger = LoggerFactory.getLogger(ColumnAutocode.class);

    public static String run() throws IOException {

//        File file = new File(AutocodeHelper.antdesignPath+ Table2Domain.domainName+"Columns.jsx");
//        if(file.exists()) {
//            file.delete();
//        }
//
//        OutputStream outStream = new FileOutputStream(file, true);
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outStream));

        List<Column> columns = new ArrayList<>();
        for(Map.Entry<String, FieldPair> entry: Table2Domain.fieldPairMap.entrySet()) {
            FieldPair fieldPair = entry.getValue();
            String columnName        = fieldPair.columnName;
            String columnType        = fieldPair.columnType;
            String propertyName      = fieldPair.propertyName;
            String propertyType      = fieldPair.propertyType;
            String propertyClassName = fieldPair.propertyClassName;

            Column column = new Column();
            column.title     = "'"+fieldPair.getDisplayName()+"'";
            column.dataIndex = "'"+fieldPair.getPropertyName()+"'";

            if ("DATETIME".equals(columnType)) {
                column.width = 170;
                column.render = "(val) => { return val ? moment(val).format('YYYY-MM-DD HH:mm:ss') : '' }";
            }

            if ("DATE".equals(columnType)) {
                column.width = 170;
                column.render = "(val) => { return val ? moment(val).format('YYYY-MM-DD') : '' }";
            }

            if(isNotEmpty(fieldPair.filters)) {

                column.filteredValue = "filteredInfo."+fieldPair.getPropertyName()+" || null";
                column.filterMultiple = false;
                column.filters = fieldPair.filters;

                StringBuffer renderBuf = new StringBuffer();
                renderBuf.append("(").append(fieldPair.getPropertyName()).append(") => {");
                renderBuf.append("switch (").append(fieldPair.getPropertyName()).append(") {");
                for(Filter filter: column.filters) {
                    renderBuf.append("case ").append(filter.value).append(":")
                            .append("return ").append(filter.text).append("; ");
                }
                renderBuf.append("}}");

                column.render  = renderBuf.toString();

            }else if(column.dataIndex.contains("image") || column.dataIndex.contains("img") || column.dataIndex.contains("icon")) {
                StringBuffer renderBuf = new StringBuffer();
                renderBuf.append("(").append(fieldPair.getPropertyName()).append(") => {")
                        .append("return <img src={").append(fieldPair.getPropertyName()).append("} style={{width: '80px', height: '80px'}}/>").append("}");
                column.render = renderBuf.toString();

            }else if(fieldPair.isIndex) {
                //System.out.println(fieldPair.toString());
                //getInputTitle = (title, index, pagination, filteredInfo
                StringBuffer renderBuf = new StringBuffer();
                renderBuf.append("getInputTitle(")
                                .append(column.title).append(",")
                                .append(column.dataIndex).append(",")
                                .append("pagination, filteredInfo").append(")").toString();

                if(column.render !=null) {
                    column.render = column.render + ", ..." + renderBuf.toString();
                }else{
                    column.render = renderBuf.toString();
                }
            }
            columns.add(column);
        }

        return columns.toString().replaceAll("\"", "").replaceAll("render:getInputTitle", "...getInputTitle");
//        bw.write("const columns = "+columns.toString().replaceAll("\"", ""));
//        bw.close();
//        logger.error("成功生成文件"+ AutocodeHelper.antdesignPath+ Table2Domain.domainName+"Columns.jsx");
    }

    public static class Column {
        @Override
        public String toString() {
            return new Gson().toJson(this);
        }

        public String title;

        public String dataIndex;

        public Integer width = 100;

        public Boolean filterMultiple;

        public String filteredValue;

        public List<Filter> filters;

        public String render;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDataIndex() {
            return dataIndex;
        }

        public void setDataIndex(String dataIndex) {
            this.dataIndex = dataIndex;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Boolean getFilterMultiple() {
            return filterMultiple;
        }

        public void setFilterMultiple(Boolean filterMultiple) {
            this.filterMultiple = filterMultiple;
        }

        public String getFilteredValue() {
            return filteredValue;
        }

        public void setFilteredValue(String filteredValue) {
            this.filteredValue = filteredValue;
        }

        public List<Filter> getFilters() {
            return filters;
        }

        public void setFilters(List<Filter> filters) {
            this.filters = filters;
        }

        public String getRender() {
            return render;
        }

        public void setRender(String render) {
            this.render = render;
        }


    }
}
