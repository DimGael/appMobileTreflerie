package com.example.groupedtut.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.groupedtut.transaction_historique.Transaction;
import com.example.groupedtut.transaction_historique.TransactionAdapter;
import com.example.groupedtut.transaction_historique.TransactionDataSource;

import java.util.ArrayList;

public class DepenseActivity extends BasicTrefleActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TransactionDataSource transactionDataSource;

    @Override
    public DrawerLayout getMainDrawerLayout() {
        return (DrawerLayout)findViewById(R.id.depense_drawer_layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_depenses);
        super.onCreate(savedInstanceState);

        transactionDataSource = new TransactionDataSource(this);
        transactionDataSource.open();


        ArrayList<Transaction> transactionsTests = new ArrayList<Transaction>();

        transactionsTests.add(new Transaction(0, 10.0, "24/01/18", "N°29 : Dim", Transaction.RENTRANTE));
        transactionsTests.add(new Transaction(1, 15.26, "15/01/18", "N°31 : Nadaud", Transaction.SORTANTE));
        transactionsTests.add(new Transaction(2, 150.26, "15/01/18", "N°12 : Rebierre", Transaction.SORTANTE));

        final ListView listView = (android.widget.ListView)this.findViewById(R.id.list_view_depenses);
        listView.setAdapter(new TransactionAdapter(this.getBaseContext(), this.transactionDataSource.getAllTransaction()));

        ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.header_historique, listView, false);
        listView.addHeaderView(viewGroup);
    }

    
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Si l'utilisateur clique sur dépense, ne fais rien
        if (id == R.id.mDepenses) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.depense_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        //Sinon la méthode est gérée par la superclass (BasicTrefleActivity)
        return super.onNavigationItemSelected(item);
    }

    @Override
    public void onPause(){
        this.transactionDataSource.close();
        super.onPause();
    }

    @Override
    public void onResume() {
        this.transactionDataSource.open();
        super.onResume();
    }
}
