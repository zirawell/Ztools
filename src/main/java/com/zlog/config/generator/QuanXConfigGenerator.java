package com.zlog.config.generator;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 广告拦截配置生成器 <br/>
 * 主要实现了以下几个功能：<br/>
 * 1. 读取各配置文件生成汇总的重写规则和过滤器规则<br/>
 * 2. 生成项目的README文件<br/>
 * 3. 生成脚本文件注释内容<br/>
 * 主要方法：generateConfigs()具体细节如下：<br/>
 * 1. generateOverallConfigs() - 生成汇总重写规则和过滤器规则<br/>
 * 2. generateProjectReadMe() - 生成项目的README文件<br/>
 * 3. generateScriptComment() - 为脚本文件添加注释
 * @Author: Dark Wang
 * @Create: 2023/5/11 16:12
 **/
public class QuanXConfigGenerator {
    private static final Logger logger = LoggerFactory.getLogger("QuanXConfigGenerator.class");
    // ---------------------各类文件路径------------------------
    // Home路径
    public static final String HOME_DIRECTORY = ConfigGeneratorUtils.getHomeDirectory();
    // 项目根目录
    public static final String PROJECT_BASE_DIRECTORY = HOME_DIRECTORY + "/Git/R-Store/Rule/QuanX";
    // Git仓库根目录
    public static final String GITHUB_BASE_URL = "https://github.com/zirawell/R-Store";
    // Git仓库相对路径
    public static String GITHUB_RELATIVE_URL;
    // 脚本文件相对路径
    public static final String SCRIPT_DIRECTORY = HOME_DIRECTORY + "/Git/R-Store/Res/Scripts";
    // App配置相对路径
    public static final String APP_RELATIVE_DIRECTORY = "/Adblock/App";
    // 微信小程序相对路径
    public static final String WECHAT_RELATIVE_DIRECTORY = "/Adblock/Applet/Wechat";
    // 支付宝小程序相对路径
    public static final String ALIPAY_RELATIVE_DIRECTORY = "/Adblock/Applet/Alipay";
    // 集合相对路径
    public static final String COLLECTION_RELATIVE_DIRECTORY = "/All";
    // 全部配置输出文件路径
    public static final String outputFilterFileForAll = generateFilePath(ConfigGeneratorConstants.ALL_SIGN, ConfigGeneratorConstants.FILTER_SUFFIX);
    public static final String outputRewriteFileForAll = generateFilePath(ConfigGeneratorConstants.ALL_SIGN, ConfigGeneratorConstants.REWRITE_SUFFIX);
    // App配置输出文件路径
    public static final String outputFilterFileForApp = generateFilePath(ConfigGeneratorConstants.APP_SIGN, ConfigGeneratorConstants.FILTER_SUFFIX);
    public static final String outputRewriteFileForApp = generateFilePath(ConfigGeneratorConstants.APP_SIGN, ConfigGeneratorConstants.REWRITE_SUFFIX);
    // 微信小程序配置输出文件路径
    public static final String outputFilterFileForWechat = generateFilePath(ConfigGeneratorConstants.WECHAT_SIGN, ConfigGeneratorConstants.FILTER_SUFFIX);
    public static final String outputRewriteFileForWechat = generateFilePath(ConfigGeneratorConstants.WECHAT_SIGN, ConfigGeneratorConstants.REWRITE_SUFFIX);
    // 支付宝小程序配置输出文件路径
    public static final String outputFilterFileForAlipay = generateFilePath(ConfigGeneratorConstants.ALIPAY_SIGN, ConfigGeneratorConstants.FILTER_SUFFIX);
    public static final String outputRewriteFileForAlipay = generateFilePath(ConfigGeneratorConstants.ALIPAY_SIGN, ConfigGeneratorConstants.REWRITE_SUFFIX);

    //----------------------各类StringBuffer------------------------
    public static final StringBuffer filterSb = new StringBuffer();
    public static final StringBuffer rewriteSb = new StringBuffer();

    public static final StringBuffer appFilterSb = new StringBuffer();
    public static final StringBuffer appRewriteSb = new StringBuffer();

    public static final StringBuffer wechatFilterSb = new StringBuffer();
    public static final StringBuffer wechatRewriteSb = new StringBuffer();

    public static final StringBuffer alipayFilterSb = new StringBuffer();
    public static final StringBuffer alipayRewriteSb = new StringBuffer();

    //----------------------各类Set------------------------
    public static final Set<String> domainSet = new HashSet<>();
    public static final Set<String> appDomainSet = new HashSet<>();
    public static final Set<String> wechatDomainSet = new HashSet<>();
    public static final Set<String> alipayDomainSet = new HashSet<>();

    public static Set<String> hostSet = new HashSet<>();
    public static Set<String> hostSuffixSet = new HashSet<>();
    public static final Set<String> hostKeywordSet = new HashSet<>();
    public static final Set<String> ipcidrSet = new HashSet<>();
    public static final Set<String> ip6cidrSet = new HashSet<>();

    public static final Set<String> rewriteFileNameSet = new HashSet<>();
    public static final Set<String> allFileNameSet = new HashSet<>();
    public static final Set<String> appFileNameSet = new HashSet<>();
    public static final Set<String> wechatFileNameSet = new HashSet<>();
    public static final Set<String> alipayFileNameSet = new HashSet<>();
    public static final Set<String> webFileNameSet = new HashSet<>();
    public static final Set<String> directDomainSet = new HashSet<>();

