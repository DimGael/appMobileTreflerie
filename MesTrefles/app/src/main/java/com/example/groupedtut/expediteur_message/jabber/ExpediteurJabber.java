package com.example.groupedtut.expediteur_message.jabber;

import android.content.Context;

import com.example.groupedtut.expediteur_message.ExpediteurMessage;

import java.math.BigDecimal;

/**
 * Created by Gael on 25/02/2018.
 */

public class ExpediteurJabber implements ExpediteurMessage {
    private MyXMPP myXMPP;
    private static String DESTINATAIRE = "volet";

    public ExpediteurJabber(String userId, String pwd){
        this.myXMPP = new MyXMPP();
        this.myXMPP.init(userId, pwd);
        this.myXMPP.connectConnection();
    }

    @Override
    public void demandeSoldeActuel(Context context) {
        this.myXMPP.connectConnection();
        this.myXMPP.sendMsg(DESTINATAIRE, "S?");
        this.myXMPP.disconnectConnection();
    }

    @Override
    public void transaction(double montant, String destinataire, Context context) {
        this.myXMPP.connectConnection();
        this.myXMPP.sendMsg(DESTINATAIRE, creerMessage(montant, destinataire));
        this.myXMPP.disconnectConnection();
    }

    private String creerMessage(double montant, String numDestinataire){
        BigDecimal bd = new BigDecimal(montant);
        bd= bd.setScale(2,BigDecimal.ROUND_DOWN);
        double montantArrondi = bd.doubleValue();

        final int avantVirgule = (int) montantArrondi;

        int indexVirgule = new Double(montantArrondi).toString().indexOf(".");
        String apresVirgule = new Double(montantArrondi).toString().substring(indexVirgule + 1);

        if (apresVirgule.equals("0"))
            return avantVirgule+"/"+numDestinataire;
        else
            return avantVirgule + "," + apresVirgule + "/" + numDestinataire;
    }
}
