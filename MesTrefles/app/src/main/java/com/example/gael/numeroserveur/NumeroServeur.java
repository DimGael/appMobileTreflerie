package com.example.gael.numeroserveur;

/**
 * Created by Gael on 29/11/2017.
 */

public class NumeroServeur  {

    private long id;
    private String numeroServeur;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumeroServeur() {
        return numeroServeur;
    }

    public void setNumeroServeur(String numeroServeur) {
        this.numeroServeur = numeroServeur;
    }

    @Override
    public String toString(){
        return numeroServeur;
    }
}
