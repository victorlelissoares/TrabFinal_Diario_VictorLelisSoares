package com.example.trabfinal_diario_victorlelissoares;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;


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
        String query = "DROP TABLE IF EXISTS " + TABLE_USER_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    //métodos referentes a tabela usuário
    public void insereUser(User u){
        db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(COL_NAME_USERS, u.getNome());
            values.put(COL_EMAIL_USERS, u.getEmail());
            values.put(COL_PASSWORD_USERS, u.getSenha());
            db.insertOrThrow(TABLE_USER_NAME,null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public String buscarSenha(String usuario){//email na real
        db = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = ?",
                COL_PASSWORD_USERS, TABLE_USER_NAME, COL_EMAIL_USERS);
        String senha="não encontrado";
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(usuario)});
            try {
                if (cursor.moveToFirst()) {
                    senha = cursor.getString(0);
                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return senha;
    }

    public long excluirUser(User contato) {
        long retornoBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(contato.getIdUser())};
        retornoBD=db.delete(TABLE_USER_NAME, COL_ID_USERS + "=?", args);
        return retornoBD;
    }

    public long atualizarUser(User c){
        long retornoBD;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME_USERS,c.getNome());
        values.put(COL_EMAIL_USERS,c.getEmail());
        values.put(COL_PASSWORD_USERS,c.getSenha());
        String[] args = {String.valueOf(c.getIdUser())};
        retornoBD=db.update(TABLE_USER_NAME, values,"id=?", args);
        db.close();
        return retornoBD;
    }
}
