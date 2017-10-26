
package com.sby.risk.risksdk.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.format.DateUtils;
import android.text.format.Time;

import com.sby.risk.risksdk.constants.StaticsConfig;

/**
 * Created by sby on 2017/8/10.
 */
public class CFStatiPollMgr {

	/** DEBUG mode */
	private static final boolean DEBUG = StaticsConfig.DEBUG;
	/** Log TAG */
	private static final String LOG_TAG = CFStatiPollMgr.class.getSimpleName();
	/** MSG_TIMEOUT */
	private static final int MSG_TIMEOUT = 1;
	private CFStaticsManagerImpl staticsManagerImpl;
	/** 心跳周期 */
	private long mCardiacCycle;
	/** DefaultCycle */
	private long mDefaultCycle;

	/**
	 * Constructor
	 */
	public CFStatiPollMgr(CFStaticsManagerImpl staticsManager) {
		staticsManagerImpl = staticsManager;
	}

	/**
	 * start
	 *
	 * @param aCardiacCycle
	 *            心跳周期
	 */
	public void start(long aCardiacCycle) {
		mDefaultCycle = aCardiacCycle;
		mCardiacCycle = aCardiacCycle;
		checkDateChanging();
		stop();
		loop();
	}

	/**
	 * stop
	 */
	public void stop() {
		sPrivateHandler.get().removeMessages(MSG_TIMEOUT);
	}

	/**
	 * loop
	 */
	private void loop() {
		Message msg = sPrivateHandler.get().obtainMessage(MSG_TIMEOUT, this);
		sPrivateHandler.get().sendMessageDelayed(msg, mCardiacCycle);
	}

	/**
	 * onTimeOut msg
	 */
	public void onTimeOut() {
		staticsManagerImpl.onScheduleTimeOut();
	}

	/**
	 * checkDateChanging
	 */
	private void checkDateChanging() {
		Time time = new Time();
		time.setToNow();
		int hour = time.hour; //24小时制
		int minute = time.minute;
		if (hour == 23) { //SUPPRESS CHECKSTYLE
			int cycle = 61 - minute; // SUPPRESS CHECKSTYLE 12:01访问
			long timeSchedule = cycle * DateUtils.MINUTE_IN_MILLIS;
			if (timeSchedule < mCardiacCycle) {
				mCardiacCycle = timeSchedule;
			}
		} else {
			if (mCardiacCycle != mDefaultCycle) {
				mCardiacCycle = mDefaultCycle;
			}
		}
	}

	/**
	 * Handler
	 */
	private static final ThreadLocal<Handler> sPrivateHandler = new ThreadLocal<Handler>() {
		@Override
		protected Handler initialValue() {
			return new Handler(Looper.getMainLooper()) {

				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
						case MSG_TIMEOUT:
							CFStatiPollMgr schedule = (CFStatiPollMgr) msg.obj;
							if (schedule != null) {
								schedule.onTimeOut();
								schedule.checkDateChanging();
								schedule.loop();
							}
							break;
						default:
							break;
					}
				}
			};
		}
	};
}
