package com.sby.risk.risksdk.db.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sby.risk.risksdk.db.CFNote;

import java.util.ArrayList;

/**
 * Created by sby on 2017/8/10.
 */

public class ReadDataBaseAccess {
    private DataBaseHandler handler = null;
    private static ReadDataBaseAccess readAccess = null;
    private static boolean isConnectionBusy = false;

    protected ReadDataBaseAccess(Context context) {
        handler = DataBaseHandler.readInstance(context);
    }

    public static synchronized ReadDataBaseAccess shareInstance(Context context) {
        readAccess = new ReadDataBaseAccess(context);
        return readAccess;
    }


    //查询所有的note
    public ArrayList<CFNote> loadAll() {
        SQLiteDatabase connection = handler.getReadConnection(Thread.currentThread().getStackTrace()[2].getMethodName());
        Cursor cursor = connection.rawQuery("select * from T_Note", null);
        ArrayList<CFNote> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            CFNote note = new CFNote();
            note.setFirstCloumn(cursor.getString(1));
            note.setSecondCloumn(cursor.getString(2));
            note.setThirdCloumn(cursor.getString(3));
            note.setForthCloumn(cursor.getString(4));
            notes.add(note);
        }
        cursor.close();
        handler.closeConnection(connection, Thread.currentThread().getStackTrace()[2].getMethodName());
        return notes;
    }
}
