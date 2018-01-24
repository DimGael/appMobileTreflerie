package com.ptut.treflerie.listviews;

/**
 * Created by GaëlPortable on 23/01/2018.
 */

public class Transaction {
    public String beneficiaire;
    public double montant;

    public Transaction(String beneficiaire, double montant) {
        this.beneficiaire = beneficiaire;
        this.montant = montant;
    }
}
