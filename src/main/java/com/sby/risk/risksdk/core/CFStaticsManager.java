package com.sby.risk.risksdk.core;

import android.content.Context;

/**
 * Created by sby on 2017/8/10.
 */
public interface CFStaticsManager {

     boolean onInit(int appId, String channel);

     void onSend();

     void onStore();
     void onSendForResult(ForResultListener tcfrl);
     void onRelease();

     void onRecordAppStart();

     void onRrecordPageEnd();

     void onRecordPageStart(Context context);

     void onRrecordAppEnd();

     void onInitPage(String... strings);

     void onPageParameter(String... strings);

     void onInitEvent(String eventName);

     void onEventParameter(String... strings);


}
