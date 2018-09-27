package com.servershit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(NoteDto.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NoteDto.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(String note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(NoteDto.COLUMN_NOTE, note);

        // insert row
        long id = db.insert(NoteDto.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public NoteDto getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NoteDto.TABLE_NAME,
                new String[]{NoteDto.COLUMN_ID, NoteDto.COLUMN_NOTE, NoteDto.COLUMN_TIMESTAMP},
                NoteDto.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        NoteDto note = new NoteDto(
                cursor.getInt(cursor.getColumnIndex(NoteDto.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(NoteDto.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(NoteDto.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return note;
    }

    public List<NoteDto> getAllNotes() {
        List<NoteDto> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + NoteDto.TABLE_NAME + " ORDER BY " +
                NoteDto.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NoteDto note = new NoteDto();
                note.setId(cursor.getInt(cursor.getColumnIndex(NoteDto.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(NoteDto.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(NoteDto.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + NoteDto.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(NoteDto note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteDto.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(NoteDto.TABLE_NAME, values, NoteDto.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(NoteDto note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NoteDto.TABLE_NAME, NoteDto.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }


}