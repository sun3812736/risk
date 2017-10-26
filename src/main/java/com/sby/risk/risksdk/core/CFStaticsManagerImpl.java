package com.sby.risk.risksdk.core;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.sby.risk.risksdk.constants.StaticsConfig;
import com.sby.risk.risksdk.db.helper.DataConstruct;
import com.sby.risk.risksdk.db.helper.StaticsAgent;
import com.sby.risk.risksdk.model.DataBlock;
import com.sby.risk.risksdk.service.Platform;
import com.sby.risk.risksdk.util.JsonUtil;
import com.sby.risk.risksdk.util.NetworkUtil;
import com.sby.risk.risksdk.util.StatLog;

import java.io.InputStream;
import java.util.HashMap;

import static com.sby.risk.risksdk.core.CFNetEngine.TAG;


/**
 * Created by sby on 2017/8/10.
 */
public class CFStaticsManagerImpl implements CFStaticsManager, CFObserverPresenter.ScheduleListener {

    /** context */
    private Context mContext;
    /** sInstance */
    private static CFStaticsManager sInstance;

    private static CFObserverPresenter paObserverPresenter;

    private StaticsListener eventInterface;

    private CFStatiPollMgr statiPollMgr;

//    HashMap<String, String> pageIdMaps = new HashMap<String, String>();
    /** Log TAG */
    private static final String LOG_TAG = CFStatiPollMgr.class.getSimpleName();

    public CFStaticsManagerImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public boolean onInit(int appId, String channel) {

        // init  ObserverPresenter
        paObserverPresenter = new CFObserverPresenter(this);

        // init StaticsAgent
        StaticsAgent.init(mContext);

        // init CrashHandler
        CrashHandler.getInstance().init(mContext);

        // load pageIdMaps
//        pageIdMaps = getStatIdMaps(fileName);

        // init  StatiPoll
        statiPollMgr = new CFStatiPollMgr(this);
        // init Header
        return initHeader(appId, channel);
    }

    @Override
    public void onSend() {
        // report data to server
        Platform.get().execute(new Runnable() {
            @Override
            public void run() {
                DataBlock dataBlock = StaticsAgent.getDataBlock();

                if (dataBlock.getApp_action().isEmpty() &&
                        dataBlock.getEvent().isEmpty() &&
                        dataBlock.getPage().isEmpty()) {
                    return;
                }
                StatLog.d(TAG, "TcStatfacr >> report is Start");
                CFUpLoadManager.getInstance(mContext).report(JsonUtil.toJSONString(dataBlock));
            }
        });

    }
    @Override
    public void onSendForResult(final ForResultListener tcfrl) {
        onStore();
        // report data to server
        Platform.get().execute(new Runnable() {
            @Override
            public void run() {
                DataBlock dataBlock = StaticsAgent.getDataBlock();

                if (dataBlock.getApp_action().isEmpty() &&
                        dataBlock.getEvent().isEmpty() &&
                        dataBlock.getPage().isEmpty()) {
                    return;
                }
                StatLog.d(TAG, "TcStatfacr >> report is Start");
                CFUpLoadManager.getInstance(mContext).reportForResult(tcfrl,JsonUtil.toJSONString(dataBlock));
            }
        });
    }
    @Override
    public void onStore() {
        DataConstruct.storeEvent();
        DataConstruct.storePage();
    }



    @Override
    public void onRelease() {
        if (paObserverPresenter != null) {
            paObserverPresenter.destroy();
        }

        stopSchedule();

    }

    @Override
    public void onRecordAppStart() {
        //send
        DataConstruct.storeAppAction("1");
        onSend();
        // store appAction

    }

    @Override
    public void onRrecordPageEnd() {
        DataConstruct.storeEvent();
        DataConstruct.storePage();
        if (paObserverPresenter != null) {
            paObserverPresenter.onStop(mContext);
        }
        stopSchedule();
    }

