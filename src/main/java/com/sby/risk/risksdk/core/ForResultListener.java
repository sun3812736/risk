package com.sby.risk.risksdk.core;

/**
 * Created by sby on 2017/8/18.
 */

public interface ForResultListener {
    void onSucess();
    void onFailure(String errorcode);
}