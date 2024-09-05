// This code is an Android application that tracks the number of calories consumed by the user.
// The application uses a SQLite database to store food and calorie data.

package com.example.myapplication;

import android.content.ContentValues;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.Locale;

public class CalorieTracker extends AppCompatActivity {

    // Declare EditText, CheckBox, and Button views
    private EditText nameEditText;
    private EditText quantityEditText;
    private CheckBox breakfastCheckBox;
    private CheckBox lunchCheckBox;
    private CheckBox dinnerCheckBox;
    private Button submitButton;
    private Button progressButton;
    private Button recommendFood;

    // Declare SQLiteDatabase and DatabaseHelper objects
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_tracker);

        // Bind views to layout elements
        nameEditText = findViewById(R.id.edit_text);
        quantityEditText = findViewById(R.id.quantity_edit_text);
        breakfastCheckBox = findViewById(R.id.breakfast);
        lunchCheckBox = findViewById(R.id.lunch);
        dinnerCheckBox = findViewById(R.id.dinner);
        submitButton = findViewById(R.id.button);
        progressButton = findViewById(R.id.progress);
        recommendFood = findViewById(R.id.recommendFood);

        // Initialize DatabaseHelper and get writable database
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        // Set click listener for submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get food name, quantity, and meal from input fields
                String foodName = nameEditText.getText().toString();
                String quantity = quantityEditText.getText().toString();
                String meal = "";
                if (breakfastCheckBox.isChecked()) {
                    meal = "breakfast";
                } else if (lunchCheckBox.isChecked()) {
                    meal = "lunch";
                } else if (dinnerCheckBox.isChecked()) {
                    meal = "dinner";
                }

                // Validate input fields
                if (foodName.isEmpty()) {
                    nameEditText.setError("Name is Required");
                    return;
                }
                if (quantity.isEmpty()) {
                    quantityEditText.setError("Quantity is Required");
                    return;
                }
                if (meal.isEmpty()) {
                    Toast.makeText(CalorieTracker.this, "Please select a meal option", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if food name is already in food table
                Cursor cursor = database.rawQuery("SELECT * FROM " + "food_table" + " WHERE " + DatabaseHelper.COL_2 + " = ?", new String[]{foodName});
                if (cursor.moveToFirst()) {
                    int quantityIndex = cursor.getColumnIndex(DatabaseHelper.COL_8);
                    double quantity2 = cursor.getDouble(quantityIndex);
                    double calories = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_9)));
                    double caloriesPerQuantity = calories / quantity2;
                    double totalCalories = Double.parseDouble(quantity) * caloriesPerQuantity;

                    // Add food name, date, quantity, and meal to dates table
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
                    ContentValues values = new ContentValues();
                    values.put("food", foodName);
                    values.put("date", date);
                    values.put("quantity_eaten", quantity);
                    values.put("quantity_calorie", totalCalories);
                    values.put("meal", meal);
                    database.insert("dates_table", null, values);
                    Toast.makeText(CalorieTracker.this, "Food added to tracker", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CalorieTracker.this, "Food not found in database", Toast.LENGTH_SHORT).show();
                }
                cursor.close();

            }
        });
// Set click listener for progress button
        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Start progress activity
                Intent intent = new Intent(getApplicationContext(), CalorieCounter.class);
                startActivity(intent);
            }
        });

        recommendFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EatingPlan.class);
                startActivity(intent);
            }
        });
    }
}