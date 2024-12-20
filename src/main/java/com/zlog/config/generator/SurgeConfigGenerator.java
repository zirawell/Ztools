package com.zlog.config.generator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * @Description: 广告拦截配置生成器
 * @Author: Dark Wang
 * @Create: 2023/5/11 16:12
 **/
public class SurgeConfigGenerator {
    private static final Logger logger = LoggerFactory.getLogger("SurgeConfigGenerator.class");
    public static File quanXRuleDir = new File(ConfigGeneratorConstants.QUANX_RULE_DIRECTORY);
    public static HashSet<String> appPaths = new HashSet<>();
    public static HashSet<String> wechatAppletPaths = new HashSet<>();
    public static HashSet<String> alipayAppletPaths = new HashSet<>();
    public static HashSet<String> webPaths = new HashSet<>();
    public static HashMap<String, StringBuilder> headerMap = new HashMap<>();
    public static HashMap<String, StringBuilder> contentMap = new HashMap<>();
    public static HashSet<String> generatedConfigPaths = new HashSet<>();

    /**
     * 获取原始文件路径，目前使用QuanX规则作为原始文件
     *
     * @param file 原始文件
     */
    static void getOriginalFilePaths(File file) {
        if (file.exists()) {
            File[] subFiles = file.listFiles();
            if (null != subFiles) {
                boolean appPathFlag = false;
                boolean wechatAppletPathFlag = false;
                boolean alipayAppletPathFlag = false;
                boolean webPathFlag = false;
                HashSet<String> tmpAppPaths = new HashSet<>();
                for (File subFile : subFiles) {
                    if (!subFile.getAbsolutePath().contains(".DS_Store")) {
                        tmpAppPaths.add(subFile.getAbsolutePath());
                    }else{
                        if(subFile.delete()){
                            System.out.println(subFile.getAbsolutePath() + "is deleted!");
                        }
                    }
                    // 寻找含有README.md的Adblock目录
                    if (subFile.getName().contains(ConfigGeneratorConstants.README_SIGN)
                            && subFile.getAbsolutePath().contains(ConfigGeneratorConstants.ADBLOCK_SIGN)) {
                        // 去除README.md
                        tmpAppPaths.remove(subFile.getAbsolutePath());
                        // 收录App
                        if (subFile.getAbsolutePath().contains(ConfigGeneratorUtils.capitalizeFirstLetter(ConfigGeneratorConstants.APP_SIGN) + ConfigGeneratorConstants.FILE_SEPARATOR)
                                && subFile.getAbsolutePath().split(ConfigGeneratorConstants.FILE_SEPARATOR).length == ConfigGeneratorConstants.APP_LEVEL) {
                            appPathFlag = true;
                            // 收录微信小程序
                        } else if (subFile.getAbsolutePath().contains(ConfigGeneratorUtils.capitalizeFirstLetter(ConfigGeneratorConstants.WECHAT_SIGN) + ConfigGeneratorConstants.FILE_SEPARATOR)
                                && subFile.getAbsolutePath().split(ConfigGeneratorConstants.FILE_SEPARATOR).length == ConfigGeneratorConstants.APPLET_LEVEL) {
                            wechatAppletPathFlag = true;
                            // 收录支付宝小程序
                        } else if (subFile.getAbsolutePath().contains(ConfigGeneratorUtils.capitalizeFirstLetter(ConfigGeneratorConstants.ALIPAY_SIGN) + ConfigGeneratorConstants.FILE_SEPARATOR)
                                && subFile.getAbsolutePath().split(ConfigGeneratorConstants.FILE_SEPARATOR).length == ConfigGeneratorConstants.APPLET_LEVEL) {
                            alipayAppletPathFlag = true;
                            // 收录网站
                        } else if (subFile.getAbsolutePath().contains(ConfigGeneratorUtils.capitalizeFirstLetter(ConfigGeneratorConstants.WEB_SIGN) + ConfigGeneratorConstants.FILE_SEPARATOR)
                                && subFile.getAbsolutePath().split(ConfigGeneratorConstants.FILE_SEPARATOR).length == ConfigGeneratorConstants.WEB_LEVEL) {
                            webPathFlag = true;
                        }
                    } else if (subFile.isDirectory()) {
                        getOriginalFilePaths(subFile);
                    }
                }
                // 将各路径放到对应的集合中
                // 各app路径
                if (appPathFlag) {
                    appPaths.addAll(tmpAppPaths);
                    // 微信小程序路径
                } else if (wechatAppletPathFlag) {
                    wechatAppletPaths.addAll(tmpAppPaths);
                    // 支付宝小程序路径
                } else if (alipayAppletPathFlag) {
                    alipayAppletPaths.addAll(tmpAppPaths);
                    // 网站路径
                } else if (webPathFlag) {
                    webPaths.addAll(tmpAppPaths);
                }
            }
        }
    }

