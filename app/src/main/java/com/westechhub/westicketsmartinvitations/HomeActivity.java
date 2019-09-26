package com.westechhub.westicketsmartinvitations;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.common.hash.Hashing;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.westechhub.westicketsmartinvitations.Model.Users;
import com.westechhub.westicketsmartinvitations.Prevalent.Prevalent;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView scanInvitations, scannedInivations , btnActivationKey;
    public String dialogOldPass, dialogNewPass, dialogConfirmPass;

    @Override
    protected void onStart() {
        requestPermission();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SliderLayout sliderShow = (SliderLayout) findViewById(R.id.slider);
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Welcome To wesTicket",R.drawable.scanimagezero);
        file_maps.put("Trust",R.drawable.qr1);
        file_maps.put("Secure",R.drawable.westkt);
        file_maps.put("Fast",R.drawable.sliderx);
        file_maps.put("Modern",R.drawable.qr4);
//        "?>><"

//        /**/
        for(String name : file_maps.keySet()){

            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name));

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderShow.addSlider(textSliderView);

        }

            // Qr Scanning Button
        scanInvitations = findViewById(R.id.img_btn_scan);
        scannedInivations = findViewById(R.id.img_btn_scanned_invitations);

        scanInvitations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
                Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });
            // Check List
        scannedInivations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check List

                Intent intent = new Intent(HomeActivity.this, TicketListActivity.class);
                startActivity(intent);

            }
        });

        btnActivationKey = findViewById(R.id.activation_button);

        btnActivationKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ActivationScanActivity.class);
                startActivity(intent);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        String activationStatus = Paper.book().read(Prevalent.ticketActivationCode);

       if (TextUtils.isEmpty(activationStatus)){
           getMenuInflater().inflate(R.menu.menu_inactive, menu);
       }
       else{
           getMenuInflater().inflate(R.menu.menu_active, menu);
       }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.saxx) {

            Intent intent = new Intent(HomeActivity.this, ActivationScanActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_activate) {
            Intent intent = new Intent(HomeActivity.this, ActivationScanActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_changepass) {
            showDialog(HomeActivity.this);

        } else if (id == R.id.nav_contact_us) {
            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("tel:+252634005024"));
            startActivity(callIntent);

        }else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            Paper.book().delete(Prevalent.SessionKey); // Delete Session
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    private void ChangePassword() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Changing Password");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Users").child(Prevalent.currentOnlineUsers.getUsername()).exists()) {
                    Users usersData = dataSnapshot.child("Users").child(Prevalent.currentOnlineUsers.getUsername()).getValue(Users.class);

                    if (usersData.getUsername().equals(Prevalent.currentOnlineUsers.getUsername())) {
                        if (usersData.getPassword().equals(Hashing.sha256().hashString(dialogOldPass, Charset.forName("UTF-8")).toString())) {
                            //change Pass
                            RootRef.child("Users").child(Prevalent.currentOnlineUsers.getUsername()).child("password")
                                    .setValue(Hashing.sha256().hashString(dialogNewPass, Charset.forName("UTF-8")).toString());
                            progressDialog.dismiss();

                            Toast.makeText(HomeActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();

                        } else {

                            showDialog(HomeActivity.this);
                            Toast.makeText(HomeActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                } else {
                    showDialog(HomeActivity.this);
                    Toast.makeText(HomeActivity.this, "Account with this " + Prevalent.currentOnlineUsers.getUsername() + " number do not exists.", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void showDialog(HomeActivity homeActivity) {
        final Dialog dialog = new Dialog(homeActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.change_password);

        final EditText oldPassInput = dialog.findViewById(R.id.old_password);
        final EditText newPassInput = dialog.findViewById(R.id.new_password);
        final EditText confirmInput = dialog.findViewById(R.id.confirm_password);

//        String newPassword = newPassInput.getText().toString();
//        String oldPassword = oldPassInput.getText().toString();
//        String confirmPassword = newPassInput.getText().toString();

        Button updatePass = dialog.findViewById(R.id.update_password);
        Button closeDialog = dialog.findViewById(R.id.close_dialog);

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOldPass = String.valueOf(oldPassInput.getText());
                dialogNewPass = String.valueOf(newPassInput.getText());
                dialogConfirmPass = String.valueOf(confirmInput.getText());

                if (dialogNewPass.equals(dialogConfirmPass)){
                    ChangePassword();
                    dialog.dismiss();

                }else{
                    Toast.makeText(HomeActivity.this, "New Password doesn't match", Toast.LENGTH_SHORT).show();
                    dialog.show();
                }


            }
        });

        dialog.show();
    }
}
