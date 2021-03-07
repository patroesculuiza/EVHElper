package com.example.albunsifinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private static int GRANTED_ACCESS = 0;

    private Button guestbutton;
    private Button register;
    private Button login;
    private Button bugrep; //Buton bug reporting Coco
    private Button fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("EVHelper");

        //butoanele de pe mainscreen
        guestbutton = findViewById(R.id.button);
        bugrep = findViewById(R.id.bug);
        register=findViewById(R.id.register);
        login=findViewById(R.id.login);
        fb=findViewById(R.id.feedback);

       fb.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FeedbackActivity.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });




        guestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                fetchLocation();
                if(GRANTED_ACCESS == 1){
                    Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                    startActivity(intent);
                   finish();
                }

            }
        });

        bugrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, BugReportingActivity.class);
                    startActivity(intent);
                    finish();

            }
        });
    }





    private void fetchLocation() {
        // Here, thisActivity is the current activity
        // Permission has already been granted
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(this)
                        .setTitle("Required Location Permission")
                        .setMessage("You have given this permission to access the feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else GRANTED_ACCESS = 1;
    }
}