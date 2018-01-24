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

    public Transaction(){}

    public Transaction(long id, double montant, String date, String beneficiaire, String typeTransaction) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.beneficiaire = beneficiaire;
        this.typeTransaction = typeTransaction;
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

    public void setTypeTransaction(String typeTransaction){
        this.typeTransaction = typeTransaction;
    }

    public void setTypeTransactionRentrant() {
        this.typeTransaction = "rentrant";
    }

    public void setTypeTransactionSortant(){
        this.typeTransaction = "sortant";
    }

    public boolean estSortant(){
        return this.typeTransaction == "sortant";
    }

    @Override
    public String toString(){
        return (this.beneficiaire + " " + Double.toString(this.montant) + " " + this.date + " " + this.typeTransaction);
    }
}
