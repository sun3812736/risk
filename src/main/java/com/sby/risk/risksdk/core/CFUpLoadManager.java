package com.sby.risk.risksdk.core;

import android.content.Context;
import android.text.TextUtils;


import com.sby.risk.risksdk.constants.NetConfig;
import com.sby.risk.risksdk.db.helper.StaticsAgent;
import com.sby.risk.risksdk.http.CFHttpClient;
import com.sby.risk.risksdk.service.Platform;
import com.sby.risk.risksdk.util.JsonUtil;
import com.sby.risk.risksdk.util.NetworkUtil;
import com.sby.risk.risksdk.util.StatLog;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by sby on 2017/8/10.
 */
public class CFUpLoadManager implements IUpLoadlistener {


    /**
     * context
     */
    private Context mContext;
    /**
     * http client
     */
    private CFHttpClient mHttpClient;
    /**
     * UpLoadManager
     */
    private static CFUpLoadManager sInstance;

    private Boolean isRunning = false;

    private AtomicReference<CFNetEngine> atomic;

    private CFNetEngine netEngine;
    private CFNetEngine netEnginefrl;
    /**
     * Log TAG
     */
    private static final String TAG = CFNetEngine.class.getSimpleName();

    /**
     * getInstance
     *
     * @param aContext context
     * @return UpLoadManager
     */
    public static synchronized CFUpLoadManager getInstance(Context aContext) {
        if (sInstance == null) {
            sInstance = new CFUpLoadManager(aContext);
        }
        return sInstance;
    }

    /**
     * constructor
     *
     * @param aContext context
     */
    private CFUpLoadManager(Context aContext) {
        mContext = aContext;
        init();
    }

    /**
     * init
     */
    private void init() {
        mHttpClient = getHttpclient();
        atomic = new AtomicReference<>();
        netEngine = new CFNetEngine(mContext, this);
    }


    /**
     * report
     */
    public void report(String jsonString) {

        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            return;
        }

        if (TextUtils.isEmpty(jsonString)) {
            return;
        }
        //netEngine.setHttpClient(getHttpclient());
        atomic.set(netEngine);
        atomic.getAndSet(netEngine).start(jsonString);
    }
    /**
     * reportForResult
     */
    public void reportForResult(ForResultListener forResultListener, String jsonString) {

        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            return;
        }

        if (TextUtils.isEmpty(jsonString)) {
            return;
        }
        //netEngine.setHttpClient(getHttpclient());
        netEngine=new CFNetEngine(mContext,null,this, forResultListener);
        atomic.set(netEngine);
        atomic.getAndSet(netEngine).start(jsonString);
    }
    /**
     * cancle
     */
    public void cancle() {

        if (atomic.get() != null) {
            atomic.get().cancel();

        }

    }


    /**
     * get http client
     *
     * @return http client
     */
    public CFHttpClient getHttpclient() {
        if (mHttpClient == null) {
            // HttpClient
            mHttpClient = new CFHttpClient();
            mHttpClient.setTimeOut(NetConfig.TIME_OUT);
        }
        return mHttpClient;

    }


    @Override
    public void onStart() {

        isRunning = true;
    }

    @Override
    public void onUpLoad() {

        isRunning = true;
    }

    @Override
    public void onSucess() {

        isRunning = false;
        // delete data
        StatLog.d(TAG, "DELETE  ：StaticsAgent.deleteTable()");
        // delete data
        Platform.get().execute(new Runnable() {
            @Override
            public void run() {
                StaticsAgent.deleteData();
                String ss =JsonUtil.toJSONString(StaticsAgent.getDataBlock());
                StatLog.d(TAG, "delete after :>>>>>>" + ss);
            }
        });

    }

    @Override
    public void onFailure() {

        isRunning = false;

    }

    @Override
    public void onCancell() {

        isRunning = false;
    }
}
