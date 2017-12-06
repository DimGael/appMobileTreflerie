package com.example.gael.soldeactuel;

import android.content.Context;
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
        allColumns = new String[]{MySQLiteHelperSolde.COLUMN_ID, MySQLiteHelperSolde.COLUMN_SOLDE, MySQLiteHelperSolde.COLUMN_DATE_ACTUALISATION};

        dbHelper = new MySQLiteHelperSolde(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        this.dbHelper.close();
    }

    /*
    public void majSolde(double solde);
    public Solde getSoldeActuel();
     */
}
