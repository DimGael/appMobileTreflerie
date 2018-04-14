package com.example.groupedtut.reception_sms.tri;

import java.util.regex.Pattern;

/**
 * Created by GaëlPortable on 26/01/2018.
 */

public class MessageDechiffreReceptionTransaction extends MessageDechiffre {

    private double montantRecu;
    private String nomExpediteur;
    private String prenomExpediteur;
    private String numeroCompte;
    private String commentaire;

    public MessageDechiffreReceptionTransaction(String messageBrut) {
        super(messageBrut);
        if(messageRecuCorrespond()) {

            //Montant Reçu
            String partieTrefles[] = messageBrut.split(Pattern.quote(":"));
            String treflesRecus = partieTrefles[1].split("T")[0];
            this.montantRecu = super.getDoubleSansVirgule(treflesRecus);

            //Nom et prénom
            String[] smsSplitBySpace = messageBrut.split(" ");
            this.nomExpediteur = smsSplitBySpace[2];
            this.prenomExpediteur = smsSplitBySpace[3];

            //Numéro de compte
            String partieAvecNumeroCompte[] = smsSplitBySpace[4].split(Pattern.quote("("));
            partieAvecNumeroCompte = partieAvecNumeroCompte[1].split(Pattern.quote(")"));
            this.numeroCompte = (partieAvecNumeroCompte[0]);

            //Commentaire
            if(messageBrut.contains("#")){
                String partieAvecCommentaire = messageBrut.split("#")[1];
                this.commentaire = partieAvecCommentaire.trim();
            }
            else
                this.commentaire = "";
        }
    }

    @Override
    public boolean messageRecuCorrespond() {
        if (this.getCinqPremieresLettres().equals("Recu "))
            return true;

        return false;
    }

    public String getCommentaire(){
        return this.commentaire;
    }

    public double getMontantRecu() {
        return montantRecu;
    }

    public String getNomExpediteur() {
        return nomExpediteur;
    }

    public String getPrenomExpediteur() {
        return prenomExpediteur;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public boolean aUnCommentaire(){
        return !this.commentaire.equals("");
    }
}
