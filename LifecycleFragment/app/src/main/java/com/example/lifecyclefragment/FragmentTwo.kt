package com.example.lifecyclefragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FragmentTwo : Fragment() {


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("FragmentA", "onStart")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentA", "onCreate")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FragmentA", "onCreateView")
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FragmentA", "onViewCreated")

    }

    override fun onStart() {
        super.onStart()
        Log.d("FragmentA", "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.d("FragmentA", "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d("FragmentA", "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("FragmentA", "onStop")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FragmentA", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FragmentA", "onDestroy")

    }

    override fun onDetach() {
        super.onDetach()
        Log.d("FragmentA", "onDeattach")

    }




}