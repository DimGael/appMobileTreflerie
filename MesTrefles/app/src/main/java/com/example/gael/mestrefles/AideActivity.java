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
        this.ajoutEcouteursSurBoutons();
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

    private void ajoutEcouteursSurBoutons() {
        //Rajout des écouteurs sur les boutons aide et mail
        Button boutonSiteAide = (Button)this.findViewById(R.id.boutonSiteAide);
        boutonSiteAide.setOnClickListener(this);

        Button boutonMailSupport = (Button)this.findViewById(R.id.boutonMailSupport);
        boutonMailSupport.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.boutonSiteAide) {

            String url= "http://moloco.px.free.fr/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        else if (view.getId() == R.id.boutonMailSupport) {
            //Action lorsque l'utilisateur appuie sur le bouton site
            String url= "http://moloco.px.free.fr/spip.php?article7";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }
    }
}
