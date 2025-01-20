package com.zlog.config.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 图标订阅库生成器
 * @Author: Dark Wang
 * @Create: 2025/2/7 10:40
 **/
public class IconGalleryGenerator {
    public static ArrayList<String> allIconPaths = new ArrayList<>();
    public static HashSet<String> appIconPaths = new HashSet<>();
    public static HashSet<String> countryIconPaths = new HashSet<>();
    public static HashSet<String> countryRIconPaths = new HashSet<>();
    public static HashSet<String> countrySIconPaths = new HashSet<>();
    public static HashSet<String> filterIconPaths = new HashSet<>();
    public static HashSet<String> providerIconPaths = new HashSet<>();
    // Icon根目录
    public static final String ICON_PATH = ConfigGeneratorConstants.ICON_DIRECTORY;

    /**
     * 获取图标文件路径
     *
     * @param file 图标文件存放目录
     */
    static void getIconFilePaths(File file) {
        if (!file.exists()) {
            return;
        }
        File[] subFiles = file.listFiles();
        if (null != subFiles) {
            for (File subFile : subFiles) {
                // 删除无用文件
                if (subFile.getAbsolutePath().endsWith(ConfigGeneratorConstants.MACOS_FILE_SIGN)) {
                    if (subFile.delete()) {
                        System.out.println(subFile.getAbsolutePath() + "is deleted!");
                    }
                }
                String currentFilePath = subFile.getAbsolutePath();
                // 获取App图标路径
                if (subFile.getAbsolutePath().contains(
                        ConfigGeneratorUtils.capitalizeFirstLetter(ConfigGeneratorConstants.APP_SIGN) + ConfigGeneratorConstants.FILE_SEPARATOR)
                        && subFile.getAbsolutePath().endsWith(ConfigGeneratorConstants.IMAGE_SIGN)) {
                    appIconPaths.add(currentFilePath);
                    // 获取国家图标路径
                } else if (subFile.getAbsolutePath().contains(
                        ConfigGeneratorUtils.capitalizeFirstLetter(ConfigGeneratorConstants.COUNTRY_SIGN) + ConfigGeneratorConstants.FILE_SEPARATOR)
                        && subFile.getAbsolutePath().endsWith(ConfigGeneratorConstants.IMAGE_SIGN)) {
                    countryIconPaths.add(currentFilePath);
                    if (subFile.getAbsolutePath().contains("/R/")) {
                        countryRIconPaths.add(currentFilePath);
                    } else if (subFile.getAbsolutePath().contains("/S/")) {
                        countrySIconPaths.add(currentFilePath);
                    }
                    // 获取过滤器图标路径
                } else if (subFile.getAbsolutePath().contains(
                        ConfigGeneratorUtils.capitalizeFirstLetter(ConfigGeneratorConstants.FILTER_SIGN) + ConfigGeneratorConstants.FILE_SEPARATOR)
                        && subFile.getAbsolutePath().endsWith(ConfigGeneratorConstants.IMAGE_SIGN)) {
                    filterIconPaths.add(currentFilePath);
                    // 获取供应商图标路径
                } else if (subFile.getAbsolutePath().contains(
                        ConfigGeneratorUtils.capitalizeFirstLetter(ConfigGeneratorConstants.PROVIDER_SIGN) + ConfigGeneratorConstants.FILE_SEPARATOR)
                        && subFile.getAbsolutePath().endsWith(ConfigGeneratorConstants.IMAGE_SIGN)) {
                    providerIconPaths.add(currentFilePath);
                    // 目录则递归调用
                } else if (subFile.isDirectory()) {
                    getIconFilePaths(subFile);
                }
            }
        }
    }

