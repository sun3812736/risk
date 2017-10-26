package com.sby.risk.risksdk.core;

/**
 * Created by sby on 2017/8/10.
 */
public interface IUpLoadlistener {

    void onStart();

    void onUpLoad();

    void onSucess();

    void onFailure();

    void onCancell();
}
