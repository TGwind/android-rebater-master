package com.rebater.cash.well.fun.util.pre;

public class Constans { //常量类
    public static boolean ISDEBUG = true;
    public static byte[] Basepkgbytes = {0x63, 0x6F, 0x6D, 0x2E, 0x61, 0x6D, 0x75, 0x73, 0x69, 0x6E, 0x67, 0x2E, 0x63, 0x61, 0x73, 0x68, 0x2E, 0x77, 0x65, 0x6C, 0x6C, 0x2E, 0x66, 0x75, 0x6E};
    public static  String  pkg=new String(Basepkgbytes);
//    public static  String  pkg="com.amusing.cash.well.fun";

    public static String VERSION = "1";
    public static String VERSIONNAME = "1.4";
    //    public static byte[] Baseurlbytes={ 0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x6F, 0x77, 0x2E, 0x6E, 0x6A, 0x6F, 0x77, 0x6F, 0x66, 0x66, 0x65, 0x72, 0x2E, 0x63, 0x6F, 0x6D, 0x2F, 0x61, 0x70, 0x69, 0x2F };
//    public static  String  BASE_URL=new String(Baseurlbytes);
////            public static  String  BASE_URL="https://ow.njowoffer.com/api/";
    public static byte[] Baseurlbytes = {0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x6F, 0x77, 0x2E, 0x79, 0x61, 0x6E, 0x67, 0x6F, 0x66, 0x66, 0x65, 0x72, 0x2E, 0x63, 0x6F, 0x6D, 0x2F, 0x61, 0x70, 0x69, 0x2F};
    public static  String  BASE_URL=new String(Baseurlbytes);
//            public static  String  BASE_URL="https://ow.yangoffer.com/api/";

    public static byte[] BasePKGSbytes= { 0x2D, 0x63, 0x6F, 0x6D, 0x2E, 0x61, 0x6D, 0x75, 0x73, 0x69, 0x6E, 0x67, 0x2E, 0x63, 0x61, 0x73, 0x68, 0x2E, 0x77, 0x65, 0x6C, 0x6C, 0x2E, 0x66, 0x75, 0x6E };
    public static String packageName = new String(BasePKGSbytes);
    //    public static String packageName="-com.amusing.cash.well.fun";
//https://ow.ow1offer.com/termofasuming.html
    public static byte[] termsbytes = {0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x6F, 0x77, 0x2E, 0x6F, 0x77, 0x31, 0x6F, 0x66, 0x66, 0x65, 0x72, 0x2E, 0x63, 0x6F, 0x6D, 0x2F, 0x74, 0x65, 0x72, 0x6D, 0x6F, 0x66, 0x61, 0x73, 0x75, 0x6D, 0x69, 0x6E, 0x67, 0x2E, 0x68, 0x74, 0x6D, 0x6C};
    public static String urlTerms = new String(termsbytes);

    //http://ip-api.com/json
    public static byte[] urlbytes={ 0x68, 0x74, 0x74, 0x70, 0x3A, 0x2F, 0x2F, 0x69, 0x70, 0x2D, 0x61, 0x70, 0x69, 0x2E, 0x63, 0x6F, 0x6D, 0x2F, 0x6A, 0x73, 0x6F, 0x6E };
    public static String urlLocation = new String(urlbytes);
    //
//https://ow.ow1offer.com/privacy2asuming.html
    public static byte[] privacybytes = {0x68, 0x74, 0x74, 0x70, 0x73, 0x3A, 0x2F, 0x2F, 0x6F, 0x77, 0x2E, 0x6F, 0x77, 0x31, 0x6F, 0x66, 0x66, 0x65, 0x72, 0x2E, 0x63, 0x6F, 0x6D, 0x2F, 0x70, 0x72, 0x69, 0x76, 0x61, 0x63, 0x79, 0x32, 0x61, 0x73, 0x75, 0x6D, 0x69, 0x6E, 0x67, 0x2E, 0x68, 0x74, 0x6D, 0x6C};
    public static String urlPrivacy = new String(privacybytes);

    public static  String  CHANNEL="google";
    //  public static  String  CHANNEL="mob01";
    //market://details?id=
    public static byte[] market= { 0x6D, 0x61, 0x72, 0x6B, 0x65, 0x74, 0x3A, 0x2F, 0x2F, 0x64, 0x65, 0x74, 0x61, 0x69, 0x6C, 0x73, 0x3F, 0x69, 0x64, 0x3D };
    public static final String  playMarketWayToApp = new String(market);
    public static String check_vieo="sign";
    public static String normal_video="video";
//    public static String insert_video="native";

    public static String user_register="user_register";
    public static String user_login_ok ="user_login_ok";
    public static String user_login_err="user_login_err";
    public static String home_page_view="home_page_view";
    public static String home_task_click="home_task_click";
    public static String home_recommend_task_click="home_recommend_task_click";
    public static String task_page_view = "task_page_view";
    public static String task_start_btn_click = "task_start_btn_click";
    public static String task_install = "task_install";
    public static String task_installed = "task_installed";
    public static String task_step_finish = "task_step_finish";
    public static String withdraw_page_view = "withdraw_page_view";
    public static String withdraw_btn_click = "withdraw_btn_click";
    public static String wishlist_page_view = "wishlist_page_view";
    public static String daily_checkin_click = "daily_checkin_click";
    public static String watch_video_ad = "watch_video_ad";
    public static String daily_walk_click = "daily_walk_click";
    public static String offer_open_ok = "offer_open_ok";
    public static String push_notification = "push_notification";
    public static String permisiionIn = "task_permission_ok";
    public static String user_login_agree_policy = "user_login_agree_policy";
    public static String home_instructions_01 = "home_instructions_A";
    public static String home_instructions_02 = "home_instructions_B";
    public static String home_instructions_03 = "home_instructions_C";
    public static String home_instructions_reward = "home_instructions_reward";
    public static String offer_open_err = "offer_open_err";
    public static String video_no_reward = "video_no_reward";
    public static String home_page_view_ok = "home_page_view_ok";
    public static String withdraw_btn_click_ok = "withdraw_btn_click_ok";
    public static String daily_video_checkin_click = "daily_checkin_click";
    public static String watch_video_ad_error = "watch_video_ad_error";
    //数据库名字
    public static String DBNAME = "happy-db";
}
