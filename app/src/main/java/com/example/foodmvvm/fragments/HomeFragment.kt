package com.example.foodmvvm.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodmvvm.R
import com.example.foodmvvm.activites.CategoriesMealActivity
import com.example.foodmvvm.activites.MainActivity
import com.example.foodmvvm.activites.MealActivity
import com.example.foodmvvm.adapter.CategoriesAdapter
import com.example.foodmvvm.adapter.MostPopularAdapter
import com.example.foodmvvm.databinding.FragmentHomeBinding
import com.example.foodmvvm.fragments.mealbottom.MealBottomFragment
import com.example.foodmvvm.pojo.CategoryMeals
import com.example.foodmvvm.pojo.Meal
import com.example.foodmvvm.pojo.MealList
import com.example.foodmvvm.retrofit.RetrofitInstance
import com.example.foodmvvm.viewModels.HomeViewMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeMVVM : HomeViewMode
    private lateinit var randomMeal : Meal
    private lateinit var popularitemsAdapter : MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter


    companion object{
        const val MEAL_ID = "com.example.foodmvvm.fragments.idMeal"
        const val MEAL_NAME = "com.example.foodmvvm.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.foodmvvm.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.foodmvvm.fragments.categoryName"

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMVVM = (activity as MainActivity).viewModel
        popularitemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemRecyclerView()

        homeMVVM.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        homeMVVM.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        prepareCategoryRecyclerView()

        homeMVVM.getCategories()
        observeCategoryLiveData()

        onCategoryClick()
        onPopulatItemClick()

        onSearchIconClick()
    }

    private fun onSearchIconClick() {
        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopulatItemClick() {
        popularitemsAdapter.onLongItemClick = { meal ->
            val mealBottomSheetFragment = MealBottomFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager, "Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoriesMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoryRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoryLiveData() {
        homeMVVM.observeCategoryLiveData().observe(viewLifecycleOwner, Observer { categories->
            categoriesAdapter.setCategoriesList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularitemsAdapter.onItemClick = { meal->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemRecyclerView() {
        binding.recViewPopular.apply {
          layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
            //todo yeh line nahi add ki hui thi ap ny
            adapter = popularitemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeMVVM.observePopularItemsLiveData().observe(viewLifecycleOwner,
            { mealList->
                popularitemsAdapter.setMeals(mealList = mealList as ArrayList<CategoryMeals>)
        })
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMVVM.observeRandomMealLivedata().observe(viewLifecycleOwner,
            { meal ->
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.imgRandomMeal)

                this.randomMeal = meal

        })
    }
    }
