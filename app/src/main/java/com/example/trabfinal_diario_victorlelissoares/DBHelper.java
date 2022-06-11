package com.example.trabfinal_diario_victorlelissoares;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Diario.db";

    /*variaveis para a criação da tabela de usuario*/
    private static final String TABLE_USER_NAME = "users";
    private static final String COL_ID_USERS = "id";
    private static final String COL_NAME_USERS = "name";
    private static final String COL_EMAIL_USERS = "email";
    private static final String COL_PASSWORD_USERS = "password";
    private static final String TABLE_USERS_CREATE = "create table " + TABLE_USER_NAME +
            "("+ COL_ID_USERS + " integer primary key autoincrement, " + COL_NAME_USERS +
            " text not null, " + COL_EMAIL_USERS + " text not null, " + COL_PASSWORD_USERS +
            " text not null);" ;

    SQLiteDatabase db;
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USERS_CREATE);//query de criação da tabela
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
