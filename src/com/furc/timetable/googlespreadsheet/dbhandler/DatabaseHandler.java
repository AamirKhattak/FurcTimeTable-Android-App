package com.furc.timetable.googlespreadsheet.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
	private static String ClassBatch = "ClassBatch";
	private static String DATABASE_NAME = "timetablefurc.db";
	private static String DayOfWeek;
	private static String Period;
	private static String Room;
	private static int SCHEMA_VERSION;
	private static String Subject;
	private static String TABLE_NAME = "timetable";
	private static String Teacher = "Teacher";

	private ArrayList<String> classBatchList;

	static {
		SCHEMA_VERSION = 1;
		DayOfWeek = "DayOfWeek";
		Period = "Period";
		Room = "Room";
		Subject = "Subject";
	}

	public DatabaseHandler(Context paramContext) {
		super(paramContext, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	public ArrayList<String> getClassBatchList() {
		return this.classBatchList;
	}

	public void deleteDB() {
		getWritableDatabase().execSQL("DELETE from " + TABLE_NAME);
	}

	public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
		paramSQLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ( "
				+ DayOfWeek + " INTEGER PRIMERY KEY, " + Period
				+ " TEXT PRIMERY KEY, " + Room + " TEXT PRIMERY KEY," + Subject
				+ " TEXT, " + ClassBatch + " TEXT, " + Teacher + " Text)");
		Log.d("Logcat", "Database created");
	}

	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,
			int paramInt2) {
		paramSQLiteDatabase.delete(TABLE_NAME, null, null);
		onCreate(paramSQLiteDatabase);
	}

	// write to database
	public long writeToDatabase(int dayOfWeek, String period, String room,
			String subject, String classBatch, String teacher) {
		SQLiteDatabase timetableDB = getWritableDatabase();
		try {
			ContentValues timetableAttributes = new ContentValues();
			timetableAttributes.put(DayOfWeek, Integer.valueOf(dayOfWeek));
			timetableAttributes.put(Period, period);
			timetableAttributes.put(Room, room);
			timetableAttributes.put(Subject, subject);
			timetableAttributes.put(ClassBatch, classBatch);
			timetableAttributes.put(Teacher, teacher);
			long l = timetableDB.insert(TABLE_NAME, null, timetableAttributes);
			return l;
		} finally {
			timetableDB.close();
		}
	}

	// reading from database
	public List<TimeTableAttribSetterGetter> readFromDatabase(String QUERY) {
		ArrayList<TimeTableAttribSetterGetter> timetableAttributesArrayList = new ArrayList<TimeTableAttribSetterGetter>();

		classBatchList = new ArrayList<String>();

		SQLiteDatabase timetableDB = getWritableDatabase();
		Cursor cursor = timetableDB.rawQuery(QUERY, null);

		if (cursor.moveToFirst()) {
			do {
				TimeTableAttribSetterGetter ttAttributes = new TimeTableAttribSetterGetter();

				// coursor.getString(colNumber)
				int DAY_OF_WEEK = 0;
				int PERIOD = 1;
				int ROOM = 2;
				int SUBJECT = 3;
				int CLASS_BATCH = 4;
				int TEACHER = 5;

				if (QUERY.equals("select distinct classBatch from timetable")) {
					classBatchList.add(cursor.getString(0));
				}
				 else {
					ttAttributes.setDayOfWeek(cursor.getString(DAY_OF_WEEK));
					ttAttributes.setPeriod(cursor.getString(PERIOD));
					ttAttributes.setRoom(cursor.getString(ROOM));
					ttAttributes.setSubject(cursor.getString(SUBJECT));
					ttAttributes.setClassBatch(cursor.getString(CLASS_BATCH));
					ttAttributes.setTeacher(cursor.getString(TEACHER));
				}

				timetableAttributesArrayList.add(ttAttributes);
			} while (cursor.moveToNext());
		}

		cursor.close();
		timetableDB.close();
		return timetableAttributesArrayList;
	}
}
