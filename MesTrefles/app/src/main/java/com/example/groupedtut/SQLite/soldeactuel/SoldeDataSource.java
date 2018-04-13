package com.example.groupedtut.SQLite.soldeactuel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.groupedtut.SQLite.MySQLiteHelper;


/**
 * Created by Gael on 06/12/2017.
 */

public class SoldeDataSource {

    public static final String TABLE_SOLDE = "solde";
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SOLDE = "solde";

    private static final String[] allColumns = new String[]{COLUMN_ID, COLUMN_SOLDE};

    public static final String TABLE_CREATE = "CREATE TABLE "+ TABLE_SOLDE + "("+
            COLUMN_ID+" integer primary key autoincrement," +
            COLUMN_SOLDE+" number not null)";

    public SoldeDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        this.dbHelper.close();
    }

    public Solde majSolde(double solde){
        return SoldeDataSource.majSolde(solde, this.database);
    }

    public static Solde majSolde(double solde, SQLiteDatabase sqldb){
        ContentValues values = new ContentValues();

        values.put(COLUMN_SOLDE, solde);

        //insertId récupère l'id du nouvel element inséré dans la bdd
        long insertId = sqldb.insert(TABLE_SOLDE, null, values);

        Cursor cursor = sqldb.query(TABLE_SOLDE, allColumns, COLUMN_ID+"="+insertId, null, null, null, null);
        cursor.moveToFirst();

        Solde nouvSolde = new Solde();
        nouvSolde.setId(cursor.getLong(0));
        nouvSolde.setSoldeActuel(cursor.getDouble(1));

        //Retirer les anciens soldes de la bdd
        SoldeDataSource.deleteAncienSoldes(nouvSolde.getId(), sqldb);

        return nouvSolde;

    }

    private void deleteAncienSoldes(long insertId){
        this.database.delete(TABLE_SOLDE, COLUMN_ID+"!="+insertId, null);
    }

    private static void deleteAncienSoldes(long insertId, SQLiteDatabase sqldb){
        sqldb.delete(TABLE_SOLDE, COLUMN_ID+"!="+insertId, null);
    }

    public double getSoldeActuel(){
        Cursor cursor = this.database.query(TABLE_SOLDE, allColumns, null, null, null, null, null);
        if(cursor.moveToFirst()){
            return cursor.getDouble(1);
        }
        else{
            return 0.0;
        }
    }

}
