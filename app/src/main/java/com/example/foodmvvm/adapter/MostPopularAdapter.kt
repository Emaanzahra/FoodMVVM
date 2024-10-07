package com.example.foodmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodmvvm.databinding.PopularItemBinding
import com.example.foodmvvm.pojo.CategoryMeals
import com.example.foodmvvm.pojo.MealCategory
import com.example.foodmvvm.pojo.MealList

class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>(){

    lateinit var onItemClick : ((CategoryMeals )-> Unit)
    var onLongItemClick: ((CategoryMeals) -> Unit)? = null
    private var mealList = ArrayList<CategoryMeals>()

    fun setMeals (mealList: ArrayList<CategoryMeals>){
        this.mealList = mealList
        notifyDataSetChanged()
    }

    class PopularMealViewHolder ( val binding : PopularItemBinding ): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick ?.invoke(mealList[position])
            true
        }
    }
}