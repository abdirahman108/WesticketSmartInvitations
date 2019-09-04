package com.westechhub.westicketsmartinvitations;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.paperdb.Paper;

public class TicketProcessing extends AppCompatActivity {

    private String ResultData = "", ticketStatus = "", Supported = "";
    private String saveCurrentDate, saveCurrentTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);

        ResultData =  getIntent().getStringExtra("Data");
        Supported =  getIntent().getStringExtra("Supported");


        if (ResultData != null && Supported.contains("Yes")){

            //Decode Scan Results
            byte[] decoded= Base64.decode(ResultData, Base64.DEFAULT);
            try {
                String DecData = new String(decoded, "UTF-8");

                //Split Scan Result
                if (DecData.contains(":")) {
                    String[] split = DecData.split("\\:");
                    String eventName = split[0];
                    String ticketNo = split[1];

                    checkTicketStatus(eventName, ticketNo);

                }else {
                    alertDialog();
                    Toast.makeText(this, "Unsupported QR Code", Toast.LENGTH_SHORT).show();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }else {
            alertDialog();
            Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_SHORT).show();
        }

    }

    private void alertDialog() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setTitle("Title");

        dialog.setContentView(R.layout.alert_dialog);
        TextView alertTxt = dialog.findViewById(R.id.dialog_info);
        alertTxt.setText("");
        alertTxt.setText("Unsupported QR Code, please scan the designated and supported codes");

        dialog.show();

        Button btn_dialog = dialog.findViewById(R.id.dialog_ok);
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                finish();
            }
        });
    }


    private void checkTicketStatus(String eventName, String ticketNo) {
        
        String savedTickedNo = Paper.book().read(ticketNo);

        if (TextUtils.isEmpty(savedTickedNo)){

            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("dd, MMM, yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calendar.getTime());

            String timeID = ticketNo + "Time";

            Paper.book().write(ticketNo, ticketNo);
            Paper.book().write(timeID, saveCurrentTime);

            ticketStatus = "Allowed";
            continueToResults(ticketStatus);
        }else {
            ticketStatus = "Denied";
            continueToResults(ticketStatus);
        }
    }

    private void continueToResults(String ticketStatus) {
        Intent intent = new Intent(TicketProcessing.this, ResultsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("Data", ResultData);
        intent.putExtra("Status", ticketStatus);
        startActivity(intent);
        finish();
    }

}
