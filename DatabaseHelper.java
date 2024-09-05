package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "fitnessApp_db";

    // Table Names
    public static final String TABLE_NAME = "food_table";
    private static final String USER_TABLE = "personal_info";
    public static final String DATES_TABLE = "dates_table";

    // Columns for food table
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "CARBOHYDRATE";
    public static final String COL_4 = "PROTEIN";
    public static final String COL_5 = "FIBER";
    public static final String COL_6 = "FAT";
    public static final String COL_7 = "VITAMINS";
    public static final String COL_8 = "QUANTITY";
    public static final String COL_9 = "CALORIE";

    // Columns for personal info table
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_HEIGHT = "height";

    // Columns for login table
    public static final String COLUMN_ID = "ID";


    // Columns for dates table
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_QUANTITY_EATEN = "quantity_eaten";
    public static final String COLUMN_CALORIE_EATEN = "quantity_calorie";
    public static final String COLUMN_MEAL = "meal";
    public static final String COLUMN_FOOD = "food";

    // Create table SQL queries
    private static final String CREATE_FOOD_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_2 + " TEXT, " +
                    COL_3 + " INTEGER, " +
                    COL_4 + " INTEGER, " +
                    COL_5 + " INTEGER, " +
                    COL_6 + " INTEGER, " +
                    COL_7 + " INTEGER, " +
                    COL_8 + " INTEGER, " +
                    COL_9 + " INTEGER)";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_AGE + " INTEGER, " +
                    COLUMN_WEIGHT + " REAL, " +
                    COLUMN_HEIGHT + " REAL)";


    private static final String CREATE_DATES_TABLE =
            "CREATE TABLE " + DATES_TABLE + " (" +
                    COLUMN_QUANTITY_EATEN + " REAL, " +
                    COLUMN_MEAL + " TEXT, " +
                    COLUMN_FOOD + " STRING, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_CALORIE_EATEN + " REAL)";
// Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FOOD_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_DATES_TABLE);
    }

    // Upgrade tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATES_TABLE);
        onCreate(db);
    }

    // Methods for the FOOD Table.
    public boolean addFoodData(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, food.getName());
        contentValues.put(COL_3, food.getCarbohydrate());
        contentValues.put(COL_4, food.getProtein());
        contentValues.put(COL_5, food.getFiber());
        contentValues.put(COL_6, food.getFat());
        contentValues.put(COL_7, food.getVitamins());
        contentValues.put(COL_8, food.getQuantity());
        contentValues.put(COL_9, food.getCalorieCount());
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public String getColumnAge() {
        return COLUMN_AGE;
    }
    public String getColumnWeight() {
        return COLUMN_WEIGHT;
    }
    public String getColumnHeight() {
        return COLUMN_HEIGHT;
    }
    public String getColumnCalories() {
        return COL_9;
    }
    public String getColumnName() {
        return COL_2;
    }

    public Cursor getAllFoodData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public boolean checkFoodExists(String foodName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COL_2},
                COL_2 + " = ?",
                new String[]{foodName},
                null, null, null);

        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }


    // Methods for the USER Table.
    public boolean addUserData(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.getName());
        contentValues.put(COLUMN_AGE, user.getAge());
        contentValues.put(COLUMN_WEIGHT, user.getWeight());
        contentValues.put(COLUMN_HEIGHT, user.getHeight());
        long result = db.insert(USER_TABLE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + USER_TABLE, null);
        return res;
    }
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }


    public boolean updateUserData(String id, String name, int age, int weight, int height) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_AGE, age);
        contentValues.put(COLUMN_WEIGHT, weight);
        contentValues.put(COLUMN_HEIGHT, height);
        db.update(USER_TABLE, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteUserData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_TABLE, "ID = ?", new String[]{id});
    }

    public void addDate() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = calendar.getTime().toString();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, currentDate);

        db.insert(DATES_TABLE, null, values);
        db.close();
    }

    public boolean addFoodConsumption(String food, String meal, String date, double quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FOOD, food);
        contentValues.put(COLUMN_MEAL, meal);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_QUANTITY_EATEN, quantity);
        long result = db.insert(DATES_TABLE, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

}
