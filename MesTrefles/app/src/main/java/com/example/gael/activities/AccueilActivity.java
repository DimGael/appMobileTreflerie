package com.example.gael.activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.gael.numeroserveur.NumeroServeurDataSource;


public class AccueilActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private NumeroServeurDataSource numeroServeurDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        this.numeroServeurDataSource = new NumeroServeurDataSource(this);
        this.numeroServeurDataSource.open();



        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("STATE", "ça marche 1");


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

                Log.d("STATE", "ça marche 2");
                final Intent intentRefus = new Intent(this, RefusActivity.class);
                this.startActivity(intentRefus);

                Log.d("STATE", "ça marche 3");

            } else {
                //Sinon demander la permission
                Log.d("STATE", "ça marche 4");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intentMenu = new Intent(AccueilActivity.this, MenuPrincipal.class);
                    startActivity(intentMenu);
                    finish();
                }
            }, 2000);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intentMenu1 = new Intent(AccueilActivity.this, MenuPrincipal.class);
                            startActivity(intentMenu1);
                            finish();
                        }
                    }, 3000);

                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intentMenu2 = new Intent(AccueilActivity.this, RefusActivity.class);
                            startActivity(intentMenu2);
                            finish();
                        }
                    }, 3000);
                }
                return;
            }
        }
    }

    @Override
    public void onResume(){
        this.numeroServeurDataSource.open();
        super.onResume();
    }

    @Override
    public void onPause(){
        this.numeroServeurDataSource.close();
        super.onPause();
    }


}
