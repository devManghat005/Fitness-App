package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent; //importing Intent class to handle navigation between activities
import android.os.Bundle; //importing Bundle class to handle savedInstanceState of the activity
import android.view.View; //importing View class to handle onClick events of the buttons
import android.widget.Button; //importing Button class to define the UI elements as buttons


public class MainActivity extends AppCompatActivity {

    // Declare UI elements
    Button btnUserEntry, btnFoodInput, btnCalculator, btnCalorieTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find UI elements by their IDs
        btnUserEntry = findViewById(R.id.btnUserEntry);
        btnFoodInput = findViewById(R.id.btnFoodInput);
        btnCalculator = findViewById(R.id.btnCalculator);
        btnCalorieTracker = findViewById(R.id.btnCalorieTracker);

        // Set onClick listeners for each button to handle navigation to different activities
        btnUserEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the UserInput activity
                Intent intent = new Intent(getApplicationContext(), UserInput.class);
                // Start the UserInput activity
                startActivity(intent);
            }
        });
        btnFoodInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the FoodInput activity
                Intent intent = new Intent(getApplicationContext(), FoodInput.class);
                // Start the FoodInput activity
                startActivity(intent);
            }
        });
        btnCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the Calculator activity
                Intent intent = new Intent(getApplicationContext(), Calculator.class);
                // Start the Calculator activity
                startActivity(intent);
            }
        });
        btnCalorieTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the EatingPlan activity
                Intent intent = new Intent(getApplicationContext(), CalorieTracker.class);
                // Start the EatingPlan activity
                startActivity(intent);
            }
        });
    }
}

