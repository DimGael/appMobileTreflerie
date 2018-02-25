package com.example.groupedtut.expediteur_message.SMS;

import android.content.Context;
import android.telephony.SmsManager;

import com.example.groupedtut.expediteur_message.ExpediteurMessage;
import com.example.groupedtut.numeroserveur.NumeroServeurDataSource;

import java.math.BigDecimal;

/**
 * Created by Gael on 24/02/2018.
 */

public class ExpediteurSMS implements ExpediteurMessage {

    @Override
    public void demandeSoldeActuel(Context context) {
        final NumeroServeurDataSource numServeurDataSource = new NumeroServeurDataSource(context);
        numServeurDataSource.open();

        SmsManager.getDefault().sendTextMessage(numServeurDataSource.getNumeroServeur(),null,"S?",null,null);

        numServeurDataSource.close();
    }

    @Override
    public void transaction(double montant, String destinataire, Context context) {
        final NumeroServeurDataSource numServeurDataSource = new NumeroServeurDataSource(context);
        numServeurDataSource.open();

        SmsManager.getDefault().sendTextMessage(numServeurDataSource.getNumeroServeur(),null,this.creerMessage(montant, destinataire),null,null);

        numServeurDataSource.close();
    }

    private String creerMessage(double montant, String numDestinataire){
        BigDecimal bd = new BigDecimal(montant);
        bd= bd.setScale(2,BigDecimal.ROUND_DOWN);
        double montantArrondi = bd.doubleValue();

        final int avantVirgule = (int) montantArrondi;

        int indexVirgule = new Double(montantArrondi).toString().indexOf(".");
        String apresVirgule = new Double(montantArrondi).toString().substring(indexVirgule + 1);

        if (apresVirgule.equals("0"))
            return avantVirgule+"/"+numDestinataire;
        else
            return avantVirgule + "," + apresVirgule + "/"+numDestinataire;
    }
}
