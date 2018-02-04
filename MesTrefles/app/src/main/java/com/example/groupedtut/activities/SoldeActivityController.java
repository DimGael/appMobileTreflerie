package com.example.groupedtut.activities;

import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.View;

import com.example.groupedtut.numerocompte.NumeroCompteDataSource;
import com.example.groupedtut.numeroserveur.NumeroServeurDataSource;

/**
 * Cette classe contient une instance de SoldeActivity.
 * Elle va gérer les boutons ainsi que les changements de texte.
 */
public class SoldeActivityController implements View.OnClickListener {
    private SoldeActivity soldeActivity;
    private boolean demandeSoldeEnCours;

    //Connexion à la Bdd
    private NumeroServeurDataSource numeroServeurDataSource;
    private NumeroCompteDataSource numeroCompteDataSource;

    public SoldeActivityController(SoldeActivity soldeActivity) {
        this.soldeActivity = soldeActivity;
        this.demandeSoldeEnCours = false;
        this.soldeActivity.getBoutonActualiser().setOnClickListener(this);
        this.numeroServeurDataSource = new NumeroServeurDataSource(this.soldeActivity.getBaseContext());
        this.numeroServeurDataSource.open();
        this.numeroCompteDataSource = new NumeroCompteDataSource(this.soldeActivity.getBaseContext());
        this.numeroCompteDataSource.open();

        this.soldeActivity.getTextNumeroCompte().setText("Compte N°"+this.numeroCompteDataSource.getNumeroCompte());
    }

    @Override
    public void onClick(View view) {
        //Lors de l'activation du bouton Actualiser
        this.demandeSoldeEnCours = true;
        final String message = "S?";

        SmsManager.getDefault().sendTextMessage(this.numeroServeurDataSource.getNumeroServeur(),null,message,null,null);
        this.soldeActivity.getTextReponseSolde().setText("Actualisation du solde en cours ...");
        this.soldeActivity.getBoutonActualiser().setEnabled(true);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                afficherMessageSiDemandeSoldeEnCours();
            }
        }, 40000);
    }

    private void afficherMessageSiDemandeSoldeEnCours() {
        if(SoldeActivity.instance!=null && this.demandeSoldeEnCours) {
            new AlertDialog.Builder(this.soldeActivity)
                    .setTitle(R.string.titreErreurServeur)
                    .setMessage(R.string.messageErreurServeur)
                    .setPositiveButton(R.string.boutonErreurServeur, null)
                    .create().show();

            this.soldeActivity.getBoutonActualiser().setEnabled(true);
            this.soldeActivity.getTextReponseSolde().setText("");
        }
    }

    public void majSoldeAffichage(double nouvSolde){
        this.soldeActivity.getTextMontantSolde().setText(Double.toString(nouvSolde));
        this.demandeSoldeEnCours = false;
    }


    public void onPause(){
        this.numeroServeurDataSource.close();
    }

    public void onResume(){
        this.numeroServeurDataSource.open();
    }
}
