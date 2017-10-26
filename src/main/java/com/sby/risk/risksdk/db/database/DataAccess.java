package com.sby.risk.risksdk.db.database;

import android.content.Context;

import com.sby.risk.risksdk.db.CFNote;

import java.util.ArrayList;

/**
 * Created by sby on 2017/8/10.
 */
/*
 * 对外接口，数据库访问层，所有的数据库操作都只能通过该类访问
 */
public class DataAccess {
    private static DataBaseHandler handler = null;
    private static DataAccess access = null;

    private static ReadDataBaseAccess readAccess = null;
    private static WriteDataBaseAccess writeAccess = null;
    private static Context appContext;

    public static synchronized DataAccess shareInstance(Context context) {
        appContext = context;
        readAccess = ReadDataBaseAccess.shareInstance(appContext);
        if (access == null) {
            access = new DataAccess();
            handler = DataBaseHandler.writeInstance(appContext);
            writeAccess = WriteDataBaseAccess.shareInstance(appContext);
        }
        return access;
    }

    public void closeDataBase() {
        handler.close();
    }

    //创建所有表
    public void createAllTables() {
        createNoteTable();
        createCustomerTable();
    }

    private void createNoteTable() {
        boolean isCreatedSec = handler.createTableWithSQL("create table if not exists T_Note(_id integer primary key autoincrement,firstcloumn text,secondcloumn text," +
                "thirdcloumn text,forthcloumn text)");
        if (!isCreatedSec) {
            System.out.println("create table T_Note failure!");
        }
    }

    private void createCustomerTable() {
        boolean isCreatedSec = handler.createTableWithSQL("create table if not exists T_Customer(_id integer primary key autoincrement,name text)");
        if (!isCreatedSec) {
            System.out.println("create table T_Customer failure!");
        }
    }

    //添加接口
    public boolean insertData(Object obj) {
        return writeAccess.insertData(obj);
    }

    //批量插入事件
    public boolean insertNotes(ArrayList<?> notes) {
        return writeAccess.insertDatas(notes);
    }

    //插入事件
    public boolean insertNote(CFNote note) {
        return writeAccess.insertData(note);
    }
}
