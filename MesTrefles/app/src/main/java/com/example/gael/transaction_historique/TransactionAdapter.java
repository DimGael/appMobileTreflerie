package com.example.gael.transaction_historique;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gael.activities.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaëlPortable on 23/01/2018.
 */

public class TransactionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Transaction> transactions;
    private LayoutInflater layoutInflater;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = (ArrayList<Transaction>)transactions;
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
        beneficiaire.setText(this.transactions.get(position).getCompte());


        TextView montant = (TextView)convertView.findViewById(R.id.montant);
        if(this.transactions.get(position).getTypeTransaction().equals(Transaction.SORTANTE)){
            montant.setText("-"+new Double(this.transactions.get(position).getMontant()).toString());
            montant.setTextColor(convertView.getResources().getColor(R.color.transactionSortante));
        }
        else
            {
            montant.setText("+"+new Double(this.transactions.get(position).getMontant()).toString());
            montant.setTextColor(convertView.getResources().getColor(R.color.transactionRentrante));
        }

        TextView date = (TextView)convertView.findViewById(R.id.date);
        date.setText(this.transactions.get(position).getDate());

        return convertView;
    }
}
