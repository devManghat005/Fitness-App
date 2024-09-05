// The package declaration indicates the package to which this class belongs
package com.example.myapplication;

// Importing the necessary classes and interfaces for the class
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;


import android.content.DialogInterface;


import android.content.Intent; // Importing Intent class to handle navigation between activities


import android.os.Bundle; // Importing Bundle class to handle savedInstanceState of the activity
import android.view.View; // Importing View class to handle onClick events of the button
import android.widget.Button; // Importing Button class to define the UI element as a button
import android.widget.EditText; // Importing EditText class to define the UI elements as editable text fields
import android.widget.Toast; // Importing Toast class to show messages to the user

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

// The class that represents the activity where the user can input food data
public class FoodInput extends AppCompatActivity {
    // Declaring variables to hold the UI elements
    private EditText nameEditText, carbohydrateEditText, proteinEditText, fiberEditText, fatEditText, vitaminsEditText, quantityEditText, calorieEditText;
    private Button addFoodButton, QRscanner, list;

    // Creating an instance of the database helper class to interact with the database
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_input);

        // Initializing the variables with the respective UI elements
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        carbohydrateEditText = (EditText) findViewById(R.id.carbohydrateEditText);
        proteinEditText = (EditText) findViewById(R.id.proteinEditText);
        fiberEditText = (EditText) findViewById(R.id.fiberEditText);
        fatEditText = (EditText) findViewById(R.id.fatEditText);
        vitaminsEditText = (EditText) findViewById(R.id.vitaminsEditText);
        quantityEditText = (EditText) findViewById(R.id.quantityEditText);
        calorieEditText = (EditText) findViewById(R.id.calorieEditText);
        QRscanner = (Button) findViewById(R.id.QRscanner);
        list = (Button) findViewById(R.id.List);
        addFoodButton = (Button) findViewById(R.id.addFoodButton);

        dbHelper = new DatabaseHelper(this);

        // Setting an OnClickListener on the addFoodButton to add the food data to the database
        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieving the values entered by the user and parsing them into the required data types
                String name = nameEditText.getText().toString().trim();
                String carbohydrateStr = carbohydrateEditText.getText().toString().trim();
                String proteinStr = proteinEditText.getText().toString().trim();
                String fiberStr = fiberEditText.getText().toString().trim();
                String fatStr = fatEditText.getText().toString().trim();
                String vitaminsStr = vitaminsEditText.getText().toString().trim();
                String quantityStr = quantityEditText.getText().toString().trim();
                String calorieStr = calorieEditText.getText().toString().trim();

                // Validating the input fields
                if (name.isEmpty()) {
                    nameEditText.setError("Name is required");
                    return;
                }
                if (carbohydrateStr.isEmpty()) {
                    carbohydrateEditText.setError("Carbohydrate is required");
                    return;
                }
                int carbohydrate = Integer.parseInt(carbohydrateStr);
                if (proteinStr.isEmpty()) {
                    proteinEditText.setError("Protein is required");
                    return;
                }
                int protein = Integer.parseInt(proteinStr);
                if (fiberStr.isEmpty()) {
                    fiberEditText.setError("Fiber is required");
                    return;
                }
                int fiber = Integer.parseInt(fiberStr);
                if (fatStr.isEmpty()) {
                    fatEditText.setError("Fat is required");
                    return;
                }
                int fat = Integer.parseInt(fatStr);
                if (vitaminsStr.isEmpty()) {
                    vitaminsEditText.setError("Vitamins is required");
                    return;
                }
                int vitamins = Integer.parseInt(vitaminsStr);
                if (quantityStr.isEmpty()) {
                    quantityEditText.setError("Quantity is required");
                    return;
                }
                int quantity = Integer.parseInt(quantityStr);
                if (calorieStr.isEmpty()) {
                    calorieEditText.setError("Calorie is required");
                    return;
                }
                int calorie = Integer.parseInt(calorieStr);

                // Creating an instance of the Food class and setting its properties
                Food food = new Food();
                food.setName(name);
                food.setCarbohydrate(carbohydrate);
                food.setProtein(protein);
                food.setFiber(fiber);
                food.setFat(fat);
                food.setVitamins(vitamins);
                food.setQuantity(quantity);
                food.setCalorieCount(calorie);

                // Inserting the food data into the database
                boolean isInserted = dbHelper.addFoodData(food);
                if(isInserted){
                    Toast.makeText(FoodInput.this,"Data Inserted",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(FoodInput.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                }
            }
        });
        // Setting an OnClickListener on the QRscanner button to launch the scanCode function
        QRscanner.setOnClickListener(v -> {
            scanCode();
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FoodList.class);
                startActivity(intent);
            }
        });
    }
    // The scanCode function launches the barcode scanner to scan a barcode
    private void scanCode() {
// Configuring the barcode scanner options
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        // Launching the barcode scanner with the configured options
        barLaucher.launch(options);

    }

    // Registering an ActivityResultLauncher to handle the result of the barcode scanner activity
    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null){
// Displaying the barcode result in an AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(FoodInput.this);
            builder.setTitle("Result:");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });

}
