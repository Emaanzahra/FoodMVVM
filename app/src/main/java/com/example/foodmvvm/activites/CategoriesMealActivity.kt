package com.example.foodmvvm.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.foodmvvm.R
import com.example.foodmvvm.adapter.CategoryMealsAdapter
import com.example.foodmvvm.databinding.ActivityCategoriesMealBinding
import com.example.foodmvvm.fragments.HomeFragment
import com.example.foodmvvm.viewModels.CategoryMealViewModel

class CategoriesMealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesMealBinding
    lateinit var categoryMealViewModel : CategoryMealViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealViewModel = ViewModelProvider(this).get(CategoryMealViewModel::class.java)

        categoryMealViewModel.getMealByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealViewModel.observeMealLiveData().observe(this, Observer {mealList ->
            binding.tvCategoryCount.text = mealList.size.toString()
           categoryMealsAdapter.setMealsList(mealList)
        })
    }

    private fun prepareRecyclerView() {

        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}