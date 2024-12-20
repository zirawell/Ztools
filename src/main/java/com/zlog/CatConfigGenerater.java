package com.zlog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @Description:
 * @Author: Dark Wang
 * @Create: 2023/5/11 16:12
 **/
public class CatConfigGenerater {
    public static final String a = "'ShellBoxKit': { name: 'pro', id: 'ShellBoxKit.Lifetime', cm: 'sjb' },  //CareServer-服务器监控\n" +
            "\t'IDM': { name: 'premium', id: 'sub_yearly_idm', cm: 'sja' },  //IDM-下载\n" +
            "\t'Whisper': { name: 'all_features', id: 'whisperai_80_y', cm: 'sja' },  //Whisper\n" +
            "\t'Shapy': { name: 'premium', id: 'com.blake.femalefitness.subscription.yearly', cm: 'sja' },  //Shapy-健身\n" +
            "\t'Carbon-iOS': { name: 'pro', id: 'carbon.unlockall', cm: 'sjb' },  //Carbon-碳\n" +
            "\t'%E6%89%8B%E6%8C%81%E5%BC%B9%E5%B9%95': { name: 'Pro access', id: 'com.tech.LedScreen.VIPALL', cm: 'sjb' },  //手持弹幕\n" +
            "\t'%E8%AF%AD%E9%9F%B3%E8%AE%A1%E7%AE%97%E5%99%A8': { name: 'Pro access', id: 'com.tech.counter.All', cm: 'sjb' },  //语音计算器\n" +
            "\t'%E7%BE%8E%E5%A6%86%E6%97%A5%E5%8E%86': { name: 'Pro access', id: 'com.tech.Aula.VIPALL', cm: 'sjb' },  //美妆日历\n" +
            "\t'LiveWallpaper': { name: 'Pro access', id: 'com.tech.LiveWallpaper.ALL', cm: 'sjb' },  //动态壁纸\n" +
            "\t'Chat%E7%BB%83%E5%8F%A3%E8%AF%AD': { name: 'Pro access', id: 'com.tech.AiSpeak.All', cm: 'sjb' },  //Chat练口语\n" +
            "\t'dtdvibe': { name: 'pro', id: 'com.dtd.aroundu.life', cm: 'sjb' },  //DtdSounds-睡眠白噪音\n" +
            "\t'Clipboard': { name: 'Premium', id: 'Premium_0_99_1M_1MFree', cm: 'sja' },  //Clipboard-剪贴板\n" +
            "\t'Hi%E8%AE%BA%E5%9D%9B/69': { name: 'plus', id: 'plus_yearly', cm: 'sja' },  //Hi论坛\n" +
            "\t'AnimeArt': { name: 'AnimeArt.Gold', id: 'WaifuArt.Lifetime', cm: 'sjb' },  //Anime Waifu-AI\n" +
            "\t'LiveCaption': { name: 'Plus', id: 'rc_0400_1m', cm: 'sja' },  //iTranscreen-屏幕/游戏翻译\n" +
            "\t'EraseIt': { name: 'ProVersionLifeTime', id: 'com.uzero.cn.eraseit.premium1.fromyear', cm: 'sjb' },  //Smoothrase-AI擦除照片中任何物体\n" +
            "\t'MusicPutty': { name: 'Premium', id: 'mp_2999_1y', cm: 'sja' },  //MusicPutty\n" +
            "\t'SleepDown': { name: 'Pro', id: 'pro_student_0926', cm: 'sjb' },  //StaySleep-熬夜助手\n" +
            "\t'PhotoRoom': { name: 'pro', id: 'com.background.pro.yearly', cm: 'sja' },  //PhotoRoom\n" +
            "\t'Bg%20Remover': { name: 'Premium', id: 'net.kaleidoscope.cutout.premium1', cm: 'sja' },  //Bg Remover+\n" +
            "\t'Sex%20Actions': { name: 'Premium Plus', id: 'ru.sexactions.subscriptionPromo1', cm: 'sja' },  //情侣性爱游戏-Sex Actions\n" +
            "\t'reader': { name: 'vip2', id: 'com.valo.reader.vip2.year', cm: 'sja' },  //读不舍手\n" +
            "\t'StarFocus': { name: 'pro', id: 'com.gsdyx.StarFocus.nonConsumable.forever', cm: 'sjb' },  //星垂专注\n" +
            "\t'StarDiary': { name: 'pro', id: 'com.gsdyx.StarDiary.nonConsumable.forever', cm: 'sjb' },  //星垂日记\n" +
            "\t'CountDuck': { name: 'premium', id: 'Lifetime', cm: 'sjb' },  //倒数鸭\n" +
            "\t'wordswag': { name: 'pro', id: 'Pro_Launch_Monthly', cm: 'sja' },  //WordSwag\n" +
            "\t'LockFlow': { name: 'unlimited_access', id: 'lf_00.00_lifetime', cm: 'sjb' },  //LockFlow-锁屏启动\n" +
            "\t'TextMask': { name: 'pro', id: 'tm_lifetime', cm: 'sjb' },  //TextMask\n" +
            "\t'%E5%96%B5%E7%BB%84%E4%BB%B6': { name: 'pro', id: 'MiaoLifeTime', cm: 'sja' },  //喵组件\n" +
            "\t'Chatty': { name: 'pro', id: 'chatty.yearly.1', cm: 'sja' },  //Chatty.AI\n" +
            "\t'ImagineAI': { name: 'pro', id: 'artistai.yearly.1', cm: 'sja' },  //ImagineAI\n" +
            "\t'Langster': { name: 'Premium', id: 'com.langster.universal.lifetime', cm: 'sjb' },  //Langster-学习外语\n" +
            "\t'VoiceAI': { name: 'Special Offer', id: 'voiceannualspecial', cm: 'sjb' },  //VoiceAI-配音\n" +
            "\t'Rootd': { name: 'pro', id: 'subscription_lifetime', cm: 'sjb' },  //Rootd-情绪引导\n" +
            "\t'MusicMate': { name: 'premium', id: 'mm_lifetime_68_premium', cm: 'sjb' },  //MusicMate-音乐\n" +
            "\t'AIKeyboard': { name: 'plus_keyboard', id: 'aiplus_keyboard_yearly', cm: 'sja' },  //AIKeyboard键盘\n" +
            "\t'SmartAIChat': { name: 'Premium', id: 'sc_3999_1y', cm: 'sja' },  //SmartAI\n" +
            "\t'AIChat': { name: 'AI Plus', id: 'ai_plus_yearly', cm: 'sja' },  //AIChat\n" +
            "\t'LazyReply': { name: 'lazyReplyYearlySubscription', id: 'com.bokhary.lazyreply.yearlyprosubscription', cm: 'sja' },  //ReplyAssistant键盘\n" +
            "\t'LazyBoard': { name: 'lazyboardPro', id: 'com.bokhary.magicboard.magicboardpro', cm: 'sjb' },  //LazyBoard键盘\n" +
            "\t'PDF%20Viewer': { name: 'sub.pro', id: 'com.pspdfkit.viewer.sub.pro.yearly', cm: 'sja' },  //PDF Viewerr\n" +
            "\t'Joy': { name: 'pro', id: 'com.indiegoodies.Agile.lifetime2', cm: 'sjb' },  //Joy AI\n" +
            "\t'AnkiPro': { name: 'Premium', id: 'com.ankipro.app.lifetime', cm: 'sjb' },  //AnkiPro\n" +
            "\t'SharkSMS': { name: 'VIP', id: 'com.gaapp.sms.permanently', cm: 'sjb' },  //鲨鱼短信\n" +
            "\t'EncryptNote': { name: 'Pro', id: 'com.gaapp.2019note.noAds', cm: 'sjb' },  //iNotes私密备忘录\n" +
            "\t'One4WallSwiftUI': { name: 'lifetime', id: 'lifetime_key', cm: 'sjb' },  //One4Wall\n" +
            "\t'Pigment': { name: 'pro', id: 'com.pixite.pigment.1yearS', cm: 'sja' },  //色调-Pigment\n" +
            "\t'GradientMusic': { name: 'Pro', id: 'com.gradient.vision.new.music.one.time.79', cm: 'sjb' },  //GradientMusic音乐\n" +
            "\t'iBody': { name: 'Pro', id: 'com.tickettothemoon.bodyfilter.one.time.purchase', cm: 'sjb' },  //BodyFilter\n" +
            "\t'Persona': { name: 'unlimited', id: 'com.tickettothemoon.video.persona.one.time.purchase', cm: 'sjb' },  //Persona-修饰脸部与相机\n" +
            "\t'easy_chart': { name: 'unlock all', id: 'qgnjs_2', cm: 'sja' },  //快速图表\n" +
            "\t'Snipd': { name: 'premium', id: 'snipd_premium_1y_7199_trial_2w_v2', cm: 'sja' },  //Snipd播客\n" +
            "\t'Tide%20Guide': { name: 'Tides+', id: 'TideGuidePro_Lifetime_Family_149.99', cm: 'sjb' },  //Tide Guide潮汐\n" +
            "\t'Gear': { name: 'subscription', id: 'com.gear.app.yearly', cm: 'sja' },  //Gear浏览器\n" +
            "\t'Aisten': { name: 'pro', id: 'aisten_pro', cm: 'sjb' },  //Aisten-播客学英语\n" +
            "\t'ASKAI': { name: 'pro', id: 'askai_pro', nameb: 'pro_plan', idb: 'token_pro_plan', cm: 'sjb' },  //ASKAI\n" +
            "\t'Subtrack': { name: 'pro', id: 'com.mohitnandwani.subtrack.subtrackpro.family', cm: 'sjb' },  //Subtrack\n" +
            "\t'shipian-ios': { name: 'vipOffering', id: 'shipian_25_forever', cm: 'sjb' },  //诗片\n" +
            "\t'My%20Time': { name: 'Pro', id: 'ninja.fxc.mytime.pro.lifetime', cm: 'sjb' },  //我的时间\n" +
            "\t'LUTCamera': { name: 'ProVersionLifeTime', id: 'com.uzero.funforcam.lifetimepurchase', cm: 'sjb' },  //方弗相机\n" +
            "\t'Heal%20Clock': { name: 'pro', id: 'com.mad.HealClock.pro', cm: 'sjb' },  //自愈时钟\n" +
            "\t'tiimo': { name: 'full_access', id: 'lifetime.iap', cm: 'sjb' },  //Tiimo-可视化日程\n" +
            "\t'IPTVUltra': { name: 'premium', id: 'com.chxm1023.lifetime', cm: 'sjb' },  //IPTVUltra\n" +
            "\t'Wozi': { name: 'wozi_pro_2023', id: 'wozi_pro_2023', cm: 'sjb' },  //喔知Wozi背单词\n" +
            "\t'Color%20Widgets': { name: 'pro', id: 'cw_1999_1y_3d0', cm: 'sja' },  //Color Widgets小组件\n" +
            "\t'server_bee': { name: 'Pro', id: 'pro_45_lifetime', cm: 'sjb' },  //serverbee终端监控管理\n" +
            "\t'MyPianist': { name: 'pro', id: 'com.collaparte.mypianist.pro.yearly', cm: 'sja' },  //MyPianist乐谱\n" +
            "\t'ProCam': { name: 'pro', id: 'pro_lifetime', cm: 'sjb' },  //ProCam相机\n" +
            "\t'Drops': { name: 'premium', id: 'forever_unlimited_time_discounted_80_int', cm: 'sjb' },  //Drops外语\n" +
            "\t'transmission_ui': { name: 'Premium', id: '200002', cm: 'sjb' },  //Transmission服务器\n" +
            "\t'fastdiet': { name: 'premium', id: 'com.happy.fastdiet.forever', cm: 'sjb' },  //小熊轻断食\n" +
            "\t'money_manager': { name: 'premium', id: 'com.happy.money.forever', cm: 'sjb' },  //小熊记账\n" +
            "\t'Overdue': { name: 'Pro', id: '1', cm: 'sjb' },  //我的物品\n" +
            "\t'Ledger': { name: 'Pro', id: 'com.lifetimeFamily.pro', cm: 'sjb' },  //Pure账本\n" +
            "\t'Reader': { name: 'pro', id: 'reader.lifetime.pro', cm: 'sjb' },  //PureLibro\n" +
            "\t'WeNote': { name: 'pro', id: 'Yearly', cm: 'sja' },  //Emote\n" +
            "\t'Scelta': { name: 'pro', id: 'SceltaProLifetime', cm: 'sjb' },  //Scelta\n" +
            "\t'%E5%87%B9%E5%87%B8%E5%95%A6%E6%9F%A5%E5%A6%86': { name: 'Pro access', id: 'com.smartitfarmer.MakeUpAssistant.UNLIMITED', cm: 'sjb' },  //upahead\n" +
            "\t'PM4': { name: 'pro', id: 'pm4_pro_1y_2w0', cm: 'sja' },  //Obscura\n" +
            "\t'Project%20Delta': { name: 'rc_entitlement_obscura_ultra', id: 'com.benricemccarthy.obscura4.obscura_ultra_sub_annual', cm: 'sja' },  //Obscura\n" +
            "\t'Zettelbox': { name: 'Power Pack', id: 'powerpack_permanent_1', cm: 'sjb' },  //Zettelbox\n" +
            "\t'Packr': { name: 'Pro', id: 'com.jeremieleroy.packr.premiumyearly', cm: 'sja' },  //派克\n" +
            "\t'%E7%BF%BB%E9%A1%B5%E6%97%B6%E9%92%9F': { name: 'Pro access', id: 'com.douwan.aiclock.ALL', cm: 'sjb' },  //翻页时钟\n" +
            "\t'%E7%A7%A9%E5%BA%8F%E6%97%B6%E9%92%9F': { name: 'lifetime', id: 'com.metaorder.orderclocko.lifetime', cm: 'sjb' },  //秩序时钟\n" +
            "\t'%E7%A7%A9%E5%BA%8F%E7%9B%AE%E6%A0%87': { name: 'pro', id: 'com.metaorder.OKRTomato.vip.supremacy', cm: 'sjb' },  //秩序目标\n" +
            "\t'%E4%BA%BA%E7%94%9F%E6%B8%85%E5%8D%95': { name: 'premium', id: 'com.metaorder.lifelist.premium', cm: 'sjb' },  //人生清单\n" +
            "\t'Vision': { name: 'promo_3.0', id: 'vis_lifetime_3.0_promo', cm: 'sja' },  //Vision\n" +
            "\t'TruthOrDare': { name: 'premium', id: 'truth_or_dare_premium_monthly', cm: 'sja' },  //真心话大冒险\n" +
            "\t'HurtYou': { name: 'premium', id: 'hurtyou_199_1y', cm: 'sja' },  //一起欺词\n" +
            "\t'%E4%BF%A1%E6%81%AF%E8%AE%A1%E7%AE%97': { name: 'pro', id: 'informaticcalculations.pro.lifetime', cm: 'sjb' },  //信息计算\n" +
            "\t'Context_iOS': { name: 'Context Pro', id: 'ctx_sub_1y_sspai_preorder_angel', cm: 'sja' },  //Context\n" +
            "\t'Structured': { name: 'pro', id: 'today.structured.pro', cm: 'sjb' },  //Structured\n" +
            "\t'%E7%9B%B8%E6%9C%BA%E5%8D%B0': { name: 'Unlimited', id: 'com.dujinke.CameraMark.Unlimited', cm: 'sjb' },  //相机印\n" +
            "\t'HTTPBot': { name: 'pro', id: 'com.behindtechlines.HTTPBot.prounlock', cm: 'sjb' },  //Httpbot抓包工具\n" +
            "\t'Counter': { name: 'Unlimited', id: 'com.dujinke.Counter.Unlimited', cm: 'sjb' },  //计数器\n" +
            "\t'%E7%8C%9C%E6%96%87%E5%AD%97': { name: 'Unlimited', id: 'com.dujinke.Chinese.Unlimited', cm: 'sjb' },  //猜文字\n" +
            "\t'%E4%BC%8A%E6%91%A9%E5%9F%BA': { name: 'Unlimited', id: 'com.dujinke.Emoji.Unlimited', cm: 'sjb' },  //伊摩基\n" +
            "\t'%E5%8D%85%E5%85%AD%E9%97%AE': { name: 'Unlimited', id: 'com.dujinke.36Questions.Unlimited', cm: 'sjb' },  //卅六问\n" +
            "\t'MinimalDiary': { name: 'pro', id: 'com.mad.MinimalDiary.lifetime', cm: 'sjb' },  //极简日记\n" +
            "\t'Zen%20Flip%20Clock': { name: 'pro', id: 'com.mad.zenflipclock.iap.buymeacoffee', cm: 'sjb' },  //极简时钟\n" +
            "\t'Transfer': { name: 'pro', id: 'transfer_ios_premium_year_2022_1', cm: 'sja' },  //WeTransfer\n" +
            "\t'Collect': { name: 'pro', id: 'com.revenuecat.product.yearly.ios', cm: 'sja' },  //Collect收集\n" +
            "\t'Paper': { name: 'pro', id: 'com.fiftythree.paper.credit', cm: 'sjb' },  //Paper\n" +
            "\t'Ape': { name: 'pro-iOS', id: 'ape.lifetime', cm: 'sjb' },  //Sharp AI\n" +
            "\t'Boar': { name: 'pro-iOS', id: 'boar.yearly', cm: 'sja' },  //Erase Objects\n" +
            "\t'Loopsie': { name: 'pro-iOS', id: 'com.reader.autoRenewableSeason', cm: 'sja' },  //Loopsie\n" +
            "\t'MySticker': { name: 'mysticker premium', id: 'com.miiiao.MySticker.lifetime', cm: 'sjb' },  //Tico-贴抠\n" +
            "\t'Rec': { name: 'rec.paid', id: 'rec.paid.onetime', cm: 'sjb' },  //Rec相机\n" +
            "\t'Photon': { name: 'photon.paid', id: 'photon.paid.onetime', cm: 'sjb' },  //Photon相机\n" +
            "\t'OneTodo': { name: 'pro', id: 'onetodo_lifetime', cm: 'sjb' },  //OneTodo\n" +
            "\t'OneFlag': { name: 'pro', id: 'oneflag_lifetime', cm: 'sjb' },  //OneList\n" +
            "\t'OneClear': { name: 'pro', id: 'oneclear_lifetime', cm: 'sjb' },  //OneClear透明小组件\n" +
            "\t'OneScreen': { name: 'pro', id: 'onescreen_lifetime', cm: 'sjb' },  //OneScreen截图带壳\n" +
            "\t'Photomator': { name: 'pixelmator_photo_pro_access', id: 'pixelmator_photo_lifetime_v1', cm: 'sjb' },  //Photomator\n" +
            "\t'Endel': { name: 'pro', id: 'Lifetime', cm: 'sjb' },  //Endel\n" +
            "\t'Drowsy': { name: 'Pro', id: 'Drowsy_Life', cm: 'sjb' },  //解压动画\n" +
            "\t'Thiro': { name: 'pro', id: 'atelerix_pro_lifetime', cm: 'sjb' },  //Thiro\n" +
            "\t'Stress': { name: 'StressWatch Pro', id: 'stress_membership_lifetime', cm: 'sjb' },  //StressWatch压力自测提醒\n" +
            "\t'Worrydolls': { name: 'magicmode', id: 'magicmode', cm: 'sjb' },  //解忧娃娃\n" +
            "\t'Echo': { name: 'PLUS', id: 'com.LEMO.LemoFm.plus.lifetime.l3', cm: 'sjb' },  //LEMO FM睡眠\n" +
            "\t'Falendar': { name: 'Falendar+', id: 'falendar_68_life', cm: 'sjb' },  //Falendar日历\n" +
            "\t'%E8%BD%A6%E7%A5%A8%E7%A5%A8': { name: 'vip+watch_vip', id: 'eticket_with_watch_life_a', cm: 'sjb' },  //车票票\n" +
            "\t'iRead': { name: 'vip', id: 'com.vip.forever_1', cm: 'sjb' },  //已阅\n" +
            "\t'MOZE': { name: 'MOZE_PREMIUM_SUBSCRIPTION', id: 'MOZE_PRO_SUBSCRIPTION_YEARLY_BASIC', cm: 'sja' },  //MOZE记账\n" +
            "\t'app/112': { name: 'Pro', id: 'com.wengqianshan.friends.pro', cm: 'sjb' },  //贴心记\n" +
            "\t'app/38': { name: 'Pro', id: 'com.wengqianshan.diet.pro', cm: 'sjb' },  //饭卡\n" +
            "\t'MatrixClock': { name: 'Premium', id: 'com.lishaohui.matrixclock.lifetimesharing', cm: 'sjb' },  //MatrixClocca-矩阵时钟\n" +
            "\t'SalesCat': { name: 'Premium', id: 'com.lishaohui.salescat.lifetime', cm: 'sjb' },  //SalesCat-RevenueCat客户端\n" +
            "\t'MoneyThings': { name: 'Premium', id: 'com.lishaohui.cashflow.lifetime', cm: 'sjb' },  //Money Things记账\n" +
            "\t'ChatGPTApp': { name: 'Advanced', id: 'com.palligroup.gpt3.yearlyyy', cm: 'sja' },  //ChatAI-中文智能聊天机器人\n" +
            "\t'Journal_iOS': { name: 'PRO', id: 'com.pureformstudio.diary.yearly_2022_promo', cm: 'sja' },  //Diarly日历\n" +
            "\t'LemonKeepAccounts': { name: 'VIP', id: 'lm_1_1month', cm: 'sja' },  //旺财记账\n" +
            "\t'mizframa': { name: 'premium', id: 'mf_20_lifetime2', cm: 'sjb' },  //Mizframa\n" +
            "\t'EasyClicker': { name: 'pro', id: 'easyclicker.premium.discount2', cm: 'sjb' },  //自动点击器\n" +
            "\t'ImageX': { name: 'imagex.pro.ios', id: 'imagex.pro.ios.lifetime', cm: 'sjb' },  //Imagex\n" +
            "\t'image_upscaler': { name: 'pro', id: 'yearly_sub_pro', cm: 'sja' },  //Lens智图\n" +
            "\t'DayPoem': { name: 'Pro Access', id: 'com.uzero.poem.month1', cm: 'sja' },  //西江诗词\n" +
            "\t'Personal%20Best': { name: 'pro', id: 'PersonalBestPro_Yearly', cm: 'sja' },  //Personal Best-运动报告\n" +
            "\t'Darkroom': { name: 'co.bergen.Darkroom.entitlement.allToolsAndFilters', id: 'co.bergen.Darkroom.product.forever.everything', cm: 'sja' },  //Darkroom-照片/视频编辑\n" +
            "\t'CardPhoto': { name: 'allaccess', id: 'CardPhoto_Pro', cm: 'sjb' },  //卡片馆-相框与复古胶片\n" +
            "\t'OneWidget': { name: 'allaccess', id: 'com.onewidget.vip', cm: 'sjb' },  //奇妙组件-轻巧桌面小组件\n" +
            "\t'PinPaper': { name: 'allaccess', id: 'Paper_Lifetime', cm: 'sjb' },  //InPaper-创作壁纸\n" +
            "\t'Cookie': { name: 'allaccess', id: 'app.ft.Bookkeeping.lifetime', cm: 'sjb' },  //Cookie-记账\n" +
            "\t'MyThings': { name: 'pro', id: 'xyz.jiaolong.MyThings.pro.infinity', cm: 'sjb' },  //物品指南\n" +
            "\t'%E4%BA%8B%E7%BA%BF': { name: 'pro', id: 'xyz.jiaolong.eventline.pro.lifetime', cm: 'sjb' },  //事线-串事成线\n" +
            "\t'PipDoc': { name: 'pro', id: 'pipdoc_pro_lifetime', cm: 'sjb' },  //PipDoc-画中画\n" +
            "\t'Facebook': { name: 'pro', id: 'fb_pro_lifetime', cm: 'sjb' },  //MetaSurf-社交网站浏览器\n" +
            "\t'Free': { name: 'pro', id: 'appspree_pro_lifetime', cm: 'sjb' },  //Appspree\n" +
            "\t'Startodo': { name: 'pro', id: 'pro_lifetime', cm: 'sjb' },  //Startodo\n" +
            "\t'Browser': { name: 'pro', id: 'pro_zoomable', cm: 'sjb' },  //Zoomable-桌面浏览器\n" +
            "\t'YubePiP': { name: 'pro', id: 'piptube_pro_lifetime', cm: 'sjb' },  //YubePiP-油管播放器\n" +
            "\t'PrivateBrowser': { name: 'pro', id: 'private_pro_lifetime', cm: 'sjb' },  //Brovacy-隐私浏览器\n" +
            "\t'Photo%20Cleaner': { name: 'premium', id: 'com.monocraft.photocleaner.lifetime.3', cm: 'sjb' },  //照片清理Photo Cleaner\n" +
            "\t'bluredit': { name: 'Premium', id: 'net.kaleidoscope.bluredit.premium1', cm: 'sja' },  //bluredit-模糊视频&照片\n" +
            "\t'TouchRetouchBasic': { name: 'premium', id: 'tr5_yearlysubsc_15dlrs_2', cm: 'sja' },  //TouchRetouch-水印清理\n" +
            "\t'TimeFinder': { name: 'pro', id: 'com.lukememet.TimeFinder.Premium', cm: 'sjb' },  //TimeFinder-提醒App\n" +
            "\t'Alpenglow': { name: 'newPro', id: 'ProLifetime', cm: 'sja' },  //Alpenglow-日出日落\n" +
            "\t'Decision': { name: 'com.nixwang.decision.entitlements.pro', id: 'com.nixwang.decision.pro.annual', cm: 'sja' },  //小决定-选择困难症克星\n" +
            "\t'ElementNote': { name: 'pro', id: 'com.soysaucelab.element.note.lifetime', cm: 'sjb' },  //ElementNote-笔记&PDF\n" +
            "\t'Noto%20%E7%AC%94%E8%AE%B0': { name: 'pro', id: 'com.lkzhao.editor.full', cm: 'sja' },  //Noto-笔记\n" +
            "\t'Tangerine': { name: 'Premium', id: 'PremiumMonthly', cm: 'sja' },  //Tangerine-习惯与情绪追踪\n" +
            "\t'Email%20Me': { name: 'premium', id: 'ventura.media.EmailMe.premium.lifetime', cm: 'sjb' },  //Email Me-给自己发邮箱\n" +
            "\t'Brass': { name: 'pro', id: 'brass.pro.annual', cm: 'sja' },  //Brass-定制图标&小组件\n" +
            "\t'Happy%3ADays': { name: 'pro', id: 'happy_999_lifetime', cm: 'sjb' },  //Happy:Days-小组件App\n" +
            "\t'Aphrodite': { name: 'all', id: 'com.ziheng.aphrodite.onetime', cm: 'sjb' },  //Aphrodite-啪啪啪日历\n" +
            "\t'apollo': { name: 'all', id: 'com.ziheng.apollo.onetime', cm: 'sjb' },  //Apollo-记录影视\n" +
            "\t'widget_art': { name: 'all', id: 'com.ziheng.widgetart.onetime', cm: 'sjb' },  //WidgetArt-自定义小组件\n" +
            "\t'audiomack-iphone': { name: 'Premium1', id: 'com.audiomack.premium.2022', cm: 'sja' },  //AudioMack-音乐App\n" +
            "\t'MallocVPN': { name: 'IOS_PRO', id: 'malloc_yearly_vpn', cm: 'sja' },  //Malloc VPN\n" +
            "\t'WhiteCloud': { name: 'allaccess', id: 'wc_pro_1y', cm: 'sja' },  //白云天气\n" +
            "\t'Spark': { name: 'premium', id: 'spark_5999_1y_1w0', cm: 'sja' },  //Spark_Mail-邮箱管理\n" +
            "\t'Grow': { name: 'grow.pro', id: 'grow_lifetime', cm: 'sjb' },  //Grow-健康运动\n" +
            "\t'NotePlan': { name: 'premium', id: 'co.noteplan.subscription.personal.annual', cm: 'sja' },  //NotePlan\n" +
            "\t'vibes': { name: 'patron', id: 'com.andyworks.vibes.yearlyPatron', cm: 'sja' },  //NotBoring-Vibes个性化音乐\n" +
            "\t'simple-weather': { name: 'patron', id: 'com.andyworks.weather.yearlyPatron', cm: 'sja' },  //NotBoring-天气\n" +
            "\t'streaks': { name: 'patron', id: 'com.andyworks.weather.yearlyPatron', cm: 'sja' },  //NotBoring-习惯\n" +
            "\t'andyworks-calculator': { name: 'patron', id: 'com.andyworks.weather.yearlyPatron', cm: 'sja' },  //NotBoring-计算器\n" +
            "\t'simple-timer': { name: 'patron', id: 'com.andyworks.weather.yearlyPatron', cm: 'sja' },  //NotBoring-时间\n" +
            "\t'Harukong': { name: 'premium', id: 'com.bluesignum.harukong.lifetime.premium', cm: 'sjb' },  //天天豆-日记应用\n" +
            "\t'UTC': { name: 'Entitlement.Pro', id: 'tech.miidii.MDClock.subscription.month', cm: 'sja' },  //花样文字\n" +
            "\t'OffScreen': { name: 'Entitlement.Pro', id: 'tech.miidii.offscreen.pro', cm: 'sjb' },  //OffScreen-自律番茄钟\n" +
            "\t'%E8%B0%9C%E5%BA%95%E9%BB%91%E8%83%B6': { name: 'Entitlement.Pro', id: 'tech.miidii.MDVinyl.lifetime', cm: 'sja' },  //谜底黑胶\n" +
            "\t'%E8%B0%9C%E5%BA%95%E6%97%B6%E9%92%9F': { name: 'Entitlement.Pro', id: 'tech.miidii.MDClock.pro', cm: 'sjb' },  //目标地图\n" +
            "\t'%E7%9B%AE%E6%A0%87%E5%9C%B0%E5%9B%BE': { name: 'pro', id: 'com.happydogteam.relax.lifetimePro', cm: 'sjb' },  //\n" +
            "\t'APTV': { name: 'Pro', id: 'com.kimen.aptvpro.lifetime', cm: 'sjb' },  //APTV\n" +
            "\t'Seamless': { name: 'Seamless.Pro', id: 'net.shinystone.Seamless.Pro', cm: 'sjb' },  //Seamless同步\n" +
            "\t'Anybox': { name: 'pro', id: 'cc.anybox.Anybox.annual', cm: 'sja' },  //Anybox-跨平台书签管理\n" +
            "\t'ScannerPro': { name: 'plus', id: 'com.chxm1024.premium.yearly', cm: 'sja' },  //Scanner Pro-文档扫描\n" +
            "\t'Pillow': { name: 'premium', id: 'com.neybox.pillow.premium.year', cm: 'sja' },  //Pillow-睡眠周期跟踪\n" +
            "\t'Taio': { name: 'full-version', id: 'taio_1651_1y_2w0_std_v2', cm: 'sja' },  //Tiao\n" +
            "\t'CPUMonitor': { name: 'Pro', id: 'com.mars.cpumonitor_removeAd', cm: 'sjb' },  //手机硬件管家\n" +
            "\t'totowallet': { name: 'all', id: 'com.ziheng.totowallet.onetimepurchase', cm: 'sjb' },  //图图记账\n" +
            "\t'1Blocker': { name: 'premium', id: 'blocker.ios.iap.lifetime', cm: 'sjb' },  //1Blocker-广告拦截\n" +
            "\t'VSCO': { name: 'pro', id: 'vscopro_global_5999_annual_7D_free', cm: 'sja' }  //VSCO-照片与视频编辑\n" +
            "\t'FaceMa': { name: 'Pro access', id: 'Pro_Lifetime', cm: 'sja' },  //FaceMa\n" +
            "\t'ShellBean': { name: 'Pro', id: 'com.ningle.shellbean.subscription.year', cm: 'sjb' },  //ShellBean\n" +
            "\t'Currency': { name: 'plus', id: 'com.jeffreygrossman.currencyapp.iap.pro.crossgrade', cm: 'sjb' },  //Currency\n" +
            "\t'MadeYu': { name: 'pro_plus', id: 'my_1499_1000', cm: 'sjb' }  //MadeYu\n";
    public static final String b = "'APTV': { name: 'pro', id: 'com.kimen.aptvpro.lifetime'},\n" +
            "    'Authenticator': { name: 'PRO', id: '2fa_0499_1y'},\n" +
            "    'Ape': { name: 'pro', id: 'ape.weekly.discount'},\n" +
            "    'app': { name: 'Pro', id: 'com.wengqianshan.diet.pro'},\n" +
            "    'AudioPlayer': { name: 'Pro', id: 'xyz.lijun.www.AudioPlayer.premium.newuser'},\n" +
            "    'AmiiBoss': { name: 'pro', id: 'amiiboss_pro'},\n" +
            "    'Anybox': { name: 'pro', id: 'cc.anybox.Anybox.annual'},\n" +
            "    'apollo': { name: 'all', id: 'com.ziheng.apollo.onetime'},\n" +
            "    'AIWeatherBot': { name: 'Premium', id: 'com.lionsapp.AIWeatherBot.Premium.Annual'},\n" +
            "    'AIChat': { name: 'AI Plus', id: 'ai_plus_yearly'}, //AIChat\n" +
            "    'Aisten': { name: 'pro', id: 'aisten_pro'},  //Aisten-播客学英语\n" +
            "    'Aphrodite': { name: 'all', id: 'com.ziheng.aphrodite.lifetime'},\n" +
            "    'AnkiPro': { name: 'Premium', id: 'com.ankipro.app.lifetime'}, //AnkiPro\n" +
            "    'AIKeyboard': { name: 'plus_keyboard', id: 'aiplus_keyboard_yearly'}, //AIKeyboard键盘\n" +
            "    'AnimeArt': { name: 'AnimeArt.Gold', id: 'WaifuArt.Lifetime'},  //Anime Waifu-AI\n" +
            "    'ASKAI': { name: 'pro', id: 'askai_pro', nameb: 'pro_plan', idb: 'token_pro_plan'},  //ASKAI\n" +
            "    'Browser': { name: 'pro', id: 'pro_zoomable'},\n" +
            "    'Blurer': { name: 'paid_access', id: 'pro_free'},\n" +
            "    'BlackBox': { name: 'plus', id: 'app.filmnoir.appstore.purchases.lifetime'},\n" +
            "    'Bg%20Remover': { name: 'Premium', id: 'net.kaleidoscope.cutout.premium1'},  //Bg Remover+\n" +
            "    'card': { name: 'vip', id: 'card_vip'},\n" +
            "    'cdiary': { name: 'Premium', id: 'pub.kiya.daymoment.lifetime'},\n" +
            "    'clica': { name: 'pro', id: 'clica.vip.year'},\n" +
            "    'Carbon-iOS': { name: 'pro', id: 'carbon.unlockall'},  //Carbon-碳\n" +
            "    'CardPhoto': { name: 'allaccess', id: 'CardPhoto_Pro'},\n" +
            "    'ChatGPTApp': { name: 'Basic', id: 'com.palligroup.gpt3.yearly'},\n" +
            "    'Chatty': { name: 'pro', id: 'chatty.yearly.1'},  //Chatty.AI\n" +
            "    'Chat%E7%BB%83%E5%8F%A3%E8%AF%AD': { name: 'Premium', id: 'com.tech.AiSpeak.All'},\n" +
            "    'Clockology': { name: 'Clockology+', id: 'com.mikehill.clockologyios.yearly'},\n" +
            "    'Clipboard': { name: 'Premium', id: 'Premium_0_99_1M_1MFree'},  //Clipboard-剪贴板\n" +
            "    'Cookie': { name: 'allaccess', id: 'app.ft.Bookkeeping.lifetime'},\n" +
            "    'Color%20Widgets': { name: 'pro', id: 'cw_1999_1y_3d0'},  //Color Widgets小组件\n" +
            "    'CountDuck': { name: 'premium', id: 'Lifetime'},  //倒数鸭\n" +
            "    'Context_iOS': { name: 'pro', id: 'ctx_3y_sspai_preorder_angel'},\n" +
            "    'Currency': { name: 'plus', id: 'com.jeffreygrossman.currencyapp.iap.plus'},\n" +
            "    //'Cuto': { name: 'cutopro', id: 'com.potatsolab.cuto.pro'},\n" +
            "    'Done': { name: 'Growth_Bundle_Subscription', id: 'com.tbd.Done.growth.bundle.yearly.v6'},\n" +
            "    'Dark%20Noise': { name: 'pro', id: 'dn_4999_lifetime'},\n" +
            "    'DayPoem': { name: 'Pro Lifetime', id: 'com.uzero.poem.life1'},\n" +
            "    'GoodThing': { name: 'pro', id: 'goodhappens_basic_forever'},\n" +
            "    'Decision': { name: 'com.nixwang.decision.entitlements.pro', id: 'com.nixwang.decision.pro'},\n" +
            "    'Drops': { name: 'premium', id: 'forever_unlimited_time_discounted_80_int'}, //Drops外语\n" +
            "    'dtdvibe': { name: 'pro', id:'com.dtd.aroundu.life'},\n" +
            "    'easy_chart': { name: 'unlock all', id: 'qgnjs_2'},  //快速图表\n" +
            "    'Echo': { name: 'PLUS', id: 'com.LEMO.LemoFm.plus.lifetime.l3'},\n" +
            "    'Emphasis': { name: 'premium', id: 'app.emphasis.subscription.yearly'},\n" +
            "    'Emoji%E4%B8%96%E7%95%8C': { name: 'Premium', id: 'com.lishaohui.emojiworld.lifetime'},\n" +
            "    'Emoji+%20%F0%9F%98%9': { name: 'premium', id: 'com.emoji.freemium.subscription.premium'},\n" +
            "    'EncryptNote': { name: 'Pro', id: 'com.gaapp.2019note.noAds'},  //iNotes私密备忘录\n" +
            "    'EZPZ': { name: 'premium', id: 'com.chroma.sounddoodles.annual.trial'},\n" +
            "    'EraseIt': { name: 'ProVersionLifeTime', id: 'com.uzero.cn.eraseit.premium1.fromyear'},  //Smoothrase-AI擦除照片中任何物体\n" +
            "    'fastdiet': { name: 'premium', id: 'com.happy.fastdiet.forever'},  //小熊轻断食\n" +
            "    'Falendar': { name: 'Falendar+', id: 'falendar_68_life'},\n" +
            "    'FTChatAI': { name: 'pro', id: 'ft_ai_1799_1y'},\n" +
            "    'FastingForWoman': { name: 'wefast_premium', id: 'wefast.yearlysubscription'},\n" +
            "    'Finale%20To%20Do': { name: 'FinalePro', id: 'FinaleProLifetime'},\n" +
            "    'FaceMa': { name: 'Pro access', id: 'Pro_Lifetime'},\n" +
            "    'Free': { name: 'pro', id: 'appspree_pro_lifetime'},\n" +
            "    'Funexpected%20Math': { name: 'plus', id: 'Plus6Months14DaysTrial'},\n" +
            "    'Gear': { name: 'subscription', id: 'com.gear.app.yearly'},  //Gear浏览器\n" +
            "    'G%20E%20I%20S%20T': { name: 'memorado_premium', id: 'com.memorado.subscription.yearly.v2'},\n" +
            "    'Grateful': { name: 'Growth_Bundle_Subscription', id: 'com.tbd.Done.growth.bundle.yearly.v6'},\n" +
            "    'GradientMusic': { name: 'Pro', id: 'com.gradient.vision.new.music.one.time.79'}, //GradientMusic音乐\n" +
            "    'Grow': { name: 'grow.pro', id: 'grow_lifetime'},//Grow 版本号：857848362\n" +
            "    'HealthView': { name: 'Growth_Bundle_Subscription', id: 'com.tbd.Done.growth.bundle.yearly.v6'},\n" +
            "    'Heal%20Clock': { name: 'pro', id: 'com.mad.HealClock.pro'},  //自愈时钟\n" +
            "    'HabitKit': { name: 'Pro', id: 'habitkit_1799_lt'},\n" +
            "    'HabitMinder': { name: 'habitminder_premium', id: 'habitminder.yearlysubscription'},\n" +
            "    'HRZN': { name: 'plus', id: 'plus_999_lifetime'},\n" +
            "    'Happy%3ADays': { name: 'pro', id: 'happy_999_lifetime'},\n" +
            "    'HTTPBot': { name: 'Pro', id: 'httpbot_1499_1y_1w0'},\n" +
            "    'Hi%E8%AE%BA%E5%9D%9B/69': { name: 'plus', id: 'plus_yearly',  //Hi论坛\n" +
            "    'iRead': { name: 'vip', id: 'com.vip.forever_1'},\n" +
            "    'ImageX': { name: 'imagex.pro.ios', id: 'imagex.pro.ios.lifetime'},\n" +
            "    'inaugurate': { name: 'pro', id: 'iagr_pro_1m'},\n" +
            "    'ImAFish': { name: 'Pro', id: 'ProOver'},\n" +
            "    'ImagineAI': { name: 'pro', id: 'artistai.yearly.1'}, //ImagineAI\n" +
            "    'image_upscaler': { name: 'pro', id: 'yearly_sub_pro'},\n" +
            "    'IDM': { name: 'premium', id: 'sub_yearly_idm'},  //IDM-下载\n" +
            "    'iBody': { name: 'Pro', id: 'com.tickettothemoon.bodyfilter.one.time.purchase'},  //BodyFilter\n" +
            "    'IPTVUltra': { name: 'premium', id: 'com.chxm1023.lifetime'},  //IPTVUltra\n" +
            "    'jizhi': { name: 'jizhi_vip', id: 'jizhi_vip'},\n" +
            "    'Joy': { name: 'pro', id: 'com.indiegoodies.Agile.lifetime2'},  //Joy AI\n" +
            "    'lilbucket': { name: 'pro', id: 'bucket_lifetime'},\n" +
            "    'Last': { name: 'Growth_Bundle_Subscription', id: 'com.tbd.Done.growth.bundle.yearly.v6'},\n" +
            "    'Langster': { name: 'Premium', id: 'com.langster.universal.lifetime'}, //Langster-学习外语\n" +
            "    'LazyReply': { name: 'lazyReplyYearlySubscription', id: 'com.bokhary.lazyreply.yearlyprosubscription'},  //ReplyAssistant键盘\n" +
            "    'LazyBoard': { name: 'lazyboardPro', id: 'com.bokhary.magicboard.magicboardpro'},  //LazyBoard键盘\n" +
            "    'Law': { name: 'vip', id: 'LawVIPOneYear'},\n" +
            "    'Ledger': { name: 'Pro', id: 'com.lifetimeFamily.pro'},  //Pure账本\n" +
            "    'Liftbear': { name: 'Pro', id: 'liftbear_2399_1y'},\n" +
            "    'LiveWallpaper': { name: 'Pro access', id: 'com.tech.LiveWallpaper.ALL'},  //动态壁纸\n" +
            "    'LiveCaption': { name: 'Plus', id: 'rc_0400_1m'},  //iTranscreen-屏幕/游戏翻译\n" +
            "    'LockFlow': { name: 'unlimited_access', id: 'lf_00.00_lifetime'},  //LockFlow-锁屏启动\n" +
            "    'LongmaoApp': { name: 'pro', id: 'douyina_forever_01'},\n" +
            "    'LUTCamera': { name: 'ProVersionLifeTime', id: 'com.uzero.funforcam.lifetimepurchase'}, //方弗相机\n" +
            "    'Lungy': { name: 'Deluxe', id: 'lungy_1999_lifetime'},\n" +
            "    'money_manager': { name: 'premium', id: 'com.happy.money.forever'},  //小熊记账\n" +
            "    'Monefy': { name: 'monefy_premium', id: 'monefy.yearlysubscription'},\n" +
            "    'MinimalDiary': { name: 'pro', id: 'com.mad.MinimalDiary.lifetime'},\n" +
            "    'My%20Time': { name: 'Pro', id: 'ninja.fxc.mytime.pro.lifetime'}, //我的时间\n" +
            "    'MoneyThings': { name: 'Premium', id: 'com.lishaohui.cashflow.lifetime'},\n" +
            "    'Miiloot': { name: 'pro', id: 'miiloot_pro'},\n" +
            "    'MallocVPN': { name: 'IOS_PRO', id: 'ios_vpn_monthly'},\n" +
            "    'MyPianist': { name: 'pro', id: 'com.collaparte.mypianist.pro.yearly'}, //MyPianist乐谱\n" +
            "    'Math%20Makers': { name: 'Subscriber', id: 'com.ululab.numbers.subscription_one_year_03'},\n" +
            "    'Mojo': { name: 'pro', id: 'video.mojo.pro.yearly'},\n" +
            "    'MadeYu': { name: 'pro_plus', id: 'my_549_1m_400'},\n" +
            "    'MusicMate': { name: 'premium', id: 'mm_lifetime_68_premium'}, //MusicMate-音乐\n" +
            "    'Muse': { name: 'pro', id: 'monthly_pro_muse'},\n" +
            "    'MusicPutty': { name: 'Premium', id: 'mp_2999_1y'},  //MusicPutty\n" +
            "    'NotSmoke': { name: 'not_smoke_vip_01', id: 'not_smoke_01_12_month'},\n" +
            "    'Numpkin': { name: 'pro', id: 'numpkin.pro.yearly.regular'},\n" +
            "    'Noverdue': { name: 'pro', id: 'noverdue.yearly.plan0'},\n" +
            "    'OffScreen': { name: 'Entitlement.Pro', id: 'tech.miidii.offscreen.subscription.year.intro.first_year_discount'},\n" +
            "    'Overdue': { name: 'Pro', id: '1'},  //我的物品\n" +
            "    'OneWidget': { name: 'allaccess', id: 'com.onewidget.vip'},\n" +
            "    'OneBox': { name: 'all', id: 'com.ziheng.pandora.lifetime'},\n" +
            "    'One4WallSwiftUI': { name: 'lifetime', id: 'lifetime_key'},  //One4Wall\n" +
            "    'Paper': { name: 'pro', id: 'com.fiftythree.paper.pro_12'},\n" +
            "    'Photo%20Vault': { name: 'PRO', id: '2fa_0499_1y'},\n" +
            "    'PhotoRoom': { name: 'business', id: 'com.background.business.yearly'},\n" +
            "    'PhotoSync': { name: 'premium', id: 'com.touchbyte.PhotoSync.ProLifetime'},\n" +
            "    'Photomator': { name: 'pixelmator_photo_pro_access', id: 'pixelmator_photo_yearly_v1'},\n" +
            "    'Photo%20Cleaner': { name: 'premium', id: 'com.monocraft.photocleaner.lifetime.3'},\n" +
            "    'PinPaper': { name: 'allaccess', id: 'Paper_Lifetime'},\n" +
            "    'Pillow': { name: 'premium', id: 'com.neybox.pillow.premium.year'},\n" +
            "    'pixel_me_tokyo': { name: 'pro', id: 'pm_jpy2000_7d0'},\n" +
            "    'Purr': { name: 'Pro', id: 'com.purr.annual.trial'},\n" +
            "    'Pomodoro': { name: 'Plus', id: 'com.MINE.PomodoroTimer.plus.lifetime'},\n" +
            "    'PDF%20Viewer': { name: 'sub.pro', id: 'com.pspdfkit.viewer.sub.pro.yearly'}, //PDF Viewerr\n" +
            "    'Percento': { name: 'premium', id: 'app.percento.premium.9.monthly'},\n" +
            "    'Persona': { name: 'unlimited', id: 'com.tickettothemoon.video.persona.one.time.purchase'},  //Persona-修饰脸部与相机\n" +
            "    'Plant%20Journal': { name: 'pro', id: 'pro_3999_lifetime'},\n" +
            "    'PomoStiker': { name: 'lifetimeForever', id: 'com.daya.lifetimeforever'},\n" +
            "    'Pigment': { name: 'pro', id: 'com.pixite.pigment.1yearS'},  //色调-Pigment\n" +
            "    'ProCam': { name: 'pro', id: 'pro_lifetime'},  //ProCam相机\n" +
            "    'PM4': { name: 'pro', id: 'pm4_pro_1y_2w0'},  //Obscura\n" +
            "    'Project%20Delta': { name: 'rc_entitlement_obscura_ultra', id: 'com.benricemccarthy.obscura4.obscura_ultra_sub_annual'},  //Obscura\n" +
            "    'reader': { name: 'vip2', id: 'com.valo.reader.vip2.year'},  //读不舍手\n" +
            "    'Readle': { name: 'Premium', id: 'com.hello.german.yearly'},\n" +
            "    'Reader': { name: 'pro', id: 'reader.lifetime.pro'},  //PureLibro\n" +
            "    'Record2Text': { name: 'afi.recnote.pro', id: 'afi.recnote.pro'},\n" +
            "    'Reflectly': { name: 'Growth_Bundle_Subscription', id: 'com.tbd.Done.growth.bundle.yearly.v6'},\n" +
            "    'Rond': { name: 'pro', id: 'lifetime_108'},\n" +
            "    'Rootd': { name: 'pro', id: 'subscription_lifetime'},  //Rootd-情绪引导\n" +
            "    'server_bee': { name: 'Pro', id: 'pro_45_lifetime'},  //serverbee终端监控管理\n" +
            "    'shipian-ios': { name: 'vipOffering', id: 'shipian_25_forever'}, //诗片\n" +
            "    'streaks': { name: 'patron', id: 'com.andyworks.streaks.yearlyPatron'},\n" +
            "    'Scelta': { name: 'pro', id: 'SceltaProLifetime'},  //Scelta\n" +
            "    'Spark': { name: 'premium', id: 'spark_5999_1y_1w0'},\n" +
            "    'Structured': { name: 'pro', id: 'today.structured.pro'},\n" +
            "    'SleepTimer': { name: 'Growth_Bundle_Subscription', id: 'com.tbd.Done.growth.bundle.yearly.v6'},\n" +
            "    'SwitchGallery': { name: 'switchbuddy+', id: 'sb_lifetime_purchase'},\n" +
            "    'ShellBean': { name: 'pro', id: 'com.ningle.shellbean.subscription.year'},\n" +
            "    'StarDiary': { name: 'pro', id: 'com.gsdyx.StarFocus.nonConsumable.forever'}, //星垂日记\n" +
            "    'StarFocus': { name: 'pro', id: 'com.gsdyx.StarFocus.nonConsumable.forever'}, //星垂专注\n" +
            "    'Subtrack': { name: 'pro', id: 'com.mohitnandwani.subtrack.subtrackpro.family'}, //Subtrack\n" +
            "    'Stress': { name: 'StressWatch Pro', id: 'stress_membership_yearly'},\n" +
            "    'Seamless': { name: 'Seamless.Pro', id: 'net.shinystone.Seamless.Pro'},\n" +
            "    'Snipd': { name: 'premium', id: 'snipd_premium_1y_7199_trial_2w_v2'}, //Snipd播客\n" +
            "    'Strides': { name: 'plus', id: 'Annual'},\n" +
            "    'Summit_iOS': { name: 'pro', id: 'Summit_Monthly1'},\n" +
            "    'SmartAIChat': { name: 'Premium', id: 'sc_3999_1y'}, //SmartAI\n" +
            "    'Sex%20Actions': { name: 'Premium Plus', id: 'ru.sexactions.subscriptionPlusWeek1'}, //情侣性爱游戏-Sex Actions\n" +
            "    'ShellBoxKit': { name: 'pro', id: 'ShellBoxKit.Lifetime'},  //CareServer-服务器监控\n" +
            "    'Shapy': { name: 'premium', id: 'com.blake.femalefitness.subscription.yearly'},  //Shapy-健身\n" +
            "    'SleepDown': { name: 'Pro', id: 'pro_student_0926'},  //StaySleep-熬夜助手\n" +
            "    'SharkSMS': { name: 'VIP', id: 'com.gaapp.sms.permanently'},  //鲨鱼短信\n" +
            "    'totowallet': { name: 'all', id: 'com.ziheng.totowallet.onetimepurchase'},\n" +
            "    'tiimo': { name: 'full_access', id: 'lifetime.iap'}, //Tiimo-可视化日程\n" +
            "    'transmission_ui': { name: 'Premium', id: '200002'},  //Transmission服务器\n" +
            "    'Tally': { name: 'Growth_Bundle_Subscription', id: 'com.tbd.Done.growth.bundle.yearly.v6'},\n" +
            "    'Time%20Machine': { name: 'timemachine_pro', id: 'com.abouttime.timemachine.year'},\n" +
            "    'TimeBloc': { name: 'Growth_Bundle_Subscription', id: 'com.tbd.Done.growth.bundle.yearly.v6'},\n" +
            "    'Tide%20Guide': { name: 'Tides+', id: 'TideGuidePro_Lifetime_Family_149.99'},  //Tide Guide潮汐\n" +
            "    'todo': { name: 'Pro', id: 'com.abouttime.todo.year'},\n" +
            "    'Tagmiibo': { name: 'pro', id: 'nfc_pro_lifetime'},\n" +
            "    'Thenics': { name: 'pro', id: 'pro_yearly_subscription_ios'},\n" +
            "    'Thiro': { name: 'pro', id: 'atelerix_pro_lifetime'},\n" +
            "    'TouchRetouchBasic': { name: 'premium', id: 'tr5_yearlysubsc_20dlrs_1'},\n" +
            "    'Text%20Workflow': { name: 'pro', id: 'tw_99_1m'},\n" +
            "    'TextMask': { name: 'pro', id: 'tm_lifetime'}, //TextMask\n" +
            "    'Usage': { name: 'pro', id: 'com.olegst.usage.pro'},\n" +
            "    'Uio': { name: 'PRO', id: 'com.daya.Uio.quarterly'},\n" +
            "    'universal': { name: 'Premium', id: 'remotetv.yearly.01'},\n" +
            "    'Version': { name: 'pro', id: 'httpbot_1499_1y_1w0'},\n" +
            "    'VideoToPhoto': { name: 'Premium', id: 'VideoToPhoto_premium'},\n" +
            "    'Vision': { name: 'promo_3.0', id: 'vis_lifetime_3.0_promo'},\n" +
            "    'VoiceAI': { name: 'Special Offer', id: 'voiceannualspecial'}, //VoiceAI-配音\n" +
            "    'VSCO': { name: 'pro', id: 'vscopro_global_5999_annual_AutoFreeTrial'},\n" +
            "    'windiary': { name: 'Pro', id: 'windiary_1799_lt'},\n" +
            "    'wordswag': { name: 'pro', id: 'Pro_Launch_Monthly'}, //WordSwag\n" +
            "    'Waterlytics': { name: 'Pro', id: 'app.waterlytics.pro.yearly.v2'},\n" +
            "    'WeatherFit': { name: 'Pro', id: 'com.antonchuiko.wthrd.premium_onetime'},\n" +
            "    'WeeklyNote': { name: 'org.zrey.weeklynote', id: 'org.zrey.weeklynote.flash_lifetime'},\n" +
            "    'WeNote': { name: 'pro', id: 'Yearly'},  //Emote\n" +
            "    'Whisper': { name: 'all_features', id: 'whisperai_80_y'},  //Whisper\n" +
            "    'WhiteCloud': { name: 'allaccess', id: 'wc_pro_1y'},\n" +
            "    'WidgetSmith': { name: 'Premium', id: 'PremiumAnnualWidget'},\n" +
            "    'Worrydolls': { name: 'magicmode', id: 'magicmode'},\n" +
            "    'Wozi': { name: 'wozi_pro_2023', id: 'wozi_pro_2023'},  //喔知Wozi背单词\n" +
            "    'Zen%20Flip%20Clock': { name: 'pro', id: 'com.mad.zenflipclock.iap.buymeacoffee'},\n" +
            "    'Zettelbox': { name: 'Power Pack', id: 'powerpack_permanent_1'},  //Zettelbox\n" +
            "    'Zoomerang': { name: 'pro', id: 'zoomerang.yearly'},\n" +
            "    '%E8%BD%A6%E7%A5%A8%E7%A5%A8': { name: 'vip+watch_vip', id: 'eticket_with_watch_1y_7d0'},\n" +
            "    '%E6%9E%81%E7%AE%80%E5%BC%B9%E5%B9%95': { name: 'pro', id: 'com.abouttime.flash.year'},\n" +
            "    '%E6%98%9F%E5%BA%A7': { name: 'Premium', id: 'com.rk.horoscope.month12'},\n" +
            "    '%E6%B0%B4%E5%BF%83%E8%AE%B0': { name: 'pro', id: 'com.abouttime.heart.year'},\n" +
            "    '%E5%85%83%E6%B0%94%E8%AE%A1%E6%97%B6': { name: 'plus', id: 'powertimer.plus'},\n" +
            "    '%E5%96%B5%E7%BB%84%E4%BB%B6': { name: 'MiaoWidgetPro', id: 'MiaoWidgetProYear'},\n" +
            "    '%E5%9B%BE%E7%89%87%E6%B6%88%E9%99%A4': { name: 'premium membership', id: 'com.skysoft.removalfree.introduction.yearly'},\n" +
            "    '%E7%9B%AE%E6%A0%87%E5%9C%B0%E5%9B%BE': { name: 'pro', id: 'com.happydogteam.relax.lifetimePro'},\n" +
            "    '%E7%A7%A9%E5%BA%8F%E7%9B%AE%E6%A0%87': { name: 'pro', id: 'com.metaorder.OKRTomato.vip.supremacy'},\n" +
            "    '%E7%94%BB%E5%8E%86': { name: 'pro', id: 'com.bapaws.calendar.lifetime'},\n" +
            "    '%E7%BE%8E%E5%A6%86%E6%97%A5%E5%8E%86': { name: 'Pro access', id: 'com.tech.Aula.VIPALL'},  //美妆日历\n" +
            "    '%E8%AF%AD%E9%9F%B3%E8%AE%A1%E7%AE%97%E5%99%A8': { name: 'Pro access', id: 'com.tech.counter.All'},  //语音计算器\n" +
            "    '%E6%89%8B%E6%8C%81%E5%BC%B9%E5%B9%95': { name: 'Pro access', id: 'com.tech.LedScreen.VIPALL'},  //手持弹幕\n" +
            "    '%E5%96%B5%E7%BB%84%E4%BB%B6': { name: 'pro', id: 'MiaoLifeTime'},  //喵组件\n" +
            "    '%E5%87%B9%E5%87%B8%E5%95%A6%E6%9F%A5%E5%A6%86': { name: 'Pro access', id: 'com.smartitfarmer.MakeUpAssistant.UNLIMITED'},  //upahead\n";


    public static void main(String[] args) {
        try {
            String[] strArrA =a.replaceAll("//.*\n","").replaceAll("\\t",",").replaceAll(" ","").replaceAll(",cm:'sjb'","").replaceAll(",cm:'sja'","").split(",,");
            String[] strArrB =b.replaceAll("//.*\n","").replaceAll("\n","").replaceAll(" ","").replaceAll("},","}},").split("},");
             String filePath = "/Users/zirawell/Downloads/1.txt";
            ArrayList<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(strArrB));
            list.addAll(Arrays.asList(strArrA));
            Collections.sort(list);
            for (String s : list) {
                System.out.println(s);
                outputFile(filePath,new StringBuffer(s+",\n"),true);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
     private static void outputFile(String filePath, StringBuffer sb,boolean appendFlag) {
        FileOutputStream out = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file,appendFlag);
            out.write(sb.toString().getBytes());

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





}
