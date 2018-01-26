package com.example.gael.reception_sms;

import com.example.gael.reception_sms.dechiffrage.MessageDechiffre;
import com.example.gael.reception_sms.dechiffrage.MessageDechiffreSolde;

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
                        new MessageDechiffreSolde(messageBrut)
                )
        );
    }

    public MessageDechiffre recupererMessageDechiffre(){

        for (MessageDechiffre typeMessage: this.listTypeSms) {
            if(typeMessage.messageRecuCorrespond()){
                return typeMessage;
            }
        }

        return null;
    }
}
