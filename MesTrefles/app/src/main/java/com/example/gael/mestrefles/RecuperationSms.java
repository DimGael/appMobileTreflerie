package com.example.gael.mestrefles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GaëlPortable on 25/01/2018.
 */

public class RecuperationSms {
    private  String messageBrut;
    private List<DechiffreurMessage> listTypeSms;


    public RecuperationSms(String messageBrut) {
        this.messageBrut = messageBrut;
        this.listTypeSms = new ArrayList<DechiffreurMessage>(
                Arrays.asList(new MessageDemandeSolde(messageBrut))
        );
    }

    public DechiffreurMessage determinerType(){
        for (DechiffreurMessage typeMessage: this.listTypeSms) {
            if(typeMessage.estDeCeType()){
                return typeMessage;
            }
        }

        return null;
    }
}
