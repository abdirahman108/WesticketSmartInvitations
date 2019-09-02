package com.westechhub.westicketsmartinvitations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context){

        this.openHelper = new DatabaseOpenHelper(context);

    }

    public static DatabaseAccess getInstances(Context context){

        if (instance == null){
            instance = new DatabaseAccess(context);
        }

        return instance;
    }

    public void open(){
        this.openHelper.getWritableDatabase();
    }

    public void close(){
        if (db != null){
            this.db.close();
        }
    }

    public String getStatus(String TicketNo){
        c = db.rawQuery(" select Status from guest_list where Ticket_No = '" + TicketNo + "'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()){
            String status = c.getString(0);
            buffer.append(""+status);
        }
        return buffer.toString();
    }

}
