package com.example.gael.numeroserveur;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Gael on 29/11/2017.
 */

public class MySQLiteHelperNumeroServeur extends SQLiteOpenHelper {

    public static final String TABLE_NUMERO_SERVEUR = "numero_serveur";
    public static final String COLUMN_NUMERO_SERVEUR = "numero";
    public static final String COLUMN_ID = "_id";

    private static final String TABLE_CREATE = "create table "+ TABLE_NUMERO_SERVEUR +" ("
            +COLUMN_ID+" integer primary key autoincrement,"
            + COLUMN_NUMERO_SERVEUR +" text not null)";

    public MySQLiteHelperNumeroServeur(Context context) {
        super(context, "trefle.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelperNumeroServeur.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NUMERO_SERVEUR);
        onCreate(sqLiteDatabase);
    }
}
