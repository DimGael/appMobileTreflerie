package com.example.groupedtut.reception_sms;

import com.example.groupedtut.reception_sms.tri.MessageDechiffre;
import com.example.groupedtut.reception_sms.tri.MessageDechiffreDerniereTransaction;
import com.example.groupedtut.reception_sms.tri.MessageDechiffreReceptionTransaction;
import com.example.groupedtut.reception_sms.tri.MessageDechiffreSolde;
import com.example.groupedtut.reception_sms.tri.MessageDechiffreTransactionEchouee;
import com.example.groupedtut.reception_sms.tri.MessageDechiffreTransactionReussie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GaëlPortable on 25/01/2018.
 */

/**
 * Récupère les SMS et les insère dans des déchiffreur de message
 */
public class RecuperationSms {
    private  String messageBrut;
    private List<MessageDechiffre> listTypeSms;


    public RecuperationSms(String messageBrut) {
        this.messageBrut = messageBrut;
        this.listTypeSms = new ArrayList<MessageDechiffre>(
                Arrays.asList(
                        new MessageDechiffreSolde(messageBrut),
                        new MessageDechiffreTransactionEchouee(messageBrut),
                        new MessageDechiffreTransactionReussie(messageBrut),
                        new MessageDechiffreReceptionTransaction(messageBrut),
                        new MessageDechiffreDerniereTransaction(messageBrut)
                )
        );
    }

    public MessageDechiffre getMessageDechiffre(){

        for (MessageDechiffre messageDechiffre: this.listTypeSms) {
            if(messageDechiffre.messageRecuCorrespond()){
                return messageDechiffre;
            }
        }

        return null;
    }
}
