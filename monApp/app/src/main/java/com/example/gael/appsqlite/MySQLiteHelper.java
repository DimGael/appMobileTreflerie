package com.example.gael.appsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Gael on 29/11/2017.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_MONTANT_MAX = "montant_maximum";
    public static final String COLUMN_MONTANT_MAX = "montantMax";
    public static final String COLUMN_ID = "_id";

    private static final java.lang.String DATABASE_CREATE = "create table "+TABLE_MONTANT_MAX+" ("
            +COLUMN_ID+" integer primary key autoincrement,"
            +COLUMN_MONTANT_MAX+" integer not null)";

    public MySQLiteHelper(Context context) {
        super(context, "trefle.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS montantMax");
        onCreate(sqLiteDatabase);
    }
}
