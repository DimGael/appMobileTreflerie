package com.example.gael.appsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MontantMaxDataSource montantMaxDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        montantMaxDataSource = new MontantMaxDataSource(this);
        montantMaxDataSource.open();

        this.majAffichageMontantMax();

        final Button boutonValide = (Button)this.findViewById(R.id.boutonValider);
        boutonValide.setOnClickListener(this);
    }

    private void majAffichageMontantMax() {
        final double montantMaxActuel = montantMaxDataSource.getMontantMax();
        if(montantMaxActuel != 0.0 && montantMaxActuel>0.0 && montantMaxActuel<250) {
            final TextView affMontantMax = (TextView) this.findViewById(R.id.affichageMontantMax);
            affMontantMax.setText("Montant maximum de vos Transactions : " + montantMaxActuel);
        }
    }

    @Override
    public void onClick(View view) {
        //Pas besoin de vérifier si c'est la méthode est appelée par le bouton car il n'y a que lui

        final EditText editMontantMax = (EditText) this.findViewById(R.id.montantMax);

        if(!editMontantMax.getText().toString().equals("")) {
            //Récupération du montant Max
            final double montantMax = Double.valueOf(editMontantMax.getText().toString());

            if (montantMax >= 250.0) {
               Toast.makeText(getApplicationContext(),"Le montant maximum d'une transaction est de 250", Toast.LENGTH_SHORT).show();
            } else if (montantMax > 0) {
                //Ajout du montant max dans la bdd
                montantMaxDataSource.majMontantMax(montantMax);
                this.majAffichageMontantMax();
            }
        }
    }

    @Override
    protected void onResume() {
        montantMaxDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        montantMaxDataSource.close();
        super.onPause();
    }
}