    public static void generateConfigs() {
        try {
            getOriginalFilePaths(quanXRuleDir);
            generateConfigDirectory(appPaths, wechatAppletPaths, alipayAppletPaths, webPaths);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 生成配置文件的父目录
     *
     * @param args 原始配置文件路径集合
     */
    @SafeVarargs
    private static void generateConfigDirectory(HashSet<String>... args) throws IOException {
        for (HashSet<String> arg : args) {
            for (String path : arg) {
                File file = getSurgeModuleFile(path);
                // 若父目录不存在则创建
                if (!file.exists()) {
                    if (file.mkdirs()) {
                        generateConfigFile(path);
                    } else {
                        throw new IOException("Failed to create directory: " + file.getAbsolutePath());
                    }
                    // 若父目录存在则生成配置文件
                } else {
                    generateConfigFile(path);
                }
            }
        }
    }

    /**
     * QuanX路径转换为Surge路径
     *
     * @param path QuanX路径
     * @return String Surge路径
     */
    private static String convertIntoSurgePath(String path) {
        path = path.replace(ConfigGeneratorConstants.QUANX_SIGN, ConfigGeneratorConstants.SURGE_SIGN);
        if (path.endsWith(ConfigGeneratorConstants.LIST_SIGN) || path.endsWith(ConfigGeneratorConstants.CONF_SIGN)) {
            path = path.replace(ConfigGeneratorConstants.LIST_SIGN, ConfigGeneratorConstants.SURGE_MODULE_SIGN);
            path = path.replace(ConfigGeneratorConstants.CONF_SIGN, ConfigGeneratorConstants.SURGE_MODULE_SIGN);
            path = path.replace(ConfigGeneratorConstants.FILE_SEPARATOR + ConfigGeneratorConstants.FILTER_SIGN, "");
            path = path.replace(ConfigGeneratorConstants.FILE_SEPARATOR + ConfigGeneratorConstants.REWRITE_SIGN, "");
        }
        return path;
    }

    /**
     * 获取Surge配置文件
     * @param path QuanX路径
     * @return File
     */
    private static File getSurgeModuleFile(String path){
        return new File(convertIntoSurgePath(path));
    }

    /**
     * 获取特殊处理标识
     * @param path QuanX路径
     * @return boolean
     */
    private static boolean getSpecialFlag(String path) {
        for(String skipApp : ConfigGeneratorConstants.SKIP_APP){
            if(StringUtils.containsIgnoreCase(path,skipApp)){
                return true;
            }
        }
        return false;
    }

    /**
     * 通过QuanX配置文件生成Surge配置文件
     *
     * @param path QuanX配置文件父目录
     */
    private static void generateConfigFile(String path) {
        File originDir = new File(path);
        File[] subFiles = originDir.listFiles();
        if (null != subFiles) {
            for (File subFile : subFiles) {
                generateSurgeConfigFile(subFile);
                if (subFile.getAbsolutePath().contains(ConfigGeneratorConstants.REWRITE_SIGN)) {
                    processRewriteFile(subFile);
                } else if (subFile.getAbsolutePath().contains(ConfigGeneratorConstants.FILTER_SIGN)) {
                    processFilterFile(subFile);
                }
            }
            for (String generatedConfigPath : generatedConfigPaths) {
                StringBuilder fileContent = new StringBuilder();
                StringBuilder header = headerMap.get(generatedConfigPath);
                StringBuilder content = contentMap.get(generatedConfigPath);
                fileContent.append(null == header ? "" : header).append(System.lineSeparator()).append(null == content ? "" : content);
                try {
                    ConfigGeneratorUtils.writeToFile(new File(generatedConfigPath), fileContent.toString(), false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    /**
     * 生成Surge配置空文件
     * @param subFile QuanX配置文件目录或文件
     */
    private static void generateSurgeConfigFile(File subFile) {
        if(subFile.isDirectory()){
            if (null != subFile.listFiles() && Objects.requireNonNull(subFile.listFiles()).length != 0) {
                generateSurgeConfigFile(Objects.requireNonNull(subFile.listFiles())[0]);
            }
        }else{
            File targetFile = getSurgeModuleFile(subFile.getAbsolutePath());
            try {
                if(replaceFile(targetFile)){
                    generatedConfigPaths.add(targetFile.getAbsolutePath());
                }
            } catch (IOException e) {
                System.out.println("Failed to generate file: " + targetFile.getAbsolutePath());
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 处理重写文件
     *
     * @param subFile 重写文件
     */
    private static void processRewriteFile(File subFile) {
        ///Users/zirawell/Git/R-Store/Rule/QuanX/Adblock/Applet/Wechat/#/rewrite/wechatAppletGeneralRewrite.conf
        ///Users/zirawell/Git/R-Store/Rule/QuanX/Adblock/Applet/Wechat/X/享道出行/rewrite
        if(subFile.getAbsolutePath().endsWith(ConfigGeneratorConstants.CONF_SIGN)){
            //processRewriteContent(subFile);
        }else{
            if (null != subFile.listFiles() && Objects.requireNonNull(subFile.listFiles()).length != 0) {
                processRewriteFile(Objects.requireNonNull(subFile.listFiles())[0]);
            }
        }

    }

    /**
     * 处理重写文件内容
     * @param subFile 原始重写文件
     */
    private static void processRewriteContent(File subFile) {
        // 目标文件路径
        File targetFile;
        targetFile = getSurgeModuleFile(subFile.getAbsolutePath());
        try {
            // 1. 读取源文件内容
            StringBuilder fileContent = new StringBuilder();
            StringBuilder commentContent = new StringBuilder();
            StringBuilder currentHeader = headerMap.get(targetFile.getAbsolutePath());
            StringBuilder urlRewriteContent = new StringBuilder();
            StringBuilder headerRewriteContent = new StringBuilder();
            StringBuilder bodyRewriteContent = new StringBuilder();
            StringBuilder mapLocalContent = new StringBuilder();
            StringBuilder scriptContent = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new FileReader(subFile, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().startsWith("#")) {
                        if(null != currentHeader && !currentHeader.toString().trim().contains(line.trim())) {
                            commentContent.append(line).append(System.lineSeparator());
                        }
                    } else {
                        fileContent.append(line).append(System.lineSeparator());
                    }
                }
                fileContent.insert(0, ("[Rule]\n"));
                saveToMap(headerMap, targetFile.getAbsolutePath(), commentContent.toString());
            }





        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void saveToMap(HashMap<String, StringBuilder> map, String key, String content) {
        if(null != map) {
            if (null != map.get(key)) {
                StringBuilder tmpSb = map.get(key);
                tmpSb.append(content);
                map.put(key, tmpSb);
            } else {
                map.put(key, new StringBuilder(content));
            }
        }
    }

    /**
     * 处理过滤器文件
     *
     * @param subFile 原始过滤器文件
     */
    private static void processFilterFile(File subFile) {
        if (subFile.getAbsolutePath().endsWith(ConfigGeneratorConstants.LIST_SIGN)) {
            processFilterContent(subFile);
        } else {
            if (null != subFile.listFiles() && Objects.requireNonNull(subFile.listFiles()).length != 0) {
                processFilterFile(Objects.requireNonNull(subFile.listFiles())[0]);
            }
        }
    }

    /**
     * 处理过滤器内容
     *
     * @param subFile 原始过滤器文件
     */
    private static void processFilterContent(File subFile) {
        // 目标文件路径
        File targetFile = getSurgeModuleFile(subFile.getAbsolutePath());
        // 需要替换的内容
        String oldContent = "host";
        // 替换成的内容
        String newContent = "domain";
        try {
            // 1. 读取源文件内容
            StringBuilder fileContent = new StringBuilder();
            StringBuilder commentContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(subFile, StandardCharsets.UTF_8))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.trim().startsWith("#")) {
                        commentContent.append(line).append(System.lineSeparator());
                    } else {
                        fileContent.append(line).append(System.lineSeparator());
                    }
                }
                fileContent.insert(0, ("[Rule]\n"));
                saveToMap(headerMap, targetFile.getAbsolutePath(), commentContent.toString());
            }
            // 2. 替换指定内容
            String modifiedContent = fileContent.toString().replace(oldContent, newContent);
            saveToMap(contentMap, targetFile.getAbsolutePath(), modifiedContent);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static boolean replaceFile(File targetFile) throws IOException {
        if(getSpecialFlag(targetFile.getAbsolutePath())){
            return false;
        }
        return (!targetFile.exists() || targetFile.delete()) && targetFile.createNewFile();
    }


    /**
     * 打印路径集合元素及数量
     *
     * @param paths 路径集合
     * @param tips  打印提示
     */
    private static void printPathCount(HashSet<String> paths, String tips) {
        for (String path : paths) {
            System.out.println(path);
        }
        System.out.println(tips + ":" + paths.size());
    }


    public static void main(String[] args) throws IOException {
        getOriginalFilePaths(quanXRuleDir);
        generateConfigDirectory(appPaths, wechatAppletPaths, alipayAppletPaths, webPaths);
    }
}
