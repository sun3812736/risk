package com.sby.risk.risksdk.db.helper;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.sby.risk.risksdk.db.CFNote;
import com.sby.risk.risksdk.db.database.DataAccess;
import com.sby.risk.risksdk.db.database.ReadDataBaseAccess;
import com.sby.risk.risksdk.db.database.WriteDataBaseAccess;
import com.sby.risk.risksdk.model.AppAction;
import com.sby.risk.risksdk.model.DataBlock;
import com.sby.risk.risksdk.model.Event;
import com.sby.risk.risksdk.model.ExceptionInfo;
import com.sby.risk.risksdk.model.Page;
import com.sby.risk.risksdk.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sby on 2017/8/10.
 */
public class StaticsAgent {
    private static Context mContext;
    private static CFNote note;

    /**
     * @param context
     */
    public static void init(Context context) {

        mContext = context;

        DataAccess.shareInstance(context).createAllTables();

    }

    /**
     * 存储appAction相关信息
     *
     * @param appAction
     */
    public static void storeAppAction(String appAction) {
        if (TextUtils.isEmpty(appAction))
            throw new NullPointerException("appAction is null");
        storeData(appAction, "", "");
    }

    /**
     * storePage
     *
     * @param pageString
     */
    public static void storePage(String pageString) {
        if (TextUtils.isEmpty(pageString))
            throw new NullPointerException("pageString is null");
        storeData("", pageString, "");
    }

    /**
     * storeEvent
     *
     * @param eventString
     */
    public static void storeEvent(String eventString) {
        if (TextUtils.isEmpty(eventString))
            throw new NullPointerException("eventString is null");
        storeData("", "", eventString);
    }

    /**
     * storePage
     *
     * @param exceptionInfo
     */
    public static void storeException(String exceptionInfo) {
        if (TextUtils.isEmpty(exceptionInfo))
            throw new NullPointerException("exceptionInfo is null");
        storeData("", "", "", exceptionInfo);
    }

    public static DataBlock getDataBlock() {
        DataBlock dataBlock = new DataBlock();
        List<CFNote> list = ReadDataBaseAccess.shareInstance(mContext).loadAll();
        AppAction appAction = new AppAction();
        Page page = new Page();
        Event event = new Event();
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        List<AppAction> actionList = new ArrayList<AppAction>();
        List<Page> pageList = new ArrayList<Page>();
        List<Event> eventList = new ArrayList<Event>();
        List<ExceptionInfo> exceptionInfos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!TextUtils.isEmpty(list.get(i).getFirstCloumn())) {
                if(i==0){
                    appAction = JsonUtil.parseObject(list.get(i).getFirstCloumn(), AppAction.class);
                    actionList.add(appAction);
                }else {
                    boolean iseauql=false;
                    for(int j=0;j<i;j++){
                        if (list.get(i).getFirstCloumn().equals(list.get(j).getFirstCloumn())){
                            iseauql=true;
                        }
                    }
                    if(!iseauql){
                        appAction = JsonUtil.parseObject(list.get(i).getFirstCloumn(), AppAction.class);
                        actionList.add(appAction);
                    }


                }

            }
            if (!TextUtils.isEmpty(list.get(i).getSecondCloumn())) {
                if(i==0){
                    page = JsonUtil.parseObject(list.get(i).getSecondCloumn(), Page.class);
                    pageList.add(page);
                }else {
//                    if (!list.get(i).getSecondCloumn().equals(list.get(i-1).getSecondCloumn())){
//                        page = JsonUtil.parseObject(list.get(i).getSecondCloumn(), Page.class);
//                        pageList.add(page);
//                    }
                    boolean iseauql_2=false;
                    for(int j=0;j<i;j++){
                        if (list.get(i).getSecondCloumn().equals(list.get(j).getSecondCloumn())){
                            iseauql_2=true;
                        }
                    }
                    if(!iseauql_2){
                       page = JsonUtil.parseObject(list.get(i).getSecondCloumn(), Page.class);
                        pageList.add(page);
                    }

                }
            }
            if (!TextUtils.isEmpty(list.get(i).getThirdCloumn())) {
                if(i==0){
                    event = JsonUtil.parseObject(list.get(i).getThirdCloumn(), Event.class);
                    eventList.add(event);
                }else {
//                    if (!list.get(i).getThirdCloumn().equals(list.get(i-1).getThirdCloumn())){
//                        event = JsonUtil.parseObject(list.get(i).getThirdCloumn(), Event.class);
//                        eventList.add(event);
//                    }else {
//                        String ss = list.get(i).getThirdCloumn();
//
//                    }
                    boolean iseauql_3=false;
                    for(int j=0;j<i;j++){
                        if (list.get(i).getThirdCloumn().equals(list.get(j).getThirdCloumn())){
                            iseauql_3=true;
                        }
                    }
                    if(!iseauql_3){
                        event = JsonUtil.parseObject(list.get(i).getThirdCloumn(), Event.class);
                        eventList.add(event);
                    }
                }


            }if (!TextUtils.isEmpty(list.get(i).getForthCloumn())) {
                if(i==0){
                    exceptionInfo = JsonUtil.parseObject(list.get(i).getForthCloumn(), ExceptionInfo.class);
                    exceptionInfos.add(exceptionInfo);
                }else {
                    if (!list.get(i).getForthCloumn().equals(list.get(i-1).getForthCloumn())){
                        exceptionInfo = JsonUtil.parseObject(list.get(i).getForthCloumn(), ExceptionInfo.class);
                        exceptionInfos.add(exceptionInfo);
                    }
                }

            }
        }
        dataBlock.setApp_action(actionList);
        dataBlock.setPage(pageList);
        dataBlock.setExceptionInfos(exceptionInfos);
        dataBlock.setEvent(eventList);
        return dataBlock;
    }

    public static void storeData(String firstcloumn, String secondcloumn, String thirdcloumn) {
        storeData(firstcloumn, secondcloumn, thirdcloumn, null);
    }


    public static void storeData(String firstcloumn, String secondcloumn, String thirdcloumn, String forthCloumn) {
        note = new CFNote(null, firstcloumn, secondcloumn, thirdcloumn, forthCloumn);
        WriteDataBaseAccess.shareInstance(mContext).insertData(note);
    }


    /**
     * storeObject
     *
     * @param o
     */
    public static void storeObject(Object o) {
        if (o instanceof Event) {
            storeEvent(JSONObject.toJSONString(o));
        } else if (o instanceof AppAction) {
            storeAppAction(JSONObject.toJSONString(o));
        } else if (o instanceof Page) {
            storePage(JSONObject.toJSONString(o));
        } else if (o instanceof ExceptionInfo) {
            storeException(JSONObject.toJSONString(o));
        }

    }

    public static void deleteData() {
        WriteDataBaseAccess.shareInstance(mContext).deleteAllNote();
//        DataAccess.shareInstance(mContext).createAllTables();
    }

}

