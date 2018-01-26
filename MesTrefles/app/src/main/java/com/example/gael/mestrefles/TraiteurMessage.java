package com.example.gael.mestrefles;

/**
 * Created by GaëlPortable on 26/01/2018.
 */

public abstract class TraiteurMessage {
    protected DechiffreurMessage dechiffreurMessage;

    public TraiteurMessage(DechiffreurMessage typeMessageServeur) {
        this.dechiffreurMessage = typeMessageServeur;
    }

    /**
     * Traite le message si il est du bon type.
     */
    public abstract void traiterMessage();
}
