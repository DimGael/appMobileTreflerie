package com.example.groupedtut.SQLite.soldeactuel;

/**
 * Created by Gael on 06/12/2017.
 */

public class Solde {

    private long id;
    private double soldeActuel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSoldeActuel() {
        return soldeActuel;
    }

    public void setSoldeActuel(double soldeActuel) {
        this.soldeActuel = soldeActuel;
    }

    @Override
    public String toString(){
        return Double.toString(this.soldeActuel);
    }
}
