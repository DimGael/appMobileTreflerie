package com.example.gael.mestrefles;

import android.content.Intent;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.telephony.SmsMessage;
import android.widget.Toast;



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

                if(address.equals("+33687988327")) {
                    String smsBody = smsMessage.getMessageBody().toString();
                     smsMessageStr += "SMS Provenant de : " + address + "\n";
                    smsMessageStr += smsBody + "\n";
                    doitAfficher = true;
                }
            }
            if(doitAfficher){
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            //Traduisons le message reçu :

                //this will update the UI with message
                // SmsActivity inst = SmsActivity.instance();
                // inst.updateList(smsMessageStr);
                this.dirigerMessage(smsMessageStr, context);
            }
        }
    }

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

    }
}