package com.example.groupedtut.SQLite.transaction_historique;

/**
 * Created by Alexis on 10/12/2017.
 */

public class Transaction  {

    public static String SORTANTE = "sortante";
    public static String RENTRANTE = "rentrante";

    private long id;
    private double montant;
    private String date;
    private String beneficiaire;
    private String typeTransaction;

    private String commentaire;

    public Transaction(){}

    public Transaction(long id, double montant, String date, String beneficiaire, String typeTransaction) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.beneficiaire = beneficiaire;
        this.typeTransaction = typeTransaction;
        this.commentaire = "";
    }

    public Transaction(long id, double montant, String date, String beneficiaire, String typeTransaction, String commentaire) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.beneficiaire = beneficiaire;
        this.typeTransaction = typeTransaction;
        this.commentaire = commentaire;
    }

    public boolean aUnCommentaire(){
        return !this.commentaire.equals("");
    }

    public String getCommentaire(){
        return commentaire;
    }

    public void setCommentaire(String commentaire){
        this.commentaire = commentaire;
    }

    public long getId() {
        return id;
    }

    public Transaction setId(long id) {
        this.id = id;
        return this;
    }

    public double getMontant() {
        return montant;
    }

    public Transaction setMontant(double montant) {
        this.montant = montant;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Transaction setDate(String date) {
        this.date = date;
        return this;
    }

    public String getCompte() {
        return beneficiaire;
    }

    public Transaction setCompte(String beneficiaire) {
        this.beneficiaire = beneficiaire;
        return this;
    }

    public String getTypeTransaction() { return typeTransaction; }

    public Transaction setTypeTransaction(String typeTransaction){
        this.typeTransaction = typeTransaction;
        return this;
    }

    @Override
    public String toString(){
        return (this.beneficiaire + " " + Double.toString(this.montant) + " " + this.date + " " + this.typeTransaction);
    }
}
