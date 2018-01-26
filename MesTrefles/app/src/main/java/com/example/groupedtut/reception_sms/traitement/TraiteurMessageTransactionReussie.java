package com.example.groupedtut.reception_sms.traitement;

import android.content.Context;

import com.example.groupedtut.activities.TransactionActivity;
import com.example.groupedtut.reception_sms.tri.MessageDechiffre;
import com.example.groupedtut.reception_sms.tri.MessageDechiffreTransactionReussie;

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


}

