package com.example.gael.reception_sms;

import android.content.Context;

import com.example.gael.reception_sms.dechiffrage.MessageDechiffre;
import com.example.gael.reception_sms.traiteur.TraiteurMessage;
import com.example.gael.reception_sms.traiteur.TraiteurMessageReceptionTransaction;
import com.example.gael.reception_sms.traiteur.TraiteurMessageSolde;
import com.example.gael.reception_sms.traiteur.TraiteurMessageTransactionEchouee;
import com.example.gael.reception_sms.traiteur.TraiteurMessageTransactionReussie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GaëlPortable on 26/01/2018.
 */


/**
 * Récupère les Messages déchiffrés par la classe MessageDechiffre, puis traite ces messages
 */
public class RecuperationSmsDechiffre {

    private final List<TraiteurMessage> listTraiteurs;

    public RecuperationSmsDechiffre(MessageDechiffre typeMessageServeur) {
        this.listTraiteurs = new ArrayList<TraiteurMessage>(
                Arrays.asList(
                        new TraiteurMessageSolde(typeMessageServeur),
                        new TraiteurMessageTransactionEchouee(typeMessageServeur),
                        new TraiteurMessageTransactionReussie(typeMessageServeur),
                        new TraiteurMessageReceptionTransaction(typeMessageServeur)
                )
        );
    }

    public void traiterLeMessage(Context context) {
        for (TraiteurMessage traiteurMessage: listTraiteurs) {

            //Si le traiteur correspond au message
            if(traiteurMessage.aLeBonMessage()){
                traiteurMessage.traiterMessage(context);
            }

        }
    }
}
