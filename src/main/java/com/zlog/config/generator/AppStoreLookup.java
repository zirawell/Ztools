package com.zlog.config.generator;

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
public class AppStoreLookup {

    public static class AppInfo {
        private String name;
        private String storeUrl;
        private String iconUrl;
        private String bundleId;

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

        if (results.size() == 0) return null;

        JsonObject appData = results.get(0).getAsJsonObject();
        String name = appData.get("trackName").getAsString();
        String storeUrl = appData.get("trackViewUrl").getAsString();
        String iconUrl = appData.get("artworkUrl100").getAsString()
                .replace("100x100bb", "512x512bb"); // 获取高清图标
        String bundleId = appData.get("bundleId").getAsString();

        return new AppInfo(name, storeUrl, iconUrl, bundleId);
    }

    public void downloadIcon(String savePath,String iconUrl) throws IOException {
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        HttpGet request = new HttpGet(iconUrl);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            byte[] iconData = EntityUtils.toByteArray(response.getEntity());
            Files.write(Paths.get(savePath), iconData);
        }
    }
}

    public static void main(String[] args) {
        try {
            AppInfo appInfo = getAppInfo("微信", "CN");
            if (appInfo != null) {
                System.out.println("App 名称: " + appInfo.getName());
                System.out.println("商店链接: " + appInfo.getStoreUrl());
                System.out.println("图标链接: " + appInfo.getIconUrl());
                System.out.println("Bundle ID: " + appInfo.getBundleId());
            } else {
                System.out.println("未找到相关 App");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
