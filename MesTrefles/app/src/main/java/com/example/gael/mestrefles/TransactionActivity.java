package com.example.gael.mestrefles;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gael.montantmax.MontantMaxDataSource;


public class TransactionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //Classe qui va nous servir à manipuler la table MontantMax
    private MontantMaxDataSource montantMaxDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        montantMaxDataSource = new MontantMaxDataSource(this);
        montantMaxDataSource.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button boutonValider = (Button)this.findViewById(R.id.boutonValider);
        boutonValider.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final Intent intentAccueil = new Intent(TransactionActivity.this, MenuPrincipal.class);
            this.startActivity(intentAccueil);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mAccueil) {
            final Intent intentAccueil = new Intent(TransactionActivity.this, MenuPrincipal.class);
            this.startActivity(intentAccueil);
        } else if (id == R.id.mDepenses) {
            final Intent intentDepenses = new Intent(TransactionActivity.this, DepenseActivity.class);
            this.startActivity(intentDepenses);

        } else if (id == R.id.mSolde) {
            final Intent intentSolde = new Intent(TransactionActivity.this, SoldeActivity.class);
            this.startActivity(intentSolde);

        } else if (id == R.id.mTransaction) {
            //Ne rien faire car on est déjà sur cette activité

        } else if (id == R.id.mAide) {

        } else if (id == R.id.mParametres) {
            final Intent intentParam = new Intent(TransactionActivity.this, ParametresActivity.class);
            this.startActivity(intentParam);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {


        final EditText editTextMontant = (EditText)this.findViewById(R.id.editTextMontant);
        final EditText editTextNumDestinataire = (EditText)this.findViewById(R.id.editTextNumeroDest);

        if (editTextNumDestinataire.getText().toString().equals("") || editTextMontant.getText().toString().equals("")) {
            Toast.makeText(TransactionActivity.this, "Vous n'avez pas rempli les champs", Toast.LENGTH_SHORT).show();
        }
        else {
            final double montant;
            int numDestinataire = 0;

            montant = Double.valueOf(editTextMontant.getText().toString()).doubleValue();

            try {
                numDestinataire = Integer.valueOf(editTextNumDestinataire.getText().toString()).intValue();
            }
            catch (NumberFormatException e){
                numDestinataire = 10001;
            }

            final double montantMax = this.montantMaxDataSource.getMontantMax();
            final TextView editTextErrMontant = (TextView) this.findViewById(R.id.erreurMontantTransaction);
            final TextView editTextErrNumDestinaire = (TextView) this.findViewById(R.id.erreurNumeroCompte);

            if (numDestinataire > 10000) {
                editTextErrNumDestinaire.setText("Erreur, la saisie du numéro du destinataire est invalide");
                editTextErrMontant.setText("");
            } else if (montant > montantMax) {
                editTextErrMontant.setText("Montant trop élevé \n (Vous pouvez paramètrer le montant maximum dans les paramètres)");
                editTextErrNumDestinaire.setText("");
            } else {
                final String message = montant + "/" + numDestinataire;
                Toast.makeText(TransactionActivity.this, "Message : " + message, Toast.LENGTH_SHORT).show();
                //SmsManager.getDefault().sendTextMessage("0782572437",null,message,null,null);
                editTextMontant.setText("");
                editTextNumDestinataire.setText("");
                editTextErrNumDestinaire.setText("");
                editTextErrMontant.setText("");
            }
        }
    }

    @Override
    public void onPause(){
        this.montantMaxDataSource.close();
        super.onPause();
    }

    @Override
    public void onResume(){
        this.montantMaxDataSource.open();
        super.onResume();
    }

    private String creerMessage(double montant, int numDestinataire){
        return "3,25"+numDestinataire;
    }
}
