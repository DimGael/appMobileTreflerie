package com.example.groupedtut.reception_sms;

import android.content.Context;

import com.example.groupedtut.reception_sms.tri.MessageDechiffre;
import com.example.groupedtut.reception_sms.traitement.TraiteurMessage;
import com.example.groupedtut.reception_sms.traitement.TraiteurMessageReceptionTransaction;
import com.example.groupedtut.reception_sms.traitement.TraiteurMessageSolde;
import com.example.groupedtut.reception_sms.traitement.TraiteurMessageTransactionEchouee;
import com.example.groupedtut.reception_sms.traitement.TraiteurMessageTransactionReussie;

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
