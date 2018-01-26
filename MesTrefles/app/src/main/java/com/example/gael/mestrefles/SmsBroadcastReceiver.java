package com.example.gael.mestrefles;
import android.content.Intent;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.telephony.SmsMessage;
import android.widget.TextView;


import com.example.gael.TransactionHistorique.TransactionDataSource;

import com.example.gael.numerocompte.NumeroCompteDataSource;
import com.example.gael.numeroserveur.NumeroServeurDataSource;
import com.example.gael.soldeactuel.SoldeDataSource;

import java.util.Date;


public class SmsBroadcastReceiver extends BroadcastReceiver {


    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {

        boolean doitAfficher = false;
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);

            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String address = smsMessage.getOriginatingAddress();

                if (address.equals(getNumeroServeur(context))) {
                    String smsBody = smsMessage.getMessageBody().toString();

                    RecuperationSms recuperationSms = new RecuperationSms(smsBody);

                    DechiffreurMessage typeMessageServeur = recuperationSms.determinerType();

                    RecuperationSmsDechiffre recuperationSmsDechiffre = new RecuperationSmsDechiffre(typeMessageServeur);

                }
            }
        }
    }

    private void ajouterNouvelleTransaction(Context context, double soldeEnvoye, boolean estSortant, String numeroCompte, String nomPersonne) {
        final TransactionDataSource transactionDataSource = new TransactionDataSource(context);
        transactionDataSource.open();

        Date d=new Date();
        String month = Integer.toString(d.getMonth()+1);
        if(month.length() == 1){
            month="0"+month;
        }
        String date = d.getDate()+"/"+month +"/"+Integer.toString(d.getYear()).substring(1);

        String compte = numeroCompte + " : " + nomPersonne;
        transactionDataSource.ajouterNouvelleTransaction(soldeEnvoye, date, compte, estSortant);
    }

    private void ajouterNouvelleTransactionSortante(Context context, double soldeEnvoye, String numeroCompte, String nomPersonne){
        this.ajouterNouvelleTransaction(context, soldeEnvoye, true, numeroCompte, nomPersonne);
    }

    private void ajouterNouvelleTransactionEntrante(Context context, double soldeEnvoye, String numeroCompte, String nomPersonne){
        this.ajouterNouvelleTransaction(context, soldeEnvoye, false, numeroCompte, nomPersonne);
    }

    private String getNumeroServeur(Context context) {
        final NumeroServeurDataSource numeroServeurDataSource = new NumeroServeurDataSource(context);
        numeroServeurDataSource.open();
        return numeroServeurDataSource.getNumeroServeur();
    }

    private void updateSoldeBdd(Context context, double nouvSolde) {
        final SoldeDataSource soldeDataSource = new SoldeDataSource(context);
        soldeDataSource.open();
        soldeDataSource.majSolde(nouvSolde);
    }

    private String getNumeroCompte(Context context, long nouvCompte) {
        NumeroCompteDataSource numeroCompteDataSource = new NumeroCompteDataSource(context);
        numeroCompteDataSource.open();
        return numeroCompteDataSource.getNumeroCompte();
    }

    private void ajouterSolde(Context context, double soldeAAjouter) {
        final SoldeDataSource soldeDataSource = new SoldeDataSource(context);
        soldeDataSource.open();
        soldeDataSource.majSolde(soldeDataSource.getSoldeActuel() + soldeAAjouter);
    }

    private void receptionSmsSoldeDansSoldeActivity(double nouvSolde) {
        //Choses à faire si on est dans SoldeActivity
        SoldeActivity.instance.majSoldeAffichage(nouvSolde);
        ((TextView) SoldeActivity.instance.findViewById(R.id.texteReponseSolde)).setText("Solde Actualisé !");
        ((SoldeActivity) SoldeActivity.instance).changerEtatBouton();
    }

    private double getDoubleSansVirgule(String message) {
        if (message.contains(",")) {
            String[] messages = message.split(",");
            return Double.valueOf(messages[0] + "." + messages[1]).doubleValue();
        }
        return Double.valueOf(message).doubleValue();
    }

    private void modificationNumeroCompte(Context context, String numeroCompte) {
        NumeroCompteDataSource numeroCompteDataSource = new NumeroCompteDataSource(context);
        numeroCompteDataSource.open();
        numeroCompteDataSource.setNumeroCompte(numeroCompte);
        numeroCompteDataSource.close();
    }
}