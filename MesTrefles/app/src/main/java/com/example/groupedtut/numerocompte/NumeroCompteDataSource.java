package com.example.groupedtut.numerocompte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class NumeroCompteDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelperNumeroCompte dbHelper;
    private String[] allColumns;

    public NumeroCompteDataSource(Context context){
        this.allColumns = new String[]{MySQLiteHelperNumeroCompte.COLUMN_ID,
                MySQLiteHelperNumeroCompte.COLUMN_NUMERO_COMPTE};

        dbHelper = new MySQLiteHelperNumeroCompte(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        this.dbHelper.close();
    }

    public String getNumeroCompte(){
        Cursor cursor = database.query(MySQLiteHelperNumeroCompte.TABLE_NUMERO_COMPTE, allColumns, null, null, null, null, null);
        if(cursor.moveToFirst()){
            return cursor.getString(1);
        }else
            return "?";
    }

    public NumeroCompte setNumeroCompte(String numeroCompte) throws IllegalArgumentException{

        if(numeroCompte.length()>5){
            throw new IllegalArgumentException("Le numéro de compte n'a pas le bon format");
        }

        ContentValues values = new ContentValues();

        values.put(MySQLiteHelperNumeroCompte.COLUMN_NUMERO_COMPTE, numeroCompte);

        final long insertId = database.insert(MySQLiteHelperNumeroCompte.TABLE_NUMERO_COMPTE, null, values);

        Cursor cursor = database.query(MySQLiteHelperNumeroCompte.TABLE_NUMERO_COMPTE, allColumns, MySQLiteHelperNumeroCompte.COLUMN_ID+"="+insertId, null, null, null, null);
        cursor.moveToFirst();

        NumeroCompte nouveauNumeroCompte = new NumeroCompte();
        nouveauNumeroCompte.setId(cursor.getLong(0));
        nouveauNumeroCompte.setNumeroCompte(cursor.getString(1));

        this.deleteAutresNumeroCompte(nouveauNumeroCompte);

        return nouveauNumeroCompte;
    }

    private void deleteAutresNumeroCompte(NumeroCompte nouveauNumeroCompte) {
        //Suppression de tous les autres montants sauf celui en paramètres
        database.delete(MySQLiteHelperNumeroCompte.TABLE_NUMERO_COMPTE, MySQLiteHelperNumeroCompte.COLUMN_ID+"!="+ nouveauNumeroCompte.getId(), null);
    }

}
