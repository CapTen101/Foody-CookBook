package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodycookbook.activity.RecipeActivity;
import com.example.foodycookbook.interfaces.FetchRecipeInterface;
import com.example.foodycookbook.model.RandomRecipe;
import com.example.foodycookbook.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // UI Layout Variables
    private ImageView randomRecipeImage;
    private EditText searchRecipe;
    private ProgressBar randomRecipeImageProgress;

    // Recipe Variables
    String recipeName,
            recipeCategory,
            recipeArea,
            recipeInstructions,
            recipeImageLink,
            recipeYoutubeLink;
    ArrayList<String> ingredientList = new ArrayList<>();

    private static final String TAG = "MainActivity.class";

    // The interface for calling the API
    private FetchRecipeInterface fetchRecipeInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // To make the activity go fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Populating the views
        randomRecipeImage = findViewById(R.id.random_recipe_image);
        searchRecipe = findViewById(R.id.search_recipe);
        randomRecipeImageProgress = findViewById(R.id.random_recipe_image_progress);

        // Initialising Retrofit for API call
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fetchRecipeInterface = retrofit.create(FetchRecipeInterface.class);

        // To get the random recipe object received from the API
        final Recipe[] randomArray = new Recipe[1];

        Call<RandomRecipe> randomRecipeCall = fetchRecipeInterface.getRandomRecipe();
        randomRecipeCall.enqueue(new Callback<RandomRecipe>() {
            @Override
            public void onResponse(Call<RandomRecipe> call, Response<RandomRecipe> response) {

                // If response Code is other than 2xx & 3xx
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Random Recipe Call Unsuccessful", Toast.LENGTH_SHORT).show();
                    return;
                }

                RandomRecipe randomSingleRecipe = response.body();

                // Random Recipe is received in a JSON array of "meals"
                assert randomSingleRecipe != null;
                List<Recipe> recipes = randomSingleRecipe.getMeals();

                // saving the recipe object
                randomArray[0] = recipes.get(0);

                for (Recipe recipe : recipes) {

                    recipeName = recipe.getStrMeal();
                    recipeCategory = recipe.getStrCategory();
                    recipeArea = recipe.getStrArea();
                    recipeInstructions = recipe.getStrInstructions();
                    recipeImageLink = recipe.getStrMealThumb();
                    recipeYoutubeLink = recipe.getStrYoutube();

                    for (int i = 0; i < 20; i++) {
                        if (recipe.getStrIngredient1() != null)
                            ingredientList.add(recipe.getStrIngredient1());
                        else
                            break;
                    }

                    // Load the random recipe image
                    Picasso.get().load(recipe.getStrMealThumb()).into(randomRecipeImage, new com.squareup.picasso.Callback() {

                        @Override
                        public void onSuccess() {
                            randomRecipeImageProgress.setVisibility(View.GONE);
                            randomRecipeImage.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage() + "Picasso couldn't load the image successfully", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<RandomRecipe> call, Throwable t) {

                // Flag the error and write the logs
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "" + t.getMessage());
            }
        });

        randomRecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openRecipe = new Intent(MainActivity.this, RecipeActivity.class);
                openRecipe.putExtra("RECIPE_NAME", recipeName);
                openRecipe.putExtra("RECIPE_CATEGORY", recipeCategory);
                openRecipe.putExtra("RECIPE_AREA", recipeArea);
                openRecipe.putExtra("RECIPE_INSTRUCTIONS", recipeInstructions);
                openRecipe.putExtra("RECIPE_IMAGE_LINK", recipeImageLink);
                openRecipe.putExtra("RECIPE_YT_LINK", recipeYoutubeLink);
                openRecipe.putExtra("INGREDIENT_LIST", ingredientList);
                startActivity(openRecipe);
            }
        });
    }
}