    @Override
    public void onRecordPageStart(Context context) {

        if (context == null) {
            return;
        }

        //开始计时
        startSchedule();


//        String pageId = checkValidId(context.getClass().getSimpleName());
//        if (pageId == null) {
        String    pageId = context.getClass().getSimpleName();
//        }

        // init page
        onInitPage(pageId, null);

        if (paObserverPresenter != null) {
            paObserverPresenter.init(mContext);
        }

        if (paObserverPresenter != null) {
            paObserverPresenter.onStart(mContext);
        }
    }


    @Override
    public void onRrecordAppEnd() {

        //recard APP exit
        DataConstruct.storeAppAction("2");

        onSend();

        onRelease();
    }

    @Override
    public void onInitPage(String... strings) {

        DataConstruct.initPage(mContext, eventInterface, strings[0], strings[1]);

    }

    @Override
    public void onPageParameter(String... strings) {

//        DataConstruct.initPageParameter(strings[0], strings[1]);

    }


    @Override
    public void onInitEvent(String eventName) {

        DataConstruct.initEvent(eventInterface, eventName);
    }

    @Override
    public void onEventParameter(String... strings) {

        DataConstruct.onEvent(strings[0], strings[1]);

    }

    /**
     * init header
     */
    private boolean initHeader(int appId, String channel) {


        if (!HeadrHandle.isInit()) {
            return HeadrHandle.initHeader(mContext, appId, channel);
        }

        return false;

    }

    /**
     * onScheduleTimeOut
     */
    void onScheduleTimeOut() {

        StatLog.d(LOG_TAG, "onScheduleTimeOut  is sendData");

        onSend();
    }

    /**
     * startSchedule
     */
    public void startSchedule() {
        // if debug  time is 5 min
        if (StaticsConfig.DEBUG &&
                CFStatInterface.uploadPolicy == CFStatInterface.UploadPolicy.UPLOAD_POLICY_DEVELOPMENT) {
            statiPollMgr.start(5* 1000);
            StatLog.d(LOG_TAG, "Schedule is start");
        } else {
            if (NetworkUtil.isWifi(mContext)) {
                statiPollMgr.start(CFStatInterface.getIntervalRealtime() * 60 *1000);
            } else {
                statiPollMgr.start(CFStatInterface.UPLOAD_TIME_THIRTY * 60 *1000);
            }

        }
    }

//    /** checkValidId
//     * @param name activitiyname
//     * @return  pageId
//     */
//    private  String checkValidId(String name) {
//        if (TextUtils.isEmpty(name)) {
//            return null;
//        }
//        if (name.length() <= 0) {
//            return null;
//        }
//
//        return getPageId(name);
//    }

//
//    /** getPageId
//     * @param clazz
//     * @return
//     */
//    private String getPageId(String clazz) {
//        if (mContext == null) {
//            return null;
//        }
//        return pageIdMaps.get(clazz);
//    }

    /**
     * stop Schedule
     */
    public void stopSchedule() {

        StatLog.d(LOG_TAG, "stopSchedule()");

        statiPollMgr.stop();
    }

    @Override
    public void onStart() {
        StatLog.d(LOG_TAG, "startSchedule");

        startSchedule();

    }

    @Override
    public void onStop() {

        stopSchedule();
    }

    @Override
    public void onReStart() {
        // stopSchedule
        stopSchedule();
        // startSchedule
        startSchedule();
    }


    public HashMap<String, String> getStatIdMaps(String jsonName) {


        HashMap<String, String> map = null;
        if (getFromAsset(jsonName) != null) {
            map = (HashMap<String, String>) JSON.parseObject(getFromAsset("stat_id.json"), HashMap.class);
        }
        return map;
    }

    public String getFromAsset(String fileName){
        String result="";
        try{
            InputStream in = mContext.getResources().getAssets().open(fileName);
            int length = in.available();
            byte [] buffer = new byte[length];
            in.read(buffer);
            result = cz.msebera.android.httpclient.util.EncodingUtils.getString(buffer, "UTF-8");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
