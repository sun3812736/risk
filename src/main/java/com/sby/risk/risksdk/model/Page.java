package com.sby.risk.risksdk.model;

import java.util.List;

/**
 * Created by sby on 2017/8/10.
 */
public class Page {
    private String pageId;
//    private String referer_page_id ;
    private String pageStartTime ;
    private String pageEndTime ;
    private List<KeyValueBean> parameter ;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
//    public String getPage_id() {
//        return page_id;
//    }
//
//    public void setPage_id(String page_id) {
//        this.page_id = page_id;
//    }

    public String getPageStartTime() {
        return pageStartTime;
    }

    public void setPageStartTime(String pageStartTime) {
        this.pageStartTime = pageStartTime;
    }

    public String getPageEndTime() {
        return pageEndTime;
    }

    public void setPageEndTime(String pageEndTime) {
        this.pageEndTime = pageEndTime;
    }
//    public String getReferer_page_id() {
//        return referer_page_id;
//    }
//
//    public void setReferer_page_id(String referer_page_id) {
//        this.referer_page_id = referer_page_id;
//    }
//
//    public String getPage_start_time() {
//        return pageStartTime;
//    }
//
//    public void setPage_start_time(String page_start_time) {
//        this.pageStartTime = page_start_time;
//    }
//
//    public String getPage_end_time() {
//        return pageEndTime;
//    }
//
//    public void setPage_end_time(String page_end_time) {
//        this.pageEndTime = page_end_time;
//    }


    public List<KeyValueBean> getParameter() {
        return parameter;
    }

    public void setParameter(List<KeyValueBean> parameter) {
        this.parameter = parameter;
    }
}
