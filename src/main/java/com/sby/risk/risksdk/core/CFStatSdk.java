package com.sby.risk.risksdk.core;

import android.content.Context;


/**
 * Created by sby on 2017/8/10.
 */
public class CFStatSdk {

    /** context */
    private Context mContext;
    /** Instance */
    private static CFStatSdk sInstance;

    private static final String TAG = "TcStaInterface::StatSdk";

    private CFStaticsManager staticsManager;

    /**
     * getInstance
     *
     * @param aContext
     *            context
     * @return 返回 CFStaticsManager
     */
    protected static synchronized CFStatSdk getInstance(Context aContext) {
        if (sInstance == null) {
            sInstance = new CFStatSdk(aContext,  new CFStaticsManagerImpl(aContext));
        }
        return sInstance;
    }

    /**
     * constructor
     *
     * @param aContext
     *            context
     */
    private CFStatSdk(Context aContext, CFStaticsManager aStaticsManager) {
        mContext = aContext;
        staticsManager = aStaticsManager;

    }

    protected void init(int appId, String channel) {

        staticsManager.onInit(appId, channel);

    }

    protected void send() {

        staticsManager.onSend();
    }
    protected void sendforresult(ForResultListener tcfrl) {

        staticsManager.onSendForResult(tcfrl);
    }

    protected void store() {

        staticsManager.onStore();

    }

    protected void upLoad() {

        staticsManager.onSend();
    }

    /**
     * release
     */
    protected void release() {

        staticsManager.onRelease();

    }

    protected void recordPageEnd() {

        staticsManager.onRrecordPageEnd();

    }

    protected void recordAppStart() {

        staticsManager.onRecordAppStart();

    }

    protected void recordAppEnd() {

        staticsManager.onRrecordAppEnd();

    }

    protected void recordPageStart(Context context) {

        staticsManager.onRecordPageStart(context);

    }

    protected void setPageParameter(String k, String v) {

        staticsManager.onPageParameter(k, v);

    }

    protected void initEvent(String envntName) {

        staticsManager.onInitEvent(envntName);

    }

    protected void setEventParameter(String k, String v) {

        staticsManager.onEventParameter(k, v);

    }

    protected void initPage(String pageId, String referPageId) {

        staticsManager.onInitPage(pageId, referPageId);

    }

}
