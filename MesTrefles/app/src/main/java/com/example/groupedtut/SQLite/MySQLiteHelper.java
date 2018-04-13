package com.example.groupedtut.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.groupedtut.SQLite.montantmax.MontantMaxDataSource;
import com.example.groupedtut.SQLite.numerocompte.NumeroCompteDataSource;
import com.example.groupedtut.SQLite.numeroserveur.NumeroServeur;
import com.example.groupedtut.SQLite.numeroserveur.NumeroServeurDataSource;
import com.example.groupedtut.SQLite.soldeactuel.SoldeDataSource;
import com.example.groupedtut.SQLite.transaction_historique.TransactionDataSource;

/**
 * Cette classe va être appelée par l'application pour créer la base de données et créer les tables qui vont avec.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public MySQLiteHelper(Context context) {
        super(context, "trefle.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MontantMaxDataSource.TABLE_CREATE);
        sqLiteDatabase.execSQL(TransactionDataSource.TABLE_CREATE);
        sqLiteDatabase.execSQL(NumeroCompteDataSource.TABLE_CREATE);
        sqLiteDatabase.execSQL(NumeroServeurDataSource.TABLE_CREATE);
        sqLiteDatabase.execSQL(SoldeDataSource.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MontantMaxDataSource.TABLE_MONTANT_MAX);
        onCreate(sqLiteDatabase);
    }
}
