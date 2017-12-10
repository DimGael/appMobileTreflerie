package com.example.gael.MesDepensesHistorique;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Gael on 29/11/2017.
 */

public class MySQLiteHelperMesDepensesHistorique extends SQLiteOpenHelper {

    public static final String TABLE_DEPENSES_HISTORIQUE = "DEPENSES_HISTORIQUE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BENEFICIAIRE = "beneficiaire";
    public static final String COLUMN_MONTANT = "montant";
    public static final String COLUMN_DATE = "date";

    private static final String TABLE_CREATE = "create table "+TABLE_DEPENSES_HISTORIQUE+" ("
            +COLUMN_ID+" integer primary key autoincrement,"
            +COLUMN_BENEFICIAIRE+" text not null,"
            +COLUMN_MONTANT+" double not null,"
            +COLUMN_DATE+" text not null,";

    public MySQLiteHelperMesDepensesHistorique(Context context) {
        super(context, "trefle.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelperMesDepensesHistorique.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_DEPENSES_HISTORIQUE);
        onCreate(sqLiteDatabase);
    }
}
