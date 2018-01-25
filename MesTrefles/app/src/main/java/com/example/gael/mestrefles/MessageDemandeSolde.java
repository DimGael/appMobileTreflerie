package com.example.gael.mestrefles;


/**
 * Cette classe comporte les informations du message de demande de solde : le nouveau solde et le numéro de compte.
 */
public class MessageDemandeSolde implements Message {

    private final String numeroCompte;
    private final double solde;

    public MessageDemandeSolde(String messageBrut) {
        String[] msgSolde = messageBrut.split(" ");
        this.numeroCompte = msgSolde[5];
        this.solde = getDoubleSansVirgule(msgSolde[8]);
    }

    private double getDoubleSansVirgule(String message) {
        if (message.contains(",")) {
            String[] messages = message.split(",");
            return Double.valueOf(messages[0] + "." + messages[1]).doubleValue();
        }
        return Double.valueOf(message).doubleValue();
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public double getSolde() {
        return solde;
    }
}
