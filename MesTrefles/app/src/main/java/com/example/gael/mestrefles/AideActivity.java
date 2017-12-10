package com.example.gael.mestrefles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gael.montantmax.MontantMaxDataSource;


public class AideActivity extends BasicTrefleActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    public Toolbar getToolbar() {
        return (Toolbar)this.findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_aide);
        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mAide) {

        } else if (id == R.id.mTransaction) {
            final Intent intentTransac = new Intent(AideActivity.this, TransactionActivity.class);
            this.startActivity(intentTransac);

        } else if (id == R.id.mAide) {

        } else if (id == R.id.mParametres) {
            final Intent intentParam = new Intent(AideActivity.this, ParametresActivity.class);
            this.startActivity(intentParam);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }

        return super.onNavigationItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.boutonSiteAide) {
            //MARCHE PAS 
            Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        else if (id == R.id.boutonMailSupport) {
            //Action lorsque l'utilisateur appuie sur le bouton site
            Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        }
    }
}
