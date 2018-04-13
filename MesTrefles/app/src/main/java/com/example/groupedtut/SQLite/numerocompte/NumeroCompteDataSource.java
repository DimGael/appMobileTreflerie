package com.example.groupedtut.SQLite.numerocompte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.groupedtut.SQLite.MySQLiteHelper;


public class NumeroCompteDataSource {

    public static final String TABLE_NUMERO_COMPTE = "numero_compte";
    public static final String COLUMN_NUMERO_COMPTE = "numero";
    public static final String COLUMN_ID = "_id";

    public static final String TABLE_CREATE = "create table "+ TABLE_NUMERO_COMPTE +" ("
            +COLUMN_ID+" integer primary key autoincrement,"
            + COLUMN_NUMERO_COMPTE +" text not null)";

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns;

    public NumeroCompteDataSource(Context context){
        this.allColumns = new String[]{COLUMN_ID,
                COLUMN_NUMERO_COMPTE};

        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        this.dbHelper.close();
    }

    public String getNumeroCompte(){
        Cursor cursor = database.query(TABLE_NUMERO_COMPTE, allColumns, null, null, null, null, null);
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

        values.put(COLUMN_NUMERO_COMPTE, numeroCompte);

        final long insertId = database.insert(TABLE_NUMERO_COMPTE, null, values);

        Cursor cursor = database.query(TABLE_NUMERO_COMPTE, allColumns, COLUMN_ID+"="+insertId, null, null, null, null);
        cursor.moveToFirst();

        NumeroCompte nouveauNumeroCompte = new NumeroCompte();
        nouveauNumeroCompte.setId(cursor.getLong(0));
        nouveauNumeroCompte.setNumeroCompte(cursor.getString(1));

        this.deleteAutresNumeroCompte(nouveauNumeroCompte);

        return nouveauNumeroCompte;
    }

    private void deleteAutresNumeroCompte(NumeroCompte nouveauNumeroCompte) {
        //Suppression de tous les autres montants sauf celui en paramètres
        database.delete(TABLE_NUMERO_COMPTE, COLUMN_ID+"!="+ nouveauNumeroCompte.getId(), null);
    }

}
