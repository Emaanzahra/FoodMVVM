package com.example.foodmvvm.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodmvvm.R
import com.example.foodmvvm.activites.MainActivity
import com.example.foodmvvm.adapter.FavoriteMealsAdapter
import com.example.foodmvvm.databinding.FragmentSearchBinding
import com.example.foodmvvm.viewModels.HomeViewMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var viewModel : HomeViewMode
    private lateinit var searchRecyclerViewAdapter : FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        binding.imgSearch.setOnClickListener { searchMeal() }

        observeSearchMealLiveData()

        var searchJob: Job? = null
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Cancel any existing search job
                searchJob?.cancel()
                // Create a new coroutine to handle the delay and search
                searchJob = viewLifecycleOwner.lifecycleScope.launch {
                    delay(500) // Adding delay to avoid rapid search requests
                    s?.let { searchQuery ->
                        viewModel.searchMeal(searchQuery.toString()) // Call ViewModel's search function
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun observeSearchMealLiveData() {
        viewModel.observeSearchtMealLiveDta().observe(viewLifecycleOwner, Observer { mealList ->

            searchRecyclerViewAdapter.differ.submitList(mealList)
        })
    }

    private fun searchMeal() {
        val searchQuery = binding.editTextSearch.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeal(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerViewAdapter = FavoriteMealsAdapter()
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchRecyclerViewAdapter
        }
    }

}