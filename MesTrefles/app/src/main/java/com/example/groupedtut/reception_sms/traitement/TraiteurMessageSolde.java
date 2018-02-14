package com.example.groupedtut.reception_sms.traitement;

import android.content.Context;
import android.widget.TextView;

import com.example.groupedtut.activities.R;
import com.example.groupedtut.activities.SoldeActivity;
import com.example.groupedtut.numerocompte.NumeroCompteDataSource;
import com.example.groupedtut.reception_sms.tri.MessageDechiffre;
import com.example.groupedtut.reception_sms.tri.MessageDechiffreSolde;
import com.example.groupedtut.soldeactuel.SoldeDataSource;

/**
 * Created by GaëlPortable on 26/01/2018.
 */

public class TraiteurMessageSolde extends TraiteurMessage {

    public TraiteurMessageSolde(MessageDechiffre typeMessageServeur) {
        super(typeMessageServeur);
    }

    @Override
    public void traiterMessage(Context context) {
        // Si null alors message inconnu
        if(aLeBonMessage()){
            final MessageDechiffreSolde messageDechiffreSolde = (MessageDechiffreSolde)this.messageDechiffre;
            modificationNumeroCompte(context, messageDechiffreSolde.getNumeroCompte());

            if(SoldeActivity.instance != null){
                ((SoldeActivity) SoldeActivity.instance).setNumeroCompte(messageDechiffreSolde.getNumeroCompte());
            }

            this.updateSoldeBdd(context, messageDechiffreSolde.getSolde());
            if (SoldeActivity.instance != null) {
                this.receptionSmsSoldeDansSoldeActivity(messageDechiffreSolde.getSolde());
            }
        }
    }

    @Override
    public boolean aLeBonMessage() {
        return this.messageDechiffre instanceof MessageDechiffreSolde;
    }

    private void receptionSmsSoldeDansSoldeActivity(double nouvSolde) {
        //Choses à faire si on est dans SoldeActivity
        SoldeActivity.instance.majSoldeAffichage(nouvSolde);
        ((TextView) SoldeActivity.instance.findViewById(R.id.texteReponseSolde)).setText("Solde Actualisé !");
        ((SoldeActivity) SoldeActivity.instance).changerEtatBouton();
    }

    private void updateSoldeBdd(Context context, double nouvSolde) {
        final SoldeDataSource soldeDataSource = new SoldeDataSource(context);
        soldeDataSource.open();
        soldeDataSource.majSolde(nouvSolde);
    }

    private void modificationNumeroCompte(Context context, String numeroCompte) {
        NumeroCompteDataSource numeroCompteDataSource = new NumeroCompteDataSource(context);
        numeroCompteDataSource.open();
        numeroCompteDataSource.setNumeroCompte(numeroCompte);
        numeroCompteDataSource.close();
    }
}
