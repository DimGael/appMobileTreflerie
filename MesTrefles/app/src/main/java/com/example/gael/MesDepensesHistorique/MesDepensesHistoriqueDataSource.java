package com.example.gael.MesDepensesHistorique;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Gael on 29/11/2017.
 */

public class MesDepensesHistoriqueDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelperMesDepensesHistorique dbHelper;
    private String[] allColumns;

    public MesDepensesHistoriqueDataSource(Context context){
        this.allColumns = new String[]{MySQLiteHelperMesDepensesHistorique.COLUMN_ID,
                MySQLiteHelperMesDepensesHistorique.COLUMN_MONTANT};

        dbHelper = new MySQLiteHelperMesDepensesHistorique(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        this.dbHelper.close();
    }

    public MesDepensesHistorique majMesDepensesHistorique(String beneficiaire, double montant, String date){
        //Création d'un "ContentValues" qui est un objet contenant des clés associés à des valeurs
        ContentValues values = new ContentValues();

        //Rajout de la transaction dans le content values
        values.put(MySQLiteHelperMesDepensesHistorique.COLUMN_BENEFICIAIRE, beneficiaire);
        values.put(MySQLiteHelperMesDepensesHistorique.COLUMN_MONTANT, montant);
        values.put(MySQLiteHelperMesDepensesHistorique.COLUMN_DATE, date);

        //insertId récupère l'id du nouvel element inséré dans la bdd
        long insertId = database.insert(MySQLiteHelperMesDepensesHistorique.TABLE_DEPENSES_HISTORIQUE, null, values);

        //On récupère la nouvelle entrée dans la table :
        //Le curseur pointe maintenant sur le premier élément de la selection, soit la nouvelle dépense
        Cursor cursor = database.query(MySQLiteHelperMesDepensesHistorique.TABLE_DEPENSES_HISTORIQUE, allColumns, MySQLiteHelperMesDepensesHistorique.COLUMN_ID+"="+insertId, null, null, null, null);
        cursor.moveToFirst();

        //On récupère les données dans un nouvel objet MontantMax
        MesDepensesHistorique nouveauMesDepensesHitorique = new MesDepensesHistorique();
        nouveauMesDepensesHitorique.setId(cursor.getLong(0));
        nouveauMesDepensesHitorique.setBeneficiaire(cursor.getString(1));
        nouveauMesDepensesHitorique.setMontant(cursor.getDouble(2));
        nouveauMesDepensesHitorique.setDate(cursor.getString(3));

        return nouveauMesDepensesHitorique;
    }

    public void deleteMesDepensesHistorique(MesDepensesHistorique mesDepensesHitorique){
        //Suppression d'une transaction
        database.delete(MySQLiteHelperMesDepensesHistorique.TABLE_DEPENSES_HISTORIQUE, MySQLiteHelperMesDepensesHistorique.COLUMN_ID+"="+mesDepensesHitorique.getId(), null);
    }

    public MesDepensesHistorique getMesDepensesHistorique(){
        Cursor cursor = database.query(MySQLiteHelperMesDepensesHistorique.TABLE_DEPENSES_HISTORIQUE, allColumns, null, null, null, null, null);
        MesDepensesHistorique mesDepensesHistorique = new MesDepensesHistorique();
        if(cursor.moveToFirst()){
            //(this.beneficiaire + " " + Double.toString(this.montant) + " " + this.date);

            mesDepensesHistorique.setBeneficiaire(cursor.getString(1));
            mesDepensesHistorique.setMontant(cursor.getDouble(2));
            mesDepensesHistorique.setDate(cursor.getString(3));
            return mesDepensesHistorique;
        }else
            return null;
    }

}
