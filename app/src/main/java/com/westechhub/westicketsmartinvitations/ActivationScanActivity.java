package com.westechhub.westicketsmartinvitations;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.paperdb.Paper;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;


public class ActivationScanActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    private static final String FLASH_STATE = "FLASH_STATE";
    private boolean mFlash;
    public static String SData;
    private String ScData = null;
    private ProgressDialog dialog;
    private String saveCurrentDate, saveCurrentTime;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
        if(state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
        } else {
            mFlash = false;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Paper.init(this);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
        mScannerView.setFlash(mFlash);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem;
        if(mFlash) {
            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_on);
            menuItem.setIcon(getResources().getDrawable(R.drawable.flash_on));
        } else {
            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_off);
            menuItem.setIcon(getResources().getDrawable(R.drawable.flash_off));
        }
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_flash:
                mFlash = !mFlash;
                if(mFlash) {
                    item.setIcon(getResources().getDrawable(R.drawable.flash_on));
                } else {
                    item.setIcon(getResources().getDrawable(R.drawable.flash_off));
                }
                mScannerView.setFlash(mFlash);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        // Do something with the result here
        Log.v("kkkk", result.getContents()); // Prints scan results
        Log.v("uuuu", result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)

        //Get Scan Results
        ScData = result.getContents();




        String regex = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";

        if (ScData.matches(regex)){
            // Send Scan Results to Ticket Processing Class
            Intent intent = new Intent(ActivationScanActivity.this, TicketProcessor.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("ActivationData", ScData);
            startActivity(intent);
            finish();
        }else {
            alertDialog();
            vibrate();
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
                finish();
                dialog.cancel();
            }
        });
    }
    public void toneGen(){
        ToneGenerator toneG = new ToneGenerator(AudioManager.ERROR, 100);
        toneG.startTone(ToneGenerator.TONE_SUP_ERROR, 200);
    }

    public void vibrate(){

        int vibrateTime = 200;

        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(vibrateTime, VibrationEffect.DEFAULT_AMPLITUDE));
        }else {
            vibrator.vibrate(vibrateTime);
        }

    }
}