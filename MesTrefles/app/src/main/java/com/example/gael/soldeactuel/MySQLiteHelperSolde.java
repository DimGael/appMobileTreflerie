package com.example.gael.soldeactuel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Gael on 06/12/2017.
 */

public class MySQLiteHelperSolde extends SQLiteOpenHelper {

    public static final String TABLE_SOLDE = "solde";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SOLDE = "solde";

    private static final String TABLE_CREATE = "CREATE TABLE "+ TABLE_SOLDE + "("+
            COLUMN_ID+" integer primary key autoincrement," +
            COLUMN_SOLDE+" number not null)";

    public MySQLiteHelperSolde(Context context) {
        super(context, "trefle.bd", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        Log.w(MySQLiteHelperSolde.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_SOLDE);
        onCreate(sqLiteDatabase);
    }
}
