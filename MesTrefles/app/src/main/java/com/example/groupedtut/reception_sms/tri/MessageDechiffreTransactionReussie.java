package com.example.groupedtut.reception_sms.tri;

/**
 * Created by GaëlPortable on 26/01/2018.
 */

public class MessageDechiffreTransactionReussie extends MessageDechiffre {

    private String prenomBeneficiaire;
    private String nomBeneficiaire;
    private String numeroCompteBeneficiaire;
    private double montantEnvoye;

    public MessageDechiffreTransactionReussie(String messageBrut) {
        super(messageBrut);

        if(messageRecuCorrespond()){
            String soldeEnvoyeString = messageBrut.split(":")[1];
            soldeEnvoyeString = soldeEnvoyeString.split("T")[0];
            this.montantEnvoye = super.getDoubleSansVirgule(soldeEnvoyeString);

            String msgNumCompte = messageBrut.split(" ")[4];
            msgNumCompte = msgNumCompte.substring(1); //(31):62
            msgNumCompte = msgNumCompte.split(":")[0];

            this.numeroCompteBeneficiaire = new String();
            for(int index = 0; index<msgNumCompte.length()-1;index++){
                this.numeroCompteBeneficiaire += msgNumCompte.charAt(index);
            }

            this.nomBeneficiaire = messageBrut.split(" ")[2];
            this.prenomBeneficiaire = messageBrut.split(" ")[3];
        }
    }

    @Override
    public boolean messageRecuCorrespond() {
        if (this.getCinqPremieresLettres().equals("Donné"))
            return true;

        return false;
    }

    public String getPrenomBeneficiaire() {
        return prenomBeneficiaire;
    }

    public String getNomBeneficiaire() {
        return nomBeneficiaire;
    }

    public String getNumeroCompteBeneficiaire() {
        return numeroCompteBeneficiaire;
    }

    public double getMontantEnvoye() {
        return montantEnvoye;
    }
}
