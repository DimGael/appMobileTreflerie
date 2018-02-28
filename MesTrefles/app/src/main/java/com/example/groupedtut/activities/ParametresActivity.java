package com.example.groupedtut.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.groupedtut.expediteur_message.jabber.MyXMPP;
import com.example.groupedtut.montantmax.MontantMaxDataSource;


public class ParametresActivity extends BasicTrefleActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    
    private String numServeur = "+33782572437";

    @Override
    public DrawerLayout getMainDrawerLayout() {
        return (DrawerLayout)findViewById(R.id.parametre_drawer_layout);
    }

    //Classe qui va nous servir à manipuler la table MontantMax
    private MontantMaxDataSource montantMaxDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_parametres);
        super.onCreate(savedInstanceState);

        this.montantMaxDataSource = new MontantMaxDataSource(this);
        this.montantMaxDataSource.open();

        //Ajout de l'écouteur sur les boutons
        ((Button)this.findViewById(R.id.boutonValiderParametres)).setOnClickListener(this);
        ((Button)this.findViewById(R.id.boutonDefaultServeur)).setOnClickListener(this);
        //Ajout du numéro du serveur de base sur l'edit Text
        ((EditText)this.findViewById(R.id.editTextNumServeur)).setText(this.numeroServeurDataSource.getNumeroServeur());

        //Ajouter le montant actuel du montant max de transaction
        ((EditText)this.findViewById(R.id.editTextMontantMax)).setText(Double.toString(this.montantMaxDataSource.getMontantMax()));
        
        final Switch switchPro = (Switch)this.findViewById(R.id.switchModePro);
        switchPro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switchPro.isChecked()){
                    final Intent intentParametresJabber = new Intent(getApplicationContext(), ParametreJabberActivity.class);
                    startActivity(intentParametresJabber);
                }
                else{
                    MyXMPP.globalMyXmpp.disconnectConnection();
                    MyXMPP.globalMyXmpp = null;
                }
            }
        });

        if(MyXMPP.globalMyXmpp != null) {
            switchPro.setChecked(true);
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mParametres) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.parametre_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onNavigationItemSelected(item);
    }
    //A CHANGER

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.boutonValiderParametres){
            this.modificationMontantMax();

            this.modificationNumeroServeur();

            Toast.makeText(this,"Modifications sauvegardées", Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.boutonDefaultServeur){
            Log.d("verif", "rentre dedans");
            this.numeroServeurDataSource.setNumeroServeur(this.numServeur);

            final EditText editTextNum = (EditText)this.findViewById(R.id.editTextNumServeur);
            editTextNum.setText(this.numServeur);
            Log.d("verif", "fais tout ce qu'il faut");
        }

    }

    private void modificationNumeroServeur() {
        //Modification du numéro de serveur
        final EditText editTextNum = (EditText)this.findViewById(R.id.editTextNumServeur);

        if(editTextNum.getText().toString().equals("")){
            //Si l'utilisateur n'a rien mis :
            Toast.makeText(this,"Vous n'avez rien saisi", Toast.LENGTH_SHORT).show();
        }
        else if(editTextNum.getText().toString().length() != 12 && editTextNum.getText().toString().length() != 10){
            Toast.makeText(this,"Vous avez mal écris le numéro", Toast.LENGTH_SHORT).show();
        }
        else
        {
            final String nouvNumeroServeur = editTextNum.getText().toString();

            this.numeroServeurDataSource.setNumeroServeur(nouvNumeroServeur);


        }
    }

    private void modificationMontantMax() {
        //Action lorsque l'utilisateur appuie sur le bouton valider en dessous de montant Max
        final EditText editTextMontantMax  = (EditText)this.findViewById(R.id.editTextMontantMax);

        if(editTextMontantMax.getText().toString().equals("")){
            //Si l'utilisateur n'a rien mis :
            Toast.makeText(this,"Vous n'avez rien saisi", Toast.LENGTH_SHORT).show();
        }
        else if(Double.valueOf(editTextMontantMax.getText().toString()).doubleValue() > 250.0){
            //Si l'utilisateur a mis une valeur supérieure à 250
            Toast.makeText(this,"Vous ne pouvez pas excéder 250 trèfles", Toast.LENGTH_SHORT).show();
        }
        else if(Double.valueOf(editTextMontantMax.getText().toString()).doubleValue() <= 0.0){
            //Si l'utilisateur a mis inférieure ou égale à 0
            Toast.makeText(this,"Vous ne pouvez pas définir un motant nul", Toast.LENGTH_SHORT).show();
        }
        else {
            //Si l'utilisateur a tout rempli correctement, mise à jour du montant maximal puis affichage du montant maximal
            final double nouvMontantMax = Double.valueOf(editTextMontantMax.getText().toString()).doubleValue();

            //Mise à jour du montant max
            this.montantMaxDataSource.majMontantMax(nouvMontantMax);
        }
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
}
