package fr.alexisnadaud.jabberpourtrefle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.jivesoftware.smack.filter.PacketFilter;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private MyXMPP myXMPP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.layout);

        Button boutonConnexion = (Button)this.findViewById(R.id.BoutonConnexion);
        boutonConnexion.setOnClickListener(this);

        Button boutonMessage = (Button)this.findViewById(R.id.BoutonMessage);
        boutonMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.BoutonConnexion){
            this.myXMPP = new MyXMPP();
            this.myXMPP.init("nalexis", "soleil");
            this.myXMPP.connectConnection();
        }
        else if(view.getId() == R.id.BoutonMessage){
            this.myXMPP.sendMsg();
        }
    }

}
