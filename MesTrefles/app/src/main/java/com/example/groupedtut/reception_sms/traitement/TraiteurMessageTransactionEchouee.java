package com.example.groupedtut.reception_sms.traitement;

import android.content.Context;

import com.example.groupedtut.activities.TransactionActivity;
import com.example.groupedtut.reception_sms.tri.MessageDechiffre;
import com.example.groupedtut.reception_sms.tri.MessageDechiffreTransactionEchouee;

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
