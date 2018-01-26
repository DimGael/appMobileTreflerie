package com.example.gael.reception_sms;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GaëlPortable on 26/01/2018.
 */

public class RecuperationSmsDechiffre {

    private final List<TraiteurMessage> listTraiteurs;

    public RecuperationSmsDechiffre(MessageDechiffre typeMessageServeur) {
        this.listTraiteurs = new ArrayList<TraiteurMessage>(
                Arrays.asList(
                        new TraiteurMessageSolde(typeMessageServeur)
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
