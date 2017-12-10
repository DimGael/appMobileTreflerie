package com.example.gael.soldeactuel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Gael on 06/12/2017.
 */

public class SoldeDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelperSolde dbHelper;
    private final String[] allColumns;

    public SoldeDataSource(Context context) {
        allColumns = new String[]{MySQLiteHelperSolde.COLUMN_ID, MySQLiteHelperSolde.COLUMN_SOLDE};

        dbHelper = new MySQLiteHelperSolde(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        this.dbHelper.close();
    }

    public Solde majSolde(double solde){
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelperSolde.COLUMN_SOLDE, solde);

        //insertId récupère l'id du nouvel element inséré dans la bdd
        long insertId = database.insert(MySQLiteHelperSolde.TABLE_SOLDE, null, values);

        Cursor cursor = database.query(MySQLiteHelperSolde.TABLE_SOLDE, allColumns, MySQLiteHelperSolde.COLUMN_ID+"="+insertId, null, null, null, null);
        cursor.moveToFirst();

        Solde nouvSolde = new Solde();
        nouvSolde.setId(cursor.getLong(0));
        nouvSolde.setSoldeActuel(cursor.getDouble(1));

        //Retirer les anciens soldes de la bdd
        this.deleteAncienSoldes(nouvSolde.getId());

        return nouvSolde;
    }

    private void deleteAncienSoldes(long insertId){
        this.database.delete(MySQLiteHelperSolde.TABLE_SOLDE, MySQLiteHelperSolde.COLUMN_ID+"!="+insertId, null);
    }

    public double getSoldeActuel(){
        Cursor cursor = this.database.query(MySQLiteHelperSolde.TABLE_SOLDE, allColumns, null, null, null, null, null);
        if(cursor.moveToFirst()){
            return cursor.getDouble(1);
        }
        else{
            return 0.0;
        }
    }

}
