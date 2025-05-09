package com.zlog.config.generator.constants;

import com.zlog.config.generator.utils.ConfigGeneratorUtils;

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
    // 规则标识
    public static final String RULE_SIGN = "Rule";
    // 资源标识
    public static final String RES_SIGN = "Res";
    // 脚本标识
    public static final String SCRIPTS_SIGN = "Scripts";
    // Icon标识
    public static final String ICON_SIGN = "Icon";
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
    // 国家标识
    public static final String COUNTRY_SIGN = "country";
    // 供应商标识
    public static final String PROVIDER_SIGN = "provider";
    // 过滤器后缀
    public static final String FILTER_SUFFIX = "AdBlock.list";
    public static final String LIST_SIGN = ".list";
    // 重写文件后缀
    public static final String REWRITE_SUFFIX = "AdRewrite.conf";
    public static final String CONF_SIGN = ".conf";
    // Surge模块后缀
    public static final String SURGE_MODULE_SIGN = ".sgmodule";
    // 图片文件后缀
    public static final String IMAGE_SIGN = ".png";
    // macOS文件系统文件
    public static final String MACOS_FILE_SIGN = ".DS_Store";
    // ReadMe
    public static final String README_SIGN = "README.md";
    // File Separator
    public static final String FILE_SEPARATOR = "/";
    // Dot Sign
    public static final String DOT_SIGN = ".";
    // Git Sign
    public static final String GIT_SIGN = "Git";
    // Home路径
    public static final String HOME_DIRECTORY = ConfigGeneratorUtils.getHomeDirectory();
    // 项目根目录
    public static final String PROJECT_BASE_DIRECTORY = HOME_DIRECTORY
                                                        + FILE_SEPARATOR
                                                        + GIT_SIGN
                                                        + FILE_SEPARATOR
                                                        + PROJECT_NAME_SIGN
                                                        + FILE_SEPARATOR;
    // 文件路径配置
    public static final String QUANX_RULE_DIRECTORY =  PROJECT_BASE_DIRECTORY + RULE_SIGN + FILE_SEPARATOR + QUANX_SIGN;
    public static final String SURGE_RULE_DIRECTORY = PROJECT_BASE_DIRECTORY + RULE_SIGN + FILE_SEPARATOR + SURGE_SIGN;
    public static final String ICON_DIRECTORY = PROJECT_BASE_DIRECTORY + RES_SIGN + FILE_SEPARATOR + ICON_SIGN;
    public static final String SCRIPTS_DIRECTORY = PROJECT_BASE_DIRECTORY + RES_SIGN + FILE_SEPARATOR + SCRIPTS_SIGN;
    // Git分支
    public static final String GIT_BRANCH = ConfigGeneratorUtils.getBranchName();
    // GitHub base url prefix
    public static final String GITHUB_BASE_URL_PREFIX = "https://github.com/zirawell";
    // GitHub raw url prefix
    public static final String GITHUB_RAW_URL_PREFIX = "https://raw.githubusercontent.com/zirawell";
    // QuanX添加资源链接
    public static String QUANX_ADD_RES_URL = "https://quantumult.app/x/open-app/add-resource?remote-resource=url-encoded-json";
    // Telegram组群
    public static String TELEGRAM_GROUP = "https://t.me/lanjieguanggao";

    // App所在层级
    public static final Integer APP_LEVEL = 11;
    // Applet所在层级
    public static final Integer APPLET_LEVEL = 12;
    // Web所在层级
    public static final Integer WEB_LEVEL = 11;

    //项目根目录README中统计表开始行数
    public static final Integer ROOT_README_COUNT_TABLE_START_LINE = 41;

    // 输出文件配置
    public static final String[] INDEX_ARRAY = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#", "0-9" };
    public static final String[] DIR_ARRAY = new String[]{"Adblock", "Filter", "Plugin", "Redirect", "Revision" };

    // 不处理的App
    public static final String[] SKIP_APP = new String[]{"youtube","functional"};

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
            "- [@app2smile](https://github.com/app2smile)",
            "- [@blackmatrix7](https://github.com/blackmatrix7)",
            "- [@DivineEngine](https://github.com/DivineEngine)",
            "- [@KOP-XIAO](https://github.com/KOP-XIAO)",
            "- [@NobyDa](https://github.com/NobyDa)",
            "- [@Orz-3](https://github.com/Orz-3)",
            "- [@fmz200](https://github.com/fmz200)",
            "- [@RuCu6](https://github.com/RuCu6)",
            "- [@Sliverkiss](https://github.com/Sliverkiss)",
            "- [@luestr](https://github.com/luestr)",
            "- [@wf021325](https://github.com/wf021325)",
            "- [@Maasea](https://github.com/Maasea)",
            "- [@ClydeTime](https://github.com/ClydeTime)",
            "- [@FoKit](https://github.com/FoKit)",
            "- [@Yuheng0101](https://github.com/Yuheng0101)"
    };

    public static final String SURGE_RULE_AREA = "[Rule]\n";
    public static final String SURGE_URL_REWRITE_AREA = "[URL Rewrite]\n";
    public static final String SURGE_MAP_LOCAL_AREA = "[Map Local]\n";
    public static final String SURGE_BODY_REWRITE_AREA = "[Body Rewrite]\n";
    public static final String SURGE_HEADER_REWRITE_AREA = "[Header Rewrite]\n";
    public static final String SURGE_SCRIPT_AREA = "[Script]\n";
    public static final String SURGE_HOSTNAME_AREA = "[MITM]\n";

    // 此处的关键词是Surge的规则参数
    public static final String[] RULE_KEYWORDS = new String[]{
            // Domain-based Rule Parameters: extended-matching
            "DOMAIN",
            "DOMAIN-SUFFIX",
            "DOMAIN-KEYWORD",
            "DOMAIN-SET",
            // IP-based Rule Parameters: no-resolve
            "IP-CIDR",
            "IP-CIDR6",
            "GEOIP",
            "IP-ASN",
            // HTTP Rule
            "USER-AGENT",
            "URL-REGEX"
    };
    // 此处的关键词是QuanX的规则参数
    /**
     * [URL Rewrite] : reject,302
     * [Map Local] : echo-response,reject-img,reject-dict,reject-200
     * [Body Rewrite] : request-body,response-body
     * [Header Rewrite] : url-and-header
     * [Script] : script-response-body,script-response-header,script-request-body
     */
    public static final String[] URL_REWRITE_KEYWORDS = new String[]{
            "reject",
            "302"
    };

    public static final String[] MAP_LOCAL_KEYWORDS = new String[]{
            "echo-response",
            "reject-img",
            "reject-dict",
            "reject-200"
    };
    public static final String[] BODY_REWRITE_KEYWORDS = new String[]{
            "request-body",
            "response-body"
    };
    public static final String[] HEADER_REWRITE_KEYWORDS = new String[]{
            "url-and-header",
            "request-header"
    };
    public static final String[] SCRIPT_KEYWORDS = new String[]{
            "script-response-body",
            "script-response-header",
            "script-request-body",
            "script-request-header"
    };


    // Surge替换QuanX规则字符集
    public static final String SURGE_REJECT_STR = "- reject";
    public static final String SURGE_REJECT_DICT_STR = "data-type=text data=\"{}\" status-code=200";
    public static final String SURGE_REJECT_200_STR = "data-type=text data=\"\" status-code=200";
    public static final String SURGE_REJECT_IMG_STR = "data-type=tiny-gif status-code=200";

    // 正则表达式
    // QuanX
    public static final String QUANX_JS_BODY_REGEX = "^.*?\\surl\\sscript-(request|response)-body.*?\\s.*";//body-1
    public static final String QUANX_JS_HEADER_REGEX = "^.*?\\surl\\sscript-(response|request)-header.*?\\s.*";//body-0//echo body-0
    public static final String QUANX_HOSTNAME_REGEX = "hostname\\s?=\\s?.*"; //hostname
    public static final String QUANX_302_REGEX = "^.*?\\surl\\s302\\s.*";
    public static final String QUANX_ECHO_RESPONSE_REGEX = "^.*?\\surl\\secho-response\\s.*\\secho-response\\s.*";//
    public static final String QUANX_URL_AND_HEADER_REGEX = "^.*?\\s.*\\surl-and-header\\s.*";
    public static final String QUANX_BODY_REWRITE_REGEX = "^.*?\\surl\\s(request|response)-body\\s.*?\\s(request|response)-body\\s.*";

    // Surge
    public static final String SURGE_JS_BODY_REGEX = "type=http-$2,pattern=$0,requires-body=1,max-size=0,script-path=$3";
    public static final String SURGE_JS_HEADER_REGEX = "type=http-$2,pattern=$0,requires-body=0,max-size=0,script-path=$3";
    public static final String SURGE_HOSTNAME_REGEX = "hostname= %APPEND% $2";
    public static final String SURGE_302_REGEX = "$0 $3 302";
    public static final String SURGE_ECHO_RESPONSE_REGEX = "$0 data-type=file data=\"$5\"";
    public static final String SURGE_URL_AND_HEADER_REGEX = "http-request $0 header-replace-regex 'methodname' '$1' 'null'";
    public static final String SURGE_BODY_REWRITE_REGEX = "http-$2 $0 $3 $5";

    // Surge模块注释内容
    public static final String SURGE_COMMENT_TITLE =
            "#!name=[#1]\n" +
            "#!desc=[#2]\n" +
            "#!openUrl=https://apps.apple.com/app/id[#3]\n" +
            "#!author=zirawell[https://github.com/zirawell]\n" +
            "#!tag=去广告\n" +
            "#!system=iOS\n" +
            "#!surge_version=5.14.2(Build3405,SDK18.2)\n" +
            "#!homepage=https://github.com/zirawell/R-Store/tree/main/Rule/Surge/README.md\n" +
            "#!icon=https://raw.githubusercontent.com/zirawell/Git/R-Store/Res/Icon/App/[#4].png\n" +
            "#!date=[#5]";


}
