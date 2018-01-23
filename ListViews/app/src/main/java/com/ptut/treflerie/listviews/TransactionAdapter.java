package com.ptut.treflerie.listviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by GaëlPortable on 23/01/2018.
 */

public class TransactionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Transaction> transactions;
    private LayoutInflater layoutInflater;

    public TransactionAdapter(Context context, ArrayList<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.ligne_historique, null);

        TextView beneficiaire = (TextView)convertView.findViewById(R.id.beneficiaire);
        beneficiaire.setText(this.transactions.get(position).beneficiaire);

        TextView montant = (TextView)convertView.findViewById(R.id.montant);
        montant.setText(new Double(transactions.get(position).montant).toString());

        return convertView;
    }
}
