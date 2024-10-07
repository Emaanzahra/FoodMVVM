package com.example.foodmvvm.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodmvvm.database.MealDatabase

class HomeViewModelFactory(
    private val mealDatabase : MealDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewMode(mealDatabase) as T
    }
}