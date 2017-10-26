package com.sby.risk.risksdk.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by sby on 2017/8/10.
 */
public class CFUploadCoreReceiver extends BroadcastReceiver {

    public static final String REPORT_ACTION = "action.com.pinganfang.base.send_report";

    private static final String TAG = "CFUploadCoreReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "pollSever is started");

        if (context == null || intent == null ) {
            return;
        }

        //发送数据
        CFStatSdk.getInstance(context).send();

    }
}
