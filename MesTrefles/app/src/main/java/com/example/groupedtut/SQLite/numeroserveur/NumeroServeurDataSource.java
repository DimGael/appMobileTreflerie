package com.example.groupedtut.SQLite.numeroserveur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.groupedtut.SQLite.MySQLiteHelper;

public class NumeroServeurDataSource {

    public static final String TABLE_NUMERO_SERVEUR = "numero_serveur";
    public static final String COLUMN_NUMERO_SERVEUR = "numero";
    public static final String COLUMN_ID = "_id";

    public static final String TABLE_CREATE = "create table "+ TABLE_NUMERO_SERVEUR +" ("
            +COLUMN_ID+" integer primary key autoincrement,"
            + COLUMN_NUMERO_SERVEUR +" text not null)";

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns;

    public NumeroServeurDataSource(Context context){
        this.allColumns = new String[]{COLUMN_ID,
                COLUMN_NUMERO_SERVEUR};

        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        this.dbHelper.close();
    }

    public String getNumeroServeur(){
        Cursor cursor = database.query(TABLE_NUMERO_SERVEUR, allColumns, null, null, null, null, null);
        if(cursor.moveToFirst()){
            return cursor.getString(1);
        }else
            //Numéro du serveur de test
            //return "+33782572437";
            return "+33768658944";
    }

    public boolean numeroServeurExisteDansBdd(){
        return getNumeroServeur().equals(null);
    }

    public NumeroServeur setNumeroServeur(String numeroServeur) throws IllegalArgumentException{

        if(numeroServeur.length() != 12 && numeroServeur.length() != 10){
            throw new IllegalArgumentException("Le numéro n'est pas dans un bon format");
        }

        ContentValues values = new ContentValues();

        values.put(COLUMN_NUMERO_SERVEUR, numeroServeur);

        final long insertId = database.insert(TABLE_NUMERO_SERVEUR, null, values);

        Cursor cursor = database.query(TABLE_NUMERO_SERVEUR, allColumns, COLUMN_ID+"="+insertId, null, null, null, null);
        cursor.moveToFirst();

        NumeroServeur nouveauNumeroServeur = new NumeroServeur();
        nouveauNumeroServeur.setId(cursor.getLong(0));
        nouveauNumeroServeur.setNumeroServeur(cursor.getString(1));

        this.deleteAutresNumeroServeur(nouveauNumeroServeur);

        return nouveauNumeroServeur;
    }

    private void deleteAutresNumeroServeur(NumeroServeur nouveauNumeroServeur) {
        //Suppression de tous les autres montants sauf celui en paramètres
        database.delete(TABLE_NUMERO_SERVEUR, COLUMN_ID+"!="+nouveauNumeroServeur.getId(), null);
    }

}
