package com.tilitili.common.mysqlautocode.client;


import com.tilitili.common.mysqlautocode.AutocodeHelper;
import com.tilitili.common.mysqlautocode.server.Table2Domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SimpleTableAutocode {

    private static Logger logger = LoggerFactory.getLogger(SimpleTableAutocode.class);

    public static void run() throws IOException {

        File directory = new File(AutocodeHelper.antdesignPath+ Table2Domain.domainName+File.separator);
        if(!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(AutocodeHelper.antdesignPath+ Table2Domain.domainName+File.separator+"index.jsx");
        if(file.exists()) {
            file.delete();
        }

        OutputStream outStream = new FileOutputStream(file, true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outStream));

        BufferedReader br = new BufferedReader(new FileReader(new File(AutocodeHelper.antdesignPath+"table.template")));
        StringBuffer antDesignTableTemplate = new StringBuffer();

        String line ="";
        while ((line = br.readLine()) !=null) {
            antDesignTableTemplate.append(line).append("\n");
        }
        br.close();

        String columns = ColumnAutocode.run();

        bw.write(antDesignTableTemplate.toString().replaceAll("TableNameColumns", columns).replaceAll("TableName", Table2Domain.domainName));
        bw.close();
        logger.error("成功生成文件"+ AutocodeHelper.antdesignPath+ Table2Domain.domainName+File.separator+"index.jsx");
    }
}
