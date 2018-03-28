package com.example.groupedtut.transaction_historique;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupedtut.activities.BasicTrefleActivity;
import com.example.groupedtut.activities.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaëlPortable on 23/01/2018.
 */

public class TransactionAdapter extends BaseAdapter {

    private ListView listView;
    private Context context;
    private ArrayList<Transaction> transactions;
    private LayoutInflater layoutInflater;

    public TransactionAdapter(Context context, List<Transaction> transactions, ListView listView) {
        this.context = context;
        this.transactions = (ArrayList<Transaction>)transactions;
        this.listView = listView;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Transaction transaction = this.transactions.get(position);

        convertView = layoutInflater.inflate(R.layout.ligne_historique, null);

        final TextView beneficiaire = (TextView)convertView.findViewById(R.id.beneficiaire);
        beneficiaire.setText(this.transactions.get(position).getCompte());


        TextView montant = (TextView)convertView.findViewById(R.id.montant);
        if(transaction.getTypeTransaction().equals(Transaction.SORTANTE)){
            montant.setText("-"+new Double(transaction.getMontant()).toString());
            montant.setTextColor(convertView.getResources().getColor(R.color.transactionSortante));
        }
        else
            {
            montant.setText("+"+new Double(transaction.getMontant()).toString());
            montant.setTextColor(convertView.getResources().getColor(R.color.transactionRentrante));
        }

        TextView date = (TextView)convertView.findViewById(R.id.date);
        date.setText(transaction.getDate());

        final LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.ligne_historique);

        convertView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {

                linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.ligneChoisie));

                new AlertDialog.Builder(BasicTrefleActivity.instance)
                        .setCancelable(true)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                            }
                        })
                        .setTitle("Suprimmer")
                        .setMessage("Voulez-vous supprimer la transaction \"" + transaction.getCompte() + "\" ?")
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                            }
                        })
                        .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                transactions.remove(position);

                                TransactionDataSource transactionDataSource = new TransactionDataSource(context);
                                transactionDataSource.open();
                                transactionDataSource.supprimerTransaction(transaction);
                                transactionDataSource.close();

                                listView.setAdapter(new TransactionAdapter(context, transactions, listView));

                                Toast.makeText(context, "Transaction suprimmée", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create().show();
                return true;
            }
        });

        return convertView;
    }
}
