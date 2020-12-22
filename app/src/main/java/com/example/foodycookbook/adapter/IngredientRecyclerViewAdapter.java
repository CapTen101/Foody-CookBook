package com.example.foodycookbook.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodycookbook.R;
import com.example.foodycookbook.model.Ingredient;

import java.util.List;

public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder> {

    private List<Ingredient> IngredientList;

    public IngredientRecyclerViewAdapter(List<Ingredient> IngredientList) {
        this.IngredientList = IngredientList;
    }

    @NonNull
    @Override
    public IngredientRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.ingredientName.setText(IngredientList.get(position).getIngredient());
        position++;
        holder.ingredientNumber.setText(position + ": ");
        position--;
    }

    @Override
    public int getItemCount() {
        return IngredientList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName, ingredientNumber;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
            ingredientNumber = itemView.findViewById(R.id.ingredient_number);
        }
    }
}
