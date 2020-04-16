package com.example.exercisesql_lite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "db_kontak";
    private static final String TABLE_NAME = "tb_kontak";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ALAMAT = "alamat";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        dataAwal(db);
    }

    private void dataAwal(SQLiteDatabase db) {
        String CREATE_KONTAK_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAMA + " TEXT," + COLUMN_PHONE + " TEXT," +
                COLUMN_EMAIL + " TEXT," + COLUMN_ALAMAT + " TEXT)";
        db.execSQL(CREATE_KONTAK_TABLE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void TambahKontak(ModKontak modKontak){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, modKontak.getNama());
        values.put(COLUMN_PHONE, modKontak.getPhone());
        values.put(COLUMN_EMAIL, modKontak.getEmail());
        values.put(COLUMN_ALAMAT, modKontak.getAlamat());
        // Input Data
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public int UpdateData(ModKontak modKontak) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, modKontak.getNama());
        values.put(COLUMN_PHONE, modKontak.getPhone());
        values.put(COLUMN_EMAIL, modKontak.getEmail());
        values.put(COLUMN_ALAMAT, modKontak.getAlamat());
        //proses update tabel
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",new String[] { String.valueOf(modKontak.getIdUser()) });
    }

    public int HapusData(String idUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + " = ?",new String[] {idUser });
    }

    public ModKontak getUserById(String id){
        ModKontak modKontak=null;

        String selectQuery = "SELECT  * FROM " + TABLE_NAME+" where "+COLUMN_ID+"="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                modKontak = new ModKontak();

                modKontak.setIdUser(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                modKontak.setNama(cursor.getString(cursor.getColumnIndex(COLUMN_NAMA)));
                modKontak.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                modKontak.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                modKontak.setAlamat(cursor.getString(cursor.getColumnIndex(COLUMN_ALAMAT)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return modKontak;
    }

    public   Cursor AmbilSemuaData(){

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // returning cursor
        return cursor;
    }

}
