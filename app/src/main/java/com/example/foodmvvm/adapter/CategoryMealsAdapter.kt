package com.example.foodmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodmvvm.databinding.MealItemBinding
import com.example.foodmvvm.pojo.CategoryMeals

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealViewHolder>() { // Changed the ViewHolder class reference
    private var mealList = ArrayList<CategoryMeals>()

    fun setMealsList(mealList: List<CategoryMeals>) {
        this.mealList = mealList as ArrayList<CategoryMeals>
        notifyDataSetChanged()
    }

    class CategoryMealViewHolder(val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
        return CategoryMealViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false // Added parent to attach the inflated view to the ViewGroup
            )
        )
    }

    override fun getItemCount(): Int {
        return mealList.size // Implemented to return the size of the mealList
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
        holder.binding.apply {
            tvMealName.text = mealList[position].strMeal
            Glide.with(holder.itemView)
                .load(mealList[position].strMealThumb)
                .into(imgMeal)
        }
    }
}
