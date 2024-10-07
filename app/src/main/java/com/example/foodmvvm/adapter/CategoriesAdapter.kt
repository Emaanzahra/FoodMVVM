package com.example.foodmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodmvvm.databinding.CategoryItemBinding
import com.example.foodmvvm.pojo.Category
import com.example.foodmvvm.pojo.CategoryList

class CategoriesAdapter :RecyclerView.Adapter<CategoriesAdapter.CategoiesViewHolder>() {

    private var categoriesList = ArrayList<Category>()
    var onItemClick : ((Category) -> Unit)? = null

    fun setCategoriesList(categoriesList: List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    class CategoiesViewHolder (val binding: CategoryItemBinding) :RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoiesViewHolder {
       val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoiesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoiesViewHolder, position: Int) {
       holder.binding.apply {
           tcCategoryName.text = categoriesList[position].strCategory
           Glide.with(holder.itemView)
               .load(categoriesList[position].strCategoryThumb)
               .into(imgCategory)

           holder.itemView.setOnClickListener {
               onItemClick!!.invoke(categoriesList[position])
           }
       }
    }

    override fun getItemCount(): Int {
        return  categoriesList.size
    }
}