package com.westechhub.westicketsmartinvitations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsActivity extends AppCompatActivity {

    ImageView imgStatus;
    TextView txtStatus, txtTicketInfo, txtEventName, txtTicketNo;
    Button btnContinue;
    RelativeLayout statusBox;
    ActionBar mActionBar;


    private String ResultData = "", Status = "";

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        getSupportActionBar().hide();

        ResultData =  getIntent().getStringExtra("Data");
        Status =  getIntent().getStringExtra("Status");


        imgStatus = findViewById(R.id.img_status);
        txtStatus = findViewById(R.id.txt_status);
        txtTicketInfo = findViewById(R.id.ticket_info);
        txtEventName = findViewById(R.id.event_name);
        txtTicketNo = findViewById(R.id.ticket_number);
        statusBox = findViewById(R.id.status_box);
        btnContinue = findViewById(R.id.result_continue);



        if (ResultData.contains(":")){
            String[] split = ResultData.split("\\:");

            String eventName = split[0];
            String ticketNo = split[1];

            txtStatus.setText(Status);
            txtTicketNo.setText("Ticket Number: " + ticketNo);
            txtEventName.setText("Event Name: " + eventName);

            if (Status.contains("Allowed")){
                //Status Allowed
                allowedStatus();

//                updateDatabase(invitations, Events, eventName, ticketNo);


            }else if (Status.contains("Denied")){
                //Status Denied
                deniedStatus();


            }

        }else {
            Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_SHORT).show();
        }

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, ScanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void deniedStatus() {
        imgStatus.setBackgroundResource(R.drawable.khalad);
        statusBox.setBackgroundColor(Color.parseColor("#ff1f2e"));
        btnContinue.setBackgroundColor(Color.parseColor("#ff1f2e"));


}

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void allowedStatus() {
        imgStatus.setBackgroundResource(R.drawable.sax);
        statusBox.setBackgroundColor(Color.parseColor("#34ae00"));
        btnContinue.setBackgroundColor(Color.parseColor("#34ae00"));

    }

}
