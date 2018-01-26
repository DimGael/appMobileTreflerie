package com.example.gael.reception_sms;

import android.content.Context;

/**
 * Created by GaëlPortable on 26/01/2018.
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
