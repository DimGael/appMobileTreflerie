package com.example.gael.reception_sms.traiteur;

import android.content.Context;

import com.example.gael.mestrefles.TransactionActivity;
import com.example.gael.reception_sms.dechiffrage.MessageDechiffre;
import com.example.gael.reception_sms.dechiffrage.MessageDechiffreTransactionEchouee;
import com.example.gael.reception_sms.dechiffrage.MessageDechiffreTransactionReussie;

/**
 * Created by Alexandre on 26/01/2018.
 */

public class TraiteurMessageTransactionEchouee extends TraiteurMessage {

    private MessageDechiffreTransactionEchouee messageDechiffreTransactionEchouee;

    public TraiteurMessageTransactionEchouee(MessageDechiffre typeMessageServeur) {
        super(typeMessageServeur);
        if(aLeBonMessage()){
            this.messageDechiffreTransactionEchouee = (MessageDechiffreTransactionEchouee)this.messageDechiffre;
        }
    }

    @Override
    public void traiterMessage(Context context) {
        if (TransactionActivity.instance != null) {
            ((TransactionActivity) TransactionActivity.instance).transactionEchouee();
        }
    }

    @Override
    public boolean aLeBonMessage() {
        return this.messageDechiffre instanceof MessageDechiffreTransactionEchouee;
    }
}
