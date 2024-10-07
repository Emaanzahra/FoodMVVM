package com.example.foodmvvm.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmvvm.R
import com.example.foodmvvm.activites.MainActivity
import com.example.foodmvvm.adapter.FavoriteMealsAdapter
import com.example.foodmvvm.databinding.FragmentFavoriteBinding
import com.example.foodmvvm.viewModels.HomeViewMode
import com.google.android.material.snackbar.Snackbar


class FavoriteFragment : Fragment() {
    private lateinit var binding : FragmentFavoriteBinding
    private lateinit var viewModel : HomeViewMode
    private lateinit var favoriteAdapter : FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorite()

        val iemTouchHelper = object  : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoriteAdapter.differ.currentList[position])
                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo"
                ) {
                    viewModel.insertMeal(favoriteAdapter.differ.currentList[position])
                }.show()

            }
        }
        ItemTouchHelper(iemTouchHelper).attachToRecyclerView(binding.rvfavorites)
    }

    private fun prepareRecyclerView() {
        favoriteAdapter = FavoriteMealsAdapter()
        binding.rvfavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }
    }

    private fun observeFavorite() {
        viewModel.observeFavoritesMealLiveData().observe(requireActivity(), Observer { meals ->
            favoriteAdapter.differ.submitList(meals)
        })
    }

}