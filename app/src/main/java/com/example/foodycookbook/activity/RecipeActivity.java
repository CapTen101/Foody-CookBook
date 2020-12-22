package com.example.foodycookbook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodycookbook.R;
import com.example.foodycookbook.adapter.IngredientRecyclerViewAdapter;
import com.example.foodycookbook.model.Ingredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private ImageView recipe_image;
    private ProgressBar recipeProgressBar;
    private FloatingActionButton YTFab, starFab;
    private TextView recipe_instructions, recipe_name, recipe_category, recipe_area;

    // Getting all the variables
    String recipeName,
            recipeCategory,
            recipeArea,
            recipeInstructions,
            recipeImageLink,
            recipeYoutubeLink;
    ArrayList<String> ingredientList = new ArrayList<>();

    // RecyclerView Components
    private RecyclerView IngredientListRecyclerView;
    private RecyclerView.Adapter IngredientListRecyclerViewADAPTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // To make the activity go fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_recipe);

        // Populating the views
        recipe_name = findViewById(R.id.recipe_name);
        recipe_image = findViewById(R.id.recipe_image);
        recipeProgressBar = findViewById(R.id.recipe_image_progress);
        IngredientListRecyclerView = findViewById(R.id.ingredient_recycler_view);
        recipe_instructions = findViewById(R.id.recipe_intructions);
        YTFab = findViewById(R.id.youtube_button);
        starFab = findViewById(R.id.favourite_button);
        recipe_area = findViewById(R.id.area);
        recipe_category = findViewById(R.id.category);


        Intent receiveData = getIntent();
        recipeName = receiveData.getStringExtra("RECIPE_NAME");
        recipeCategory = receiveData.getStringExtra("RECIPE_CATEGORY");
        recipeArea = receiveData.getStringExtra("RECIPE_AREA");
        recipeInstructions = receiveData.getStringExtra("RECIPE_INSTRUCTIONS");
        recipeImageLink = receiveData.getStringExtra("RECIPE_IMAGE_LINK");
        recipeYoutubeLink = receiveData.getStringExtra("RECIPE_YT_LINK");
        ingredientList = receiveData.getStringArrayListExtra("INGREDIENT_LIST");

        List<Ingredient> ingredientObjectList = new ArrayList<>();

        // Load the random recipe image
        Picasso.get().load(recipeImageLink).into(recipe_image, new com.squareup.picasso.Callback() {

            @Override
            public void onSuccess() {
                recipeProgressBar.setVisibility(View.GONE);
                recipe_image.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage() + "Picasso couldn't load the image successfully", Toast.LENGTH_LONG).show();
            }
        });

        recipe_instructions.setText(recipeInstructions);

        // Opens Youtube for the
        YTFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(recipeYoutubeLink)));
            }
        });


        // Changing the String Arraylist into List of Ingredient Objects
        for (int i = 0; i < ingredientList.size(); i++) {
            ingredientObjectList.add(new Ingredient(ingredientList.get(i)));
        }

        IngredientListRecyclerViewADAPTER = new IngredientRecyclerViewAdapter(ingredientObjectList);
        IngredientListRecyclerView.setAdapter(IngredientListRecyclerViewADAPTER);
        IngredientListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}