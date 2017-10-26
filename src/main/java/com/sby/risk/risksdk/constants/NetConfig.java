package com.sby.risk.risksdk.constants;

/**
 * Created by sby on 2017/8/10.
 */
public class NetConfig {

    /**
     * constructor
     */
    private NetConfig() {

    }

    /**
     * You Url
     */
    public static String ONLINE_URL ="http://10.17.13.10:8081/appupdata/index";

    /**
     * 数据上报Debug Url
     */
    public static final String URL ="http://10.17.13.10:8081/appupdata/index";

    /**
     * 请求超时时间
     */
    public static final int TIME_OUT = 1000 * 50 * 1;

    /** 重新请求时间 */
    public static final int RETRY_TIMES = 3;

    /** HEADERS_KEY */
    public static final String HEADERS_KEY = "data_head";

    /** key*/
    public static final String PARAMS_KEY = "data_body";


}
