package com.example.gael.TransactionHistorique;

/**
 * Created by Alexis on 10/12/2017.
 */

public class Transaction  {

    private long id;
    private double montant;
    private String date;
    private String beneficiaire;
    private String typeTransaction;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBeneficiaire() {
        return beneficiaire;
    }

    public void setBeneficiaire(String beneficiaire) {
        this.beneficiaire = beneficiaire;
    }

    public String getTypeTransaction() { return typeTransaction; }

    public void setTypeTransaction(String type) {
        this.typeTransaction = type;
    }

    @Override
    public String toString(){
        return (this.beneficiaire + " " + Double.toString(this.montant) + " " + this.date + " " + this.typeTransaction);
    }
}
