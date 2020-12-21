package com.example.foodycookbook.interfaces;

import com.example.foodycookbook.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FetchRecipeInterface {

    @GET("random.php")
    Call<List<Recipe>> getRandomRecipe();


}
