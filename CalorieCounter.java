package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalorieCounter extends AppCompatActivity {

    // Declare TextView view for displaying daily calorie consumption
    private TextView calorieConsumptionTextView;

    // Declare SQLiteDatabase and DatabaseHelper objects
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);

        // Bind view to layout element
        calorieConsumptionTextView = findViewById(R.id.calorie_consumption_text_view);

        // Initialize DatabaseHelper and get readable database
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getReadableDatabase();

        // Calculate daily calorie consumption for today
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        Cursor cursor = database.rawQuery(
                "SELECT SUM(quantity_calorie) FROM dates_table WHERE date=?",
                new String[]{date}
        );
        if (cursor.moveToFirst()) {
            double totalCalories = cursor.getDouble(0);
            calorieConsumptionTextView.setText(String.format(Locale.getDefault(), "Total calories consumed today: %.2f", totalCalories));
        } else {
            Toast.makeText(this, "No data found for today", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}
