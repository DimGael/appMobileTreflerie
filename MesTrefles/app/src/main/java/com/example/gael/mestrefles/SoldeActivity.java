package com.example.gael.mestrefles;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SoldeActivity extends BasicTrefleActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static String INTENT_NOUV_SOLDE = "INTENT_NOUV_SOLDE";

    @Override
    public Toolbar getToolbar() {
        return (Toolbar)this.findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_solde);
        super.onCreate(savedInstanceState);

        final Button boutonActualiser = (Button)this.findViewById(R.id.boutonActualiser);
        boutonActualiser.setOnClickListener(this);

        this.majSoldeAffichage(this.soldeDataSource.getSoldeActuel());

        ((TextView)this.findViewById(R.id.texteReponseSolde)).setText("");

        TextView textView1 = (TextView) findViewById(R.id.textMsgSolde);
        TextView textView2 = (TextView) findViewById(R.id.textNbrTrefles);
        TextView textView3 = (TextView) findViewById(R.id.textTrefles);
        TextView textView4 = (TextView) findViewById(R.id.texteReponseSolde);

        Button bouton1 = (Button) findViewById(R.id.boutonActualiser);

        setFont(textView1,"QSregular.ttf");
        setFont(textView2,"QSregular.ttf");
        setFont(textView3,"QSregular.ttf");
        setFont(textView4,"QSregular.ttf");

        setFont(bouton1,"QSregular.ttf");
        instance = this;
    }

    public void setFont(TextView textView, String fontName) {
        if(fontName != null){
            try {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontName);
                textView.setTypeface(typeface);
            } catch (Exception e) {
                Log.e("FONT", fontName + " not found", e);
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.mSolde) {
            //Ne rien faire car on est déjà sur cette activité

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onNavigationItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        //Lors de l'activation du bouton Actualiser
        final String message = "S?";
        SmsManager.getDefault().sendTextMessage(this.numeroServeurDataSource.getNumeroServeur(),null,message,null,null);
        ((TextView)this.findViewById(R.id.texteReponseSolde)).setText("Actualisation du solde en cours ...");
        this.changerEtatBouton();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder((SoldeActivity)instance)
                        .setTitle("Serveur indisponible")
                        .setMessage("Le serveur ne répond pas veuillez réésayer plus tard. Ne pas réessayer tout de suite svp.")
                        .setPositiveButton("J'ai compris", null)
                        .create().show();

                ((SoldeActivity)instance).changerEtatBouton();
                ((TextView)((SoldeActivity)instance).findViewById(R.id.texteReponseSolde)).setText("");

            }
        }, 40000);
    }

    public void changerEtatBouton() {
        final Button bouton = (Button)this.findViewById(R.id.boutonActualiser);
        bouton.setEnabled(!bouton.isEnabled());
    }

    @Override
    public void majSoldeAffichage(double nouvSolde){
        TextView textViewSolde = (TextView)this.findViewById(R.id.textNbrTrefles);
        textViewSolde.setText(Double.toString(nouvSolde));
        super.majSoldeAffichage(nouvSolde);
    }

    @Override
    public void onPause(){
        this.soldeDataSource.close();
        super.onPause();
    }

    @Override
    public void onResume(){
        this.soldeDataSource.open();
        super.onResume();
    }
}
