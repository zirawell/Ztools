package com.zlog.config.generator.quanx;

import com.zlog.config.generator.constants.ConfigGeneratorConstants;
import com.zlog.config.generator.utils.ConfigGeneratorUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Description:
 * @Author: Dark Wang
 * @Create: 2025/2/21 13:32
 **/
public class QuanXResourceParser {
    public static String addResUrl = ConfigGeneratorConstants.QUANX_ADD_RES_URL;
    public static String gitBranch = ConfigGeneratorConstants.GIT_BRANCH;
    public static String gitRawUrlPrefix = ConfigGeneratorConstants.GITHUB_RAW_URL_PREFIX;
    public static String homeDirectory = ConfigGeneratorConstants.PROJECT_BASE_DIRECTORY;
    public static String projectName = ConfigGeneratorConstants.PROJECT_NAME_SIGN;
    public static String generalUrlPrefix = gitRawUrlPrefix + ConfigGeneratorConstants.FILE_SEPARATOR
                                            + projectName + ConfigGeneratorConstants.FILE_SEPARATOR
                                            + gitBranch + ConfigGeneratorConstants.FILE_SEPARATOR;
    public static String generalFilterUrlPrefix = generalUrlPrefix + "Rule/QuanX/Adblock/All/filter/";
    public static String generalRewriteUrlPrefix = generalUrlPrefix + "Rule/QuanX/Adblock/All/rewrite/";

