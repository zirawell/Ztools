package com.zlog;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Dark Wang
 * @Create: 2024/10/24 10:52
 **/
public class HostnameDrawer {
    static StringBuffer surgeSb = new StringBuffer();
    static StringBuffer hostNameSb = new StringBuffer();
    static HashSet<String> hostSet = new HashSet<>();
    static String quanXRewriteInput = "(^https?:\\/\\/app\\.biliintl\\.com\\/(x\\/)?(intl|dm|reply|history|v\\d\\/(fav|msgfeed)).+?)(&s_locale=zh-Hans_[A-Z]{2})(.+?)(&sim_code=\\d+)(.+) url 302 $1&s_locale=zh-Hans_PH$6&sim_code=51503$8\n" +
            "(^https?:\\/\\/passport\\.biliintl\\.com\\/x\\/intl\\/passport-login\\/.+)(&s_locale=zh-Hans_[A-Z]{2})(.+)(&sim_code=\\d+)(.+) url 302 $1&s_locale=zh-Hans_PH$35&sim_code=51503$5\n" +
            "\n" +
            "# Redirect Google Search Service\n" +
            "^https?:\\/\\/(www.)?(g|google)\\.cn url 302 https://www.google.com\n" +
            "^https?:\\/\\/(ditu|maps).google\\.cn url 302 https://maps.google.com\n" +
            "\n" +
            "# Redirect HTTP to HTTPS\n" +
            "^http:\\/\\/(www.)?aicoin\\.cn\\/$ url 302 https://www.aicoin.com/\n" +
            "^http:\\/\\/(www.)?taobao\\.com\\/ url 302 https://taobao.com/\n" +
            "^http:\\/\\/(www.)?jd\\.com\\/ url 302 https://www.jd.com/\n" +
            "^http:\\/\\/(www.)?mi\\.com\\/ url 302 https://www.mi.com/\n" +
            "^http:\\/\\/you\\.163\\.com\\/ url 302 https://you.163.com/\n" +
            "^http:\\/\\/(www.)?suning\\.com\\/ url 302 https://suning.com/\n" +
            "^http:\\/\\/(www.)?yhd\\.com\\/ url 302 https://yhd.com/\n" +
            "\n" +
            "# Redirect False to True\n" +
            "# > IGN China to IGN Global\n" +
            "^https?:\\/\\/(www.)?ign\\.xn--fiqs8s\\/ url 302 https://cn.ign.com/ccpref/us\n" +
            "# > Fake Website Made By C&J Marketing\n" +
            "^https?:\\/\\/(www.)?abbyychina\\.com\\/ url 302 https://www.abbyy.cn/\n" +
            "^https?:\\/\\/(www.)?alienskins\\.cn\\/ url 302 https://exposure.software/\n" +
            "^https?:\\/\\/(www.)?anydeskchina\\.cn\\/ url 302 https://anydesk.com/zhs\n" +
            "^https?:\\/\\/(www.)?bartender\\.cc\\/ url 302 https://www.macbartender.com/\n" +
            "^https?:\\/\\/(www.)?(betterzipcn|betterzip)\\.(com|net)\\/ url 302 https://macitbetter.com/\n" +
            "^https?:\\/\\/(www.)?beyondcompare\\.cc\\/ url 302 https://www.scootersoftware.com/\n" +
            "^https?:\\/\\/(www.)?bingdianhuanyuan\\.cn\\/ url 302 https://www.faronics.com/zh-hans/\n" +
            "^https?:\\/\\/(www.)?chemdraw\\.com\\.cn\\/ url 302 https://www.perkinelmer.com.cn/\n" +
            "^https?:\\/\\/(www.)?codesoftchina\\.com\\/ url 302 https://www.teklynx.com/\n" +
            "^https?:\\/\\/(www.)?coreldrawchina\\.com\\/ url 302 https://www.coreldraw.com/cn/\n" +
            "^https?:\\/\\/(www.)?crossoverchina\\.com\\/ url 302 https://www.codeweavers.com/\n" +
            "^https?:\\/\\/(www.)?dongmansoft\\.com\\/ url 302 https://www.udongman.cn/\n" +
            "^https?:\\/\\/(www.)?earmasterchina\\.cn\\/ url 302 https://www.earmaster.com/\n" +
            "^https?:\\/\\/(www.)?easyrecoverychina\\.com\\/ url 302 https://www.ontrack.com/\n" +
            "^https?:\\/\\/(www.)?ediuschina\\.com\\/ url 302 https://www.grassvalley.com/\n" +
            "^https?:\\/\\/(www.)?firefox\\.com\\.cn\\/(download\\/)?$ url 302 https://www.mozilla.org/zh-CN/firefox/new/\n" +
            "^https?:\\/\\/(www.)?flstudiochina\\.com\\/ url 302 https://www.image-line.com/\n" +
            "^https?:\\/\\/(www.)?folxchina\\.cn\\/ url 302 https://mac.eltima.com/cn/download-manager.html\n" +
            "^https?:\\/\\/(www.)?formysql\\.com\\/ url 302 https://www.navicat.com.cn/\n" +
            "^https?:\\/\\/(www.)?guitarpro\\.cc\\/ url 302 https://www.guitar-pro.com/\n" +
            "^https?:\\/\\/(www.)?huishenghuiying\\.com\\.cn\\/ url 302 https://www.coreldraw.com/cn/\n" +
            "^https?:\\/\\/(www.)?hypeapp\\.cn\\/ url 302 https://tumult.com/hype/\n" +
            "^https?:\\/\\/hypersnap\\.mairuan\\.com\\/ url 302 https://www.hyperionics.com/\n" +
            "^https?:\\/\\/(www.)?iconworkshop\\.cn\\/ url 302 https://www.axialis.com/\n" +
            "^https?:\\/\\/(www.)?idmchina\\.net\\/ url 302 https://www.internetdownloadmanager.com/\n" +
            "^https?:\\/\\/(www.)?imazingchina\\.com\\/ url 302 https://imazing.com/zh\n" +
            "^https?:\\/\\/(www.)?imindmap\\.cc\\/ url 302 https://www.ayoa.com/previously-imindmap/\n" +
            "^https?:\\/\\/(www.)?jihehuaban\\.com\\.cn\\/ url 302 https://www.chartwellyorke.com/sketchpad/x24795.html\n" +
            "^https?:\\/\\/(www.)?kingdeecn\\.cn\\/ url 302 http://www.kingdee.com/\n" +
            "^https?:\\/\\/(www.)?logoshejishi\\.com\\/ url 302 https://www.sothink.com/product/logo-design-software/\n" +
            "^https?:\\/\\/logoshejishi\\.mairuan\\.com\\/ url 302 https://www.sothink.com/product/logo-design-software/\n" +
            "^https?:\\/\\/(www.)?luping\\.net\\.cn\\/ url 302 https://www.techsmith.com/\n" +
            "^https?:\\/\\/(www.)?mathtype\\.cn\\/ url 302 https://www.dessci.com/\n" +
            "^https?:\\/\\/(www.)?mindmanager\\.(cc|cn)\\/ url 302 https://www.mindjet.com/cn/\n" +
            "^https?:\\/\\/(www.)?mindmapper\\.cc\\/ url 302 https://www.mindmapper.com/\n" +
            "^https?:\\/\\/(www.)?(mycleanmymac|xitongqingli)\\.com\\/ url 302 https://macpaw.com/\n" +
            "^https?:\\/\\/(www.)?nicelabel\\.cc\\/ url 302 https://www.nicelabel.com/zh/\n" +
            "^https?:\\/\\/(www.)?ntfsformac\\.cn\\/ url 302 https://china.paragon-software.com/home-mac/ntfs-for-mac/\n" +
            "^https?:\\/\\/(www.)?officesoftcn\\.com\\/ url 302 https://www.microsoft.com/zh-cn/microsoft-365\n" +
            "^https?:\\/\\/(www.)?overturechina\\.com\\/ url 302 https://sonicscores.com/\n" +
            "^https?:\\/\\/(www.)?passwordrecovery\\.cn\\/ url 302 https://cn.elcomsoft.com/aopr.html\n" +
            "^https?:\\/\\/(www.)?pdfexpert\\.cc\\/ url 302 https://pdfexpert.com/zh\n" +
            "^https?:\\/\\/(www.)?photozoomchina\\.com\\/ url 302 https://www.benvista.com/\n" +
            "^https?:\\/\\/(www.)?shankejingling\\.com\\/ url 302 https://www.sothink.com/product/flashdecompiler/\n" +
            "^https?:\\/\\/cn\\.ultraiso\\.net\\/ url 302 https://cn.ezbsystems.com/ultraiso/\n" +
            "^https?:\\/\\/(www.)?vegaschina\\.cn\\/ url 302 https://www.vegascreativesoftware.com/\n" +
            "^https?:\\/\\/(www.)?xshellcn\\.com\\/ url 302 https://www.netsarang.com/zh/xshell/\n" +
            "^https?:\\/\\/(www.)?yuanchengxiezuo\\.com\\/ url 302 https://www.teamviewer.com/\n" +
            "^https?:\\/\\/(www.)?zbrushcn\\.com\\/ url 302 https://pixologic.com/";
    public static void main(String[] args) {
        String[] line = quanXRewriteInput.split("\n");
        for(String s: line){
            if(s.contains("#") || "".equals(s.trim())){
                surgeSb.append(s);
                continue;
            }else{
                s = s.substring(0, s.indexOf(" url")+1);
                s = s.replace("^https?:\\/\\/","").replace("^http:\\/\\/","").replace("\\/","");
                if(s.contains("biliintl")){
                    continue;
                }else{
                    if(s.contains("(www.)?")){
                        s = s.replace("(www.)?","");
                        s = s.replace("\\.",".");
                        hostSet.add(s);
                        hostSet.add("www."+ s);
                    }else{
                        hostSet.add(s.replace("\\.","."));
                    }
                }


            }

        }

         List<String> collect = hostSet.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        for(String host:collect){
            System.out.println(host);
        }


    }
}
