package com.sby.risk.risksdk.model;

import java.util.List;

/**
 * Created by sby on 2017/8/10.
 */
public class DataBlock {
    private List<AppAction> app_action ;
    private List<Page> page ;
    private List<Event> event ;
    private List<ExceptionInfo>exceptionInfos;


    public List<ExceptionInfo> getExceptionInfos() {
        return exceptionInfos;
    }

    public void setExceptionInfos(List<ExceptionInfo> exceptionInfos) {
        this.exceptionInfos = exceptionInfos;
    }

    public List<AppAction> getApp_action() {
        return app_action;
    }

    public void setApp_action(List<AppAction> app_action) {
        this.app_action = app_action;
    }

    public List<Page> getPage() {
        return page;
    }

    public void setPage(List<Page> page) {
        this.page = page;
    }

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

}
