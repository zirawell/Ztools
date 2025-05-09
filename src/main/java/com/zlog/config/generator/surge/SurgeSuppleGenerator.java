package com.zlog.config.generator.surge;

import com.zlog.config.generator.constants.ConfigGeneratorConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @Description: 用于拉取图标及生成注释
 * @Author: Dark Wang
 * @Create: 2025/5/9 17:07
 **/
public class SurgeSuppleGenerator {
    public static void main(String[] args) {
        generateCommentsAndIconsForAll();
    }

    public static List<Map<String,String>> initData(){
        List<Map<String,String>> resultList = new ArrayList<>();
        File dicFile = new File(ConfigGeneratorConstants.REF_DIC_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(dicFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#!") && line.contains(",")) {
                    Map<String,String> map = new HashMap<>();
                    String[] split = line.split(",");
                    File dirPath = new File(split[0]);
                    String filePath = Objects.requireNonNull(dirPath.listFiles())[0].getAbsolutePath();
                    map.put("filePath", filePath);
                    map.put("fileName", filePath.substring(filePath.lastIndexOf("/")+1));
                    map.put("type",split[1]);
                    map.put("appName",split[2]);
                    resultList.add(map);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    public static void generateCommentsAndIconsForAll(){
        List<Map<String,String>> dataList = initData();
        System.out.println(dataList.size());
    }

    public static void generateCommentsAndIconForApp(){

    }


}
