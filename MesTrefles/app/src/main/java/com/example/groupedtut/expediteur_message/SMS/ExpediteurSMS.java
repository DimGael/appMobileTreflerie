package com.example.groupedtut.expediteur_message.SMS;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.example.groupedtut.expediteur_message.ExpediteurMessage;
import com.example.groupedtut.SQLite.numeroserveur.NumeroServeurDataSource;

import java.util.regex.Pattern;

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

    @Override
    public void transactionCommentaire(double montant, String commentaire, String destinataire, Context context) {

        if(commentaire.equals("")){
            transaction(montant, destinataire, context);
        }
        else {

            final NumeroServeurDataSource numServeurDataSource = new NumeroServeurDataSource(context);
            numServeurDataSource.open();

            String messageDemandeTransac = creerMessage(montant, destinataire);
            messageDemandeTransac += " ## " + commentaire;
            SmsManager.getDefault().sendTextMessage(numServeurDataSource.getNumeroServeur(), null, messageDemandeTransac, null, null);

            numServeurDataSource.close();
        }
    }

    private String creerMessage(double montant, String numDestinataire) {
        String str_montant = new Double(montant).toString();
        String[] tab = str_montant.split(Pattern.quote("."));

        return tab[0] + "/" + numDestinataire + "," + tab[1];
    }
}
