package com.example.admin.amazonproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.admin.amazonproject.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 10/12/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "databaseHelper";

    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Books.db";

    public static final String TABLE_NAME = "Books";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_AUTHOR = "Author";
    public static final String COLUMN_IMAGE = "Image";
    public static final String ID = "id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY, " + COLUMN_TITLE + " TEXT, " + COLUMN_AUTHOR + " TEXT, " + COLUMN_IMAGE + " TEXT " + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long saveBook(Book book) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, book.getTitle());
        contentValues.put(COLUMN_AUTHOR, book.getAuthor());
        contentValues.put(COLUMN_IMAGE, book.getImageURL());

        long isSaved = database.insert(TABLE_NAME, null, contentValues);

        return isSaved;
    }

    public List<BookComplete> getBooksList() {
        List<BookComplete> bookCompleteList = new ArrayList<>();

        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "SELECT * from " + TABLE_NAME;

        Cursor cursor = database.rawQuery(QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                BookComplete bookComplete = new BookComplete(cursor.getString(1), cursor.getString(2), cursor.getString(3));
                //Log.d(TAG, "getBooksList: " + bookComplete.getTitle());
                bookCompleteList.add(bookComplete);
            } while (cursor.moveToNext());
        }

        return bookCompleteList;
    }
}
