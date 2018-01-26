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


}

