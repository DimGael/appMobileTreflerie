package com.example.gael.reception_sms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GaëlPortable on 25/01/2018.
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
            if(typeMessage.estDeCeType()){
                return typeMessage;
            }
        }

        return null;
    }
}
