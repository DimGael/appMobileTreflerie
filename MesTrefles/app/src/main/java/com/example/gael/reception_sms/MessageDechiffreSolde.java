package com.example.gael.reception_sms;


/**
 * Cette classe comporte les informations du message de demande de solde : le nouveau solde et le numéro de compte.
 */
public class MessageDechiffreSolde extends MessageDechiffre {

    private String numeroCompte;
    private double solde;

    public MessageDechiffreSolde(String messageBrut) {
        super(messageBrut);

        if(this.estDeCeType()) {
            String[] msgSolde = messageBrut.split(" ");
            this.numeroCompte = msgSolde[5];
            this.solde = getDoubleSansVirgule(msgSolde[8]);
        }
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public double getSolde() {
        return solde;
    }

    @Override
    public boolean estDeCeType() {
        if(messageRecu.length() >= 5) {
            final String debutMessage = messageRecu.substring(0, 5);

            if (debutMessage.equals("Le so"))
                return true;
        }
        return false;
    }
}
