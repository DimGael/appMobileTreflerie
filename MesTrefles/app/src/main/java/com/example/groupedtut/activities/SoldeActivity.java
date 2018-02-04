package com.example.groupedtut.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


import com.example.groupedtut.numerocompte.NumeroCompteDataSource;

import org.w3c.dom.Text;

/**
 * Solde Activity effectue l'affichage de base. Les changements d'affichage sont effectués par .SoldeActivityController
 */
public class SoldeActivity extends BasicTrefleActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button boutonActualiser;
    private TextView textReponseSolde;
    private TextView textNumeroCompte;
    private SoldeActivityController soldeActivityController;
    private TextView textViewSolde;

    @Override
    public DrawerLayout getMainDrawerLayout() {
        return (DrawerLayout)findViewById(R.id.solde_drawer_layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_solde);
        super.onCreate(savedInstanceState);

        this.soldeActivityController = new SoldeActivityController(this);
        this.boutonActualiser = (Button)this.findViewById(R.id.boutonActualiser);
        this.textReponseSolde = (TextView)this.findViewById(R.id.texteReponseSolde);
        this.textReponseSolde.setText("");
        this.textViewSolde = (TextView)this.findViewById(R.id.textNbrTrefles);
        this.textNumeroCompte = (TextView)findViewById(R.id.textNumCompte);

        this.majSoldeAffichage(this.soldeDataSource.getSoldeActuel());

        instance = this;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.mSolde) {
            //Ne rien faire car on est déjà sur cette activité

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.solde_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onNavigationItemSelected(item);
    }

    @Override
    public void majSoldeAffichage(double nouvSolde){
        this.soldeActivityController.majSoldeAffichage(nouvSolde);
        super.majSoldeAffichage(nouvSolde);
    }

    @Override
    public void onPause(){
        this.soldeActivityController.onPause();
        super.onPause();
    }

    @Override
    public void onResume(){
        this.soldeActivityController.onResume();
        super.onResume();
    }


    //GETTERS
    public Button getBoutonActualiser() {
        return boutonActualiser;
    }

    public TextView getTextReponseSolde() {
        return textReponseSolde;
    }

    public TextView getTextNumeroCompte() {
        return textNumeroCompte;
    }

    public TextView getTextMontantSolde() {
        return textViewSolde;
    }
}
