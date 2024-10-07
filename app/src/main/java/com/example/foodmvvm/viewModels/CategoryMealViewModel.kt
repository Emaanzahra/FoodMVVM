package com.example.foodmvvm.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodmvvm.pojo.CategoryList
import com.example.foodmvvm.pojo.CategoryMeals
import com.example.foodmvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel : ViewModel() {

    val mealLiveData = MutableLiveData<List<CategoryMeals>>()

    fun getMealByCategory(categoryName : String){
        RetrofitInstance.api.getMealByCategory(categoryName).enqueue(object  : Callback<CategoryList>{
            override fun onResponse(p0: Call<CategoryList>, p1: Response<CategoryList>) {
                p1.body()?.let { mealList ->
                    mealLiveData.postValue(mealList.meals)
                }
            }

            override fun onFailure(p0: Call<CategoryList>, p1: Throwable) {
                Log.e("CategoryMealViewModel",p1.message.toString() )
            }

        })
    }

    fun observeMealLiveData() : LiveData<List<CategoryMeals>>{
        return mealLiveData
    }
}