package com.example.gael.mestrefles;

import com.example.gael.TransactionHistorique.Transaction;

/**
 * Created by GaëlPortable on 25/01/2018.
 */

public class DechiffrementSMS {
    private final String messageBrut;
    private Message messageDechiffre;

    public DechiffrementSMS(String messageBrut) {
        this.messageBrut = messageBrut;
        this.determinerType();
    }

    public Message getMessageDechiffre(){
        return this.messageDechiffre;
    }

    public Class determinerType(){
        String debutMessage = new String();

        if(messageBrut.length() >= 5)
            debutMessage = messageBrut.substring(0,5);

        switch (debutMessage) {
            case "Votre"://Type : dernière Transaction
                break;

            case "Le so": //Type : Demande Solde
                this.messageDechiffre = new MessageDemandeSolde(messageBrut);
                break;

            case "Volum"://Type : Volume ...
                break;

            case "Donné"://Type : Confirmation Transaction
                break;

            case "Recu "://Type : Réception d'une Transaction
                break;

            case " Tran"://Type : Transaction Echouée
                break;

            default://Type : Inconnu
                break;
        }

        return this.messageDechiffre.getClass();
    }
}
