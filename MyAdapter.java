/**
 * Adapter for RecyclerView to display food data.
 */
package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList name, quantity, calorie, protein, carbohydrates, fats, fiber, vitamins;

    public MyAdapter(Context context, ArrayList name, ArrayList quantity, ArrayList calorie, ArrayList protein, ArrayList carbohydrates, ArrayList fats, ArrayList fiber, ArrayList vitamins) {
        // Initialize context and data arrays
        this.context = context;
        this.name = name;
        this.quantity = quantity;
        this.calorie = calorie;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.fiber = fiber;
        this.vitamins = vitamins;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each RecyclerView item
        View v = LayoutInflater.from(context).inflate(R.layout.userentry, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Bind data to each view holder
        holder.name.setText(String.valueOf(name.get(position)));
        holder.quantity.setText(quantity.get(position).toString().trim());
        holder.calorie.setText(calorie.get(position).toString().trim());
        holder.carbohydrates.setText(carbohydrates.get(position).toString().trim());
        holder.protein.setText(protein.get(position).toString().trim());
        holder.fiber.setText(fiber.get(position).toString().trim());
        holder.fats.setText(fats.get(position).toString().trim());
        holder.vitamins.setText(vitamins.get(position).toString().trim());
    }

    @Override
    public int getItemCount() {
        // Return the number of items to be displayed in the RecyclerView
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Define views inside the item layout
        TextView name, quantity, calorie, protein, carbohydrates, fats, fiber, vitamins;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find views by id
            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            calorie = itemView.findViewById(R.id.calorie);
            protein = itemView.findViewById(R.id.protein);
            carbohydrates = itemView.findViewById(R.id.carbohydrate);
            fats = itemView.findViewById(R.id.fat);
            fiber = itemView.findViewById(R.id.fiber);
            vitamins = itemView.findViewById(R.id.vitamins);
        }
    }
}
