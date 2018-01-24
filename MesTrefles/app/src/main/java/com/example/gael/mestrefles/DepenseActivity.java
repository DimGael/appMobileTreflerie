package com.example.gael.mestrefles;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gael.TransactionHistorique.Transaction;

import java.util.ArrayList;

public class DepenseActivity extends BasicTrefleActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    public Toolbar getToolbar() {
        return (Toolbar)this.findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_depenses);
        super.onCreate(savedInstanceState);

        TextView textView1 = (TextView) findViewById(R.id.txtMsgDepenses);
        TextView textView2 = (TextView) findViewById(R.id.txtBeneficiaire);
        TextView textView3 = (TextView) findViewById(R.id.txtMontant);
        TextView textView4 = (TextView) findViewById(R.id.txtDate);


        setFont(textView1,"QSregular.ttf");
        setFont(textView2,"QSregular.ttf");
        setFont(textView3,"QSregular.ttf");
        setFont(textView4,"QSregular.ttf");


        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        final ListView listView = (android.widget.ListView)this.findViewById(R.id.list_view_depenses);
        listView.setAdapter(new TransactionAdapter(this.getBaseContext(), transactions));
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Si l'utilisateur clique sur dépense, ne fais rien
        if (id == R.id.mDepenses) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        //Sinon la méthode est gérée par la superclass (BasicTrefleActivity)
        return super.onNavigationItemSelected(item);
    }
}
