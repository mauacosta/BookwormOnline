package com.example.pruebafirebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_FILE= "BooksDatabase";
    private static final String TABLE = "Book";
    private static final String FIELD_ID = "id";
    private static final String FIELD_MAIL = "mail";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_AUTHOR = "author";
    private static final String FIELD_YEAR = "year";


    public DbHelper (Context context){
        super(context, DB_FILE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE + "(" +
                FIELD_ID + " INTEGER PRIMARY KEY, " +
                FIELD_MAIL + " TEXT, " +
                FIELD_TITLE + " TEXT, " +
                FIELD_AUTHOR + " TEXT, " +
                FIELD_YEAR + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS ?";
        String[] params = {TABLE};
        db.execSQL(query, params);
        onCreate(db);
    }

    public void save(String mail, String title, String author, String year){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(FIELD_MAIL, mail);
        valores.put(FIELD_TITLE, title);
        valores.put(FIELD_AUTHOR, author);
        valores.put(FIELD_YEAR, year);
        db.insert(TABLE, null, valores);
    }

    public ArrayList<Book> search(String email){
        SQLiteDatabase db = getReadableDatabase();
        String clause = FIELD_MAIL + "= ?";
        String[] params = {email};

        Cursor c = db.query(TABLE, null, clause, params, null, null, null);
        ArrayList<Book> books = new ArrayList<Book>();
        while(c.moveToNext()){
            Book b = new Book(c.getString(2), c.getString(3), c.getString(4));
            books.add(b);
        }
        return books;
    }

}