    /**
     * 生成订阅库json文件
     */
    static void generateJsonFile() {
        getIconFilePaths(new File(ConfigGeneratorConstants.ICON_DIRECTORY));
        allIconPaths.addAll(appIconPaths);
        allIconPaths.addAll(countryIconPaths);
        allIconPaths.addAll(filterIconPaths);
        allIconPaths.addAll(providerIconPaths);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Map<String, Object> allIconMap = new HashMap<>();
        Map<String, Object> appIconMap = new HashMap<>();
        Map<String, Object> countryIconMap = new HashMap<>();
        Map<String, Object> countryRIconMap = new HashMap<>();
        Map<String, Object> countrySIconMap = new HashMap<>();
        Map<String, Object> filterIconMap = new HashMap<>();
        Map<String, Object> providerIconMap = new HashMap<>();

        if(ConfigGeneratorUtils.checkIsChanged(ICON_PATH + "/App")) {
            appIconMap.put("name", "App Icons");
            putGeneralData(appIconMap);
            appIconMap.put("icons", getIconPathList(appIconPaths));
            String appIconJson = gson.toJson(appIconMap);
            ConfigGeneratorUtils.writeToFile(new File(ICON_PATH + "/App/app.json"), appIconJson, false);
        }

        if(ConfigGeneratorUtils.checkIsChanged(ICON_PATH + "/Country/R")) {
            countryRIconMap.put("name", "Country Round Icons");
            putGeneralData(countryRIconMap);
            countryRIconMap.put("icons", getIconPathList(countryRIconPaths));
            String countryRIconJson = gson.toJson(countryRIconMap);
            ConfigGeneratorUtils.writeToFile(new File(ICON_PATH + "/Country/R/country.json"), countryRIconJson, false);
        }

        if(ConfigGeneratorUtils.checkIsChanged(ICON_PATH + "/Country/S")) {
            countrySIconMap.put("name", "Country Square Icons");
            putGeneralData(countrySIconMap);
            countrySIconMap.put("icons", getIconPathList(countrySIconPaths));
            String countrySIconJson = gson.toJson(countrySIconMap);
            ConfigGeneratorUtils.writeToFile(new File(ICON_PATH + "/Country/S/country.json"), countrySIconJson, false);
        }

        if(ConfigGeneratorUtils.checkIsChanged(ICON_PATH + "/Country")) {
            countryIconMap.put("name", "Country Icons");
            putGeneralData(countryIconMap);
            countryIconMap.put("icons", getIconPathList(countryIconPaths));
            String countryIconJson = gson.toJson(countryIconMap);
            ConfigGeneratorUtils.writeToFile(new File(ICON_PATH + "/Country/country.json"), countryIconJson, false);
        }

        if(ConfigGeneratorUtils.checkIsChanged(ICON_PATH + "/Filter")) {
            filterIconMap.put("name", "Filter Icons");
            putGeneralData(filterIconMap);
            filterIconMap.put("icons", getIconPathList(filterIconPaths));
            String filterIconJson = gson.toJson(filterIconMap);
            ConfigGeneratorUtils.writeToFile(new File(ICON_PATH + "/Filter/filter.json"), filterIconJson, false);
        }

        if(ConfigGeneratorUtils.checkIsChanged(ICON_PATH + "/Provider")) {
            providerIconMap.put("name", "Provider Icons");
            putGeneralData(filterIconMap);
            providerIconMap.put("icons", getIconPathList(providerIconPaths));
            String providerIconJson = gson.toJson(providerIconMap);
            ConfigGeneratorUtils.writeToFile(new File(ICON_PATH + "/Provider/provider.json"), providerIconJson, false);
        }

        if(ConfigGeneratorUtils.checkIsChanged(ICON_PATH)) {
            allIconMap.put("name", "All Icons");
            putGeneralData(allIconMap);
            allIconMap.put("icons", getIconPathList(new LinkedHashSet<>(allIconPaths)));
            String allIconJson = gson.toJson(allIconMap);
            ConfigGeneratorUtils.writeToFile(new File(ICON_PATH + "/icon.json"), allIconJson, false);
        }


    }

    /**
     * 组装通用数据
     *
     * @param map 待组装的map对象
     */
    private static void putGeneralData(Map<String, Object> map) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        map.put("description", "collected by zirawell");
        map.put("time", simpleDateFormat.format(new Date()));
    }

    /**
     * 获取图标路径列表
     *
     * @param iconPaths 图标路径集合
     * @return Object
     */
    private static Object getIconPathList(HashSet<String> iconPaths) {
        List<Map<String, String>> iconPathsList = new ArrayList<>();
        for (String iconPath : iconPaths) {
            Map<String, String> iconPathMap = new HashMap<>();
            iconPathMap.put("name", ConfigGeneratorUtils.getFileName(iconPath));
            iconPathMap.put("url", ConfigGeneratorUtils.convertFilePathToRawUrl(iconPath));
            iconPathsList.add(iconPathMap);
        }
        return iconPathsList;
    }

    public static void main(String[] args) throws IOException {
        generateJsonFile();


    }


}
