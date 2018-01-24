package com.example.gael.TransactionHistorique;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexis on 10/12/2017.
 */

public class TransactionDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelperTransaction dbHelper;
    private String[] allColumns;

    public TransactionDataSource(Context context){
        this.allColumns = new String[]{MySQLiteHelperTransaction.COLUMN_ID,
                MySQLiteHelperTransaction.COLUMN_MONTANT};

        dbHelper = new MySQLiteHelperTransaction(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        this.dbHelper.close();
    }

    public Transaction majTransaction(int montant, String date, String beneficiaire, boolean estRentrant){
        //Création d'un "ContentValues" qui est un objet contenant des clés associés à des valeurs
        ContentValues values = new ContentValues();

        //Rajout du MontantMax dans l'objet ContentValues
        values.put(MySQLiteHelperTransaction.COLUMN_MONTANT, montant);
        values.put(MySQLiteHelperTransaction.COLUMN_BENEFICIAIRE, beneficiaire);
        values.put(MySQLiteHelperTransaction.COLUMN_DATE, date);
        if(estRentrant)
            values.put(MySQLiteHelperTransaction.COLUMN_TYPE, "rentrant");
        else
            values.put(MySQLiteHelperTransaction.COLUMN_TYPE, "sortant");


        //insertId récupère l'id du nouvel element inséré dans la bdd
        long insertId = database.insert(MySQLiteHelperTransaction.TABLE_DEPENSES_HISTORIQUE, null, values);

        //On récupere le nouveau montantMax Récupéré :
        //Le curseur pointe maintenant sur le premier élément de la selection, soit le nouveau MontantMax
        Cursor cursor = database.query(MySQLiteHelperTransaction.TABLE_DEPENSES_HISTORIQUE, allColumns, MySQLiteHelperTransaction.COLUMN_ID+"="+insertId, null, null, null, null);
        cursor.moveToFirst();

        //On récupère les données dans un nouvel objet MontantMax
        Transaction transaction = new Transaction();
        transaction.setId(cursor.getLong(0));
        transaction.setBeneficiaire(cursor.getString(1));
        transaction.setMontant(cursor.getDouble(2));
        transaction.setDate(cursor.getString(3));
        transaction.setTypeTransaction(cursor.getString(4));

        return transaction;
    }

    public List<Transaction> getListeTransaction(){
        Cursor cursor = database.query(MySQLiteHelperTransaction.TABLE_DEPENSES_HISTORIQUE, allColumns, null, null, null, null, MySQLiteHelperTransaction.COLUMN_DATE);
        cursor.moveToFirst();

        Transaction transaction = new Transaction();

        transaction.setId(cursor.getLong(0));
        transaction.setBeneficiaire(cursor.getString(1));
        transaction.setMontant(cursor.getDouble(2));
        transaction.setDate(cursor.getString(3));
        transaction.setTypeTransaction(cursor.getString(4));

        //CA A L'AIR BIZARRE
        List<Transaction> transactions = new ArrayList<Transaction>();

        transactions.add(transaction);
        while (cursor.moveToNext()){

            transaction.setId(cursor.getLong(0));
            transaction.setBeneficiaire(cursor.getString(1));
            transaction.setMontant(cursor.getDouble(2));
            transaction.setDate(cursor.getString(3));
            transaction.setTypeTransaction(cursor.getString(4));

            transactions.add(transaction);
        }

        return transactions;
    }

}
