package com.example.gael.MesDepensesHistorique;

/**
 * Created by Alexis on 10/12/2017.
 */

public class MesDepensesHistorique  {

    private long id;
    private double montant;
    private String date;
    private String beneficiaire;

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


    @Override
    public String toString(){
        return (this.beneficiaire + " " + Double.toString(this.montant) + " " + this.date);
    }
}
