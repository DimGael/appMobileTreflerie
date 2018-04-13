package com.example.groupedtut.SQLite.montantmax;

/**
 * Created by Gael on 29/11/2017.
 */

public class MontantMax  {

    private long id;
    private double montantMax;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMontantMax() {
        return montantMax;
    }

    public void setMontantMax(double montantMax) {
        this.montantMax = montantMax;
    }

    @Override
    public String toString(){
        return Double.toString(this.montantMax);
    }
}
