package com.example.groupedtut.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.groupedtut.expediteur_message.jabber.MyXMPP;

/**
 * Created by Gael on 24/02/2018.
 */

public class ParametreJabberActivity extends BasicTrefleActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_param_jabber);
        super.onCreate(savedInstanceState);

        final Button boutonConnexion = (Button)findViewById(R.id.boutonConnexionJabber);
        boutonConnexion.setOnClickListener(this);
    }

    @Override
    public DrawerLayout getMainDrawerLayout() {
        return (DrawerLayout)findViewById(R.id.drawer_layout_param_jabber);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.boutonConnexionJabber){

            final EditText loginEdit = (EditText)findViewById(R.id.login_jabber);
            final EditText mdpEdit = (EditText)findViewById(R.id.mdp_jabber);

            MyXMPP.globalMyXmpp = new MyXMPP();
            MyXMPP.globalMyXmpp.init(loginEdit.getText().toString(), mdpEdit.getText().toString());
            MyXMPP.globalMyXmpp.connectConnection();

        }
    }
}
