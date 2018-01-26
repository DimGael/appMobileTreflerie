package com.example.groupedtut.reception_sms.tri;

import com.example.groupedtut.transaction_historique.Transaction;

/**
 * Created by GaëlPortable on 26/01/2018.
 */

public class MessageDechiffreDerniereTransaction extends MessageDechiffre {

    private int anneeTransaction;
    private int moisTransaction;
    private int jourTransaction;

    private String typeTransaction;

    private double montant;

    private String nom;
    private String prenom;
    private String numeroCompte;

    public MessageDechiffreDerniereTransaction(String messageBrut) {
        super(messageBrut);
        if(messageRecuCorrespond()){
            final String[] tabMotSms = messageBrut.split(" ");

            //Récupération de la Date
            final String[] tabDate = tabMotSms[5].split(".");
            this.anneeTransaction = Integer.getInteger(tabDate[2]).intValue();
            this.moisTransaction = Integer.getInteger(tabDate[1]).intValue();
            this.jourTransaction = Integer.getInteger(tabDate[0]).intValue();

            //Récupération du type de Transaction
            if(tabMotSms[7].equals("recu"))
                this.typeTransaction = Transaction.RENTRANTE;
            else
                this.typeTransaction = Transaction.SORTANTE;

            //Récupération du montant
            this.montant = getDoubleSansVirgule(tabMotSms[12]);

            //Récupération des informations du compte associé à la transaction
            this.numeroCompte = tabMotSms[9];
            this.nom = tabMotSms[10];
            this.prenom = tabMotSms[11];
        }
    }

    @Override
    public boolean messageRecuCorrespond() {
        if (this.getCinqPremieresLettres().equals("Votre"))
            return true;

        return false;
    }

    public int getAnneeTransaction() {
        return anneeTransaction;
    }

    public int getMoisTransaction() {
        return moisTransaction;
    }

    public int getJourTransaction() {
        return jourTransaction;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public double getMontant() {
        return montant;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }
}
