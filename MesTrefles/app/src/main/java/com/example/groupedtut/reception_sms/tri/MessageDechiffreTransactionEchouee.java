package com.example.groupedtut.reception_sms.tri;

/**
 * Created by GaëlPortable on 26/01/2018.
 */

public class MessageDechiffreTransactionEchouee extends MessageDechiffre {

    public MessageDechiffreTransactionEchouee(String messageBrut) {
        super(messageBrut);
    }

    @Override
    public boolean messageRecuCorrespond() {
        if (this.getCinqPremieresLettres().equals(" Tran"))
            return true;

        return false;
    }
}
