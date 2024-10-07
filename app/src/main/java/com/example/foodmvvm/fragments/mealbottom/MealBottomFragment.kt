package com.example.foodmvvm.fragments.mealbottom

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodmvvm.R
import com.example.foodmvvm.activites.MainActivity
import com.example.foodmvvm.activites.MealActivity
import com.example.foodmvvm.databinding.FragmentMealBottomBinding
import com.example.foodmvvm.fragments.HomeFragment
import com.example.foodmvvm.viewModels.HomeViewMode
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
private const val MEAL_ID = "param1"

class MealBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentMealBottomBinding
    private var mealId: String? = null
    private lateinit var viewModel : HomeViewMode


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMealBottomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealById(it)
        }

        observeBottomSheetMeal()
        onBottomSheetClick()
    }

    private fun onBottomSheetClick() {
        binding.bottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null){

//                This is an intent which is use to pass data and go to MealActivity
                val intent = Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID, mealId)
                    putExtra(HomeFragment.MEAL_NAME, mealName)
                    putExtra(HomeFragment.MEAL_THUMB, mealThumb)
                }
                startActivity(intent)
            }
        }
    }

//    This is second way with bundle to pass data

//    val bundle = Bundle()
//    bundle.apply {
//        putString(HomeFragment.MEAL_ID, mealId)
//        putString(HomeFragment.MEAL_NAME, mealName)
//        putString(HomeFragment.MEAL_THUMB, mealThumb)
//    }
//    val intent = Intent(activity, MainActivity::class.java)
//    startActivity(intent)tivity::class.java)



    private var mealName : String? = null
    private var mealThumb : String? = null

    private fun observeBottomSheetMeal() {

        viewModel.observeBottomSheetMealLiveDta().observe(viewLifecycleOwner, Observer { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgBottomSheet)
            binding.tvBottomStartArea.text = meal.strArea
            binding.tvBottomStartCategory.text = meal.strCategory
            binding.tvBottomSheetMealName.text = meal.strMeal

            mealName = meal.strMeal
            mealThumb = meal.strMealThumb
        })
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}