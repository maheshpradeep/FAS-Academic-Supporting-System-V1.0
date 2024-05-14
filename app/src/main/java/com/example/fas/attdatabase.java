package com.example.fas;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class attdatabase extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 7;
    // Database name
    private static final String DATABASE_NAME = "attendance.db";

    // Table name
    private static final String TABLE_ATTENDANCE = "attendance";
    private static final String TABLE_SUBJECTS = "subjects";

    // Columns
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_SUBJECT = "subject";
    private static final String COLUMN_STATUS = "status";

    private static final String COLUMN_SUBJECT_ID = "id";
    private static final String COLUMN_SUBJECT_NAME = "name";

    // Create table query
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_ATTENDANCE + " (" +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    COLUMN_SUBJECT + " TEXT NOT NULL, " +
                    COLUMN_STATUS + " INTEGER NOT NULL, " +
                    "PRIMARY KEY (" + COLUMN_DATE + ", " + COLUMN_SUBJECT + "))";

    private static final String CREATE_SUBJECTS_TABLE_QUERY =
            "CREATE TABLE " + TABLE_SUBJECTS + " (" +
                    COLUMN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_SUBJECT_NAME + " TEXT NOT NULL)";

    public attdatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        db.execSQL(CREATE_TABLE_QUERY);
        db.execSQL(CREATE_SUBJECTS_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        // Create tables again
        onCreate(db);
    }

    // Record attendance with subject
    public void recordAttendance(String subject, Date date, boolean attended) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = sdf.format(date);
        values.put(COLUMN_DATE, dateString);
        values.put(COLUMN_SUBJECT, subject);
        values.put(COLUMN_STATUS, attended ? 1 : 0);
        db.insertWithOnConflict(TABLE_ATTENDANCE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public List<String> getAllSubjects() {
        List<String> subjects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_SUBJECT_NAME + " FROM " + TABLE_SUBJECTS, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String subjectName = cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME));
                subjects.add(subjectName);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return subjects;
    }


    public int getTotalAttendance(String subject) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ATTENDANCE + " WHERE " + COLUMN_SUBJECT + " = ?", new String[]{subject});
        cursor.moveToFirst();
        int totalAttendance = cursor.getInt(0);
        cursor.close();
        db.close();
        return totalAttendance;
    }

    // Get attended days count for a specific subject
    public int getAttendedDays(String subject) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ATTENDANCE +
                " WHERE " + COLUMN_SUBJECT + " = ? AND " + COLUMN_STATUS + " = 1", new String[]{subject});
        cursor.moveToFirst();
        int attendedDays = cursor.getInt(0);
        cursor.close();
        db.close();
        return attendedDays;
    }

    // Get count of attendance records for a specific subject
    public int getAttendanceCountForSubject(String subject) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ATTENDANCE +
                " WHERE " + COLUMN_SUBJECT + " = ?", new String[]{subject});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    // Get all attendance records for a specific subject
    @SuppressLint("Range")
    public List<attrecord> getAllAttendance(String subject) {
        List<attrecord> attendanceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ATTENDANCE, new String[]{COLUMN_DATE, COLUMN_STATUS},
                COLUMN_SUBJECT + " = ?", new String[]{subject}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                attrecord attendanceRecord = new attrecord();
                attendanceRecord.setDateFromString(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                attendanceRecord.setAttended(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)) == 1);
                attendanceList.add(attendanceRecord);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return attendanceList;
    }


    public boolean addSubject(String subjectName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT_NAME, subjectName);

        long result = db.insert(TABLE_SUBJECTS, null, values);
        db.close();

        // If result is -1, insertion failed
        return result != -1;
    }
}
