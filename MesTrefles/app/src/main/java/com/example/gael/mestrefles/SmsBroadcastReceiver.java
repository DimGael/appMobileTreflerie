package com.example.gael.mestrefles;
import android.content.Intent;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.telephony.SmsMessage;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.gael.TransactionHistorique.Transaction;
import com.example.gael.TransactionHistorique.TransactionDataSource;

import com.example.gael.numerocompte.NumeroCompteDataSource;
import com.example.gael.numeroserveur.NumeroServeurDataSource;
import com.example.gael.soldeactuel.SoldeDataSource;

import java.util.Date;
import java.util.regex.Pattern;


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

                    String debutMessage = new String();

                    if(smsBody.length() >= 5)
                        debutMessage = smsBody.substring(0,5);

                    switch (debutMessage) {
                        case "Votre":
                            Toast.makeText(context, "Derniere transaction ...", Toast.LENGTH_SHORT).show();
                            break;
                        case "Le so":
                            String[] msgSolde = smsBody.split(" ");

                            if(!msgSolde[5].isEmpty()) {
                                //Traiter le numéro de compte

                                String numeroCompte = (msgSolde[5]);
                                modificationNumeroCompte(context, numeroCompte);
                            }

                            if (!msgSolde[8].isEmpty()) {
                                final double nouvSolde = getDoubleSansVirgule(msgSolde[8]);
                                this.updateSoldeBdd(context, nouvSolde);
                                if (SoldeActivity.instance != null) {
                                    this.receptionSmsSoldeDansSoldeActivity(this.getDoubleSansVirgule(msgSolde[8]));
                                }
                            }
                            break;
                        case "Volum":
                            Toast.makeText(context, "Volume ...", Toast.LENGTH_SHORT).show();
                            Intent volume = new Intent(context, MenuPrincipal.class);
                            volume.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(volume);
                            break;
                        case "Donné":

                            //A faire lors de la confirmation d'un transaction
                            String soldeEnvoyeString = smsBody.split(":")[1];
                            soldeEnvoyeString = soldeEnvoyeString.split("T")[0];

                            String msgAvecNumCompte = smsBody.split(" ")[4];
                            msgAvecNumCompte = msgAvecNumCompte.substring(1); //(31):62
                            msgAvecNumCompte = msgAvecNumCompte.split(":")[0];

                            String numeroCompte = new String();
                            for(int indexMsgNumCompte = 0; indexMsgNumCompte<msgAvecNumCompte.length()-1;indexMsgNumCompte++){
                                numeroCompte += msgAvecNumCompte.charAt(indexMsgNumCompte);
                            }

                            String nomPersonne = smsBody.split(" ")[2];

                            double soldeEnvoye = getDoubleSansVirgule(soldeEnvoyeString);

                            this.ajouterSolde(context, soldeEnvoye*(-1));

                            this.ajouterNouvelleTransactionSortante(context, soldeEnvoye, numeroCompte, nomPersonne);

                            if(TransactionActivity.instance != null){
                                ((TransactionActivity)TransactionActivity.instance).transactionReussie();
                            }
                            break;

                        case "Recu ":
                            String[] msgReception = smsBody.split(" ");
                            Toast.makeText(context, "Reçu de ...", Toast.LENGTH_SHORT).show();
                            if (!msgReception[2].isEmpty() && !msgReception[3].isEmpty()) {
                                String provenanceNomPrenom = (msgReception[2] + " " + msgReception[3]);
                                Toast.makeText(context, provenanceNomPrenom, Toast.LENGTH_SHORT).show();
                            }

                            if (!msgReception[4].isEmpty()) {
                                String partieAvecNumeroCompte[] = msgReception[4].split(Pattern.quote("("));
                                String partieAvecNumeroCompte2[] = partieAvecNumeroCompte[1].split(Pattern.quote(")"));
                                String provenanceNumeroCompte = (partieAvecNumeroCompte2[0]);
                                String partieTrefles[] = smsBody.split(Pattern.quote(":"));
                                String treflesRecus = partieTrefles[1];

                                final double treflesRecusValeur = this.getDoubleSansVirgule(treflesRecus);
                                this.ajouterSolde(context, treflesRecusValeur);
                            }
                            break;
                        case " Tran":

                            if (TransactionActivity.instance != null) {
                                ((TransactionActivity) TransactionActivity.instance).transactionEchouee();
                            }
                            break;
                        default:
                            break;
                    }
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

    private void ajouterNouvelleTransactionEntrante(Context context, double soldeEnvoye, String type, String numeroCompte, String nomPersonne){
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

    }
}