    public static String getResourceUrlFromDirectory(String dirPath, String tag){
        String resultStr = null;
        // 小程序通用规则特殊处理
        if(dirPath.contains(ConfigGeneratorUtils.capitalizeFirstLetter(ConfigGeneratorConstants.APPLET_SIGN))
        && dirPath.contains("/#/")){
            //dirPath = dirPath.substring(0, dirPath.lastIndexOf("/"));
            tag = dirPath.contains(ConfigGeneratorConstants.ALIPAY_SIGN) ? "支付宝小程序通用规则" : "微信小程序通用规则";
        }
        File dir = new File(dirPath);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            File[] filteredFiles = Arrays.stream(files != null ? files : new File[0])
                .filter(file -> !(ConfigGeneratorConstants.README_SIGN.equals(file.getName())
                        ||ConfigGeneratorConstants.MACOS_FILE_SIGN.equals(file.getName())))
                .toArray(File[]::new);
            int size = filteredFiles.length;
            String rewriteFile = "";
            String filterFile = "";
            String rewriteFileUrl = null;
            String filterFileUrl = null;
            StringBuilder sb = new StringBuilder();
            if (size > 1) {
                String fileName1 = Objects.requireNonNull(filteredFiles[0].listFiles())[0].getName();
                String fileName2 = Objects.requireNonNull(filteredFiles[1].listFiles())[0].getName();
                rewriteFile = fileName1.endsWith(ConfigGeneratorConstants.CONF_SIGN) ? fileName1 : fileName2;
                filterFile = fileName2.endsWith(ConfigGeneratorConstants.LIST_SIGN) ? fileName2 : fileName1;
            } else if (size == 1) {
                String fileName = Objects.requireNonNull(filteredFiles[0].listFiles())[0].getName();
                if(fileName.endsWith(ConfigGeneratorConstants.LIST_SIGN)){
                    filterFile = fileName;
                }else if(fileName.endsWith(ConfigGeneratorConstants.CONF_SIGN)){
                    rewriteFile = fileName;
                }
            }

            if (rewriteFile.equals(filterFile)) {
                throw new RuntimeException("The resource directory contains multiple files, please check the directory.");
            }
            if(StringUtils.isNotBlank(rewriteFile)){
                String rewriteFilePath = dirPath
                        + ConfigGeneratorConstants.FILE_SEPARATOR
                        + ConfigGeneratorConstants.REWRITE_SIGN
                        + ConfigGeneratorConstants.FILE_SEPARATOR
                        + rewriteFile;
                rewriteFileUrl = rewriteFilePath.replace(homeDirectory, generalUrlPrefix);
            }

            if(StringUtils.isNotBlank(filterFile)){
                String filterFilePath = dirPath
                        + ConfigGeneratorConstants.FILE_SEPARATOR
                        + ConfigGeneratorConstants.FILTER_SIGN
                        + ConfigGeneratorConstants.FILE_SEPARATOR
                        + filterFile;
                filterFileUrl = filterFilePath.replace(homeDirectory, generalUrlPrefix);
            }
            if(StringUtils.isNotBlank(filterFileUrl)){
                sb.append("{\"filter_remote\": [\"").append(filterFileUrl).append(", tag=").append(tag).append("\"");
            }
            if(StringUtils.isNotBlank(rewriteFileUrl)){
                if(!sb.toString().isEmpty()) {
                    sb.append("],");
                }else{
                    sb.append("{");
                }
                sb.append("\"rewrite_remote\": [\"").append(rewriteFileUrl).append(", tag=").append(tag).append("\"");
            }
            sb.append("]}");
            resultStr = encodeResult(sb.toString());
        }
        return resultStr;
    }

    /**
     * 根据标识生成资源的过滤和重写URL
     * 此方法根据提供的标识，生成一个包含过滤和重写URL的JSON字符串
     * 这些URL用于在特定的上下文中标识和处理资源
     *
     * @param sign 资源的标识，用于生成个性化的URL
     * @return 包含过滤和重写URL的编码后的JSON字符串
     */
    public static String getResourceUrl(String sign) {
        // 根据签名类型生成标签如果签名表示全部，则使用特定的标签，否则首字母大写
        String tag = ConfigGeneratorConstants.ALL_SIGN.equals(sign) ?
                ConfigGeneratorConstants.ADBLOCK_SIGN :
                ConfigGeneratorUtils.capitalizeFirstLetter(sign);

        // 生成过滤URL的后缀
        String lastFilterUrl = sign + ConfigGeneratorConstants.FILTER_SUFFIX;
        // 生成重写URL的后缀
        String lastRewriteUrl = sign + ConfigGeneratorConstants.REWRITE_SUFFIX;

        // 构造包含过滤和重写URL的JSON字符串
        String str = "{\"filter_remote\": [" +
                "\"" + generalFilterUrlPrefix + lastFilterUrl + ", tag=" + tag + "\"" + "]," +
                "\"rewrite_remote\": [" +
                "\"" + generalRewriteUrlPrefix + lastRewriteUrl + ", tag=" + tag + "\"]}";

        // 返回编码后的JSON字符串
        return encodeResult(str);
    }


    public static String getAppResourceUrl() {
        return getResourceUrl(ConfigGeneratorConstants.APP_SIGN);
    }
    public static String getWechatResourceUrl() {
        return getResourceUrl(ConfigGeneratorConstants.WECHAT_SIGN);
    }
    public static String getAlipayResourceUrl() {
        return getResourceUrl(ConfigGeneratorConstants.ALIPAY_SIGN);
    }
    public static String getAllResourceUrl() {
        return getResourceUrl(ConfigGeneratorConstants.ALL_SIGN);
    }

    public static void generateResource() {
        System.out.println("app:" + getAppResourceUrl());
        System.out.println("wechat:" + getWechatResourceUrl());
        System.out.println("alipay:" + getAlipayResourceUrl());
        System.out.println("all:" + getAllResourceUrl());
    }

    /**
     * Encodes a string as a URL.
     * This method takes in a string, escapes it in URL format, and replaces the original URL placeholder with the escaped string.
     * It is mainly used to process the result URL, encoding the JSON data in the URL.
     *
     * @param str The string to be encoded.
     * @return The URL after encoding.
     */
    private static String encodeResult(String str) {
        // Replaces the URL placeholder with the URL-encoded string.
        // Uses URLEncoder to encode the string in UTF-8 format, then replaces the space character "+" with "%20".
        return addResUrl.replace("url-encoded-json", URLEncoder.encode(str, StandardCharsets.UTF_8).replace("+", "%20"));
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        generateResource();
    }


}
