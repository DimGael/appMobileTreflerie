package fr.alexisnadaud.jabberpourtrefle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private MyXMPP myXMPP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.layout);

        Button boutonSolde = (Button)this.findViewById(R.id.Bouton);
        boutonSolde.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.Bouton){
            //Action à faire lors de l'utilisation du bouton Transaction
            final Intent intentTransac = new Intent(this, MainActivity.class);
            this.startActivity(intentTransac);
            this.myXMPP = new MyXMPP();
            this.myXMPP.init("nalexis", "soleil");
            this.myXMPP.connectConnection();
            this.myXMPP.login();
            this.myXMPP.sendMsg();

        }
    }

}
