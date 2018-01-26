package com.example.gael.reception_sms.traiteur;

import android.content.Context;

import com.example.gael.reception_sms.dechiffrage.MessageDechiffre;
import com.example.gael.reception_sms.dechiffrage.MessageDechiffreReceptionTransaction;
import com.example.gael.reception_sms.dechiffrage.MessageDechiffreSolde;
import com.example.gael.reception_sms.dechiffrage.MessageDechiffreTransactionEchouee;

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
        double treflesRecusValeur = messageDechiffreReceptionTransaction.getMontantRecu();
            this.ajouterSolde(context, treflesRecusValeur);
            this.ajouterNouvelleTransactionEntrante(context, treflesRecusValeur, messageDechiffreReceptionTransaction.getNumeroCompte(), messageDechiffreReceptionTransaction.getNomExpediteur());
        }

    @Override
    public boolean aLeBonMessage() {
        return this.messageDechiffre instanceof MessageDechiffreReceptionTransaction;
    }
}
