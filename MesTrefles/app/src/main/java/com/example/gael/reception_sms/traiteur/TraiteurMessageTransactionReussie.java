package com.example.gael.reception_sms.traiteur;

import android.content.Context;

import com.example.gael.TransactionHistorique.TransactionDataSource;
import com.example.gael.mestrefles.TransactionActivity;
import com.example.gael.reception_sms.dechiffrage.MessageDechiffre;
import com.example.gael.reception_sms.dechiffrage.MessageDechiffreSolde;
import com.example.gael.reception_sms.dechiffrage.MessageDechiffreTransactionReussie;
import com.example.gael.soldeactuel.SoldeDataSource;

import java.util.Date;

/**
 * Created by Alexandre et Gael on 26/01/2018.
 */

public class TraiteurMessageTransactionReussie extends TraiteurMessage {
    private MessageDechiffreTransactionReussie messageDechiffreTransactionReussie;
    public TraiteurMessageTransactionReussie(MessageDechiffre typeMessageServeur) {
        super(typeMessageServeur);
        if(aLeBonMessage()){
            this.messageDechiffreTransactionReussie = (MessageDechiffreTransactionReussie)this.messageDechiffre;
        }
    }

    @Override
    public void traiterMessage(Context context) {

        String numeroCompte = this.messageDechiffreTransactionReussie.getNumeroCompteBeneficiaire();

        String nomPersonne = this.messageDechiffreTransactionReussie.getNomBeneficiaire();

        double soldeEnvoye = this.messageDechiffreTransactionReussie.getMontantEnvoye();

        this.ajouterSolde(context, soldeEnvoye*(-1));

        this.ajouterNouvelleTransactionSortante(context, soldeEnvoye, numeroCompte, nomPersonne);

        if(TransactionActivity.instance != null) {
            ((TransactionActivity) TransactionActivity.instance).transactionReussie();
        }
    }

    @Override
    public boolean aLeBonMessage() {
        return this.messageDechiffre instanceof MessageDechiffreTransactionReussie;
    }

    private void ajouterSolde(Context context, double soldeAAjouter) {
        final SoldeDataSource soldeDataSource = new SoldeDataSource(context);
        soldeDataSource.open();
        soldeDataSource.majSolde(soldeDataSource.getSoldeActuel() + soldeAAjouter);
    }


    private void ajouterNouvelleTransaction(Context context, double soldeEnvoye, boolean estSortant, String numeroCompte, String nomPersonne) {
        final TransactionDataSource transactionDataSource = new TransactionDataSource(context);
        transactionDataSource.open();

        Date d=new Date();
        String month = Integer.toString(d.getMonth()+1);
        if(month.length() == 1){
            month="0"+month;
        }
        String date = d.getDate()+"/"+month +"/"+Integer.toString(d.getYear()).substring(1);

        String compte = numeroCompte + " : " + nomPersonne;
        transactionDataSource.ajouterNouvelleTransaction(soldeEnvoye, date, compte, estSortant);
    }

    private void ajouterNouvelleTransactionSortante(Context context, double soldeEnvoye, String numeroCompte, String nomPersonne){
        this.ajouterNouvelleTransaction(context, soldeEnvoye, true, numeroCompte, nomPersonne);
    }
}

