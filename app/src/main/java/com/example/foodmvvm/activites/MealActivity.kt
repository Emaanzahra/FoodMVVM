package com.example.foodmvvm.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodmvvm.R
import com.example.foodmvvm.database.MealDatabase
import com.example.foodmvvm.databinding.ActivityMealBinding
import com.example.foodmvvm.fragments.HomeFragment
import com.example.foodmvvm.pojo.Meal
import com.example.foodmvvm.viewModels.MealViewModel
import com.example.foodmvvm.viewModels.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var youtubeLink : String
    private lateinit var binding : ActivityMealBinding
    private lateinit var mealMVVM: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealInformationFromIntent()

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMVVM = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]


        mealMVVM.getMealDetail(mealId)
        observeMealLiveData()

        setInformationInViews()

        loadingCase()

        onYoutubeClick()
        onfavoriteClick()
    }

    private fun onfavoriteClick() {
        binding.floatBtn.setOnClickListener {
            mealToSave?.let {
                mealMVVM.insertMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave : Meal? = null
    private fun observeMealLiveData() {
        mealMVVM.observeMealDetailLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(value: Meal) {

                onResponseCase()

                val meal = value
                mealToSave = meal
                binding.tvCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvInstructions.text = meal.strInstructions

                youtubeLink = meal.strYoutube
            }

        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingBar.title = mealName
        binding.collapsingBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.floatBtn.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.floatBtn.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}