    //----------------------各类Count------------------------
    public static int All_COUNT = 0;
    public static int APP_COUNT = 0;
    public static int WECHAT_COUNT = 0;
    public static int ALIPAY_COUNT = 0;
    public static int WEB_COUNT = 0;


    // Git分支
    public static String GIT_BRANCH = getBranchName();

    /**
     * 生成文件名
     *
     * @param typeSign   类型标识：all/app/wechat/alipay
     * @param fileSuffix 文件类型：AdBlock.list/AdRewrite.conf
     * @return String 文件路径
     */
    private static String generateFilePath(String typeSign, String fileSuffix) {
        String fileName = typeSign + fileSuffix;
        String subDir;
        if (ConfigGeneratorConstants.FILTER_SUFFIX.equals(fileSuffix)) {
            subDir = ConfigGeneratorConstants.FILTER_SIGN;
        } else if (ConfigGeneratorConstants.REWRITE_SUFFIX.equals(fileSuffix)) {
            subDir = ConfigGeneratorConstants.REWRITE_SIGN;
        } else {
            throw new RuntimeException("Unknown file suffix");
        }
        return String.join(ConfigGeneratorConstants.FILE_SEPARATOR, PROJECT_BASE_DIRECTORY, ConfigGeneratorConstants.ADBLOCK_SIGN, ConfigGeneratorConstants.COLLECTION_SIGN, subDir, fileName);

    }

    /**
     * 配置文件生成逻辑
     */
    public static void generateConfigs() {
        //Output Config Files
        try {
            // init variables
            // default main branch
            GIT_BRANCH = "main";
            GITHUB_RELATIVE_URL = "/tree/" + GIT_BRANCH + "/Rule/QuanX";
            //generate Overall rewrite and filter files
            generateOverallConfigs();
            //generate README for project
            generateProjectReadMe();
            //generate Script Comment
            generateScriptComment();
            //output the project url
            System.out.println("Project Homepage:\n" + GITHUB_BASE_URL);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 生成汇总的配置文件内容
     */
    private static void generateOverallConfigs() throws IOException {
        File baseDirectory = new File(PROJECT_BASE_DIRECTORY);
        File appDirectory = new File(PROJECT_BASE_DIRECTORY + APP_RELATIVE_DIRECTORY);
        File wechatAppletDirectory = new File(PROJECT_BASE_DIRECTORY + WECHAT_RELATIVE_DIRECTORY);
        File alipayAppletDirectory = new File(PROJECT_BASE_DIRECTORY + ALIPAY_RELATIVE_DIRECTORY);
        //outputFileForAll
        processFlow(baseDirectory, rewriteSb, filterSb, domainSet, ConfigGeneratorConstants.ALL_SIGN, outputRewriteFileForAll, outputFilterFileForAll);
        //outputFileForApp
        processFlow(appDirectory, appRewriteSb, appFilterSb, appDomainSet, ConfigGeneratorConstants.APP_SIGN, outputRewriteFileForApp, outputFilterFileForApp);
        //outputFileForWechatApplet
        processFlow(wechatAppletDirectory, wechatRewriteSb, wechatFilterSb, wechatDomainSet, ConfigGeneratorConstants.WECHAT_SIGN, outputRewriteFileForWechat, outputFilterFileForWechat);
        //outputFileForAlipayApplet
        processFlow(alipayAppletDirectory, alipayRewriteSb, alipayFilterSb, alipayDomainSet, ConfigGeneratorConstants.ALIPAY_SIGN, outputRewriteFileForAlipay, outputFilterFileForAlipay);

    }

    /**
     * 加工处理流程
     *
     * @param baseDirectory     基础目录
     * @param rewriteSb         重写内容
     * @param filterSb          过滤内容
     * @param domainSet         域名集合
     * @param sign              类型标识(all/app/wechat/alipay)
     * @param outputRewriteFile 输出重写文件
     * @param outputFilterFile  输出过滤文件
     * @throws IOException IO异常
     */
    private static void processFlow(File baseDirectory, StringBuffer rewriteSb, StringBuffer filterSb, Set<String> domainSet, String sign, String outputRewriteFile, String outputFilterFile) throws IOException {
        processFile(baseDirectory, rewriteSb, filterSb, domainSet);
        generateRewriteFile(sign, rewriteSb, outputRewriteFile, domainSet);
        generateFilterFile(sign, filterSb, outputFilterFile);
        cleanSet();
    }

    /**
     * 生成Filter文件
     *
     * @param typeSign         Filter类型标识(all/app/wechat/alipay)
     * @param filterSb         Filter内容
     * @param outputFilterFile Filter文件路径
     */
    private static void generateFilterFile(String typeSign, StringBuffer filterSb, String outputFilterFile) throws IOException {
        checkFileExists(outputFilterFile);
        processFilterContent(filterSb);
        boolean isExist = checkStringInFile(outputFilterFile, filterSb.toString());
        if (!isExist) {
            processFilterTitle(filterSb, typeSign);
            outputFile(outputFilterFile, filterSb, false);
        }
    }


    /**
     * 生成Rewrite文件
     *
     * @param typeSign          Rewrite类型标识(all/app/wechat/alipay)
     * @param rewriteSb         Rewrite内容
     * @param outputRewriteFile Rewrite文件路径
     * @param domainSet         域名集合
     */
    private static void generateRewriteFile(String typeSign, StringBuffer rewriteSb, String outputRewriteFile, Set<String> domainSet) throws IOException {
        checkFileExists(outputRewriteFile);
        boolean isExist = checkStringInFile(outputRewriteFile, rewriteSb.toString());
        if (!isExist) {
            processRewriteTitle(rewriteSb, typeSign);
            outputFile(outputRewriteFile, rewriteSb, false);
            processRewriteDomain(outputRewriteFile, domainSet);
        }
    }

    private static void checkFileExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println(filePath);
            if (!file.createNewFile()) {
                throw new IOException("Create File: " + filePath + " Failed");
            }
        }
    }

