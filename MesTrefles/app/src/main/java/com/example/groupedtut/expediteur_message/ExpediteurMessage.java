package com.example.groupedtut.expediteur_message;


import android.content.Context;

/**
 * Interface qui va servir à envoyer des messages au serveur.
 */
public interface ExpediteurMessage {

    void demandeSoldeActuel(Context context);

    void transaction(double montant, String destinataire, Context context);

    void transactionCommentaire(double montant, String commentaire, String destinataire, Context context);
}
