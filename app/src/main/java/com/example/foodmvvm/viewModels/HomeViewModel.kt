package com.example.foodmvvm.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodmvvm.database.MealDatabase
import com.example.foodmvvm.pojo.Category
import com.example.foodmvvm.pojo.CategoryList
import com.example.foodmvvm.pojo.CategoryMeals
import com.example.foodmvvm.pojo.Meal
import com.example.foodmvvm.pojo.MealCategory
import com.example.foodmvvm.pojo.MealList
import com.example.foodmvvm.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewMode(
    private val mealDatabase: MealDatabase
) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<CategoryMeals>>()
    private var categoriesLieData = MutableLiveData<List<Category>>()
    private var favoriteMealLiveData = mealDatabase.mealdao().getAllMeal()
    private var bottomSheetLiveData = MutableLiveData<Meal>()
    private var searchMeaLiveData = MutableLiveData<List<Meal>>()

//    This is use for configuration changes like orientation
    private var savedStateRandomMeal : Meal ?= null

    fun getRandomMeal(){
        savedStateRandomMeal?.let {randomMeal ->
            randomMealLiveData.postValue(randomMeal)
            return
        }

        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() !=null){
                    val  randomMeal : Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                    savedStateRandomMeal = randomMeal
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment",  t.message.toString())
            }

        })
    }

    fun getPopularItems() {
        RetrofitInstance.api.getPopularMeal("Seafood").enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                // Check if the response is successful and not null
                if (response.body() !=null){
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("Meal Fragment", t.message.toString())
            }
        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object :Callback<MealCategory>{
            override fun onResponse(call: Call<MealCategory>, response: Response<MealCategory>) {
                response.body()?.let {categoryList ->
                    categoriesLieData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<MealCategory>, t: Throwable) {
                Log.e("HomeViewModel", t.message.toString())
            }

        })
    }

    fun getMealById(id : String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object  : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let { meal ->
                    bottomSheetLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home ViewModel", t.message.toString())
            }

        })

    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealdao().delete(meal)
        }
    }
    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealdao().update(meal)
        }
    }

    fun searchMeal(searchQuery : String) = RetrofitInstance.api.searchMeal(searchQuery).enqueue(
        object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
               val mealList = response.body()?.meals
                mealList?.let {
                    searchMeaLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home ViewModel", t.message.toString())
            }

        }
    )

    fun observeRandomMealLivedata() : LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<CategoryMeals>>{
        return popularItemsLiveData
    }

    fun observeCategoryLiveData() :LiveData<List<Category>>{
        return categoriesLieData
    }

    fun observeFavoritesMealLiveData() : LiveData<List<Meal>>{
        return favoriteMealLiveData

    }

    fun observeBottomSheetMealLiveDta() : LiveData<Meal> = bottomSheetLiveData

    fun observeSearchtMealLiveDta() : LiveData<List<Meal>> {
        return searchMeaLiveData
    }

}