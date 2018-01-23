package com.ptut.treflerie.listviews;

/**
 * Created by GaëlPortable on 23/01/2018.
 */

public class Transaction {
    private String beneficiaire;
    private long id;
    private double montant;

    public Transaction(long id, String beneficiaire, double montant) {
        this.beneficiaire = beneficiaire;
        this.id = id;
        this.montant = montant;
    }

    public String getBeneficiaire() {
        return beneficiaire;
    }

    public void setBeneficiaire(String beneficiaire) {
        this.beneficiaire = beneficiaire;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
