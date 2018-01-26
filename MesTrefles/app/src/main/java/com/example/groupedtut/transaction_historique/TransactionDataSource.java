package com.example.groupedtut.transaction_historique;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TransactionDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelperTransaction dbHelper;
    private String[] allColumns;

    public TransactionDataSource(Context context){
        this.allColumns = new String[]{
                MySQLiteHelperTransaction.COLUMN_ID,
                MySQLiteHelperTransaction.COLUMN_COMPTE,
                MySQLiteHelperTransaction.COLUMN_MONTANT,
                MySQLiteHelperTransaction.COLUMN_DATE,
                MySQLiteHelperTransaction.COLUMN_TYPE
        };

        dbHelper = new MySQLiteHelperTransaction(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        this.dbHelper.close();
    }

    public Transaction ajouterNouvelleTransaction(Transaction transaction){
        return this.ajouterNouvelleTransaction(transaction.getMontant(), transaction.getDate(), transaction.getCompte(), transaction.getTypeTransaction() == Transaction.SORTANTE);
    }

    public Transaction ajouterNouvelleTransaction(double montant, String date, String compte, boolean estSortant){
        //Création d'un "ContentValues" qui est un objet contenant des clés associés à des valeurs
        ContentValues values = new ContentValues();


        values.put(MySQLiteHelperTransaction.COLUMN_COMPTE, compte);
        values.put(MySQLiteHelperTransaction.COLUMN_MONTANT, montant);
        values.put(MySQLiteHelperTransaction.COLUMN_DATE, date);

        if(estSortant)
            values.put(MySQLiteHelperTransaction.COLUMN_TYPE, Transaction.SORTANTE);
        else
            values.put(MySQLiteHelperTransaction.COLUMN_TYPE, Transaction.RENTRANTE);


        //insertId récupère l'id du nouvel element inséré dans la bdd
        long insertId = database.insert(MySQLiteHelperTransaction.TABLE_DEPENSES_HISTORIQUE, null, values);

        //On récupere le nouveau montantMax Récupéré :
        //Le curseur pointe maintenant sur le premier élément de la selection, soit le nouveau MontantMax
        Cursor cursor = database.query(MySQLiteHelperTransaction.TABLE_DEPENSES_HISTORIQUE, allColumns, MySQLiteHelperTransaction.COLUMN_ID+"="+insertId, null, null, null, null);

        Transaction transaction = new Transaction();
        if(cursor.moveToFirst()) {
            //On récupère les données dans un nouvel objet MontantMax
            transaction = new Transaction()
                    .setId(
                            insertId)
                    .setCompte(
                            cursor.getString(1))
                    .setMontant(
                            cursor.getDouble(2))
                    .setDate(
                            cursor.getString(3))
                    .setTypeTransaction(
                            cursor.getString(4));
        }
        return transaction;
    }

    public List<Transaction> getAllTransaction(){
        Cursor cursor = database.query(MySQLiteHelperTransaction.TABLE_DEPENSES_HISTORIQUE, allColumns, null, null, null, null, MySQLiteHelperTransaction.COLUMN_DATE);

        List<Transaction> transactions = new ArrayList<Transaction>();
        Transaction transaction = new Transaction();

        if(cursor.moveToFirst()) {
            transaction = new Transaction()
                    .setId(
                            cursor.getLong(0))
                    .setCompte(
                            cursor.getString(1))
                    .setMontant(
                            cursor.getDouble(2))
                    .setDate(
                            cursor.getString(3))
                    .setTypeTransaction(
                            cursor.getString(4));

            transactions.add(transaction);

            while (cursor.moveToNext()) {
                transactions.add(new Transaction().setId(
                            cursor.getLong(0))
                        .setCompte(
                                cursor.getString(1))
                        .setMontant(
                                cursor.getDouble(2))
                        .setDate(
                                cursor.getString(3))
                        .setTypeTransaction(
                                cursor.getString(4)));
            }
        }
            return transactions;
    }

}
