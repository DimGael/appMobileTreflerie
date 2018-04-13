package com.example.groupedtut.SQLite.transaction_historique;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.groupedtut.SQLite.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class TransactionDataSource {

    public static final String TABLE_DEPENSES_HISTORIQUE = "historique";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMPTE = "beneficiaire";
    public static final String COLUMN_MONTANT = "montant";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TYPE = "type";

    public static final String TABLE_CREATE = "create table "+TABLE_DEPENSES_HISTORIQUE+" ("
            +COLUMN_ID+" integer primary key autoincrement,"
            + COLUMN_COMPTE +" text not null,"
            +COLUMN_MONTANT+" number not null,"
            +COLUMN_DATE+" text not null,"
            +COLUMN_TYPE+" text not null)";

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns;

    public TransactionDataSource(Context context){
        this.allColumns = new String[]{
                COLUMN_ID,
                COLUMN_COMPTE,
                COLUMN_MONTANT,
                COLUMN_DATE,
                COLUMN_TYPE
        };

        dbHelper = new MySQLiteHelper(context);
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


        values.put(COLUMN_COMPTE, compte);
        values.put(COLUMN_MONTANT, montant);
        values.put(COLUMN_DATE, date);

        if(estSortant)
            values.put(COLUMN_TYPE, Transaction.SORTANTE);
        else
            values.put(COLUMN_TYPE, Transaction.RENTRANTE);


        //insertId récupère l'id du nouvel element inséré dans la bdd
        long insertId = database.insert(TABLE_DEPENSES_HISTORIQUE, null, values);

        //On récupere le nouveau montantMax Récupéré :
        //Le curseur pointe maintenant sur le premier élément de la selection, soit le nouveau MontantMax
        Cursor cursor = database.query(TABLE_DEPENSES_HISTORIQUE, allColumns, COLUMN_ID+"="+insertId, null, null, null, null);

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
        Cursor cursor = database.query(TABLE_DEPENSES_HISTORIQUE, allColumns, null, null, null, null, COLUMN_ID + " DESC");

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

    public int supprimerTransaction(Transaction transaction){
        return this.supprimerTransaction(transaction.getId());
    }

    public int supprimerTransaction(long id) {
        return this.database.delete(TABLE_DEPENSES_HISTORIQUE, COLUMN_ID+" = "+id, null);
    }

}
