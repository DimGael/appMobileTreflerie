package com.example.groupedtut.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupedtut.expediteur_message.ExpediteurMessage;
import com.example.groupedtut.expediteur_message.jabber.ExpediteurJabber;
import com.example.groupedtut.expediteur_message.jabber.MyXMPP;
import com.example.groupedtut.numerocompte.NumeroCompteDataSource;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;


public class SoldeActivity extends BasicTrefleActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ChatMessageListener {

    private boolean demandeSoldeEnCours = false;
    private ExpediteurMessage expediteurMessage;

    @Override
    public DrawerLayout getMainDrawerLayout() {
        return (DrawerLayout)findViewById(R.id.solde_drawer_layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_solde);
        super.onCreate(savedInstanceState);

        //this.expediteurMessage = new ExpediteurSMS();
        this.expediteurMessage = new ExpediteurJabber(MyXMPP.globalMyXmpp);
        MyXMPP.globalMyXmpp.setMessageRecuListener(this);

        final Button boutonActualiser = (Button)this.findViewById(R.id.boutonActualiser);
        boutonActualiser.setOnClickListener(this);

        this.majSoldeAffichage(this.soldeDataSource.getSoldeActuel());

        ((TextView)this.findViewById(R.id.texteReponseSolde)).setText("");

        TextView textViewNum = (TextView) findViewById(R.id.textNumCompte);

        NumeroCompteDataSource numeroCompteDataSource = new NumeroCompteDataSource(this);
        numeroCompteDataSource.open();

        setNumeroCompte(numeroCompteDataSource.getNumeroCompte());

        instance = this;
    }

    public void setNumeroCompte(String numeroCompte){
        final TextView textViewNum = (TextView) findViewById(R.id.textNumCompte);
        textViewNum.setText("Compte N°"+numeroCompte);
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
    public void onClick(View view) {
        //Lors de l'activation du bouton Actualiser
        this.demandeSoldeEnCours = true;

        this.expediteurMessage.demandeSoldeActuel(this);
        ((TextView)this.findViewById(R.id.texteReponseSolde)).setText("Actualisation du solde en cours ...");
        this.changerEtatBouton();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                afficherMessageSiDemandeSoldeEnCours();
            }
        }, 40000);


    }


    private void afficherMessageSiDemandeSoldeEnCours() {
        if(instance.getClass() == SoldeActivity.class && this.demandeSoldeEnCours) {
            new AlertDialog.Builder(instance)
                    .setTitle(R.string.titreErreurServeur)
                    .setMessage(R.string.messageErreurServeur)
                    .setPositiveButton(R.string.boutonErreurServeur, null)
                    .create().show();

            final Button boutonActualiser = (Button) instance.findViewById(R.id.boutonActualiser);
            boutonActualiser.setEnabled(true);

            final TextView texteRep = (TextView) instance.findViewById(R.id.texteReponseSolde);
            texteRep.setText("");
        }
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
        this.demandeSoldeEnCours = false;
    }


    @Override
    public void onPause(){
        this.numeroServeurDataSource.close();
        super.onPause();
    }

    @Override
    public void onResume(){
        this.numeroServeurDataSource.open();
        super.onResume();
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        Toast.makeText(this, "Vous avez reçu "+ message.getBody().toString(), Toast.LENGTH_SHORT).show();
    }
}
