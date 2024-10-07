package com.example.foodmvvm.retrofit

import com.example.foodmvvm.pojo.CategoryList
import com.example.foodmvvm.pojo.MealCategory
import retrofit2.Call  // Correct import for Retrofit's Call
import com.example.foodmvvm.pojo.MealList
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>


//   If there is ? it means you are looking something with it's ID
    @GET("lookup.php?")
    fun getMealDetails (@Query("i") id : String) : Call<MealList>

    @GET("filter.php?")
    fun getPopularMeal (@Query("c") categoryName : String) : Call<CategoryList>

    @GET("categories.php")
    fun getCategories () :Call<MealCategory>

    @GET("filter.php")
    fun getMealByCategory(@Query("c") categoryName : String) : Call<CategoryList>

    @GET("search.php")
    fun searchMeal(@Query("s") categoryName : String) : Call<MealList>
}
