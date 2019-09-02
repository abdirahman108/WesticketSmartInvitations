package com.westechhub.westicketsmartinvitations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "TicketsDB.db";
    public final static String TABLE_NAME = "guest_list";
    public final static String COL_1 = "Ticket_No";
    public final static String COL_2 = "Event";
    public final static String COL_3 = "Status";
    public final static String COL_4 = "Date";
    public final static String COL_5 = "TIme";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);


    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "(Ticket_No TEXT ,Event TEXT, Status TEXT, Date TEXT, TIme TEXT  ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String TicketNo, String EventName, String TicketStatus, String ScanDate, String ScanTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1, TicketNo);
        cv.put(COL_2, EventName);
        cv.put(COL_3, TicketStatus);
        cv.put(COL_4, ScanDate);
        cv.put(COL_5, ScanTime);

        long results = db.insert(TABLE_NAME, null, cv);
        if (results == -1) return false;
        else return true;

    }

    public Cursor getData(String TicketNo){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + "WHERE Ticket_No = '" +TicketNo+ "'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    public boolean updateData(String TicketNo, String EventName, String TicketStatus, String ScanDate, String ScanTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1, TicketNo);
        cv.put(COL_2, EventName);
        cv.put(COL_3, TicketStatus);
        cv.put(COL_4, ScanDate);
        cv.put(COL_5, ScanTime);

        db.update(TABLE_NAME, cv, "TicketNo = ?", new String[]{TicketNo});
        return true;
    }
    public Integer deleteData (String TicketNo){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "TicketNo = ?", new String[]{TicketNo});
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        return res;
    }


}
