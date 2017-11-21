package com.example.gael.testchangerdact;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private LinearLayout layout;
    private Button monBouton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.layout = (LinearLayout)LinearLayout.inflate(this, R.layout.activity_main, null);

        this.monBouton = (Button)layout.findViewById(R.id.grosBouton);
        this.monBouton.setOnTouchListener(this);


        this.setContentView(layout);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Les actions s'effectuent lors du toucher cad jusqu'à qu'on enleve son doigt
        float coordonnee_x, coordonnee_y, largeur_du_bouton, hauteur_du_bouton;

        coordonnee_x = event.getX();
        coordonnee_y = event.getY();
        largeur_du_bouton = this.monBouton.getWidth();
        hauteur_du_bouton = this.monBouton.getHeight();

        this.monBouton.setTextSize(Math.abs(coordonnee_x - largeur_du_bouton / 2) + Math.abs(coordonnee_y - hauteur_du_bouton / 2));

        return true;
    }
}
