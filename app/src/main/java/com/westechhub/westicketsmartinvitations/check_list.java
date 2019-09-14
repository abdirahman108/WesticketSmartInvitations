package com.westechhub.westicketsmartinvitations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.paperdb.Paper;

public class check_list extends AppCompatActivity {
    TextView txtTime, txtTicketNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        Paper.init(this );
        txtTime = findViewById(R.id.timechk);

        String timeID = "Time";
        String showTime = Paper.book().read(timeID);
        txtTime.setText("Scanned On: "+ showTime);
    }

}
