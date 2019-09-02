package com.westechhub.westicketsmartinvitations;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Processing extends AppCompatActivity {

    private String ResultData = null;
    String TicketNo, EventName, TicketStatus, ScanDate, ScanTime;
    DatabaseHelper MyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ResultData =  getIntent().getStringExtra("Data");

        if (ResultData.contains(":")){
            String[] split = ResultData.split("\\:");
            String spEventName = split[0];
            String spTicketNo = split[1];

//            DatabaseAccess databaseAccess = DatabaseAccess.getInstances(getApplicationContext());
//            databaseAccess.open();

            Cursor res = MyDb.getData(spTicketNo);
            String DbData = null;

            if (res.moveToFirst()){
                TicketNo = res.getString(0);
                EventName = res.getString(1);
                TicketStatus = res.getString(2);
                ScanDate = res.getString(3);
                ScanTime = res.getString(4);

                DbData = TicketNo +":"+EventName+":"+TicketStatus+":"+ScanDate+":"+ScanTime;
            }
            Toast.makeText(this, DbData, Toast.LENGTH_SHORT).show();


//            TicketStatus = databaseAccess.getStatus(TicketNo);



//            Toast.makeText(this, TicketNo, Toast.LENGTH_SHORT).show();

            if (TicketStatus.contentEquals("unused")){
                Intent intent = new Intent(Processing.this, ResultsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("Data", ResultData);
                intent.putExtra("Status", "Allowed");
                startActivity(intent);
                finish();
            }

//            databaseAccess.close();
        }

//        Cursor res = MyDb.getData(ResultData);
//        String DbData = null;
//
//        if (res.moveToFirst()){
//            TicketNo = res.getString(0);
//            EventName = res.getString(1);
//            TicketStatus = res.getString(2);
//            ScanDate = res.getString(3);
//            ScanTime = res.getString(4);
//
//            DbData = TicketNo +":"+EventName+":"+TicketStatus+":"+ScanDate+":"+ScanTime;
//        }
//        Toast.makeText(this, "Everything is OK", Toast.LENGTH_SHORT).show();

    }
}
