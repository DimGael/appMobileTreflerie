package com.example.gael.appsqlite;

/**
 * Created by Gael on 29/11/2017.
 */

public class MontantMax  {

    private long id;
    private int montantMax;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMontantMax() {
        return montantMax;
    }

    public void setMontantMax(int montantMax) {
        this.montantMax = montantMax;
    }

    @Override
    public String toString(){
        return Double.toString(this.montantMax);
    }
}
