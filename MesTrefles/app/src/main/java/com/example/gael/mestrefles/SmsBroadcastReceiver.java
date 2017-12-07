package com.example.gael.mestrefles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
            }
        }
    }

    public String dirigerMessage(String message){
        String pageChoisie = "Accueil";
        String debutMessage = message.substring(0,4);
        switch (debutMessage){
            case "Votre":
                pageChoisie = "DerniereTransaction";
                break;
            case "Le so":
                pageChoisie = "AfficherSolde";
                break;
            case "Volum":
                pageChoisie = "HistoriqueDepenses";
                break;
            case "Donné":
                pageChoisie = "Transaction";
                break;
            case "Recu ":
                //Je ne sais pas
                pageChoisie = "Accueil";
                break;
            case "Trans":
                pageChoisie = "EffectuerTransaction";
                break;
            default:
                pageChoisie = "Accueil";
                break;

        }

        return pageChoisie;

    }
}