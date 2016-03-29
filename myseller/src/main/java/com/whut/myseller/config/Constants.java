package com.whut.myseller.config;

/**
 * Created by root on 16-3-21.
 */
public class Constants {
    public static final String PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXhN0QWgxsMDx9T8bCYP4qAFeyeSirVl1/CCttNSIalcPgth9JnlwPafmcsrvYjNmauBP2ds5vwybBAMNMIn8ZNBkmmZqffijnrThUPeI+8pbJt1p4Woq7/hJG28vX6XIKfvHKXx/VcAbN0NBAcHxcXIn1sYxcntp+hNGB0Hl4DQIDAQAB";
    // 登录成功后获得的token
    public static String USER_COOKIE = "";

    public static final String VIP_BROADCAST = RequestParams.ROOT_HTTP.replace("http","ws")+"/node-tail-web/notify/vip";


    public static final String CHART_NO_DATA_TEXT="no data show";
}
