package com.zlog;

import com.zlog.config.generator.constants.ConfigGeneratorConstants;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Description:
 * @Author: Dark Wang
 * @Create: 2023/5/11 16:12
 **/
public class ListGenerater {
    public static final String baseDirectory = "/Users/zirawell/Developer/WorkSpace/IntelliJ/picc_market";
    public static final String outputDirectory = "/Users/zirawell/Downloads/test";


    public static StringBuffer MFileSb = new StringBuffer();
    public static StringBuffer CronFileSb = new StringBuffer();
    public static StringBuffer TFileSb = new StringBuffer();

    public static Set<String> MFileSet = new HashSet<>();
    public static Set<String> CronFileSet = new HashSet<>();
    public static Set<String> TFileSet = new HashSet<>();

    public static String[] MArray = {
            "MD5Util", "MLogger", "MMember", "MMethod",
            "MSUtils", "MacData", "MacTest", "MainKey", "Manager",
            "Mandate", "MapInfo", "MapItem", "MapType", "MapUtil",
            "Mapping", "Maps$10", "Maps$11", "MaskBit", "Matcher",
            "Matches", "Maven$1", "MaxCore", "Maybe$1", "Measure",
            "MemoKey", "Message", "Meter$1", "Metered", "Methods",
            "Metrics", "MinusOp", "Minutes", "Mixin$1", "MockMvc",
            "MockRef", "Mockito", "ModUtil", "Modules", "MongoDS",
            "Moniker", "Monitor", "MonoAll", "MonoAny", "MonoLog",
            "MonoMap", "MonoZip", "Mont256", "MonthDV", "Morpher",
            "MqttQoS", "Mutable", "MysqlIO","Md5Util", "MngBean"

    };
    public static String[] TArray = {
            "TEA$ECB", "TEATest", "TIFFIFD", "TIFFTag", "TImport", "TLSUtil", "TLadder", "TNSFile", "TResult", "TSPUtil",
            "TSTInfo", "TTCData", "TTree$1", "TabStop", "Table$1", "TagBits", "TagList", "TagMojo", "TagName", "TagTest",
            "Tainted", "TarFile", "Targets", "TaskJob", "TempDir", "Terms$1", "Ternary", "TestAll", "TestRun", "TestTag",
            "TextBox", "TextCap", "TextRun", "TextTag", "Textbox", "Timeout", "Timer$1", "Timer$2", "Timeval", "TinyLog",
            "TlbBase", "TlbEnum", "TlsPeer", "Token$1", "TokenId", "Toolbox", "TopDocs", "TreeBag", "TreeMap", "TreeSet",
            "Trigger", "Tunable", "Twofish", "TxUtils", "Type$10", "Type$11", "Type$12", "Type$13", "Type$14", "Type$15",
            "Type$16", "Type$17", "Type$18", "Type$19", "TypeIds", "TypeKey", "TypeLib", "TypeRef", "TypeTag", "Types$1",
            "Types$2", "Types$3"
    };

    static String origin = "获取主密钥,M000001,获取工作密钥,M000002,获取主密钥和工作密钥,M000003,生成图片验证码,M000011,生成短信验证码,M000012," +
                    "验证短信验证码,M000021,用户登录,M001001,修改密码,M001002,重置密码,M001003,退出登录,M001004,首次登录修改密码,M001005," +
                    "内部渠道维护列表,M005001,系统参数列表,M001010,系统参数修改,M001011,数据字典列表,M001021,数据字典-新增,M001022," +
                    "数据字典-修改,M001023,数据字典子项-查看,M001024,数据字典子项-新增,M001025,数据字典子项-修改,M001026,查询所有的数据字典项," +
                    "M001027,查询机构列表,M001031,查询部门列表,M001041,新增部门,M001042,修改部门,M001043,删除部门,M001044,查询系统操作日志列表," +
                    "M001051,查询用户列表,M002001,查询用户详情,M002002,新增用户,M002003,修改用户,M002004,删除用户,M002005,冻结/启用用户," +
                    "M002006,查询角色列表,M002011,查询角色详情,M002012,新增角色,M002013,修改角色,M002014,删除角色,M002015,冻结/启用角色," +
                    "M002016,查询权限节点,M002017,审批流列表,M009001,审批流详情,M009002,审批流新增,M009003,审批流修改,M009004,审批流删除," +
                    "M009005,我的已办列表,M004001,我的待办列表,M004002,审核详情,M004003,审核,M004004,实物派发列表查询,M021041,发货地址查询," +
                    "M021042,维护运单号,M021043,活动工作台,M022030,我的活动,M022041,模板活动列表,M022031,模板活动详情,M022032,模板活动新增（签到、抽奖）," +
                    "M022033,模板活动修改（签到、抽奖）,M022034,模板活动启停,M022035,模板活动删除,M022036,模板活动审核,M022037,签到活动页面访问数据查询," +
                    "M099030,签到活动效果查询,M099031,签到活动效果明细查询,M099032,签到活动明细查询,M099033,签到活动参与情况查询,M099034,抽奖活动页面访问数据查询," +
                    "M099040,抽奖活动效果查询,M099041,抽奖活动效果明细查询,M099042,抽奖活动明细查询,M099043,抽奖活动参与情况查询,M099044,广告图配置," +
                    "M011091,广告图详情查看,M011092,轮播图配置,M011011,轮播图详情查看,M011012,栏位详情查询,M011071,栏位修改,M011072,栏位审核," +
                    "M011073,权益统计报表,M014067,活动权益报表,M014068,供应商列表,M021001,供应商详情,M021002,供应商新增,M021003,供应商修改," +
                    "M021004,供应商删除,M021005,供应商提交审核,M021006,供应商启停,M021007,模板库管理,M022021,模板详情,M022022,模板新增,M022023,模板修改," +
                    "M022024,模板删除,M022025,模板启停,M022026,模板提交审批,M022027,权益列表查询,M022011,权益详情,M022012,权益新增,M022013,权益修改," +
                    "M022014,权益删除,M022015,冻结与启用,M022016,权益提交审核,M02201";

