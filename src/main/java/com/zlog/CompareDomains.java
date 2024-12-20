package com.zlog;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @Description:
 * @Author: Dark Wang
 * @Create: 2024/10/24 15:40
 **/
@SuppressWarnings("unchecked,rawtypes")
public class CompareDomains {
    public static void main(String[] args) {
        String a = "abbyychina.com,aicoin.cn,alienskins.cn,anydeskchina.cn,app.biliintl.com,bartender.cc,beyondcompare.cc,bingdianhuanyuan.cn,chemdraw.com.cn,cn.ultraiso.net,codesoftchina.com,coreldrawchina.com,crossoverchina.com,ditu.google.cn,dongmansoft.com,earmasterchina.cn,easyrecoverychina.com,ediuschina.com,firefox.com.cn,flstudiochina.com,folxchina.cn,formysql.com,g.cn,google.cn,guitarpro.cc,huishenghuiying.com.cn,hypeapp.cn,hypersnap.mairuan.com,iconworkshop.cn,idmchina.net,ign.xn--fiqs8s,imazingchina.com,imindmap.cc,jd.com,jihehuaban.com.cn,kingdeecn.cn,logoshejishi.com,logoshejishi.mairuan.com,luping.net.cn,map.google.cn,mathtype.cn,mi.com,mindmanager.cc,mindmanager.cn,mindmapper.cc,mycleanmymac.com,nicelabel.cc,ntfsformac.cn,officesoftcn.com,overturechina.com,passport.biliintl.com,passwordrecovery.cn,pdfexpert.cc,photozoomchina.com,shankejingling.com,suning.com,taobao.com,vegaschina.cn,www.abbyychina.com,www.aicoin.cn,www.alienskins.cn,www.anydeskchina.cn,www.bartender.cc,www.betterzip.com,www.betterzip.net,www.betterzipcn.com,www.betterzipcn.net,www.beyondcompare.cc,www.bingdianhuanyuan.cn,www.chemdraw.com.cn,www.codesoftchina.com,www.coreldrawchina.com,www.crossoverchina.com,www.dongmansoft.com,www.earmasterchina.cn,www.easyrecoverychina.com,www.ediuschina.com,www.firefox.com.cn,www.flstudiochina.com,www.folxchina.cn,www.formysql.com,www.g.cn,www.google.cn,www.guitarpro.cc,www.huishenghuiying.com.cn,www.hypeapp.cn,www.iconworkshop.cn,www.idmchina.net,www.ign.xn--fiqs8s,www.imazingchina.com,www.imindmap.cc,www.jd.com,www.jihehuaban.com.cn,www.kingdeecn.cn,www.logoshejishi.com,www.luping.net.cn,www.mathtype.cn,www.mi.com,www.mindmanager.cc,www.mindmanager.cn,www.mindmapper.cc,www.mycleanmymac.com,www.nicelabel.cc,www.ntfsformac.cn,www.officesoftcn.com,www.overturechina.com,www.passwordrecovery.cn,www.pdfexpert.cc,www.photozoomchina.com,www.shankejingling.com,www.suning.com,www.xitongqingli.com,www.xshellcn.com,www.yhd.com,www.yuanchengxiezuo.com,www.zbrushcn.com,xitongqingli.com,xshellcn.com,yhd.com,you.163.com,yuanchengxiezuo.com,zbrushcn.com";
        String b = "app.biliintl.com, passport.biliintl.com, betterzipcn.com, betterzipcn.net, betterzip.com, betterzip.net, ditu.google.cn, maps.google.cn, g.cn, google.cn, mycleanmymac.com, xitongqingli.com, abbyychina.com, aicoin.cn, alienskins.cn, anydeskchina.cn, bartender.cc, beyondcompare.cc, bingdianhuanyuan.cn, chemdraw.com.cn, cn.ultraiso.net, codesoftchina.com, coreldrawchina.com, crossoverchina.com, dongmansoft.com, earmasterchina.cn, easyrecoverychina.com, ediuschina.com, firefox.com.cn, flstudiochina.com, folxchina.cn, formysql.com, guitarpro.cc, huishenghuiying.com.cn, hypeapp.cn, hypersnap.mairuan.com, iconworkshop.cn, idmchina.net, ign.xn--fiqs8s, imazingchina.com, imindmap.cc, jd.com, jihehuaban.com.cn, kingdeecn.cn, logoshejishi.com, logoshejishi.mairuan.com, luping.net.cn, mathtype.cn, mi.com, mindmanager.cn, mindmanager.cc, mindmapper.cc, nicelabel.cc, ntfsformac.cn, officesoftcn.com, overturechina.com, passwordrecovery.cn, pdfexpert.cc, photozoomchina.com, shankejingling.com, suning.com, taobao.com, vegaschina.cn, www.betterzipcn.com, www.betterzipcn.net, www.betterzip.com, www.betterzip.net, www.google.cn, www.g.cn, www.mycleanmymac.com, www.xitongqingli.com, www.abbyychina.com, www.aicoin.cn, www.alienskins.cn, www.anydeskchina.cn, www.bartender.cc, www.beyondcompare.cc, www.bingdianhuanyuan.cn, www.chemdraw.com.cn, www.codesoftchina.com, www.coreldrawchina.com, www.crossoverchina.com, www.dongmansoft.com, www.earmasterchina.cn, www.easyrecoverychina.com, www.ediuschina.com, www.firefox.com.cn, www.flstudiochina.com, www.folxchina.cn, www.formysql.com, www.guitarpro.cc, www.huishenghuiying.com.cn, www.hypeapp.cn, www.iconworkshop.cn, www.idmchina.net, www.ign.xn--fiqs8s, www.imazingchina.com, www.imindmap.cc, www.jd.com, www.jihehuaban.com.cn, www.kingdeecn.cn, www.logoshejishi.com, www.luping.net.cn, www.mathtype.cn, www.mi.com, www.mindmanager.cc, www.mindmanager.cn, www.mindmapper.cc, www.nicelabel.cc, www.ntfsformac.cn, www.officesoftcn.com, www.overturechina.com, www.passwordrecovery.cn, www.pdfexpert.cc, www.photozoomchina.com, www.shankejingling.com, www.suning.com, www.taobao.com, www.vegaschina.cn, www.xshellcn.com, www.yhd.com, www.yuanchengxiezuo.com, www.zbrushcn.com, xshellcn.com, yhd.com, you.163.com, yuanchengxiezuo.com, zbrushcn.com";

        String[] aArr = a.split(",");
        String[] bArr = b.split(", ");

        HashSet aSet = new HashSet(Arrays.asList(aArr));
        HashSet bSet = new HashSet(Arrays.asList(bArr));
        System.out.println("aSet:" + aSet.size());
        System.out.println("bSet:" + bSet.size());
        for (String s:bArr){
            if(!aSet.contains(s)){
                System.out.println("bArr:" + s);
            }
        }

        for (String s:aArr){
            if(!bSet.contains(s)){
                System.out.println("aArr:" + s);
            }
        }
    }
}
