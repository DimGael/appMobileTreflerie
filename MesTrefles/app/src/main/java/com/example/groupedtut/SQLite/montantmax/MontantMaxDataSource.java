package com.example.groupedtut.SQLite.montantmax;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.groupedtut.SQLite.MySQLiteHelper;

/**
 * Created by Gael on 29/11/2017.
 */

public class MontantMaxDataSource {

    public static final String TABLE_MONTANT_MAX = "montant_maximum";
    public static final String COLUMN_MONTANT_MAX = "montantMax";
    public static final String COLUMN_ID = "_id";

    public static final String TABLE_CREATE = "create table "+TABLE_MONTANT_MAX+" ("
            +COLUMN_ID+" integer primary key autoincrement,"
            +COLUMN_MONTANT_MAX+" number not null)";


    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns;

    public MontantMaxDataSource(Context context){
        this.allColumns = new String[]{COLUMN_ID,
                COLUMN_MONTANT_MAX};

        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        this.dbHelper.close();
    }

    public MontantMax majMontantMax(double montantMax){
        //Création d'un "ContentValues" qui est un objet contenant des clés associés à des valeurs
        ContentValues values = new ContentValues();

        //Rajout du MontantMax dans l'objet ContentValues
        values.put(COLUMN_MONTANT_MAX, montantMax);

        //insertId récupère l'id du nouvel element inséré dans la bdd
        long insertId = database.insert(TABLE_MONTANT_MAX, null, values);

        //On récupere le nouveau montantMax Récupéré :
        //Le curseur pointe maintenant sur le premier élément de la selection, soit le nouveau MontantMax
        Cursor cursor = database.query(TABLE_MONTANT_MAX, allColumns, COLUMN_ID+"="+insertId, null, null, null, null);
        cursor.moveToFirst();

        //On récupère les données dans un nouvel objet MontantMax
        MontantMax nouveauMontantMax = new MontantMax();
        nouveauMontantMax.setId(cursor.getLong(0));
        nouveauMontantMax.setMontantMax(cursor.getInt(1));

        //On enlève tous les autres Montants max pour qu'il ne reste plus que celui que l'on vient d'ajouter
        deleteAutresMontantMax(nouveauMontantMax);

        return nouveauMontantMax;
    }

    public void deleteMontantMax(MontantMax montantMax){
        //Suppression du MontantMax
        database.delete(TABLE_MONTANT_MAX, COLUMN_ID+"="+montantMax.getId(), null);
    }

    public void deleteAutresMontantMax(MontantMax montantMaxAGarder){
        //Suppression de tous les autres montants sauf celui en paramètres
        database.delete(TABLE_MONTANT_MAX, COLUMN_ID+"!="+montantMaxAGarder.getId(), null);
    }

    public double getMontantMax(){
        Cursor cursor = database.query(TABLE_MONTANT_MAX, allColumns, null, null, null, null, null);
        if(cursor.moveToFirst()){
            return cursor.getDouble(1);
        }else
            return 250;
    }

}
