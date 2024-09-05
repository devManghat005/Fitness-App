// Package statement indicating the package name and its corresponding directory
package com.example.myapplication;

// Import statements to access external classes and interfaces
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.text.DecimalFormat;

// Definition of Calculator class that extends AppCompatActivity class
public class Calculator extends AppCompatActivity {

    // onCreate method that runs when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout file for the activity
        setContentView(R.layout.activity_calculator);

        // Find the button and set a listener to it
        Button calculateButton = findViewById(R.id.calculateBMI);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Find the input fields and get their values
                EditText weightEditText = findViewById(R.id.weightInput);
                EditText heightEditText = findViewById(R.id.heightInput);

                String weightInput = weightEditText.getText().toString();
                String heightInput = heightEditText.getText().toString();

                // Check if either of the input fields are empty and show a toast message if they are
                if (weightInput.isEmpty() || heightInput.isEmpty()) {
                    Toast.makeText(Calculator.this, "Please enter both weight and height", Toast.LENGTH_SHORT).show();
                } else {
                    // Parse the input values as floats and calculate the BMI
                    float weight = Float.parseFloat(weightInput);
                    float height = Float.parseFloat(heightInput);

                    float bmi = (weight / (height * height)) * 10000;

                    // Format the BMI value with one decimal place
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    String bmiValue = decimalFormat.format(bmi);

                    // Determine the BMI status based on the calculated value
                    String status;
                    if (bmi < 18.5) {
                        status = "Underweight";
                    } else if (bmi >= 18.5 && bmi <= 24.9) {
                        status = "Healthy";
                    } else if (bmi >= 25 && bmi <= 29.9) {
                        status = "Overweight";
                    } else {
                        status = "Obese";
                    }

                    // Show a toast message with the calculated BMI and its status
                    Toast.makeText(Calculator.this, "Your BMI is " + bmiValue + " and you are " + status, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}