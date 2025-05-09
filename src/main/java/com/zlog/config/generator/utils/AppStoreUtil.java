package com.zlog.config.generator.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Description:
 * @Author: Dark Wang
 * @Create: 2025/5/8 18:20
 **/
public class AppStoreUtil {

    public static class AppInfo {
        private final String name;
        private final String storeUrl;
        private final String iconUrl;
        private final String bundleId;

        public AppInfo(String name, String storeUrl, String iconUrl, String bundleId) {
            this.name = name;
            this.storeUrl = storeUrl;
            this.iconUrl = iconUrl;
            this.bundleId = bundleId;
        }

        // Getters
        public String getName() { return name; }
        public String getStoreUrl() { return storeUrl; }
        public String getIconUrl() { return iconUrl; }
        public String getBundleId() { return bundleId; }
    }

    public static AppInfo getAppInfo(String appName, String countryCode) throws IOException {
        String url = "https://itunes.apple.com/search";
        String query = String.format("term=%s&country=%s&entity=software",
                appName.replace(" ", "+"), countryCode);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url + "?" + query);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = EntityUtils.toString(entity);
                return parseJson(jsonResponse);
            }
        }
    }

    private static AppInfo parseJson(String json) {
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(json, JsonObject.class);
        JsonArray results = root.getAsJsonArray("results");

        if (results.isEmpty()) return null;

        JsonObject appData = results.get(0).getAsJsonObject();
        String name = appData.get("trackName").getAsString();
        String storeUrl = appData.get("trackViewUrl").getAsString();
        String iconUrl = appData.get("artworkUrl100").getAsString()
                .replace("100x100bb", "512x512bb"); // 获取高清图标
        String bundleId = appData.get("bundleId").getAsString();

        return new AppInfo(name, storeUrl, iconUrl, bundleId);
    }

    /**
     * 下载App图标
     * @param savePath 保存路径
     * @param iconUrl 图标链接
     */
    public static void downloadIcon(String savePath, String iconUrl) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(iconUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                byte[] iconData = EntityUtils.toByteArray(response.getEntity());
                Files.write(Paths.get(savePath), iconData);
            }
        }


    }

    /**
     * 简化App下载链接
     * @param url 原始链接
     * @return String 简化后的链接
     */
    public static String simplifyUrl(String url){
        String resultUrl;
        int index1 = url.indexOf("/app/") + 4;
        int index2 = url.indexOf("/id");
        resultUrl = url.replace(url.substring(index1, index2),"");
        int index3 = resultUrl.indexOf("?uo=");
        resultUrl = resultUrl.substring(0,index3);
        return resultUrl;
    }

    /**
     * 图片压缩
     *
     * @param filePath 需要压缩的图片路径
     */
    public static void compressImage(String filePath) {
        StringBuilder sbTmp = new StringBuilder();
        String dirPath = filePath.substring(0, filePath.lastIndexOf("/"));
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        // 定义要执行的命令
        String command = "cd " + dirPath + "&& ffmpeg -y -i "+fileName+" -vf \"scale=108:108\" -q:v 2 -map_metadata -1 " + fileName;
        try {
            int exitCode = ConfigGeneratorUtils.executeShellCommand(sbTmp, command);
            if (exitCode != 0) {
                System.out.println("compressImage: Command failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getAppIconAndUrl(String appName, String downloadPath, String countryCode) throws IOException {
        AppInfo appInfo = getAppInfo(appName, countryCode);
        if (appInfo == null || appInfo.getIconUrl() == null) {
            return null;
        }else{
            downloadIcon(downloadPath, appInfo.getIconUrl());
            compressImage(downloadPath);
            return simplifyUrl(appInfo.getStoreUrl());
        }

    }

    public static void main(String[] args) throws IOException {
         getAppIconAndUrl("微信","/Users/zirawell/Downloads/icon.png","CN");
    }
}
