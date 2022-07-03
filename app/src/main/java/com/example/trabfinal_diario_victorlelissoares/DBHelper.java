package com.example.trabfinal_diario_victorlelissoares;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;

import java.util.ArrayList;
import java.util.Objects;


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

    /*variaveis para a criação da tabela de notas*/
    private static final String TABLE_NOTE_NAME = "notes";
    private static final String COL_ID_NOTE = "id";
    private static final String COL_ID_USER_NOTE = "idUser";
    private static final String COL_TITLE_NOTE = "title";
    private static final String COL_TEXT_NOTE = "texto";
    private static final String TABLE_NOTES_CREATE = "create table " + TABLE_NOTE_NAME +
            "("+ COL_ID_NOTE + " integer primary key autoincrement, " + COL_ID_USER_NOTE +
            " integer not null, " + COL_TITLE_NOTE + " text not null, " + COL_TEXT_NOTE +
            " text not null, " + "FOREIGN KEY(idUser) REFERENCES users(id));";


    SQLiteDatabase db;
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USERS_CREATE);//query de criação da tabela de usuarios
        db.execSQL(TABLE_NOTES_CREATE);//query de criação da table de notas
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_USER_NAME;
        db.execSQL(query);

        query = "DROP TABLE IF EXISTS " + TABLE_NOTE_NAME;
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

    public User buscarUser(String usuario){//email na real
        db = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = ?",
                "*", TABLE_USER_NAME, COL_EMAIL_USERS);//todas as colunas
        //String senha="não encontrado";
        db.beginTransaction();
        User use =  new User();
        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(usuario)});
            try {
                if (cursor.moveToFirst()) {

                    use.setIdUser(cursor.getInt(0));
                    use.setNome(cursor.getString(1));
                    use.setEmail(cursor.getString(2));
                    use.setSenha(cursor.getString(3));
                    //senha = cursor.getString(0);
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
        return use;
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

    //métodos referentes a tabela notas
    public void insereNote(Note u){
        db = getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(COL_ID_USER_NOTE, u.getExternIdUser());
            values.put(COL_TITLE_NOTE, u.getNoteTitle());
            values.put(COL_TEXT_NOTE, u.getNoteText());

            db.insertOrThrow(TABLE_NOTE_NAME,null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<Note> listNotes(String idUser) {
        String[] coluns = {"*"};//todas as colunas
        String[] whereArg = {idUser};
        Cursor cursor;

        //caso seja nulo
        if(Objects.isNull(idUser)){
            cursor = getReadableDatabase().query(
                    TABLE_NOTE_NAME,
                    coluns,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
        }
        else{
            cursor = getReadableDatabase().query(
                    TABLE_NOTE_NAME,
                    coluns,
                    COL_ID_USER_NOTE + "=?",
                    whereArg,
                    null,
                    null,
                    null,
                    null);
        }

        /*private static final String TABLE_NOTES_CREATE = "create table " + TABLE_NOTE_NAME +
                "("+ COL_ID_NOTE + " integer primary key autoincrement, " + COL_ID_USER_NOTE +
                " integer not null, " + COL_TITLE_NOTE + " text not null, " + COL_TEXT_NOTE +
                " text not null, " + "FOREIGN KEY(idUser) REFERENCES users(id));";*/
        ArrayList<Note> list = new ArrayList<Note>();
        while(cursor.moveToNext()){
            Note j = new Note();
            j.setIdNote(cursor.getInt(0));
            j.setExternIdUser(cursor.getInt(1));
            j.setNoteTitle(cursor.getString(2));
            j.setNoteText(cursor.getString(3));
            list.add(j);
        }
        cursor.close();
        return list;
    }


}
