package com.whut.myseller.config;

/**
 * Created by yubo on 2016/3/18.
 */
public class RequestParams {



    //Charts NO Data text
    public static final String CHART_NO_DATA_TEXT="数据加载中，请稍等。。。";


    //http请求码
    public static final int REQUEST_UPLOAD_IMAGE = 0;
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_DELETE = 2;
    public static final int REQUEST_UPDATE = 3;
    public static final int REQUEST_QUERY = 4;
    public static final int REQUEST_QUERY_ONE = 5;
    public static final int REQUEST_QUERY_TWO = 6;
    public static final int REQUEST_QUERY_THREE = 7;
    public static final int REQUEST_QUERY_FOUR = 8;
    public static final int REQUEST_UPDATE_ONE = 9;
    public static final int REQUEST_UPDATE_TWO = 10;
    public static final int REQUEST_GET = 11;
    //cookie请求码
    public static final int COOKIE = 10000;
    public static final int SHOPID = 10001;
    public static final int GOODID = 10002;
    //根地址

   // public static final String SERVER_IP = "http://115.28.9.186:8020";
//   public static final String SERVER_IP = "http://219.153.20.141";
//    public static final String SERVER_IP = "http://192.168.2.204";
//    public static final String SERVER_IP = "http://192.168.2.202";
  public static final String SERVER_IP = "http://119.60.8.152";

    public static final String ROOT_HTTP = SERVER_IP + "/web/store/service/201";

//    public static final String ROOT_HTTP = "http://192.168.2.109/ads_web/store/service/201";

    //获取系统时间
    public static final String GET_SYSTEM_TIME=ROOT_HTTP+"/node-tair-web/owner/servertime";

    //获取店铺信息
    public static final String GET_SHOPID = ROOT_HTTP + "/node-tair-web/ad/shop/list";
    //获取店主姓名
    public static final String GET_SHOP_OWNER = ROOT_HTTP + "/node-tair-web/owner/whoIm?";
    //修改店铺信息
    public static final String UPDATE_SHOP_INFO = ROOT_HTTP + "/ad/shop/update";
    //上传图片目录
    public static final String UPLOAD_URL_DIR = "/file-repo";
    //修改密码
    public static final String SET_PASSWORD = ROOT_HTTP + "/node-tair-web/owner/changepwd";

    // 添加商品
    public static final String ADD_GOODS_PATH = ROOT_HTTP
            + "/node-tair-web/app/mall/addGoods";
    //删除商品
    public static final String DEL_GOODS_PATH = ROOT_HTTP + "/node-tair-web/app/mall/GoodsDel";
    // 上传图片
    public static final String UPLOAD_PATH_IMAGE = ROOT_HTTP
            + "/file-repo/uploads";
    // 添加优惠券
    public static final String ADD_COUPON_PATH = ROOT_HTTP
            + "/node-tair-web/app/mall/addCoupon";
    // 获取优惠券列表
    public static final String GET_COUPON_LIST = ROOT_HTTP
            + "/node-tair-web/app/mall/CouponList";
    // 更新优惠券
    public static final String UPDATE_COUPON_PATH = "";
    // 获取商品列表
    public static final String GET_GOODS_LIST = ROOT_HTTP
            + "/node-tair-web/app/mall/GoodsList";
    // 获取商品详情
    public static final String GET_GOODS_DETAIL = ROOT_HTTP
            + "/node-tair-web/app/mall/GoodsDetail";
    // 更新商品信息
    public static final String UPDATE_GOODS_PATH = "";
    // 获取店铺信息
    public static final String GET_STORE_PATH = "";
    // 更新店铺信息
    public static final String UPDATE_STORE_PATH = "";
    // 获取门户图片
    public static final String GET_PORTAL_IMAGE = "";
    // 更新门户图片
    public static final String UPDATE_PORTAL_IMAGE = SERVER_IP + "/web/store/service/abloomy/db/add.php";
    // 获取门户图片跳转URL
    public static final String GET_PORTAL_URL = SERVER_IP + "/web/store/service/abloomy/db/find.php";
    // 设置门户图片跳转URL
    public static final String SET_PORTAL_URL = SERVER_IP + "/web/store/service/abloomy/db/add_jump.php";
    //获取门户图片标题
    public static  final String GET_PORTAL_TITLE = SERVER_IP + "/web/store/service/abloomy/db/get_title.php";
    //设置门户图片标题
    public static final String SET_PORTAL_TITLE = SERVER_IP + "/web/store/service/abloomy/db/add_title.php";
    // 云监控
    // public static final String REAL_TIME_GUEST =
    // ROOT_HTTP+"/user_op.cgi?action=list_all";
    // 用户登录
    public static final String LOGIN_PATH = ROOT_HTTP
            + "/node-tair-web/account/owner/login";
    // AP列表
    public static final String GET_AP_LIST = ROOT_HTTP
            + "/node-tair-web/ap/list";
    // 重启AP
    public static final String REBOOT_AP = ROOT_HTTP
            + "/node-tair-web/ap/reboot";
    // AP详细信息
    public static final String GET_AP_CLIENT = ROOT_HTTP
            + "/node-tair-web/ap/client/duration";

    // 获得ssid
    public static final String GET_AP_SSID = ROOT_HTTP
            + "/node-tair-web/ap/ssid/get";
    // 修改ssid
    public static final String UPDATE_AP_SSID = ROOT_HTTP
            + "/node-tair-web/ap/ssid/set";
    // 添加黑白名单
    public static final String ADD_BLACK_WHITE = ROOT_HTTP
            + "/node-tair-web/ap/wblist/add";
    // 展示黑白名单
    public static final String GET_BLACK_WHITE_LIST = ROOT_HTTP
            + "/node-tair-web/ap/wblist/list";
    //删除黑白名单
    public static final String DELETE_BLACK_WHITE = ROOT_HTTP + "/node-tair-web/ap/wblist/remove";
    // 获得AP上传下载流量
    public static final String GET_UPLOAD_DOWNLOAD = ROOT_HTTP + "";
    // 通过mac地址获取用户信息
    public static final String GET_PHONE_BY_MAC = ROOT_HTTP + "/node-tair-web/users/getbymac";

    // 实时客流
    public static final String REAL_TIME_GUEST = ROOT_HTTP
            + "/user_op.cgi?action=list_all";
    // wifi用户统计
    public static final String WIFI_USER_COUNT = ROOT_HTTP
            + "/node-tair-web/ap/client/summary";
    //添加VIP
    public static final String ADD_VIP = ROOT_HTTP + "/node-tair-web/ap/shop_member/add";
    //获取VIP列表
    public static final String GET_VIP_LIST = ROOT_HTTP + "/node-tair-web/ap/shop_member/list";

    //获取用户信息
    public static  final String GET_USER_INFO=ROOT_HTTP+"/users/get";


}
