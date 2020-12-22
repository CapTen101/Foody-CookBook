package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodycookbook.interfaces.FetchRecipeInterface;
import com.example.foodycookbook.model.RandomRecipe;
import com.example.foodycookbook.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageView randomRecipeImage;
    private EditText searchRecipe;
    private ProgressBar randomRecipeImageProgress;
    private static final String TAG = "MainActivity.class";

    private FetchRecipeInterface fetchRecipeInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // To make the activity go fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        randomRecipeImage = findViewById(R.id.random_recipe_image);
        searchRecipe = findViewById(R.id.search_recipe);
        randomRecipeImageProgress = findViewById(R.id.random_recipe_image_progress);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fetchRecipeInterface = retrofit.create(FetchRecipeInterface.class);

        Call<RandomRecipe> randomRecipeCall = fetchRecipeInterface.getRandomRecipe();
        randomRecipeCall.enqueue(new Callback<RandomRecipe>() {
            @Override
            public void onResponse(Call<RandomRecipe> call, Response<RandomRecipe> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Random Recipe Call Unsuccessful", Toast.LENGTH_SHORT).show();
                    return;
                }

                RandomRecipe randomSingleRecipe = response.body();

                assert randomSingleRecipe != null;
                List<Recipe> recipes = randomSingleRecipe.getMeals();

                for (Recipe recipe : recipes) {
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
                    Log.e(TAG, "" + recipe.getStrMealThumb() + " " + recipe.getStrCategory());
                }
            }

            @Override
            public void onFailure(Call<RandomRecipe> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });

    }
}