/** 
 * Filename:    CFScreenObserver.java
 * Description:  
 * Copyright:   Baidu MIC Copyright(c)2011 
 * @author:     CoCoMo 
 * @version:    1.0
 * Create at:   2013-1-6 下午1:55:30
 * 
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2013-1-6    CoCoMo      1.0         1.0 Version 
 */
package com.sby.risk.risksdk.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.util.Log;

import com.sby.risk.risksdk.constants.StaticsConfig;
import com.sby.risk.risksdk.core.IntentManager;

/**
 * Created by sby on 2017/8/10.
 */
public class CFScreenObserver extends BroadcastReceiver {

	/** DEBUG mode */
	private static final boolean DEBUG = StaticsConfig.DEBUG;
	/** Log TAG */
	private static final String LOG_TAG = CFScreenObserver.class.getSimpleName();

	/** Context */
	private Context mContext;
	/** ScreenObserver  */
	private IScreenListener mListener;

	/**
	 * Constructor
	 *
	 * @param aContext
	 *            Context
	 * @param aListener
	 *            IScreenListener
	 */
	public CFScreenObserver(Context aContext, IScreenListener aListener) {
		mContext = aContext;
		mListener = aListener;
	}


	public void start() {
		try {
			IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_SCREEN_ON);
			filter.addAction(Intent.ACTION_SCREEN_OFF);
			mContext.registerReceiver(this, filter);

			if (isScreenOn(mContext)) {
				if (mListener != null) {
					mListener.onScreenOn(mContext);
				}
			} else {
				if (mListener != null) {
					mListener.onScreenOff(mContext);
				}
			}
		} catch (Exception e) {
			if (DEBUG) {
				Log.w(LOG_TAG, "start Exception", e);
			}
		}
	}


	public void stop() {
		try {
			mContext.unregisterReceiver(this);
		} catch (Exception e) {
			if (DEBUG) {
				Log.w(LOG_TAG, "stop Exception", e);
			}
		}
	}

	/**
	 * isScreenOn
	 *
	 * @param aContext
	 *            Context
	 */
	public boolean isScreenOn(Context aContext) {
		PowerManager pm = (PowerManager) aContext.getSystemService(Context.POWER_SERVICE);
		return pm.isScreenOn();
	}

	@Override
	public void onReceive(Context aContext, Intent aIntent) {
		if (IntentManager.getInstance().isScreenOnIntent(aIntent)) {
			if (mListener != null) {
				mListener.onScreenOn(aContext);
			}
		} else if (IntentManager.getInstance().isScreenOffIntent(aIntent)) {
			if (mListener != null) {
				mListener.onScreenOff(aContext);
			}
		}
	}

	/**
	 * IScreenListener
	 */
	public interface IScreenListener {


		void onScreenOn(Context aContext);


		void onScreenOff(Context aContext);
	}

}
