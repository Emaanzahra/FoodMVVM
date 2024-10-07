package com.example.foodmvvm.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodmvvm.R
import com.example.foodmvvm.activites.CategoriesMealActivity
import com.example.foodmvvm.activites.MainActivity
import com.example.foodmvvm.adapter.CategoriesAdapter
import com.example.foodmvvm.databinding.FragmentCategoryBinding
import com.example.foodmvvm.viewModels.HomeViewMode


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter : CategoriesAdapter
    private lateinit var viewModel : HomeViewMode


    companion object {

        const val CATEGORY_NAME = "com.example.foodmvvm.fragments.categoryName"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCategories()

        onCategoryClick()

    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoriesMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategories() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoryAdapter.setCategoriesList(categories)
        })
    }

    private fun prepareRecyclerView() {
        categoryAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

}