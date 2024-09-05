// This class represents an activity that displays personalized eating recommendations.
package com.example.myapplication;

// Import necessary libraries for Android components.
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EatingPlan extends AppCompatActivity {
    // Create instance variables for database access and data storage.
    private DatabaseHelper db;
    private List<String> recommendedFoods;
    private SQLiteDatabase database;
    String status;
    int age;
    float height;
    float weight;

    // Create arrays to store recommended foods by nutrient type.
    String[] ProteinfoodNames;
    String[] FibrefoodNames;

    // Create text views to display recommendations.
    TextView recommendationAge;
    TextView recommendationBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eating_plan);

        // Instantiate DatabaseHelper to connect to the database.
        db = new DatabaseHelper(this);

        // Find the text views for age and BMI recommendations.
        recommendationAge = findViewById(R.id.recommendation);
        recommendationBMI = findViewById(R.id.recommendation2);

        // Open a readable database connection and retrieve the latest personal info.
        database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + "personal_info" + " ORDER BY ID DESC LIMIT 1",
                null
        );

        // Retrieve personal data from the cursor and calculate BMI.
        if (cursor.moveToFirst()) {
            int weightIndex = cursor.getColumnIndex("weight");
            weight = cursor.getFloat(weightIndex);

            int heightIndex = cursor.getColumnIndex("height");
            height = cursor.getFloat(heightIndex);

            int ageIndex = cursor.getColumnIndex("age");
            age = cursor.getInt(ageIndex);

            float bmi = weight / (height * height);

            // Set status based on calculated BMI value.
            if (bmi < 18.5) {
                status = "Underweight";
            } else if (bmi >= 18.5 && bmi <= 24.9) {
                status = "Normal";
            } else if (bmi >= 25 && bmi <= 29.9) {
                status = "Overweight";
            } else {
                status = "Obese";
            }
        }

        // Query the food_table database for recommended foods with high protein content.
        Cursor cursor2 = database.query("food_table",
                new String[]{"NAME", "PROTEIN"},
                null,
                null,
                null,
                null,
                "PROTEIN DESC LIMIT 3"
        );

        // Store the protein-rich food names and their nutrient values in an array.
        if (cursor2.moveToFirst()) {
            ProteinfoodNames = new String[3];
            int index = 0;
            do {
                String food_name = cursor2.getString(cursor2.getColumnIndex("NAME"));
                float protein_count = cursor2.getFloat(cursor2.getColumnIndex("PROTEIN"));
                ProteinfoodNames[index] = food_name + " (" + protein_count + " g)";
                index++;
            } while (cursor2.moveToNext());
        }

        // Query the food_table database for recommended foods with high fiber content.
        Cursor cursor3 = database.query("food_table",
                new String[]{"NAME", "FIBER"},
                null,
                null,
                null,
                null,
                "FIBER DESC LIMIT 3");

        // Store the fiber-rich food names and their nutrient values in an array.
        if (cursor3.moveToFirst()) {
            FibrefoodNames = new String[3];
            int index = 0;
            do {
                String food_name = cursor3.getString(cursor3.getColumnIndex("NAME"));
                float fiber_count = cursor3.getFloat(cursor3.getColumnIndex("FIBER"));
                FibrefoodNames[index] = food_name + " (" + fiber_count + " g)";
                index++;
            } while (cursor3.moveToNext());
        }
        ListView proteinListView = findViewById(R.id.protein_list);
        ArrayAdapter<String> proteinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ProteinfoodNames);
        proteinListView.setAdapter(proteinAdapter);


        recommendedFoods = new ArrayList<>();

        if (status.equals("Underweight")) {
            recommendedFoods.add("Peanut Butter");
            recommendedFoods.add("Banana");
            recommendedFoods.add("Cheese");
        } else if (status.equals("Normal")) {
            recommendedFoods.add("Almonds");
            recommendedFoods.add("Salmon");
            recommendedFoods.add("Broccoli");
        } else if (status.equals("Overweight")) {
            recommendedFoods.add("Greek Yogurt");
            recommendedFoods.add("Quinoa");
            recommendedFoods.add("Avocado");
        } else if (status.equals("Obese")) {
            recommendedFoods.add("Eggs");
            recommendedFoods.add("Spinach");
            recommendedFoods.add("Chicken Breast");
        }

        ListView recommendedFoodsListView = findViewById(R.id.recommended_foods_list);
        ArrayAdapter<String> recommendedFoodsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recommendedFoods);
        recommendedFoodsListView.setAdapter(recommendedFoodsAdapter);

        recommendationAge.setText("Recommended daily calorie intake for a " + age + " year old:");
        recommendationBMI.setText("BMI Status: " + status);

        database.close();
        cursor.close();
        cursor2.close();
        cursor3.close();
    }
}