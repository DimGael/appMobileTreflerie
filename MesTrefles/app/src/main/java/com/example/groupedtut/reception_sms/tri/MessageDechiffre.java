package com.example.groupedtut.reception_sms.tri;

/**
 * Created by GaëlPortable on 25/01/2018.
 */

public abstract class MessageDechiffre {

    protected final String messageRecu;

    /**
     * @return vrai si le message est du type du nom de la classe
     */
    public abstract boolean messageRecuCorrespond();

    public MessageDechiffre(String messageBrut){
        this.messageRecu = messageBrut;
    }

    public String getMessageRecu() {
        return messageRecu;
    }

    /**
     * Transforme les 10,25 en double de valeur 10.25.
     * @param message
     * @return
     */
    protected double getDoubleSansVirgule(String message) {
        if (message.contains(",")) {
            String[] messages = message.split(",");
            final double retour = Double.valueOf(messages[0] + "." + messages[1].substring(0,2)).doubleValue();
            return retour;
        }
        return Double.valueOf(message).doubleValue();
    }

    /**
     * @return les 5 premières lettres du messages
     */
    protected String getCinqPremieresLettres() {
        if (messageRecu.length() >= 5) {
            return messageRecu.substring(0, 5);
        }
        return messageRecu;
    }

}
