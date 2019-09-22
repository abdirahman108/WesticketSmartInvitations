package com.westechhub.westicketsmartinvitations;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.westechhub.westicketsmartinvitations.Prevalent.Prevalent;

import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView contactUs;

    @Override
    protected void onStart() {
        requestPermission();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        String Hubiye =  Paper.book().read(Prevalent.ticketActivationCode);
        String Session = Paper.book().read(Prevalent.SessionKey);

        if (!TextUtils.isEmpty(Session)){

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        loginButton = findViewById(R.id.main_getting_started);
        contactUs = findViewById(R.id.main_contact_us_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Please call us", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestPermission(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            // only for Marshmallow and newer versions
            Dexter.withActivity(this)
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).withListener(new MultiplePermissionsListener() {
                @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

                @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        }

    }
}
