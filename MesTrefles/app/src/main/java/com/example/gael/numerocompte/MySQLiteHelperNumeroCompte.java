package com.example.gael.numerocompte;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelperNumeroCompte extends SQLiteOpenHelper {

    public static final String TABLE_NUMERO_COMPTE = "numero_compte";
    public static final String COLUMN_NUMERO_COMPTE = "numero";
    public static final String COLUMN_ID = "_id";

    private static final String TABLE_CREATE = "create table "+ TABLE_NUMERO_COMPTE +" ("
            +COLUMN_ID+" integer primary key autoincrement,"
            + COLUMN_NUMERO_COMPTE +" text not null)";

    public MySQLiteHelperNumeroCompte(Context context) {
        super(context, "numCompteTrefle.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelperNumeroCompte.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NUMERO_COMPTE);
        onCreate(sqLiteDatabase);
    }
}
