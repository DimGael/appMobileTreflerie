package com.example.gael.mestrefles;

import android.content.Intent;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.widget.Toast;

import com.example.gael.soldeactuel.Solde;
import com.example.gael.soldeactuel.SoldeDataSource;


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

                if(address.equals("+33782572437")) {
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
                            if(!msgSolde[8].isEmpty()) {
                                /*Gael help me with MySQL to change the value of "solde" GENRE UN SETTEUR LE MAJ MARCHE PAS XD

                                SoldeDataSource soldeActuel = new SoldeDataSource(context);
                                soldeActuel.majSolde(Double.parseDouble(msgSolde[8]));*/

                                String message = msgSolde[8];
                                String[] messages = message.split(",");
                                double nouvSolde = Double.valueOf(messages[0]+"."+messages[1]).doubleValue();
                                Toast.makeText(context, nouvSolde+"", Toast.LENGTH_SHORT).show();
                                Intent solde = new Intent(context, SoldeActivity.class);
                                solde.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                solde.putExtra(SoldeActivity.INTENT_NOUV_SOLDE, nouvSolde);
                                context.startActivity(solde);
                            }
                            break;
                        case "Volum":
                            Toast.makeText(context, "Volume ...", Toast.LENGTH_SHORT).show();
                            Intent volume = new Intent(context, MenuPrincipal.class);
                            volume.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(volume);
                            break;
                        case "Donné":
                            Toast.makeText(context, "Donné à ...", Toast.LENGTH_SHORT).show();
                            Intent donne = new Intent(context, TransactionActivity.class);
                            donne.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(donne);
                            break;
                        case "Recu ":
                            Toast.makeText(context, "Reçu de ...", Toast.LENGTH_SHORT).show();
                            Intent recu = new Intent(context, TransactionActivity.class);
                            recu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(recu);
                            break;
                        case "Trans":
                            Toast.makeText(context, "Transaction", Toast.LENGTH_SHORT).show();
                            Intent transaction = new Intent(context, TransactionActivity.class);
                            transaction.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(transaction);
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
/*
    public String dirigerMessage(String message, Context context){
        String pageChoisie = "Accueil";
        String debutMessage = message.substring(0,5);
        switch (debutMessage){
            case "Votre":
                final Intent myIntent = new Intent(context, TransactionActivity.class);
                context.startActivity(myIntent);
                break;
            case "Le so":
                Toast.makeText(null, "Votre solde a été mis à jour", Toast.LENGTH_SHORT).show();
                break;
            case "Volum":
                final Intent intentP1 = new Intent(context, MenuPrincipal.class);
                context.startActivity(intentP1);
                break;
            case "Donné":
                final Intent intentP2 = new Intent(context, MenuPrincipal.class);
                context.startActivity(intentP2);
                break;
            case "Recu ":
                final Intent intentP3 = new Intent(context, MenuPrincipal.class);
                context.startActivity(intentP3);
                break;
            case "Trans":
                final Intent intentP4 = new Intent(context, MenuPrincipal.class);
                context.startActivity(intentP4);
                break;
            default:
                final Intent intentP5 = new Intent(context, MenuPrincipal.class);
                context.startActivity(intentP5);
                break;
        }

        return pageChoisie;

    }*/
}