package com.westechhub.westicketsmartinvitations;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.List;

import static com.westechhub.westicketsmartinvitations.DatabaseHelper.COL_1;
import static com.westechhub.westicketsmartinvitations.DatabaseHelper.COL_2;
import static com.westechhub.westicketsmartinvitations.DatabaseHelper.COL_3;
import static com.westechhub.westicketsmartinvitations.DatabaseHelper.COL_4;
import static com.westechhub.westicketsmartinvitations.DatabaseHelper.COL_5;
import static com.westechhub.westicketsmartinvitations.DatabaseHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView contactUs;
    SQLiteDatabase db;

//    Context context = this;


    @Override
    protected void onStart() {
        requestPermission();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);

        loginButton = findViewById(R.id.main_getting_started);
        contactUs = findViewById(R.id.main_contact_us_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importDB();
            }
        });

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Please call us", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void importDB() {

        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File sd = new File(dir);
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String backupDBPath = "/data/com.westechhub.westicketsmartinvitations/databases/A.db";
        String currentDBPath = "A.db";
        File currentDB = new File(sd, currentDBPath);
        File backupDB = new File(data, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFile() throws IOException {



    }

    private void requestPermission(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            // only for Marshmallow and newer versions
            Dexter.withActivity(this)
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                            Manifest.permission.CALL_PHONE,
//                            Manifest.permission.READ_SMS,
//                            Manifest.permission.READ_CONTACTS
                    ).withListener(new MultiplePermissionsListener() {
                @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

                @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        }

    }
}