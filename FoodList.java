/*
 * FoodList.java
 *
 * This class displays the list of food items with their nutritional values using a RecyclerView.
 */

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodList extends AppCompatActivity {

    // Declare variables
    RecyclerView recyclerView;
    ArrayList<String> name, quantity, calorie, protein, carbohydrates, fats, fiber, vitamins;
    MyAdapter adapter;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        // Initialize variables
        db= new DatabaseHelper(this);
        name=new ArrayList<>();
        quantity=new ArrayList<>();
        calorie=new ArrayList<>();
        protein=new ArrayList<>();
        carbohydrates=new ArrayList<>();
        fats=new ArrayList<>();
        fiber=new ArrayList<>();
        vitamins=new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyAdapter(this, name, quantity, calorie, protein, carbohydrates, fats, fiber, vitamins );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Display data in the RecyclerView
        displayData();
    }

    // Retrieve data from the database and add it to the RecyclerView
    private void displayData() {
        Cursor cursor = db.getAllFoodData();
        if(cursor.getCount()==0) {
            Toast.makeText(FoodList.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while(cursor.moveToNext()){
                // Add data to the corresponding ArrayLists
                name.add(cursor.getString(1)); // changed index to 1, as the name column is at index 1
                carbohydrates.add(cursor.getString(2)); // changed index to 2, as the carbohydrate column is at index 2
                protein.add(cursor.getString(3)); // changed index to 3, as the protein column is at index 3
                fiber.add(cursor.getString(4)); // changed index to 4, as the fiber column is at index 4
                fats.add(cursor.getString(5)); // changed index to 5, as the fats column is at index 5
                vitamins.add(cursor.getString(6)); // changed index to 6, as the vitamins column is at index 6
                quantity.add(cursor.getString(7)); // changed index to 7, as the quantity column is at index 7
                calorie.add(cursor.getString(8)); // changed index to 8, as the calorie column is at index 8
            }
            adapter.notifyDataSetChanged(); // Notify the adapter of the data changes
        }
    }
}
