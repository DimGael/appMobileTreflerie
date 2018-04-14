package com.example.groupedtut.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupedtut.expediteur_message.ExpediteurMessage;
import com.example.groupedtut.expediteur_message.SMS.ExpediteurSMS;
import com.example.groupedtut.SQLite.montantmax.MontantMaxDataSource;


public class TransactionActivity extends BasicTrefleActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //Classe qui va nous servir à manipuler la table MontantMax
    private MontantMaxDataSource montantMaxDataSource;

    private boolean transactionEnCours = false;

    public static final String INTENT_VALID_TRANSAC = "INTENT_VALID_TRANSAC";

    private ExpediteurMessage expediteurMessage;

    @Override
    public DrawerLayout getMainDrawerLayout() {
        return (DrawerLayout)findViewById(R.id.transaction_drawer_layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_transaction);
        super.onCreate(savedInstanceState);

        this.expediteurMessage = new ExpediteurSMS();

        this.transactionEnCours = false;

        montantMaxDataSource = new MontantMaxDataSource(this);
        montantMaxDataSource.open();

        Button boutonValider = (Button)this.findViewById(R.id.boutonValider);
        boutonValider.setOnClickListener(this);

        ((Button)this.findViewById(R.id.boutonValider)).setEnabled(true);

        //Mise en place d'un hint pour indiquer quelle valeur maximale l'utilisateur peut saisir
        ((TextView)this.findViewById(R.id.editTextMontant)).setHint("Montant Max. : "+this.montantMaxDataSource.getMontantMax());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Si l'utilisateur clique sur dépense, ne fais rien
        if (id == R.id.mTransaction) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.transaction_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        //Sinon la méthode est gérée par la superclass (BasicTrefleActivity)
        return super.onNavigationItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        final EditText editTextMontant = (EditText)this.findViewById(R.id.editTextMontant);
        final EditText editTextNumDestinataire = (EditText)this.findViewById(R.id.editTextNumeroDest);

        if (editTextNumDestinataire.getText().toString().equals("") || editTextMontant.getText().toString().equals("")) {
            Toast.makeText(TransactionActivity.this, "Vous n'avez pas rempli les champs", Toast.LENGTH_SHORT).show();
        }
        else {
            final double montant;
            int numDestinataire = 0;
            montant = Double.valueOf(editTextMontant.getText().toString());

            try {
                numDestinataire = Integer.valueOf(editTextNumDestinataire.getText().toString());
            }
            catch (NumberFormatException e){
                numDestinataire = 10001;
            }

            final double montantMax = this.montantMaxDataSource.getMontantMax();
            final TextView editTextErrMontant = (TextView) this.findViewById(R.id.erreurMontantTransaction);
            final TextView editTextErrNumDestinaire = (TextView) this.findViewById(R.id.erreurNumeroCompte);

            if (numDestinataire > 10000) {
                editTextErrNumDestinaire.setText("Erreur, la saisie du numéro du destinataire est invalide");
                editTextErrMontant.setText("");
            } else if (montant > montantMax) {
                editTextErrMontant.setText("Montant trop élevé \n (Vous pouvez paramètrer le montant maximum dans les paramètres)");
                editTextErrNumDestinaire.setText("");
            } else if(this.soldeDataSource.getSoldeActuel() == 0.0) {
                editTextErrMontant.setText("Votre solde est vide \n (Veuillez recharger votre solde ou l'actualiser si cela est déjà effectué)");
                editTextErrNumDestinaire.setText("");
            } else if(this.soldeDataSource.getSoldeActuel() < montant){
                editTextErrMontant.setText("Votre solde est insuffisant pour cette transaction \n (Veuillez recharger votre solde ou l'actualiser si cela est déjà effectué)");
                editTextErrNumDestinaire.setText("");
            }
            else {
                ((Button)this.findViewById(R.id.boutonValider)).setEnabled(false);
                ((TextView)this.findViewById(R.id.texteReponseSolde)).setText("Transaction en cours, veuillez patienter !");

                this.expediteurMessage.transaction(montant, Integer.toString(numDestinataire), this);

                this.transactionEnCours = true;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        afficherMessageSiTransactionEnCours();
                    }
                }, 40000);
            }
        }
    }

    private void afficherMessageSiTransactionEnCours() {
        if(instance.getClass() == TransactionActivity.class && transactionEnCours) {
            new AlertDialog.Builder(instance)
                    .setTitle(R.string.titreErreurServeur)
                    .setMessage(R.string.messageErreurServeur)
                    .setPositiveButton(R.string.boutonErreurServeur, null)
                    .create().show();

            final Button boutonValider = (Button) instance.findViewById(R.id.boutonValider);
            boutonValider.setEnabled(true);

            final TextView texteRep = (TextView) instance.findViewById(R.id.texteReponseSolde);
            texteRep.setText("");
            
            //Efface le contenu des EditText
            resetChampTexte();
        }
    }

    private void resetChampTexte() {
        final EditText editTextMontant = (EditText)this.findViewById(R.id.editTextMontant);
        final EditText editTextNumDestinataire = (EditText)this.findViewById(R.id.editTextNumeroDest);
        final TextView editTextErrMontant = (TextView) this.findViewById(R.id.erreurMontantTransaction);
        final TextView editTextErrNumDestinaire = (TextView) this.findViewById(R.id.erreurNumeroCompte);

        editTextMontant.setText("");
        editTextNumDestinataire.setText("");
        editTextErrNumDestinaire.setText("");
        editTextErrMontant.setText("");
    }

    @Override
    public void onPause(){
        this.montantMaxDataSource.close();
        super.onPause();
    }

    @Override
    public void onResume(){
        this.montantMaxDataSource.open();
        super.onResume();
    }

    /**
     * Méthode à éxécuter lorsqu'on reçoit la confirmation de la réussite d'une transaction.
     */
    public void transactionReussie(){
        final TextView texteRep = (TextView)this.findViewById(R.id.texteReponseSolde);
        texteRep.setText(R.string.msg_transaction_sucess);

        this.resetChampTexte();

        final Button boutonValider = (Button)this.findViewById(R.id.boutonValider);
        boutonValider.setEnabled(true);

        this.transactionEnCours = false;
    }

    /**
     * Méthode à éxécuter lorsqu'on reçoit un message signalant l'échec de la transaction.
     */
    public void transactionEchouee(){
        final TextView texteRep = (TextView)this.findViewById(R.id.texteReponseSolde);
        texteRep.setText(R.string.msg_transaction_fail);

        this.resetChampTexte();

        final Button boutonValider = (Button)this.findViewById(R.id.boutonValider);
        boutonValider.setEnabled(true);

        this.transactionEnCours = false;
    }
}
