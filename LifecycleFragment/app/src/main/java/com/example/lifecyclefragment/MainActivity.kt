package com.example.lifecyclefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lifecyclefragment.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("MainActivity", "onCreate")


        binding.mainbtn.setOnClickListener {
            val fragment = FragmentOne()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)  // Optional: Add to back stack
                .commit()
            Toast.makeText(this, "Main Button Clicked", Toast.LENGTH_SHORT).show()



        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")

    }
}

