package com.example.groupedtut.reception_sms;
import android.content.Intent;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.telephony.SmsMessage;


import com.example.groupedtut.SQLite.numeroserveur.NumeroServeurDataSource;
import com.example.groupedtut.reception_sms.tri.MessageDechiffre;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    private RecuperationSms recuperationSms;
    private TraitementMessageDechiffre recuperationSmsDechiffre;

    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);

            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String address = smsMessage.getOriginatingAddress();

                if (address.equals(getNumeroServeur(context))) {
                    String smsBody = smsMessage.getMessageBody().toString();

                    //FONCTIONNE
                    this.recuperationSms = new RecuperationSms(smsBody);
                    final MessageDechiffre messageDechiffre = this.recuperationSms.getMessageDechiffre();

                    if(messageDechiffre != null) {
                        this.recuperationSmsDechiffre = new TraitementMessageDechiffre(messageDechiffre);
                        recuperationSmsDechiffre.traiterLeMessage(context);
                    }
                }
            }
        }
    }

    private String getNumeroServeur(Context context) {
        final NumeroServeurDataSource numeroServeurDataSource = new NumeroServeurDataSource(context);
        numeroServeurDataSource.open();
        final String numServeur = numeroServeurDataSource.getNumeroServeur();
        numeroServeurDataSource.close();
        return numServeur;
    }
}