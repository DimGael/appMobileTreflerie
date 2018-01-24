package com.ptut.treflerie.listviews;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by GaëlPortable on 23/01/2018.
 */

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.activity_main);

        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        transactions.add(new Transaction("N°29", 10.0));
        transactions.add(new Transaction("N°31", 128));
        transactions.add(new Transaction("N°128", 1.2));

        final ListView listView = (ListView)this.findViewById(R.id.listView);
        listView.setAdapter(new TransactionAdapter(this.getBaseContext(), transactions));

    }
}
