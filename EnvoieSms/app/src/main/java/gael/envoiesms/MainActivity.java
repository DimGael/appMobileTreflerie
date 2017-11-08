package gael.envoiesms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEnvoie = (Button)this.findViewById(R.id.envoyer);

        btnEnvoie.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final EditText numero = (EditText)this.findViewById(R.id.numero);
        final EditText contenuMsg = (EditText)this.findViewById(R.id.message);

        String num = numero.getText().toString();
        String message = contenuMsg.getText().toString();

        if(num.length() < 4 || message.length() <= 0){
            Toast.makeText(MainActivity.this, "Entrer le numero ou le message", Toast.LENGTH_SHORT).show();
        }
        else{
            SmsManager.getDefault().sendTextMessage(num,null,message,null,null);
            numero.setText("");
            contenuMsg.setText("");
        }
    }
}
