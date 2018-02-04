package com.example.groupedtut.reception_sms.traitement;

import android.content.Context;

import com.example.groupedtut.activities.BasicTrefleActivity;
import com.example.groupedtut.reception_sms.tri.MessageDechiffre;
import com.example.groupedtut.reception_sms.tri.MessageDechiffreReceptionTransaction;

/**
 * Created by Barroblade on 26/01/2018.
 */


public class TraiteurMessageReceptionTransaction extends TraiteurMessage {

    private MessageDechiffreReceptionTransaction messageDechiffreReceptionTransaction;

    public TraiteurMessageReceptionTransaction(MessageDechiffre typeMessageServeur) {
        super(typeMessageServeur);
        if(aLeBonMessage()){
            this.messageDechiffreReceptionTransaction = (MessageDechiffreReceptionTransaction)this.messageDechiffre;
        }
    }

    @Override
    public void traiterMessage(Context context) {
        double montantRecu = messageDechiffreReceptionTransaction.getMontantRecu();
            this.ajouterSolde(context, montantRecu);
            this.ajouterNouvelleTransactionEntrante(context, montantRecu, messageDechiffreReceptionTransaction.getNumeroCompte(), messageDechiffreReceptionTransaction.getNomExpediteur());

            if(BasicTrefleActivity.instance != null){
                BasicTrefleActivity.instance.majSoldeAffichage(montantRecu);
            }
        }

    @Override
    public boolean aLeBonMessage() {
        return this.messageDechiffre instanceof MessageDechiffreReceptionTransaction;
    }
}