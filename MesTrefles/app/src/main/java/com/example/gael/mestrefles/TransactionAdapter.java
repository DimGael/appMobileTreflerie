package com.example.gael.mestrefles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gael.TransactionHistorique.Transaction;

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
        final Transaction transactionPosition = this.transactions.get(position);

        TextView beneficiaire = (TextView)convertView.findViewById(R.id.beneficiaire);
        beneficiaire.setText(transactionPosition.getBeneficiaire());


        TextView montant = (TextView)convertView.findViewById(R.id.montant);
        if(transactionPosition.estSortant()){
            montant.setText("-"+new Double(transactionPosition.getMontant()).toString());
            montant.setTextColor(convertView.getResources().getColor(R.color.transactionSortante));
        }else{
            montant.setText("+"+new Double(transactionPosition.getMontant()).toString());
            montant.setTextColor(convertView.getResources().getColor(R.color.transactionRentrante));
        }

        TextView date = (TextView)convertView.findViewById(R.id.date);
        date.setText(transactionPosition.getDate());

        return convertView;
    }
}
