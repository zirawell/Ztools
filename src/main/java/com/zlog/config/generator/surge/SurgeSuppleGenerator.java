package com.zlog.config.generator.surge;

import com.zlog.config.generator.constants.ConfigGeneratorConstants;
import com.zlog.config.generator.utils.ConfigGeneratorUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 用于拉取图标及生成注释
 * @Author: Dark Wang
 * @Create: 2025/5/9 17:07
 **/
public class SurgeSuppleGenerator {
    private static Set<String> SKIP_APP_SET = null;
    private static String APP_ICON_PATH = ConfigGeneratorConstants.ICON_DIRECTORY
                                        + ConfigGeneratorConstants.FILE_SEPARATOR
                                        + ConfigGeneratorUtils.capitalizeFirstLetter(ConfigGeneratorConstants.APP_SIGN);
    public static void main(String[] args) {
        generateCommentsAndIconsForAll();
    }

    public static List<Map<String,String>> initData(){
        SKIP_APP_SET = Arrays.stream(ConfigGeneratorConstants.SKIP_APP).collect(Collectors.toSet());
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
                    String fileName = (filePath.substring(filePath.lastIndexOf("/")+1)).replace(ConfigGeneratorConstants.SURGE_MODULE_SIGN,"");
                    map.put("filePath", filePath);
                    map.put("fileName", fileName);
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
        // 初始化数据
        List<Map<String,String>> dataList = initData();
        int totalCount = dataList.size();
        int failCount = 0;
        // 遍历集合，生成注释及图标
        for(Map<String,String> dataMap:dataList){
            String type = dataMap.get("type");
            String filePath = dataMap.get("filePath");
            String fileName = dataMap.get("fileName");
            String appName = dataMap.get("appName");
            if(type.equals("app") && !SKIP_APP_SET.contains(fileName)){
                if (generateCommentsAndIconForApp(filePath, fileName, appName)){
                    failCount++;
                }
            }
        }
        System.out.println("总计" + totalCount + "个App, 共计生成" + (totalCount-failCount) + "个app的注释及图标， 失败" + failCount + "个");

    }

    /**
     * 生成app的注释及图标
     * @param surgeModulePath 需要生成注释的Surge模块文件路径
     * @param iconName Surge模块文件名称->需要生成的icon名称
     * @param appName App名称
     * @return boolean 生成成功返回true,失败false
     */
    public static boolean generateCommentsAndIconForApp(String surgeModulePath, String iconName, String appName){
        String downloadIconPath = APP_ICON_PATH + ConfigGeneratorConstants.FILE_SEPARATOR + iconName + ConfigGeneratorConstants.IMAGE_SIGN;
        boolean resultFlag = false;

        return resultFlag;
    }


}
