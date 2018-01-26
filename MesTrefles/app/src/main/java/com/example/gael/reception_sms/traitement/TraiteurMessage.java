package com.example.gael.reception_sms.traitement;

import android.content.Context;

import com.example.gael.TransactionHistorique.TransactionDataSource;
import com.example.gael.reception_sms.tri.MessageDechiffre;
import com.example.gael.soldeactuel.SoldeDataSource;

import java.util.Date;

/**
 * Created by GaëlPortable on 26/01/2018.
 */

/**
 * Traite les messages déchiffrés
 */
public abstract class TraiteurMessage {
    protected MessageDechiffre messageDechiffre;

    public TraiteurMessage(MessageDechiffre typeMessageServeur) {
        this.messageDechiffre = typeMessageServeur;
    }

    /**
     * Traite le message s'il est du bon type.
     * @param context
     */
    public abstract void traiterMessage(Context context);

    /**
     * @return vrai si le message dechiffre correspond au traiteur
     */
    public abstract boolean aLeBonMessage();

    protected void ajouterNouvelleTransaction(Context context, double soldeEnvoye, boolean estSortant, String numeroCompte, String nomPersonne) {
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

    protected void ajouterNouvelleTransactionSortante(Context context, double soldeEnvoye, String numeroCompte, String nomPersonne){
        this.ajouterNouvelleTransaction(context, soldeEnvoye, true, numeroCompte, nomPersonne);
    }

    protected void ajouterNouvelleTransactionEntrante(Context context, double soldeEnvoye, String numeroCompte, String nomPersonne){
        this.ajouterNouvelleTransaction(context, soldeEnvoye, false, numeroCompte, nomPersonne);
    }

    protected double getDoubleSansVirgule(String message) {
        if (message.contains(",")) {
            String[] messages = message.split(",");
            return Double.valueOf(messages[0] + "." + messages[1]).doubleValue();
        }
        return Double.valueOf(message).doubleValue();
    }

    protected void ajouterSolde(Context context, double soldeAAjouter) {
        final SoldeDataSource soldeDataSource = new SoldeDataSource(context);
        soldeDataSource.open();
        soldeDataSource.majSolde(soldeDataSource.getSoldeActuel() + soldeAAjouter);
    }
}
