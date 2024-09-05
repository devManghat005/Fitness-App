package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserInput extends AppCompatActivity {

    // declaring private member variables for the editable text fields, save button, and database helper class
    private EditText edit_name, edit_age, edit_weight, edit_height;
    private Button save_button;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        // initializing the member variables with the respective UI elements
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_age = (EditText) findViewById(R.id.edit_age);
        edit_weight = (EditText) findViewById(R.id.edit_weight);
        edit_height = (EditText) findViewById(R.id.edit_height);
        save_button = (Button) findViewById(R.id.save_button);
        dbHelper = new DatabaseHelper(this); // instantiating the database helper class

        // setting a click listener for the save button
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // retrieving user input data from the editable text fields and parsing it to the appropriate data types
                String name = edit_name.getText().toString().trim();
                int age = Integer.parseInt(edit_age.getText().toString().trim());
                float weight = Float.parseFloat(edit_weight.getText().toString().trim());
                float height = Float.parseFloat(edit_height.getText().toString().trim());


                // creating a user object and setting the user input data to it
                User user = new User();
                user.setName(name);
                user.setAge(age);
                user.setWeight(weight);
                user.setHeight(height);

                // inserting the user input data into the SQLite database
                boolean isInserted = dbHelper.addUserData(user);

                // displaying a toast message to the user based on whether the data was inserted successfully or not
                if (isInserted)
                    Toast.makeText(UserInput.this, "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(UserInput.this, "Data not Inserted", Toast.LENGTH_LONG).show();
            }
        });
    }
}
