package com.zlog.config.generator;

/**
 * @Description: 配置文件常量
 * @Author: Dark Wang
 * @Create: 2024/12/20 16:23
 **/
public class ConfigGeneratorConstants {
    // ---------------------各类标识------------------------
    // 项目名称标识
    public static final String PROJECT_NAME_SIGN = "R-Store";
    // QuanX标识
    public static final String QUANX_SIGN = "QuanX";
    // Surge标识
    public static final String SURGE_SIGN = "Surge";
    // 集合标识
    public static final String COLLECTION_SIGN = "All";
    // 广告标识
    public static final String ADBLOCK_SIGN = "Adblock";
    // 全部标识
    public static final String ALL_SIGN = "all";
    // App标识
    public static final String APP_SIGN = "app";
    // 微信标识
    public static final String WECHAT_SIGN = "wechat";
    // 支付宝标识
    public static final String ALIPAY_SIGN = "alipay";
    // 小程序标识
    public static final String APPLET_SIGN = "applet";
    // WEB标识
    public static final String WEB_SIGN = "web";
    // 过滤器标识
    public static final String FILTER_SIGN = "filter";
    // 重写标识
    public static final String REWRITE_SIGN = "rewrite";
    // 过滤器后缀
    public static final String FILTER_SUFFIX = "AdBlock.list";
    public static final String LIST_SIGN = ".list";
    // 重写文件后缀
    public static final String REWRITE_SUFFIX = "AdRewrite.conf";
    public static final String CONF_SIGN = ".conf";
    // Surge模块后缀
    public static final String SURGE_MODULE_SIGN = ".sgmodule";
    // ReadMe
    public static final String README_SIGN = "README.md";
    // File Separator
    public static final String FILE_SEPARATOR = "/";
    // 文件路径配置
    public static final String QUANX_RULE_DIRECTORY = "/Users/zirawell/Git/R-Store/Rule/QuanX";
    public static final String SURGE_RULE_DIRECTORY = "/Users/zirawell/Git/R-Store/Rule/Surge";

    // App所在层级
    public static final Integer APP_LEVEL = 11;
    // Applet所在层级
    public static final Integer APPLET_LEVEL = 12;
    // Web所在层级
    public static final Integer WEB_LEVEL = 11;

    // 输出文件配置
    public static final String[] INDEX_ARRAY = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#", "0-9" };
    public static final String[] DIR_ARRAY = new String[]{"Adblock", "Filter", "Plugin", "Redirect", "Revision" };

    // 不处理的App
    public static final String[] SKIP_APP = new String[]{"youtube"};

    // 索引表
    public static final String INDEX_TABLE = "\n" +
            "| I  | N  | D  | X  |\n" +
            "|----|----|----|----|\n" +
            "| #A  | #B  | #C  | #D  |\n" +
            "| #E  | #F  | #G  | #H  |\n" +
            "| #I  | #J  | #K  | #L  |\n" +
            "| #M  | #N  | #O  | #P  |\n" +
            "| #Q  | #R  | #S  | #T  |\n" +
            "| #U  | #V  | #W  | #X  |\n" +
            "| #Y  | #Z  | ##  | #0-9|" +
            "\n";

    // 统计表
    public static final String COUNT_TABLE = "\n" +
            "| Type | Count |\n" +
            "|----------|----------|\n" +
            "| All    | #AllCount |\n" +
            "| App    | #AppCount |\n" +
            "| Wechat Applet| #WechatCount |\n" +
            "| Alipay Applet| #AlipayCount |\n" +
            "| Web    | #WebCount |\n";

    // 感谢名单
    public static final String[] THANKS_TO_ARRAY = new String[]{
            "[@app2smile](https://github.com/app2smile)",
            "[@blackmatrix7](https://github.com/blackmatrix7)",
            "[@DivineEngine](https://github.com/DivineEngine)",
            "[@KOP-XIAO](https://github.com/KOP-XIAO)",
            "[@NobyDa](https://github.com/NobyDa)",
            "[@Orz-3](https://github.com/Orz-3)",
            "[@fmz200](https://github.com/fmz200)",
            "[@RuCu6](https://github.com/RuCu6)",
            "[@Sliverkiss](https://github.com/Sliverkiss)",
            "[@luestr](https://github.com/luestr)",
            "[@wf021325](https://github.com/wf021325)",
            "[@Maasea](https://github.com/Maasea)",
            "[@ClydeTime](https://github.com/ClydeTime)",
            "[@FoKit](https://github.com/FoKit)",
            "[@Yuheng0101](https://github.com/Yuheng0101)"
    };

    // 正则表达式
    public static final String QUANX_JS_BODY_REGEX = "^.*?\\surl\\s.*?(response|request)-body.*?\\s.*?\\.js";//body-1
    public static final String QUANX_JS_HEADER_REGEX = "^.*?\\surl\\s.*?(response|request)-header.*?\\s.*?\\.js";//body-0
    public static final String QUANX_JS_ANALYZE_ECHO_REGEX ="^.*?\\surl\\s.*?script-analyze-echo-response.*?\\s.*?\\.js";//echo body-1
    public static final String QUANX_JS_ECHO_REGEX = "^.*?\\surl\\s.*?script-echo-response.*?\\s.*?\\.js";//echo body-0
    public static final String QUANX_HOSTNAME_REGEX = "^hostname\\s?=\\s?.*"; //hostname

    public static final String SURGE_JS_BODY_REGEX = "^http-$2,pattern=$1,requires-body=1,max-size=0,script-path=$3$4.js";
    public static final String SURGE_JS_HEADER_REGEX = "^http-$2,pattern=$1,requires-body=0,max-size=0,script-path=$3$4.js";
    public static final String SURGE_JS_ANALYZE_ECHO_REGEX = "^http-request,pattern=$1,requires-body=1,max-size=0,script-path=$2$3.js";
    public static final String SURGE_JS_ECHO_REGEX = "^http-request,pattern=$1,requires-body=0,max-size=0,script-path=$2$3.js";
    public static final String SURGE_HOSTNAME_REGEX = "^hostname= %APPEND% $1";



}
