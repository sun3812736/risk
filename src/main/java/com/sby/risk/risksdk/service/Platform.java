package com.sby.risk.risksdk.service;

/**
 * Created by sby on 2017/8/10.
 */

import android.os.Build;
import android.util.Log;

import java.util.concurrent.Executor;

/**
 * Created by sby on 2017/8/10.
 */
public class Platform {
    private static final Platform PLATFORM = findPlatform();

    public static Platform get() {
        Log.v("TcStatInterfacePlatform", PLATFORM.getClass().toString());
        return PLATFORM;
    }

    private static Platform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return new Android();
            }
        } catch (ClassNotFoundException ignored) {
        }
        return new Platform();
    }

    public Executor defaultCallbackExecutor() {
        return new CFHandleThreadPool().getExecutor();
    }

    public Object execute(Runnable runnable) {
        defaultCallbackExecutor().execute(runnable);
        return null;
    }

    static class Android extends Platform {
        @Override
        public Executor defaultCallbackExecutor() {
            return new CFHandleThreadPool().getExecutor();
        }

    }

}
