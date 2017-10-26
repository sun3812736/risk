package com.sby.risk.risksdk.model;

import com.sby.risk.risksdk.util.DateUtil;

import java.sql.Timestamp;

/**
 * Created by sby on 2017/8/10.
 */
public class ExceptionInfo {
    private String exceptionstring;
    private Timestamp exceptionTime;
    public ExceptionInfo() {
    }

    public ExceptionInfo( String exceptionString) {
        this.exceptionstring = exceptionString;
        this.exceptionTime= DateUtil.timestap();
    }

    public String getExceptionstring() {
        return exceptionstring;
    }

    public void setExceptionstring(String exceptionstring) {
        this.exceptionstring = exceptionstring;
    }

    public Timestamp getExceptionTime() {
        return exceptionTime;
    }

    public void setExceptionTime(Timestamp exceptionTime) {
        this.exceptionTime = exceptionTime;
    }
}