    /**
     * 生成脚本注释
     */
    private static void generateScriptComment() {
        File scriptsDirectory = new File(SCRIPT_DIRECTORY);
        File[] scriptsFiles = scriptsDirectory.listFiles();
        if (null == scriptsFiles || scriptsFiles.length == 0) return;
        Map<String, List<String>> rewriteFileMap = new HashMap<>();
        HashSet<String> scriptsFileSet = new HashSet<>();
        Map<String, String> localFileMap = new HashMap<>();
        for (File scriptsFile : scriptsFiles) {
            String keyWord = scriptsFile.getAbsolutePath().substring(scriptsFile.getAbsolutePath().indexOf("/R-Store"));
            keyWord = keyWord.replace("R-Store", "R-Store/" + GIT_BRANCH);
            localFileMap.put(keyWord, scriptsFile.getAbsolutePath());
            scriptsFileSet.add(keyWord);
        }
        getScriptsRewriteFiles(rewriteFileMap, scriptsFileSet);

        for (String key : rewriteFileMap.keySet()) {
            String scriptFilePath = localFileMap.get(key);
            String searchWord = "QuantumultX rewrite link:";
            StringBuilder replaceSb = new StringBuilder();
            replaceSb.append(searchWord).append(System.lineSeparator());
            if (rewriteFileMap.get(key).size() > 1) {
                for (String fileName : rewriteFileMap.get(key)) {
                    if (rewriteFileMap.get(key).indexOf(fileName) == rewriteFileMap.get(key).size() - 1) {
                        replaceSb.append(fileName);
                    } else {
                        replaceSb.append(fileName).append(System.lineSeparator());
                    }
                }
            } else {
                replaceSb.append(rewriteFileMap.get(key).get(0));
            }
            boolean isExist = checkStringInFile(scriptFilePath, replaceSb.toString());
            if (!isExist) {
                replaceStringInFile(scriptFilePath, searchWord, replaceSb.toString());
            }
        }
    }

