package com.example.gael.mestrefles;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuPrincipal extends BasicTrefleActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    @Override
    public Toolbar getToolbar() {
        return (Toolbar)this.findViewById(R.id.main_toolbar);
    }

    @Override
    public DrawerLayout getMainDrawerLayout() {
        return (DrawerLayout)findViewById(R.id.drawer_layout_menu_principal);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_principal);
        super.onCreate(savedInstanceState);

        this.ajoutEcouteursSurBoutons();
    }


    private void ajoutEcouteursSurBoutons() {
        //Rajout des écouteurs sur les boutons
        Button boutonTransaction = (Button)this.findViewById(R.id.BoutonTransaction);
        boutonTransaction.setOnClickListener(this);

        Button boutonSolde = (Button)this.findViewById(R.id.BoutonMonSolde);
        boutonSolde.setOnClickListener(this);

        Button boutonDepense = (Button)this.findViewById(R.id.BoutonMesDepenses);
        boutonDepense.setOnClickListener(this);

        Button boutonParametre = (Button)this.findViewById(R.id.BoutonParametres);
        boutonParametre.setOnClickListener(this);

        Button boutonAide = (Button)this.findViewById(R.id.BoutonAide);
        boutonAide.setOnClickListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Si l'utilisateur clique sur dépense, ne fais rien
        if (id == R.id.mAccueil) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_menu_principal);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        //Sinon la méthode est gérée par la superclass (BasicTrefleActivity)
        return super.onNavigationItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        //Méthode qui s'active si on appuie sur un des écouteurs de la vue
        if(view.getId() == R.id.BoutonTransaction){
            //Action à faire lors de l'utilisation du bouton Transaction
            final Intent intentTransac = new Intent(MenuPrincipal.this, TransactionActivity.class);
            this.startActivity(intentTransac);

        }

        if(view.getId() == R.id.BoutonMonSolde){
            //Action à faire lors de l'utilisation du bouton Solde
            final Intent intentSolde = new Intent(MenuPrincipal.this, SoldeActivity.class);
            this.startActivity(intentSolde);
        }

        if(view.getId() == R.id.BoutonMesDepenses){
            //Action à faire lors de l'utilisation du bouton Dépenses
            final Intent intentDepenses = new Intent(MenuPrincipal.this, DepenseActivity.class);
            this.startActivity(intentDepenses);
        }

        if(view.getId() == R.id.BoutonParametres){
            //Action à faire lors de l'utilisation du bouton Parametres
            final Intent intentParam = new Intent(MenuPrincipal.this, ParametresActivity.class);
            this.startActivity(intentParam);
        }

        if(view.getId() == R.id.BoutonAide){
            //Action à faire lors de l'utilisation du bouton Aide
            final Intent intentAide = new Intent(MenuPrincipal.this, AideActivity.class);
            this.startActivity(intentAide);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Quitter")
                .setMessage("Voulez vous vraiment quitter?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                        //MenuPrincipal.super.onBackPressed();
                    }
                }).create().show();

    }
}
