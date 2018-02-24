package com.example.groupedtut.expediteur_message.SMS;

import android.content.Context;
import android.telephony.SmsManager;

import com.example.groupedtut.activities.BasicTrefleActivity;
import com.example.groupedtut.expediteur_message.ExpediteurMessage;
import com.example.groupedtut.numeroserveur.NumeroServeurDataSource;

/**
 * Created by Gael on 24/02/2018.
 */

public class ExpediteurSMS implements ExpediteurMessage {

    @Override
    public void demandeSoldeActuel(Context context) {
        NumeroServeurDataSource numeroServeurDataSource = new NumeroServeurDataSource(context);
        numeroServeurDataSource.open();
        SmsManager.getDefault().sendTextMessage(numeroServeurDataSource.getNumeroServeur(),null,"S?",null,null);
        numeroServeurDataSource.close();
    }

    @Override
    public void transaction(double montant, String destinataire, Context context) {

    }
}