    public static void main(String[] args) {
        File sourceDirectory = new File(baseDirectory);
        File jarDirectory = new File("/Users/zirawell/.m2/market-repository");
        //Output Config Files
        try {
            //outputFileForAll
            processFile(sourceDirectory);
            processJar(jarDirectory);
            outputFile(outputDirectory,false);

        }catch (Exception e){
            e.printStackTrace();
        }
    }




    private static void outputFile(String filePath,boolean appendFlag) {
        //Set to List and Sort
        List<String> Mlist = new ArrayList<>(MFileSet);
        Collections.sort(Mlist);
        List<String> Cronlist = new ArrayList<>(CronFileSet);
        Collections.sort(Cronlist);
        List<String> Tlist = new ArrayList<>(TFileSet);
        Collections.sort(Tlist);

        String[] split = origin.split(",");
        HashMap<String,String> map = new HashMap<>();
        for (int i = 0; i < split.length; i++) {
            if((i+1)%2 == 0){
                map.put(split[i],split[i-1]);
            }
        }

        for(String file : Mlist){
            MFileSb.append(file + "-" + map.get(file) + "\n");
        }
        for(String file : Cronlist){
            CronFileSb.append(file + "-" + map.get(file) + "\n");
        }
        for(String file : Tlist){
            TFileSb.append(file + "-" + map.get(file) + "\n");
        }

        FileOutputStream out = null;
        try {
            File Mfile = new File(filePath + ConfigGeneratorConstants.FILE_SEPARATOR + "MFile.txt");
            File Cronfile = new File(filePath + ConfigGeneratorConstants.FILE_SEPARATOR + "CronFile.txt");
            File Tfile = new File(filePath + ConfigGeneratorConstants.FILE_SEPARATOR + "TFile.txt");
            if (!Mfile.exists()) {
                Mfile.createNewFile();
            }
            if (!Cronfile.exists()) {
                Cronfile.createNewFile();
            }
            if (!Tfile.exists()) {
                Tfile.createNewFile();
            }
            out = new FileOutputStream(Mfile,appendFlag);
            out.write(MFileSb.toString().getBytes());

            out = new FileOutputStream(Cronfile,appendFlag);
            out.write(CronFileSb.toString().getBytes());

            out = new FileOutputStream(Tfile,appendFlag);
            out.write(TFileSb.toString().getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void processFile(File file) throws IOException {
        if (file.isDirectory() && file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    processFile(f);
                }
            }
        }
        processData(file);
    }

    private static void processJar(File file) throws IOException {
        if (file.isDirectory() && file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    processJar(f);
                }
            }
        }
         processJarData(file);
    }

    private static void processJarData(File file) throws IOException {
        String fileName = file.getName();
        String tempName = null;
        if (fileName.endsWith(".jar")) {
            JarFile jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                entryName = entryName.substring(entryName.lastIndexOf(ConfigGeneratorConstants.FILE_SEPARATOR)+1);
                if (entryName.endsWith(".java") || entryName.endsWith(".class")) {
                    tempName = entryName.replace(".java","").replace(".class","");
                    System.out.println("jar entryName:" + entryName);
                    if(entryName.startsWith("M") && tempName.length()==7 && !ArrayUtils.contains(MArray,tempName)){
                        MFileSet.add(tempName);
                    }else if(entryName.startsWith("T") && tempName.length()==7 && !ArrayUtils.contains(TArray,tempName)){
                        TFileSet.add(tempName);
                    }else if(entryName.startsWith("Crontab") && tempName.length()==11){
                        CronFileSet.add(tempName);
                    }
                }
            }
        }
    }

    private static void processData(File file) throws IOException {
        String fileName = file.getName();
        String tempName = null;
        if (fileName.endsWith(".java") || fileName.endsWith(".class")) {
            tempName = fileName.replace(".java","").replace(".class","");
            if(fileName.startsWith("M") && tempName.length()==7 && !tempName.equals("Md5Util") && !tempName.equals("MngBean")){
                MFileSet.add(tempName);
            }else if(fileName.startsWith("T") && tempName.length()==7){
                TFileSet.add(tempName);
            }else if(fileName.startsWith("Crontab") && tempName.length()==11){
                CronFileSet.add(tempName);
            }
        }






    }
}
