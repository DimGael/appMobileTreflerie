package com.example.groupedtut.reception_sms;
import android.content.Intent;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.telephony.SmsMessage;


import com.example.groupedtut.numeroserveur.NumeroServeurDataSource;
import com.example.groupedtut.reception_sms.tri.MessageDechiffre;


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

                    //FONCTIONNE
                    RecuperationSms recuperationSms = new RecuperationSms(smsBody);
                    MessageDechiffre messageDechiffre = recuperationSms.recupererMessageDechiffre();

                    RecuperationSmsDechiffre recuperationSmsDechiffre = new RecuperationSmsDechiffre(messageDechiffre);
                    recuperationSmsDechiffre.traiterLeMessage(context);
                }
            }
        }
    }

    private String getNumeroServeur(Context context) {
        final NumeroServeurDataSource numeroServeurDataSource = new NumeroServeurDataSource(context);
        numeroServeurDataSource.open();
        return numeroServeurDataSource.getNumeroServeur();
    }
}