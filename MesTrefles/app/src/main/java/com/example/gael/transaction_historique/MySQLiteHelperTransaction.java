package com.example.gael.transaction_historique;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alexis on 10/12/2017.
 */

public class MySQLiteHelperTransaction extends SQLiteOpenHelper {

    public static final String TABLE_DEPENSES_HISTORIQUE = "historique";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMPTE = "beneficiaire";
    public static final String COLUMN_MONTANT = "montant";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TYPE = "type";

    private static final String TABLE_CREATE = "create table "+TABLE_DEPENSES_HISTORIQUE+" ("
            +COLUMN_ID+" integer primary key autoincrement,"
            + COLUMN_COMPTE +" text not null,"
            +COLUMN_MONTANT+" number not null,"
            +COLUMN_DATE+" text not null,"
            +COLUMN_TYPE+" text not null)";

    public MySQLiteHelperTransaction(Context context) {
        super(context, "historique_trefle.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelperTransaction.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_DEPENSES_HISTORIQUE);
        onCreate(sqLiteDatabase);
    }
}
