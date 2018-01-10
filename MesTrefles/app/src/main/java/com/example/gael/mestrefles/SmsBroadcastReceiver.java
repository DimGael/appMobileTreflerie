package com.example.gael.mestrefles;

import android.content.Intent;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gael.numeroserveur.NumeroServeurDataSource;
import com.example.gael.soldeactuel.SoldeDataSource;

import java.util.regex.Pattern;


public class SmsBroadcastReceiver extends BroadcastReceiver {


    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {

        boolean doitAfficher = false;
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";

            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String address = smsMessage.getOriginatingAddress();

                if(address.equals(getNumeroServeur(context))) {
                    String smsBody = smsMessage.getMessageBody().toString();
                    smsMessageStr += "SMS Provenant de : " + address + "\n";
                    smsMessageStr += smsBody + "\n";
                    String debutMessage = smsBody.substring(0,5);

                    switch (debutMessage){
                        case "Votre":
                            Toast.makeText(context, "Derniere transaction ...", Toast.LENGTH_SHORT).show();
                            Intent derniereTransaction = new Intent(context, MenuPrincipal.class);
                            derniereTransaction.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(derniereTransaction);
                            break;
                        case "Le so":
                            String[] msgSolde = smsBody.split(" ");

                            if(!msgSolde[5].isEmpty()) {
                                String numeroCompte = (msgSolde[5]);
                            }

                            if(!msgSolde[8].isEmpty()) {
                                final double nouvSolde = getDoubleSansVirgule(msgSolde[8]);
                                this.updateSoldeBdd(context, nouvSolde);
                                if(SoldeActivity.instance != null){
                                    this.receptionSmsDansSoldeActivity(this.getDoubleSansVirgule(msgSolde[8]));
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
                            Toast.makeText(context, soldeEnvoyeString, Toast.LENGTH_SHORT).show();

                            double soldeEnvoye = getDoubleSansVirgule(soldeEnvoyeString);

                            this.ajouterSolde(context, soldeEnvoye*(-1));
                            break;

                        case "Recu ":
                            String[] msgReception = smsBody.split(" ");
                            Toast.makeText(context, "Reçu de ...", Toast.LENGTH_SHORT).show();
                            if(!msgReception[2].isEmpty() && !msgReception[3].isEmpty()) {
                                String provenanceNomPrenom = (msgReception[2]+" "+msgReception[3]);
                                Toast.makeText(context, provenanceNomPrenom, Toast.LENGTH_SHORT).show();
                            }

                            if(!msgReception[4].isEmpty()) {
                                String partieAvecNumeroCompte[] = msgReception[4].split(Pattern.quote("("));
                                String partieAvecNumeroCompte2[] = partieAvecNumeroCompte[1].split(Pattern.quote(")"));
                                String provenanceNumeroCompte = (partieAvecNumeroCompte2[0]);
                                String partieTrefles[] = smsBody.split(Pattern.quote(":"));
                                String treflesRecus = partieTrefles[1];

                                final double treflesRecusValeur = this.getDoubleSansVirgule(treflesRecus);
                                this.ajouterSolde(context, treflesRecusValeur);
                            }
                            break;
                        case "Trans":
                            Toast.makeText(context, "Transaction", Toast.LENGTH_SHORT).show();
                            /*Intent transaction = new Intent(context, TransactionActivity.class);
                            transaction.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(transaction);*/
                            break;
                        default:
                            Toast.makeText(context, "DEFAULT", Toast.LENGTH_SHORT).show();
                            Intent menu = new Intent(context, MenuPrincipal.class);
                            menu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(menu);
                            break;
                    }
                }
            }
        }
    }

    private String getNumeroServeur(Context context) {
        NumeroServeurDataSource numeroServeurDataSource = new NumeroServeurDataSource(context);
        numeroServeurDataSource.open();
        return numeroServeurDataSource.getNumeroServeur();
    }

    private void updateSoldeBdd(Context context, double nouvSolde) {
        SoldeDataSource soldeDataSource = new SoldeDataSource(context);
        soldeDataSource.open();
        soldeDataSource.majSolde(nouvSolde);
    }

    private void ajouterSolde(Context context, double soldeAAjouter) {
        SoldeDataSource soldeDataSource = new SoldeDataSource(context);
        soldeDataSource.open();
        soldeDataSource.majSolde(soldeDataSource.getSoldeActuel()+soldeAAjouter);
    }

    private void receptionSmsDansSoldeActivity(double nouvSolde) {
        //Choses à faire si on est dans SoldeActivity
        SoldeActivity.instance.majSoldeAffichage(nouvSolde);
        ((TextView)SoldeActivity.instance.findViewById(R.id.texteReponseSolde)).setText("Solde Actualisé !");
        ((SoldeActivity)SoldeActivity.instance).changerEtatBouton();
    }

    private double getDoubleSansVirgule(String message) {
        if(message.contains(",")) {
            String[] messages = message.split(",");
            return Double.valueOf(messages[0]+"."+messages[1]).doubleValue();
        }
        return Double.valueOf(message).doubleValue();
    }
}