package com.westechhub.westicketsmartinvitations;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private final static String DATABASE_NAME = "TicketsDB.db";
    private final static int DATABASE_VERSION = 1;


    public DatabaseOpenHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
