package com.zlog;

import java.io.*;
import java.util.HashSet;

/**
 * @Description:
 * @Author: Dark Wang
 * @Create: 2024/8/27 13:07
 **/
public class QuanXRewriteConfigCompare {
    public static final String UNDER_CHECK_FILES = "/Users/zirawell/Git/dw-configs/QuantumultX/Rewrite/Rewrite.conf;/Users/zirawell/Git/dw-configs/QuantumultX/Rewrite/Advertising.conf";
    public static final String REFERENCE_FILE = "/Users/zirawell/Git/R-Store/Rule/QuanX/Adblock/All/rewrite/allAdRewrite.conf";
    public static final String OUTPUT_FILE = "/Users/zirawell/Downloads/tmp.txt";
    public static HashSet<String> REWRITE_SET = new HashSet<>();


    public static void main(String[] args) {
        outputFile(OUTPUT_FILE,new StringBuffer(),false);
        compareFile();
    }

    private static void compareFile() {
        File referenceFile = new File(REFERENCE_FILE);
        String[] underCheckFilePaths;
        if(UNDER_CHECK_FILES.contains(";")){
            underCheckFilePaths = UNDER_CHECK_FILES.split(";");

        }else{
            underCheckFilePaths = new String[]{UNDER_CHECK_FILES};
        }
        for(String underCheckFilePath:underCheckFilePaths){
            File underCheckFile = new File(underCheckFilePath);
        try (BufferedReader rfReader = new BufferedReader(new FileReader(referenceFile));
             BufferedReader ucReader = new BufferedReader(new FileReader(underCheckFile))
        ) {
            String line;
            while ((line = rfReader.readLine()) != null) {
                if (line.contains("^https")) {
                    String rewriteStr = line.substring(0,line.indexOf(" url"));
                    REWRITE_SET.add(rewriteStr);
                }
            }
            StringBuffer sb = new StringBuffer();
            while ((line = ucReader.readLine()) != null) {
                if (line.contains("^https")) {
                    String tmpStr = line.substring(0,line.indexOf(" url"));
                    if(REWRITE_SET.contains(tmpStr)){
                        continue;
                    }else{
                        REWRITE_SET.add(tmpStr);
                    }
                }
                sb.append(line).append(System.lineSeparator());
            }
            outputFile(OUTPUT_FILE,sb,true);



        } catch (IOException e) {
            e.printStackTrace();
        }

        }



    }

     private static void outputFile(String filePath, StringBuffer sb, boolean appendFlag) {
        FileOutputStream out = null;
        try {
            File file = new File(filePath);
            checkFileExists(filePath);
            out = new FileOutputStream(file, appendFlag);
            out.write(sb.toString().getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void checkFileExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
    }
}
