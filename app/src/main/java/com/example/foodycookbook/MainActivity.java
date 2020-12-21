package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.foodycookbook.interfaces.FetchRecipeInterface;
import com.example.foodycookbook.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // To make the activity go fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

//        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);


//        Picasso.get(context)
//                .load(ImageURL)
//                .resize(width, height).noFade().into(imageView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FetchRecipeInterface fetchRecipeInterface = retrofit.create(FetchRecipeInterface.class);

        Call<List<Recipe>> randomRecipeCall = fetchRecipeInterface.getRandomRecipe();
        randomRecipeCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });

    }
}