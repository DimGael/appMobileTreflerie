package com.example.gael.reception_sms.traiteur;

import android.content.Context;

import com.example.gael.reception_sms.dechiffrage.MessageDechiffre;

/**
 * Created by GaëlPortable on 26/01/2018.
 */

/**
 * Traite les messages déchiffrés
 */
public abstract class TraiteurMessage {
    protected MessageDechiffre messageDechiffre;

    public TraiteurMessage(MessageDechiffre typeMessageServeur) {
        this.messageDechiffre = typeMessageServeur;
    }

    /**
     * Traite le message s'il est du bon type.
     * @param context
     */
    public abstract void traiterMessage(Context context);

    /**
     * @return vrai si le message dechiffre correspond au traiteur
     */
    public abstract boolean aLeBonMessage();
}
