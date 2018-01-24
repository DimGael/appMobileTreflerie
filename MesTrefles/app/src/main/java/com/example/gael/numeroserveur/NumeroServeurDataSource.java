package com.example.gael.numeroserveur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Gael on 29/11/2017.
 */
public class NumeroServeurDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelperNumeroServeur dbHelper;
    private String[] allColumns;

    public NumeroServeurDataSource(Context context){
        this.allColumns = new String[]{MySQLiteHelperNumeroServeur.COLUMN_ID,
                MySQLiteHelperNumeroServeur.COLUMN_NUMERO_SERVEUR};

        dbHelper = new MySQLiteHelperNumeroServeur(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        this.dbHelper.close();
    }

    public String getNumeroServeur(){
        Cursor cursor = database.query(MySQLiteHelperNumeroServeur.TABLE_NUMERO_SERVEUR, allColumns, null, null, null, null, null);
        if(cursor.moveToFirst()){
            return cursor.getString(1);
        }else
            //Le numéro de base du serveur
            return "+33782572437";
    }

    public NumeroServeur setNumeroServeur(String numeroServeur) throws IllegalArgumentException{

        if(numeroServeur.length() != 12 && numeroServeur.length() != 10){
            throw new IllegalArgumentException("Le numéro n'est pas dans un bon format");
        }

        ContentValues values = new ContentValues();

        values.put(MySQLiteHelperNumeroServeur.COLUMN_NUMERO_SERVEUR, numeroServeur);

        final long insertId = database.insert(MySQLiteHelperNumeroServeur.TABLE_NUMERO_SERVEUR, null, values);

        Cursor cursor = database.query(MySQLiteHelperNumeroServeur.TABLE_NUMERO_SERVEUR, allColumns, MySQLiteHelperNumeroServeur.COLUMN_ID+"="+insertId, null, null, null, null);
        cursor.moveToFirst();

        NumeroServeur nouveauNumeroServeur = new NumeroServeur();
        nouveauNumeroServeur.setId(cursor.getLong(0));
        nouveauNumeroServeur.setNumeroServeur(cursor.getString(1));

        this.deleteAutresNumeroServeur(nouveauNumeroServeur);

        return nouveauNumeroServeur;
    }

    private void deleteAutresNumeroServeur(NumeroServeur nouveauNumeroServeur) {
        //Suppression de tous les autres montants sauf celui en paramètres
        database.delete(MySQLiteHelperNumeroServeur.TABLE_NUMERO_SERVEUR, MySQLiteHelperNumeroServeur.COLUMN_ID+"!="+nouveauNumeroServeur.getId(), null);
    }

}
