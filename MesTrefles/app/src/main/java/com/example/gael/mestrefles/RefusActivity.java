package com.example.gael.mestrefles;



import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

public class RefusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refus);
        TextView textView1 = (TextView) findViewById(R.id.messageRefus);
        TextView textView2 = (TextView) findViewById(R.id.messageRefus2);
        TextView textViewE1 = (TextView) findViewById(R.id.messageRefusExplication1);
        TextView textViewE2 = (TextView) findViewById(R.id.messageRefusExplication2);
        TextView textViewE3 = (TextView) findViewById(R.id.messageRefusExplication3);
        TextView textViewE4 = (TextView) findViewById(R.id.messageRefusExplication4);
        TextView textViewE5 = (TextView) findViewById(R.id.messageRefusExplication5);
        TextView textViewE6 = (TextView) findViewById(R.id.messageRefusExplication6);

        setFont(textView1,"QSregular.ttf");
        setFont(textView2,"QSregular.ttf");
        setFont(textViewE1,"QSregular.ttf");
        setFont(textViewE2,"QSregular.ttf");
        setFont(textViewE3,"QSregular.ttf");
        setFont(textViewE4,"QSregular.ttf");
        setFont(textViewE5,"QSregular.ttf");
        setFont(textViewE6,"QSregular.ttf");
    }
    public void setFont(TextView textView, String fontName) {
        if(fontName != null){
            try {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontName);
                textView.setTypeface(typeface);
            } catch (Exception e) {
                Log.e("FONT", fontName + " not found", e);
            }
        }
    }


}