    /**
     * 检查文件中是否已经包含，防止重复操作
     *
     * @param filePath      文件路径
     * @param replaceString 替换文本
     * @return boolean
     */
    private static boolean checkStringInFile(String filePath, String replaceString) {
        File file = new File(filePath);
        StringBuilder sb = new StringBuilder();
        if (file.isDirectory()) {
            return false;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String fileContent = sb.toString().trim().replace("\n", "");
            replaceString = replaceString.replace("\n", "");
            int index = fileContent.indexOf(replaceString);
            if (index != -1) {
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error processing file: " + filePath);
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 替换文件中特定字符
     *
     * @param filePath      文件路径
     * @param searchString  搜索字符
     * @param replaceString 替换字符
     */
    public static void replaceStringInFile(String filePath, String searchString, String replaceString) {
        File file = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        if (file.isDirectory()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            // 逐行读取文件内容，替换特定字符后写入临时文件
            while ((line = reader.readLine()) != null) {
                String modifiedLine = line.replace(searchString, replaceString);
                writer.write(modifiedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error processing file: " + filePath);
            logger.error(e.getMessage(), e);
            return;
        }

        // 删除原文件，并将临时文件重命名为原文件名
        if (!file.delete()) {
            System.err.println("Could not delete original file: " + filePath);
            return;
        }

        if (!tempFile.renameTo(file)) {
            System.err.println("Could not rename temporary file to original file: " + filePath);
        }
    }

    /**
     * 获取使用脚本的文件
     *
     * @param rewriteFileMap 重写文件集合
     * @param scriptsFileSet 脚本文件集合
     */
    private static void getScriptsRewriteFiles(Map<String, List<String>> rewriteFileMap, HashSet<String> scriptsFileSet) {
        File scanDirectory = new File(PROJECT_BASE_DIRECTORY + ConfigGeneratorConstants.FILE_SEPARATOR + ConfigGeneratorConstants.ADBLOCK_SIGN);
        for (String searchString : scriptsFileSet) {
            scanFiles(scanDirectory, rewriteFileMap, searchString);
        }
    }

    /**
     * 扫描文件
     *
     * @param scanDirectory  扫描目录
     * @param rewriteFileMap 重写文件集合
     * @param searchString   扫描文本
     */
    private static void scanFiles(File scanDirectory, Map<String, List<String>> rewriteFileMap, String searchString) {
        // 获取目录下的所有文件和子目录
        File[] files = scanDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (file.getAbsolutePath().contains(ConfigGeneratorConstants.COLLECTION_SIGN)) {
                        return;
                    }
                    // 如果是子目录，递归搜索
                    scanFiles(file, rewriteFileMap, searchString);
                } else {
                    // 如果是文件，检查文件内容
                    if (scanContents(file, searchString)) {
                        String githubUrl = file.getAbsolutePath().replace(HOME_DIRECTORY + "/Git", "https://raw.githubusercontent.com/zirawell").replace("/R-Store", "/R-Store/" + GIT_BRANCH);
                        if (rewriteFileMap.get(searchString) != null) {
                            rewriteFileMap.get(searchString).add(githubUrl);
                        } else {
                            List<String> fileList = new ArrayList<>();
                            fileList.add(githubUrl);
                            rewriteFileMap.put(searchString, fileList);
                        }
                    }
                }
            }
        }
    }

    /**
     * 扫描文件内容
     *
     * @param file         扫描文件
     * @param searchString 搜索文本内容
     * @return boolean
     */
    private static boolean scanContents(File file, String searchString) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(searchString)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getAbsolutePath());
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 生成项目的README.md文件
     */
    private static void generateProjectReadMe() {
        File generateFile = new File(PROJECT_BASE_DIRECTORY + ConfigGeneratorConstants.FILE_SEPARATOR + ConfigGeneratorConstants.README_SIGN);
        StringBuffer sb = new StringBuffer();
        processCountInfo();
        generateTreePath(sb);
        generateIndexTable(sb);
        generateCountTable(sb);
        generateBottomInfo(sb);
        outputFile(generateFile.getAbsolutePath(), sb, false);
    }


    /**
     * 生成底部信息
     *
     * @param sb 信息内容
     */
    private static void generateBottomInfo(StringBuffer sb) {
        sb.append("## Thanks To\n");
        for (String s : ConfigGeneratorConstants.THANKS_TO_ARRAY) {
            sb.append("- ").append(s).append(System.lineSeparator());
        }
    }

    /**
     * 处理统计数据
     */
    private static void processCountInfo() {
        All_COUNT = getFileNameCount(allFileNameSet);
        APP_COUNT = getFileNameCount(appFileNameSet);
        WECHAT_COUNT = getFileNameCount(wechatFileNameSet);
        ALIPAY_COUNT = getFileNameCount(alipayFileNameSet);
        WEB_COUNT = getFileNameCount(webFileNameSet);
    }

    private static int getFileNameCount(Set<String> fileNameSet) {
        // 去除通用规则
        Set<String> tmpSet = fileNameSet.stream().filter(s -> !s.contains("#")).collect(Collectors.toSet());
        return tmpSet.size();
    }

    /**
     * 生成统计表
     *
     * @param sb 外部输出内容
     */
    private static void generateCountTable(StringBuffer sb) {
        String countTableBuffer = ConfigGeneratorConstants.COUNT_TABLE;
        countTableBuffer = countTableBuffer.replace("#AllCount", All_COUNT + "");
        countTableBuffer = countTableBuffer.replace("#AppCount", APP_COUNT + "");
        countTableBuffer = countTableBuffer.replace("#WechatCount", WECHAT_COUNT + "");
        countTableBuffer = countTableBuffer.replace("#AlipayCount", ALIPAY_COUNT + "");
        countTableBuffer = countTableBuffer.replace("#WebCount", WEB_COUNT + "");
        sb.append("## Count Table\n");
        sb.append("本项目收录统计情况如下:\n");
        sb.append(countTableBuffer);
        sb.append(System.lineSeparator());
    }


    /**
     * 生成索引表
     *
     * @param sb 外部输出内容
     */
    private static void generateIndexTable(StringBuffer sb) {
        sb.append("\n## Overview Index\n");
        sb.append("\n### App Index\n");
        generateIndexTableByType(sb, ConfigGeneratorConstants.APP_SIGN);
        sb.append("\n### Wechat Applet Index\n");
        generateIndexTableByType(sb, ConfigGeneratorConstants.WECHAT_SIGN);
        sb.append("\n### Alipay Applet Index\n");
        generateIndexTableByType(sb, ConfigGeneratorConstants.ALIPAY_SIGN);
        sb.append("\n### Web Index\n");
        generateIndexTableByType(sb, ConfigGeneratorConstants.WEB_SIGN);
    }


    /**
     * 生成索引表
     *
     * @param sb       信息内容
     * @param typeSign 类型标识(all/app/wechat/alipay)
     */
    private static void generateIndexTableByType(StringBuffer sb, String typeSign) {
        Set<String> typeFileNameSet = new HashSet<>();
        Set<String> parentPathSet = new HashSet<>();
        String generatePath = null;
        String startChar;
        HashMap<String, String> urlMap = new HashMap<>();
        String indexTableBuffer = ConfigGeneratorConstants.INDEX_TABLE.trim();
        StringBuffer tmpSb = new StringBuffer();
        switch (typeSign) {
            case ConfigGeneratorConstants.APP_SIGN:
                typeFileNameSet = appFileNameSet;
                break;
            case ConfigGeneratorConstants.WECHAT_SIGN:
                typeFileNameSet = wechatFileNameSet;
                break;
            case ConfigGeneratorConstants.ALIPAY_SIGN:
                typeFileNameSet = alipayFileNameSet;
                break;
            case ConfigGeneratorConstants.WEB_SIGN:
                typeFileNameSet = webFileNameSet;
                break;
        }
        for (String fileName : typeFileNameSet) {
            String parentPath = fileName.substring(0, fileName.lastIndexOf(ConfigGeneratorConstants.FILE_SEPARATOR));
            parentPathSet.add(parentPath);
            if (null == generatePath) {
                generatePath = fileName.substring(0, parentPath.lastIndexOf(ConfigGeneratorConstants.FILE_SEPARATOR));
            }
        }

        for (String parentPath : parentPathSet) {
            parentPath = parentPath.replace(PROJECT_BASE_DIRECTORY, GITHUB_BASE_URL + GITHUB_RELATIVE_URL);
            startChar = parentPath.substring(parentPath.lastIndexOf(ConfigGeneratorConstants.FILE_SEPARATOR) + 1);
            urlMap.put(startChar, "[" + startChar + "](" + parentPath + ")");
        }

        for (String index : ConfigGeneratorConstants.INDEX_ARRAY) {
            if (null != urlMap.get(index)) {
                indexTableBuffer = indexTableBuffer.replace("#" + index, urlMap.get(index));
            } else {
                indexTableBuffer = indexTableBuffer.replace("#" + index, index);
            }
        }
        sb.append(indexTableBuffer).append(System.lineSeparator());
        tmpSb.append(indexTableBuffer).append(System.lineSeparator());
        generateReadmeByType(tmpSb, generatePath, typeSign);


    }

    /**
     * 根据不同类别生成大类的 README.md
     *
     * @param sb           外部输出内容
     * @param generatePath 生成文件路径
     * @param typeSign     类型标识(all/app/wechat/alipay)
     */
    private static void generateReadmeByType(StringBuffer sb, String generatePath, String typeSign) {
        File generateFile = new File(generatePath + ConfigGeneratorConstants.FILE_SEPARATOR + ConfigGeneratorConstants.README_SIGN);
        int count = 0;
        switch (typeSign) {
            case ConfigGeneratorConstants.APP_SIGN:
                count = APP_COUNT;
                break;
            case ConfigGeneratorConstants.WECHAT_SIGN:
                count = WECHAT_COUNT;
                break;
            case ConfigGeneratorConstants.ALIPAY_SIGN:
                count = ALIPAY_COUNT;
                break;
            case ConfigGeneratorConstants.WEB_SIGN:
                count = WEB_COUNT;
                break;
            default:
                break;
        }
        sb.insert(0, "## Index Overview\n");
        sb.insert(0, "此目录收录了" + count + "个项目\n");
        outputFile(generateFile.getAbsolutePath(), sb, false);
    }

    /**
     * 生成文件树形结构
     *
     * @param sb 外部输出内容
     */
    private static void generateTreePath(StringBuffer sb) {
        StringBuilder sbTmp = new StringBuilder();
        String output = "";
        // 定义要执行的命令

        String command = "/opt/homebrew/bin/tree -I 'Utils' -dL 2 " + PROJECT_BASE_DIRECTORY;

        try {
            int exitCode = ConfigGeneratorUtils.executeShellCommand(sbTmp, command);
            if (exitCode == 0) {
                output = sbTmp.substring(sbTmp.toString().lastIndexOf(ConfigGeneratorConstants.FILE_SEPARATOR + ConfigGeneratorConstants.PROJECT_NAME_SIGN) + 1);
            } else {
                System.out.println("generateTreePath: Command failed with exit code: " + exitCode);
            }
            sb.append("## Directory Structure\n\n");
            sb.append("```\n");
            sb.append(output);
            sb.append("```\n");
            for (String s : ConfigGeneratorConstants.DIR_ARRAY) {
                switch (s) {
                    case "Adblock":
                        sb.append("- [Adblock](https://github.com/zirawell/R-Store/tree/main/Rule/QuanX/Adblock) : ").append("收录各类 App/小程序/Web 的**广告拦截**规则\n");
                        break;
                    case "Filter":
                        sb.append("- [Filter](https://github.com/zirawell/R-Store/tree/main/Rule/QuanX/Filter) : ").append("收录常用的**分流**规则\n");
                        break;
                    case "Plugin":
                        sb.append("- [Plugin](https://github.com/zirawell/R-Store/tree/main/Rule/QuanX/Plugin) : ").append("收录各类脚本插件的**配置**规则\n");
                        break;
                    case "Redirect":
                        sb.append("- [Redirect](https://github.com/zirawell/R-Store/tree/main/Rule/QuanX/Redirect) : ").append("收录各类**重定向**规则\n");
                        break;
                    case "Revision":
                        sb.append("- [Revision](https://github.com/zirawell/R-Store/tree/main/Rule/QuanX/Revision) : ").append("收录分流**修正**规则\n");
                        break;
                    default:
                        break;
                }

            }

            System.out.println(sb);
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 获取Git分支
     *
     * @return Git分支
     */
    private static String getBranchName() {
        StringBuilder sbTmp = new StringBuilder();
        String output;
        // 定义要执行的命令
        String command = "cd " + PROJECT_BASE_DIRECTORY + "&& git branch --show-current";

        try {
            int exitCode = ConfigGeneratorUtils.executeShellCommand(sbTmp, command);
            if (exitCode != 0) {
                System.out.println("getBranchName: Command failed with exit code: " + exitCode);
            }
            System.out.println("Git分支：" + sbTmp);
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        output = sbTmp.toString();
        return output;
    }


    /**
     * 处理过滤器头部信息
     *
     * @param filterSb 过滤内容
     * @param typeSign 过滤类型标识(all/app/wechat/alipay)
     */
    private static void processFilterTitle(StringBuffer filterSb, String typeSign) {
        StringBuffer titleBuffer = new StringBuffer();
        processFileTitle(titleBuffer, typeSign, ConfigGeneratorConstants.FILTER_SIGN);
        filterSb.insert(0, (titleBuffer).append(System.lineSeparator()));
    }

    /**
     * 处理过滤器域名相关信息
     *
     * @param filterSb 过滤内容
     */
    private static void processFilterContent(StringBuffer filterSb) {
        filterSb.setLength(0);
        List<String> collect = ipcidrSet.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        for (String ip : collect) {
            if (directDomainSet.contains(ip)) {
                filterSb.append("ip-cidr,").append(ip).append(", direct").append(System.lineSeparator());
            } else {
                filterSb.append("ip-cidr,").append(ip).append(", reject").append(System.lineSeparator());
            }
        }
        collect.clear();
        collect = ip6cidrSet.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        for (String ip : collect) {
            if (directDomainSet.contains(ip)) {
                filterSb.append("ip6-cidr,").append(ip).append(", direct").append(System.lineSeparator());
            } else {
                filterSb.append("ip6-cidr,").append(ip).append(", reject").append(System.lineSeparator());
            }

        }
        // hostSet 去除 hostKeywordSet中的域名
        hostSet = hostSet.stream()
                .filter(host -> hostKeywordSet.stream().noneMatch(host::contains))
                .collect(Collectors.toSet());

        // hostSuffixSet 去除 hostKeywordSet中的域名
        hostSuffixSet = hostSuffixSet.stream()
                .filter(hostSuffix -> hostKeywordSet.stream().noneMatch(hostSuffix::contains))
                .collect(Collectors.toSet());

        collect.clear();
        collect = hostSet.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        for (String host : collect) {
            if (directDomainSet.contains(host)) {
                filterSb.append("host,").append(host).append(", direct").append(System.lineSeparator());
            } else {
                filterSb.append("host,").append(host).append(", reject").append(System.lineSeparator());
            }
        }
        collect.clear();
        collect = hostSuffixSet.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        for (String hostSuffix : collect) {
            if (directDomainSet.contains(hostSuffix)) {
                filterSb.append("host-suffix,").append(hostSuffix).append(", direct").append(System.lineSeparator());
            } else {
                filterSb.append("host-suffix,").append(hostSuffix).append(", reject").append(System.lineSeparator());
            }
        }
        collect.clear();
        collect = hostKeywordSet.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        for (String hostKeyword : collect) {
            if (directDomainSet.contains(hostKeyword)) {
                filterSb.append("host-keyword,").append(hostKeyword).append(", direct").append(System.lineSeparator());
            } else {
                filterSb.append("host-keyword,").append(hostKeyword).append(", reject").append(System.lineSeparator());
            }
        }
        if (filterSb.length() > 0) {
            filterSb.deleteCharAt(filterSb.length() - 1);  // 删除最后一个字符
        }
    }

    /**
     * 处理文件头信息
     *
     * @param titleBuffer 文件头缓存
     * @param typeSign    类型标识：all/app/wechat/alipay
     * @param fileSign    文件标识：FILTER_SIGN、REWRITE_SIGN
     */
    private static void processFileTitle(StringBuffer titleBuffer, String typeSign, String fileSign) {
        String namePrefix = null;
        String nameSuffix = null;
        String name;
        String rawFileNamePrefix = null;
        String rawFileNameSuffix = null;
        String rawFileName;
        String countName = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int count = 0;
        if (ConfigGeneratorConstants.FILTER_SIGN.equals(fileSign)) {
            count = ipcidrSet.size() + ip6cidrSet.size() + hostSet.size() + hostSuffixSet.size() + hostKeywordSet.size();
            nameSuffix = "分流规则";
            rawFileNameSuffix = ConfigGeneratorConstants.FILTER_SUFFIX;
        } else if (ConfigGeneratorConstants.REWRITE_SIGN.equals(fileSign)) {
            count = rewriteFileNameSet.size() - 1;
            nameSuffix = "重写规则";
            rawFileNameSuffix = ConfigGeneratorConstants.REWRITE_SUFFIX;
        }
        if (ConfigGeneratorConstants.ALL_SIGN.equals(typeSign)) {
            namePrefix = "#!name=全部-广告拦截合集-";
            rawFileNamePrefix = ConfigGeneratorConstants.ALL_SIGN;
            countName = "App/小程序";
        } else if (ConfigGeneratorConstants.APP_SIGN.equals(typeSign)) {
            namePrefix = "#!name=App-广告拦截合集-";
            rawFileNamePrefix = ConfigGeneratorConstants.APP_SIGN;
            countName = "App";
        } else if (ConfigGeneratorConstants.WECHAT_SIGN.equals(typeSign)) {
            namePrefix = "#!name=微信小程序-广告拦截合集-";
            rawFileNamePrefix = ConfigGeneratorConstants.WECHAT_SIGN;
            countName = "微信小程序";
        } else if (ConfigGeneratorConstants.ALIPAY_SIGN.equals(typeSign)) {
            namePrefix = "#!name=支付宝小程序-广告拦截合集-";
            rawFileNamePrefix = ConfigGeneratorConstants.ALIPAY_SIGN;
            countName = "支付宝程序";
        }
        countName = ConfigGeneratorConstants.FILTER_SIGN.equals(fileSign) ? "分流规则" : countName;
        name = namePrefix + nameSuffix;
        rawFileName = rawFileNamePrefix + rawFileNameSuffix;
        titleBuffer.append(name).append(System.lineSeparator());
        titleBuffer.append("#!desc=某些规则要清除缓存或者重新安装APP，拦截广告才会生效！").append(System.lineSeparator());
        titleBuffer.append("#!count=支持约").append(count).append("个").append(countName).append(System.lineSeparator());
        titleBuffer.append("#!author=zirawell").append(System.lineSeparator());
        titleBuffer.append("#!homepage=" + GITHUB_BASE_URL).append(System.lineSeparator());
        titleBuffer.append("#!raw-url=https://raw.githubusercontent.com/zirawell/")
                .append(ConfigGeneratorConstants.PROJECT_NAME_SIGN)
                .append(ConfigGeneratorConstants.FILE_SEPARATOR)
                .append(GIT_BRANCH).append("/Rule/QuanX")
                .append(ConfigGeneratorConstants.FILE_SEPARATOR)
                .append(ConfigGeneratorConstants.ADBLOCK_SIGN)
                .append(COLLECTION_RELATIVE_DIRECTORY)
                .append(ConfigGeneratorConstants.FILE_SEPARATOR)
                .append(fileSign).append(ConfigGeneratorConstants.FILE_SEPARATOR)
                .append(rawFileName).append(System.lineSeparator());
        titleBuffer.append("#!tg-group=https://t.me/lanjieguanggao").append(System.lineSeparator());
        titleBuffer.append("#!date=").append(simpleDateFormat.format(new Date())).append(System.lineSeparator());
        if (ConfigGeneratorConstants.FILTER_SIGN.equals(fileSign)) {
            titleBuffer.append("#!proxy-select=reject").append(System.lineSeparator());
        } else if (ConfigGeneratorConstants.REWRITE_SIGN.equals(fileSign)) {
            titleBuffer.append("#!proxy-select---------------------------------------------------------------------------------------------------").append(System.lineSeparator());
            titleBuffer.append("#! \"reject\"        策略返回 HTTP 状态码 404,不附带任何额外内容").append(System.lineSeparator());
            titleBuffer.append("#! \"reject-200\"    策略返回 HTTP 状态码 200,不附带任何额外内容").append(System.lineSeparator());
            titleBuffer.append("#! \"reject-img\"    策略返回 HTTP 状态码 200,同时附带 1px gif").append(System.lineSeparator());
            titleBuffer.append("#! \"reject-dict\"   策略返回 HTTP 状态码 200,同时附带一个空的 JSON 对象").append(System.lineSeparator());
            titleBuffer.append("#! \"reject-array\"  策略返回 HTTP 状态码 200,同时附带一个空的 JSON 数组").append(System.lineSeparator());
        }
        titleBuffer.append("#!---------------------------------------------------------------------------------------------------------------");
    }

    /**
     * 处理重写文件头部信息
     *
     * @param rewriteSb 重写内容
     * @param typeSign  重写类型标识(all/app/wechat/alipay)
     */
    private static void processRewriteTitle(StringBuffer rewriteSb, String typeSign) {
        StringBuffer titleBuffer = new StringBuffer();
        processFileTitle(titleBuffer, typeSign, ConfigGeneratorConstants.REWRITE_SIGN);
        rewriteSb.insert(0, (titleBuffer).append(System.lineSeparator()));
    }

    /**
     * 清理集合内容
     */
    private static void cleanSet() {
        hostSet.clear();
        hostSuffixSet.clear();
        hostKeywordSet.clear();
        ipcidrSet.clear();
        ip6cidrSet.clear();
        rewriteFileNameSet.clear();
    }

    /**
     * 处理重写文件的域名
     *
     * @param filePath 文件路径
     * @param dSet     域名集合
     */
    private static void processRewriteDomain(String filePath, Set<String> dSet) {
        StringBuffer sb = new StringBuffer();
        sb.append("hostname = ");
        int i = 0;
        for (String s : dSet) {
            if (i == 0) {
                sb.append(s);
            } else {
                sb.append(", ").append(s);
            }
            i++;
        }
        outputFile(filePath, sb, true);
    }

    /**
     * 文件输出
     *
     * @param filePath   文件路径
     * @param sb         文件内容
     * @param appendFlag 是否追加
     */
    private static void outputFile(String filePath, StringBuffer sb, boolean appendFlag) {
        try (FileOutputStream out = new FileOutputStream(filePath, appendFlag)) {
            checkFileExists(filePath);
            out.write(sb.toString().getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    /**
     * 文件处理
     *
     * @param file      文件
     * @param rewriteSb 重写内容
     * @param filterSb  过滤内容
     * @param domainSet 域名集合
     * @throws IOException IO异常
     */
    public static void processFile(File file, StringBuffer rewriteSb, StringBuffer filterSb, Set<String> domainSet) throws IOException {
        if (file.isDirectory() && file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    processFile(f, rewriteSb, filterSb, domainSet);
                    if (ArrayUtils.contains(ConfigGeneratorConstants.INDEX_ARRAY, f.getName())) {
                        generateReadme(f);
                    }
                }
            }
        }
        processData(file, rewriteSb, filterSb, domainSet);
    }


    /**
     * 数据处理
     *
     * @param file      文件
     * @param rewriteSb 重写内容
     * @param filterSb  过滤内容
     * @param domainSet 域名集合
     * @throws IOException IO异常
     */
    private static void processData(File file, StringBuffer rewriteSb, StringBuffer filterSb, Set<String> domainSet) throws IOException {
        String filePath = file.getAbsolutePath();
        Path path = Paths.get(filePath);
        String fileName = file.getName();
        if (!filePath.contains(ConfigGeneratorConstants.COLLECTION_SIGN)) {
            // 重写规则处理
            if (fileName.endsWith(ConfigGeneratorConstants.CONF_SIGN) && filePath.contains(ConfigGeneratorConstants.FILE_SEPARATOR + ConfigGeneratorConstants.REWRITE_SIGN + ConfigGeneratorConstants.FILE_SEPARATOR)) {
                if (!fileName.startsWith(".") && !fileName.equals(ConfigGeneratorConstants.README_SIGN)) {
                    rewriteFileNameSet.add(file.getAbsolutePath());
                }
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    // 去掉行首尾空格
                    line = line.trim();
                    if (line.startsWith("hostname")) {
                        // 删除所有空格
                        String cleanedLine = line.replaceAll(" ", "");
                        if (!cleanedLine.equals("hostname=")) {
                            String[] domains = cleanedLine.replace("hostname=", "").split(",");
                            // 添加到域名集合
                            domainSet.addAll(Arrays.asList(domains));
                        }
                    } else if (!line.isEmpty()) {
                        // 跳过注释行
                        if (line.startsWith("#") && !line.contains(">") && !line.contains("⚠️")) {
                            continue;
                        }
                        // 格式校验
                        if (!(line.contains(" url ") || line.contains(" url-and-header ")) && !line.contains("#")) {
                            throw new RuntimeException("重写规则格式有误，fileName: " + fileName);
                        }
                        rewriteSb.append(line).append(System.lineSeparator());
                    } else {
                        rewriteSb.append(System.lineSeparator());
                    }
                }
                // 过滤器规则处理
            } else if (fileName.endsWith(ConfigGeneratorConstants.LIST_SIGN)
                    && filePath.contains(ConfigGeneratorConstants.FILE_SEPARATOR + ConfigGeneratorConstants.FILTER_SIGN + ConfigGeneratorConstants.FILE_SEPARATOR)) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    filterSb.append(line).append(System.lineSeparator());
                    if (!line.contains("#")) {
                        String[] arr = line.split(",");
                        if (arr.length > 0) {
                            switch (arr[0]) {
                                case "host":
                                    hostSet.add(arr[1]);
                                    break;
                                case "ip-cidr":
                                    ipcidrSet.add(arr[1]);
                                    break;
                                case "ip6-cidr":
                                    ip6cidrSet.add(arr[1]);
                                    break;
                                case "host-suffix":
                                    hostSuffixSet.add(arr[1]);
                                    break;
                                case "host-keyword":
                                    hostKeywordSet.add(arr[1]);
                                    break;
                            }

                            if (arr[2].trim().equalsIgnoreCase("direct")) {
                                directDomainSet.add(arr[1]);
                            }

                        }
                    }
                }
            }
        }

    }

    /**
     * 生成README文件
     *
     * @param f 生成文件目录
     */
    private static void generateReadme(File f) {
        String prefixUrl = f.getAbsolutePath().replace(PROJECT_BASE_DIRECTORY, GITHUB_BASE_URL + GITHUB_RELATIVE_URL);
        String suffixUrl;
        String completeUrl;
        File readme = new File(f.getAbsolutePath() + ConfigGeneratorConstants.FILE_SEPARATOR + ConfigGeneratorConstants.README_SIGN);
        File[] subFiles = f.listFiles();
        StringBuffer sb = new StringBuffer();
        HashSet<String> subFileNameSet = new HashSet<>();
        int count;
        assert subFiles != null;
        for (File subFile : subFiles) {
            if (!subFile.getName().startsWith(".") && !subFile.getName().equals(ConfigGeneratorConstants.README_SIGN)) {
                subFileNameSet.add(subFile.getName());
                processFileName(subFile);
            }
        }
        String type;
        count = subFileNameSet.size();
        if (f.getAbsolutePath().contains(ConfigGeneratorUtils.capitalizeFirstLetter(ConfigGeneratorConstants.APPLET_SIGN))) {
            type = "小程序";
        } else if (f.getAbsolutePath().contains(ConfigGeneratorUtils.capitalizeFirstLetter(ConfigGeneratorConstants.WEB_SIGN))) {
            type = "网站";
        } else if (f.getAbsolutePath().contains("#")) {
            type = "项目";
        } else {
            type = "App";
        }
        List<String> collect = subFileNameSet.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        String summary = "本目录共收录" + count + "个" + type + "，详情见如下：";
        sb.append("# ").append(f.getName()).append(System.lineSeparator());
        sb.append("<details>").append(System.lineSeparator());
        sb.append("<summary>").append(System.lineSeparator()).append(summary).append(System.lineSeparator()).append("</summary>").append(System.lineSeparator());
        sb.append(System.lineSeparator());
        for (String fileName : collect) {
            suffixUrl = encodeToURL(fileName);
            completeUrl = prefixUrl + ConfigGeneratorConstants.FILE_SEPARATOR + suffixUrl;
            sb.append("- [").append(fileName).append("]").append("(").append(completeUrl).append(")").append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        sb.append("</details>");
        outputFile(readme.getAbsolutePath(), sb, false);

    }

    /**
     * 将文件路径加入对应的FileNameSet
     *
     * @param f 文件
     */
    private static void processFileName(File f) {
        String fileAbsolutePath = f.getAbsolutePath();
        allFileNameSet.add(fileAbsolutePath);
        if (f.getAbsolutePath().contains("Applet")) {
            if (f.getAbsolutePath().contains(WECHAT_RELATIVE_DIRECTORY)) {
                wechatFileNameSet.add(fileAbsolutePath);
            } else if (f.getAbsolutePath().contains(ALIPAY_RELATIVE_DIRECTORY)) {
                alipayFileNameSet.add(fileAbsolutePath);
            }
        } else if (f.getAbsolutePath().contains("Web")) {
            webFileNameSet.add(fileAbsolutePath);
        } else {
            appFileNameSet.add(fileAbsolutePath);
        }
    }

    /**
     * URL编码
     *
     * @param input 输入文本
     * @return URL编码后的文本
     */
    public static String encodeToURL(String input) {
        String encoded;
        // 将字符串转换为 URL 编码格式，使用 UTF-8 编码
        encoded = URLEncoder.encode(input, StandardCharsets.UTF_8);
        return encoded;
    }


    public static void main(String[] args) {
        generateConfigs();
    }
}
