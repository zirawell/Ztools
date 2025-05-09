package com.zlog.config.generator.utils;

import com.zlog.config.generator.constants.ConfigGeneratorConstants;

/**
 * @Description:
 * @Author: Dark Wang
 * @Create: 2025/4/14 09:23
 **/
public class TestUtil {
    public static String originalStr;
    public static String convertedStr;
    public static void main(String[] args) {
        test302Convert();
        testRejectImgConvert();
        testRejectConvert();
        testRejectDictConvert();
        testEchoResponseConvert();
        testScriptResponseBodyConvert();
        testScriptResponseHeaderConvert();
        testUrlAndHeaderConvert();
        testBodyRewriteRegex();
    }

    public static void test302Convert(){
        System.out.println("test302Convert...");
        originalStr = "^http:\\/\\/(www.)?aicoin\\.cn\\/$ url 302 https://www.aicoin.com/";
        convertedStr = "";
        String regex1 = ConfigGeneratorConstants.QUANX_302_REGEX;
        String regex2 = ConfigGeneratorConstants.SURGE_302_REGEX;
        System.out.println("originalStr: \n" + originalStr);
        convertedStr = ConfigGeneratorUtils.scriptRegexReplace(regex1, regex2, originalStr);
        System.out.println("convertedStr: \n" + convertedStr);
    }

    public static void testRejectImgConvert(){
        System.out.println("testRejectImgConvert...");
        originalStr = "^https?:\\/\\/www\\.nfmovies\\.com\\/pic\\/tu\\/ url reject-img";
        convertedStr = "";
        System.out.println("originalStr: \n" + originalStr);
        convertedStr = originalStr.replace("url reject-img",ConfigGeneratorConstants.SURGE_REJECT_IMG_STR);
        System.out.println("convertedStr: \n" + convertedStr);
    }

    public static void testRejectConvert(){
        System.out.println("testRejectConvert...");
        originalStr = "^https?:\\/\\/gab\\.122\\.gov\\.cn\\/eapp\\/m\\/sysquery\\/adver url reject";
        convertedStr = "";
        System.out.println("originalStr: \n" + originalStr);
        convertedStr = originalStr.replace("url reject","- reject");
        System.out.println("convertedStr: \n" + convertedStr);
    }

    public static void testRejectDictConvert(){
        System.out.println("testRejectDictConvert...");
        originalStr = "^https?:\\/\\/mapi\\.ghsmpwalmart\\.com\\/api\\d\\/ec\\/navigation\\/page\\/getPageActivityTab url reject-dict";
        convertedStr = "";
        System.out.println("originalStr: \n" + originalStr);
        convertedStr = originalStr.replace("url reject-dict",ConfigGeneratorConstants.SURGE_REJECT_DICT_STR);
        System.out.println("convertedStr: \n" + convertedStr);
    }

    public static void testEchoResponseConvert(){
        System.out.println("testEchoResponseConvert...");
        originalStr = "^https?:\\/\\/mobile\\.cmbchina\\.com\\/dcardmanage\\/salary-welfare\\/get-recommend url echo-response application/json echo-response https://raw.githubusercontent.com/zirawell/R-Store/main/Res/Data/cmb.json";
        convertedStr = "";
        String regex1 = ConfigGeneratorConstants.QUANX_ECHO_RESPONSE_REGEX;
        String regex2 = ConfigGeneratorConstants.SURGE_ECHO_RESPONSE_REGEX;
        System.out.println("originalStr: \n" + originalStr);
        convertedStr = ConfigGeneratorUtils.scriptRegexReplace(regex1, regex2, originalStr);
        System.out.println("convertedStr: \n" + convertedStr);
    }

    public static void testScriptResponseBodyConvert(){
        System.out.println("testScriptResponseBodyConvert...");
        originalStr = "^https?:\\/\\/youtubei\\.googleapis\\.com\\/youtubei\\/v\\d\\/(browse|next|player|search|reel\\/reel_watch_sequence|guide|account\\/get_setting|get_watch) url script-response-body https://raw.githubusercontent.com/zirawell/R-Store/main/Res/Scripts/AntiAd/youtube.js";
        convertedStr = "";
        String regex1 = ConfigGeneratorConstants.QUANX_JS_BODY_REGEX;
        String regex2 = ConfigGeneratorConstants.SURGE_JS_BODY_REGEX;
        System.out.println("originalStr: \n" + originalStr);
        convertedStr = ConfigGeneratorUtils.scriptRegexReplace(regex1, regex2, originalStr);
        assert convertedStr != null;
        System.out.println("convertedStr: \n" + convertedStr
                .replace("type=http-script-response-body","type=http-response")
                .replace("type=http-script-request-body","type=http-request"));
    }

    public static void testScriptResponseHeaderConvert(){
        System.out.println("testScriptResponseHeaderConvert...");
        originalStr = "^https:\\/\\/m\\.airchina\\.com\\.cn\\/airchina\\/gateway\\/v\\d(\\.\\d)*\\/api\\/services url script-response-header https://raw.githubusercontent.com/zirawell/Ad-Cleaner/main/Collection/js/airchina.js";
        convertedStr = "";
        String regex1 = ConfigGeneratorConstants.QUANX_JS_HEADER_REGEX;
        String regex2 = ConfigGeneratorConstants.SURGE_JS_HEADER_REGEX;
        System.out.println("originalStr: \n" + originalStr);
        convertedStr = ConfigGeneratorUtils.scriptRegexReplace(regex1, regex2, originalStr);
        assert convertedStr != null;
        System.out.println("convertedStr: \n" + convertedStr
                .replace("type=http-script-response-header","type=http-response")
                .replace("type=http-script-request-header","type=http-request"));

    }

    public static void testUrlAndHeaderConvert(){
        System.out.println("testUrlAndHeaderConvert...");
        originalStr = "^https?:\\/\\/gw\\.xiaocantech\\.com\\/rpc .*(GetBannerList|IsShowOrderAwardPopup|UserLifeShopList|BrandBannerList|GetPromotionGlobalCfg) url-and-header reject-dict";
        convertedStr = "";
        String regex1 = ConfigGeneratorConstants.QUANX_URL_AND_HEADER_REGEX;
        String regex2 = ConfigGeneratorConstants.SURGE_URL_AND_HEADER_REGEX;
        System.out.println("originalStr: \n" + originalStr);
        convertedStr = ConfigGeneratorUtils.scriptRegexReplace(regex1, regex2, originalStr);
        System.out.println("convertedStr: \n" + convertedStr);
    }


    public static void testBodyRewriteRegex(){
        System.out.println("testBodyRewriteRegex...");
        originalStr = "^https?:\\/\\/mobile\\.yangkeduo\\.com\\/proxy\\/api\\/api\\/express\\/post\\/waybill\\/red_packet\\/goods_list$ url response-body \"list\":\\[.+\\] response-body \"list\":[]";
        convertedStr = "";
        String regex1 = ConfigGeneratorConstants.QUANX_BODY_REWRITE_REGEX;
        String regex2 = ConfigGeneratorConstants.SURGE_BODY_REWRITE_REGEX;
        System.out.println("originalStr: \n" + originalStr);
        convertedStr = ConfigGeneratorUtils.scriptRegexReplace(regex1, regex2, originalStr);
        assert convertedStr != null;
        System.out.println("convertedStr: \n" + convertedStr
                .replace("http-response-body","http-response")
                .replace("http-request-body","http-request"));
    }
}
