package com.westechhub.westicketsmartinvitations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import io.paperdb.Paper;

public class ResultsActivity extends AppCompatActivity {

    ImageView imgStatus;
    TextView txtStatus, txtTicketInfo, txtEventName, txtTicketNo, txtTime;
    Button btnContinue;
    RelativeLayout outerLayer;
    ActionBar mActionBar;


    private String ResultData = "",  Status = "";

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
        outerLayer = findViewById(R.id.outerLayer);
        btnContinue = findViewById(R.id.result_continue);
        txtTime = findViewById(R.id.time);

        txtStatus.setText(Status);

        Paper.init(this);

        if (ResultData != null){

            //Decode Scan Results
            byte[] decoded= Base64.decode(ResultData, Base64.DEFAULT);
            try {
                String DecData = new String(decoded, "UTF-8");

                //Split Scan Result
                if (DecData.contains(":")) {
                    String[] split = DecData.split("\\:");
                    String eventName = split[0];
                    String ticketNo = split[1];

                    String str1 = String.valueOf(Html.fromHtml("<b>Ticket Number: <b>"));
                    String str2 = String.valueOf(Html.fromHtml("<b>Event Name: <b>" ));


                    txtTicketNo.setText(str1 + ticketNo);
                    txtEventName.setText(str2+ eventName);


                    if (Status.contains("Allowed")){
                        //Status Allowed
                        allowedStatus(ticketNo, eventName);


                    }else if (Status.contains("Denied")){
                        //Status Denied
                        deniedStatus(ticketNo);

                    }


                }else {
                    Toast.makeText(this, "Unsupported QR Code", Toast.LENGTH_SHORT).show();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
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
    private void deniedStatus(String ticketNo) {
        imgStatus.setBackgroundResource(R.drawable.khalad_new);
        outerLayer.setBackgroundColor(Color.parseColor("#ff1f2e"));
        btnContinue.setBackgroundColor(Color.parseColor("#ff1f2e"));

        String timeID = ticketNo + "Time";
        String showTime = Paper.book().read(timeID);
        txtTime.setText("Scanned On: "+ showTime);

}

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void allowedStatus(String ticketNo, String eventName) {
        imgStatus.setBackgroundResource(R.drawable.sax_new);
        outerLayer.setBackgroundColor(Color.parseColor("#34ae00"));
        btnContinue.setBackgroundColor(Color.parseColor("#34ae00"));

        txtTime.setText("");

    }

}
