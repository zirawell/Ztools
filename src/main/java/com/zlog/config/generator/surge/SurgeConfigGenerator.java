package com.zlog.config.generator.surge;

import com.zlog.config.generator.constants.ConfigGeneratorConstants;
import com.zlog.config.generator.utils.ConfigGeneratorUtils;
import org.apache.commons.lang3.ArrayUtils;
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
                    if (!subFile.getAbsolutePath().contains(ConfigGeneratorConstants.MACOS_FILE_SIGN)) {
                        tmpAppPaths.add(subFile.getAbsolutePath());
                    }else{
                        if(subFile.delete()){
                            System.out.println(subFile.getAbsolutePath() + " has been deleted!");
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

    /**
     * 生成配置文件
     */
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
     * 通过Rewrite文件路径获取Filter文件
     * @param path QuanX Rewrite文件路径
     * @return File Filter文件
     */
    private static File getFilterFile(String path){
        return new File(path.replace(ConfigGeneratorConstants.REWRITE_SIGN, ConfigGeneratorConstants.FILTER_SIGN)
                            .replace(ConfigGeneratorConstants.CONF_SIGN,ConfigGeneratorConstants.LIST_SIGN));
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
        String generatedConfigPath;
        File[] subFiles = originDir.listFiles();
        if (null != subFiles) {
            if(path.contains(ConfigGeneratorConstants.ADBLOCK_SIGN)) {
                generatedConfigPath = generateSurgeConfigFileForAdblock(originDir);
                File rewriteDir = new File(path + ConfigGeneratorConstants.FILE_SEPARATOR + ConfigGeneratorConstants.REWRITE_SIGN);
                File filterDir = new File(path + ConfigGeneratorConstants.FILE_SEPARATOR + ConfigGeneratorConstants.FILTER_SIGN);
                boolean stopFlag = false;
                // 打印文件路径
                //System.out.println(path);
                if (rewriteDir.exists() && Objects.requireNonNull(rewriteDir.listFiles()).length > 0) {
                    stopFlag = processRewriteContent(Objects.requireNonNull(rewriteDir.listFiles())[0]);
                }
                if (filterDir.exists() && Objects.requireNonNull(filterDir.listFiles()).length > 0 && !stopFlag) {
                    processFilterContent(Objects.requireNonNull(filterDir.listFiles())[0], false);
                }
                // 输出Surge配置文件
                writeSurgeConfigFile(generatedConfigPath);

            }else{
                for (File subFile : subFiles) {
                    if (subFile.isDirectory()) {
                        generateConfigFile(subFile.getAbsolutePath());
                    } else {
                        generatedConfigPath = generateSurgeConfigFileForAdblock(subFile);
                        String pathName = subFile.getAbsolutePath();
                        if (pathName.endsWith(ConfigGeneratorConstants.LIST_SIGN)) {
                            processFilterContent(subFile, true);
                        }else if(pathName.endsWith(ConfigGeneratorConstants.CONF_SIGN)){
                            processRewriteContent(subFile);
                        }
                        writeSurgeConfigFile(generatedConfigPath);
                    }

                }
            }
        }

    }

    /**
     * 写入Surge配置文件
     * @param generatedConfigPath Surge配置文件路径
     */
    private static void writeSurgeConfigFile(String generatedConfigPath) {
        if (!generatedConfigPaths.contains(generatedConfigPath)) {
            headerMap.clear();
            contentMap.clear();
            return;
        }
        StringBuilder fileContent = new StringBuilder();
        StringBuilder header = headerMap.get(generatedConfigPath);
        StringBuilder content = contentMap.get(generatedConfigPath);
        fileContent.append(null == header ? "" : header).append(System.lineSeparator()).append(null == content ? "" : content);
        ConfigGeneratorUtils.writeToFile(new File(generatedConfigPath), fileContent.toString(), false);
        headerMap.clear();
        contentMap.clear();
    }

    /**
     * 生成Surge配置空文件
     * @param subFile QuanX配置文件目录或文件
     */
    private static String generateSurgeConfigFileForAdblock(File subFile) {
        File targetFile = null;
        if(subFile.isDirectory()){
            if (null != subFile.listFiles() && Objects.requireNonNull(subFile.listFiles()).length != 0) {
                return generateSurgeConfigFileForAdblock(Objects.requireNonNull(subFile.listFiles())[0]);
            }
        }else{
            targetFile = getSurgeModuleFile(subFile.getAbsolutePath());
            try {
                if(replaceFile(targetFile)){
                    generatedConfigPaths.add(targetFile.getAbsolutePath());
                }
            } catch (IOException e) {
                System.out.println("Failed to generate file: " + targetFile.getAbsolutePath());
                throw new RuntimeException(e);
            }

        }
        assert targetFile != null;
        return targetFile.getAbsolutePath();
    }



    /**
     * 处理重写文件内容
     * @param subFile 原始重写文件
     */
    private static boolean processRewriteContent(File subFile) {
        // 目标文件路径
        File targetFile;
        File filterFile;
        targetFile = getSurgeModuleFile(subFile.getAbsolutePath());
        filterFile = getFilterFile(subFile.getAbsolutePath());
        boolean flag = false;
        try {
            // 文件内容（不包含注释）
            StringBuilder fileContent = new StringBuilder();
            // 注释内容
            StringBuilder commentContent = new StringBuilder();
            // Rule内容
            StringBuilder ruleContent = new StringBuilder();
            // URLRewrite内容
            StringBuilder urlRewriteContent = new StringBuilder();
            // HeaderRewrite内容
            StringBuilder headerRewriteContent = new StringBuilder();
            // BodyRewrite内容
            StringBuilder bodyRewriteContent = new StringBuilder();
            // MapLocal内容
            StringBuilder mapLocalContent = new StringBuilder();
            // Script内容
            StringBuilder scriptContent = new StringBuilder();
            // hostname
            StringBuilder hostnameContent = new StringBuilder();
            // 处理filter文件
            if(filterFile.exists()){
                processFilterContent(filterFile, true);
                commentContent = headerMap.get(targetFile.getAbsolutePath());
                ruleContent = contentMap.get(targetFile.getAbsolutePath());
                flag = true;
            }
            // 读取源文件内容
            try (BufferedReader reader = new BufferedReader(new FileReader(subFile, StandardCharsets.UTF_8))) {
                String line;
                String lastLine = null;
                while ((line = reader.readLine()) != null) {
                    // 处理注释内容
                    if (line.startsWith("# >") || line.startsWith("#!")) {
                        if (null != commentContent && !commentContent.toString().trim().contains(line.trim())) {
                            line = processCommentContent(line);
                            commentContent.append(line).append(System.lineSeparator());
                        }
                    // 处理hostname
                    }else if(line.trim().startsWith("hostname")){
                        hostnameContent.append(ConfigGeneratorUtils.scriptRegexReplace(ConfigGeneratorConstants.QUANX_HOSTNAME_REGEX,ConfigGeneratorConstants.SURGE_HOSTNAME_REGEX,line));
                    // 处理重写内容
                    } else {
                        String type = ConfigGeneratorUtils.getQuanXRewriteKeywordForLine(line);
                        String convertedLine = convertLineContent(line, type);
                        if(null != lastLine && lastLine.contains("#") && !lastLine.contains("# >") && !lastLine.contains("#!")){
                            convertedLine = lastLine + "\n" + convertedLine;
                        }
                        if(null != convertedLine && !convertedLine.isEmpty()) {
                            if(ArrayUtils.contains(ConfigGeneratorConstants.URL_REWRITE_KEYWORDS,type)){
                                urlRewriteContent.append(convertedLine).append(System.lineSeparator());
                            }else if(ArrayUtils.contains(ConfigGeneratorConstants.HEADER_REWRITE_KEYWORDS,type)){
                                headerRewriteContent.append(convertedLine).append(System.lineSeparator());
                            }else if(ArrayUtils.contains(ConfigGeneratorConstants.BODY_REWRITE_KEYWORDS,type)){
                                bodyRewriteContent.append(convertedLine).append(System.lineSeparator());
                            }else if(ArrayUtils.contains(ConfigGeneratorConstants.MAP_LOCAL_KEYWORDS,type)){
                                mapLocalContent.append(convertedLine).append(System.lineSeparator());
                            }else if(ArrayUtils.contains(ConfigGeneratorConstants.SCRIPT_KEYWORDS,type)){
                                scriptContent.append(convertedLine).append(System.lineSeparator());
                            }
                        }
                        lastLine = line;
                    }

                }
                if(null != ruleContent && !ruleContent.toString().isEmpty()){
                    fileContent.append(ConfigGeneratorConstants.SURGE_RULE_AREA).append(ruleContent).append("\n");
                }
                if(!urlRewriteContent.toString().isEmpty()){
                    fileContent.append(ConfigGeneratorConstants.SURGE_URL_REWRITE_AREA).append(urlRewriteContent).append("\n");
                }
                if(!headerRewriteContent.toString().isEmpty()){
                    fileContent.append(ConfigGeneratorConstants.SURGE_HEADER_REWRITE_AREA).append(headerRewriteContent).append("\n");
                }
                if(!bodyRewriteContent.toString().isEmpty()){
                    fileContent.append(ConfigGeneratorConstants.SURGE_BODY_REWRITE_AREA).append(bodyRewriteContent).append("\n");
                }
                if(!mapLocalContent.toString().isEmpty()){
                    fileContent.append(ConfigGeneratorConstants.SURGE_MAP_LOCAL_AREA).append(mapLocalContent).append("\n");
                }
                if(!scriptContent.toString().isEmpty()){
                    fileContent.append(ConfigGeneratorConstants.SURGE_SCRIPT_AREA).append(scriptContent).append("\n");
                }
                if(!hostnameContent.toString().isEmpty()){
                    fileContent.append(ConfigGeneratorConstants.SURGE_HOSTNAME_AREA).append(hostnameContent).append("\n");
                }

            }

        headerMap.put(targetFile.getAbsolutePath(), commentContent);
        contentMap.put(targetFile.getAbsolutePath(), fileContent);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 处理文件头部注释内容
     *
     * @param line 行内容
     * @return 修改后的行内容
     */
    private static String processCommentContent(String line) {
        line = line.replace(ConfigGeneratorConstants.CONF_SIGN, ConfigGeneratorConstants.SURGE_MODULE_SIGN);
        line = line.replace(ConfigGeneratorConstants.LIST_SIGN, ConfigGeneratorConstants.SURGE_MODULE_SIGN);
        line = line.replace(ConfigGeneratorConstants.QUANX_SIGN, ConfigGeneratorConstants.SURGE_SIGN);
        return line;
    }

    /**
     * 根据关键字类型转换重写文件行内容
     * @param line 行内容
     * @param type 类型
     * @return String
     */
    private static String convertLineContent(String line, String type) {
        if(null == line || null == type){
            return null;
        }
        String prefix = "url ";
        switch (type) {
            case "request-body":
            case "response-body":
                line = ConfigGeneratorUtils.scriptRegexReplace(ConfigGeneratorConstants.QUANX_BODY_REWRITE_REGEX,ConfigGeneratorConstants.SURGE_BODY_REWRITE_REGEX, line);
                assert line != null;
                line = line
                        .replace("http-response-body","http-response")
                        .replace("http-request-body","http-request");
                break;
            case "reject-dict":
                line = line.replace(prefix + type, ConfigGeneratorConstants.SURGE_REJECT_DICT_STR);
                break;
            case "reject-200":
                line = line.replace(prefix + type, ConfigGeneratorConstants.SURGE_REJECT_200_STR);
                break;
            case "echo-response":
                line = ConfigGeneratorUtils.scriptRegexReplace(ConfigGeneratorConstants.QUANX_ECHO_RESPONSE_REGEX,ConfigGeneratorConstants.SURGE_ECHO_RESPONSE_REGEX,line);
                break;
            case "script-request-body":
            case "script-response-body":
                line = ConfigGeneratorUtils.scriptRegexReplace(ConfigGeneratorConstants.QUANX_JS_BODY_REGEX, ConfigGeneratorConstants.SURGE_JS_BODY_REGEX, line);
                assert line != null;
                line = line
                        .replace("type=http-script-response-body", "type=http-response")
                        .replace("type=http-script-request-body", "type=http-request");
                break;
            case "302":
                line = ConfigGeneratorUtils.scriptRegexReplace(ConfigGeneratorConstants.QUANX_302_REGEX,ConfigGeneratorConstants.SURGE_302_REGEX,line);
                break;
            case "reject":
                line = line.replace(prefix + type,ConfigGeneratorConstants.SURGE_REJECT_STR);
                break;
            case "script-response-header":
            case "script-request-header":
                line = ConfigGeneratorUtils.scriptRegexReplace(ConfigGeneratorConstants.QUANX_JS_HEADER_REGEX, ConfigGeneratorConstants.SURGE_JS_HEADER_REGEX, line);
                assert line != null;
                line = line
                        .replace("type=http-script-response-header", "type=http-response")
                        .replace("type=http-script-request-header", "type=http-request");
                break;
            case "url-and-header":
                line = ConfigGeneratorUtils.scriptRegexReplace(ConfigGeneratorConstants.QUANX_URL_AND_HEADER_REGEX, ConfigGeneratorConstants.SURGE_URL_AND_HEADER_REGEX, line);
                break;
            case "reject-img":
                line = line.replace(prefix + type, ConfigGeneratorConstants.SURGE_REJECT_IMG_STR);
                break;
            default:
                break;
        }
        return line;
    }

    /**
     * 保存内容到Map
     *
     * @param map     Map
     * @param key     key
     * @param content 内容
     */
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
     * 处理过滤器内容
     *
     * @param subFile 原始过滤器文件
     */
    private static void processFilterContent(File subFile, boolean stopFlag) {
        // 目标文件路径
        File targetFile = getSurgeModuleFile(subFile.getAbsolutePath());
        // 前缀集合
        HashSet<String> keySet = new HashSet<>();
        try {
            // 1. 读取源文件内容
            StringBuilder fileContent = new StringBuilder();
            StringBuilder commentContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(subFile, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("# >") || line.startsWith("#!")) {
                        line = processCommentContent(line);
                        commentContent.append(line).append(System.lineSeparator());
                    } else {
                        String key = line.trim().toLowerCase().split(",")[0];
                        keySet.add(key + ",");
                        if (key.contains("host")) {
                            line = line.toLowerCase().replace(", reject", ", REJECT, extended-matching, pre-matching");
                        } else {
                            line = line.replace(", reject", ", REJECT, pre-matching");
                        }

                        if (line.contains(",")){
                            line = line.replace(line.split(",")[2],line.split(",")[2].toUpperCase());
                        }
                        line = line.replaceAll(" ","").replaceAll(",",", ");
                        fileContent.append(line).append(System.lineSeparator());
                    }
                }
                if(!stopFlag) fileContent.insert(0, ("[Rule]\n"));
                saveToMap(headerMap, targetFile.getAbsolutePath(), commentContent.toString());
            }
            // 2. 替换指定内容
            String modifiedContent = fileContent.toString();
            for (String key : keySet) {
                modifiedContent = modifiedContent.replace(key, key.toUpperCase());
            }
            modifiedContent = modifiedContent.replace("HOST", "DOMAIN")
                                             .replace("IP6-CIDR", "IP-CIDR6");
            saveToMap(contentMap, targetFile.getAbsolutePath(), modifiedContent);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 替换文件
     *
     * @param targetFile 目标文件
     * @return 是否替换成功
     * @throws IOException IO异常
     */
    private static boolean replaceFile(File targetFile) throws IOException {
        if(getSpecialFlag(targetFile.getAbsolutePath())){
            return false;
        }
        return (!targetFile.exists() || targetFile.delete()) && targetFile.createNewFile();
    }


    public static void main(String[] args) throws IOException {
        getOriginalFilePaths(quanXRuleDir);
        generateConfigDirectory(appPaths);
        generateConfigDirectory(wechatAppletPaths);
        generateConfigDirectory(alipayAppletPaths);
        generateConfigDirectory(webPaths);
        generateOthers();



    }

    /**
     * 其他杂项生成
     */
    private static void generateOthers() throws IOException {
        HashSet<String> others = new HashSet<>();
        others.add("/Users/zirawell/Git/R-Store/Rule/QuanX/Revision");
        others.add("/Users/zirawell/Git/R-Store/Rule/QuanX/Plugin");
        generateConfigDirectory(others);
    }
}
