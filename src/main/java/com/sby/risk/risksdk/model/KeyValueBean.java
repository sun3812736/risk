package com.sby.risk.risksdk.model;

import com.sby.risk.risksdk.util.DateUtil;

/**
 * Created by sby on 2017/8/10.
 */
public class KeyValueBean {
    private String name;
    private String value;
    private String time;
    public KeyValueBean(){}

    public KeyValueBean(String name,String value){
        this.name = name ;
        this.value = value ;
        this.time = DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") ;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

//    @Override
//    public String toString() {
//        return "KeyValueBean{" +
//                "name='" + name + '\'' +
//                ", value='" + value + '\'' +
//                '}';
//    }

    @Override
    public String toString() {
        return "KeyValueBean{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
