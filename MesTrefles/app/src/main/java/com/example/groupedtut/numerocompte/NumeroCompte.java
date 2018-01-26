package com.example.groupedtut.numerocompte;


public class NumeroCompte {

    private long id;
    private String numeroCompte;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    @Override
    public String toString(){
        return numeroCompte;
    }
}
