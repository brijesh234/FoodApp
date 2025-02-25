package com.example.foodapp.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodapp.R
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.viewModel.HomeViewModel
import com.example.foodapp.viewModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val viewModel : HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance( this )
        val homeViewModelProviderFactory = HomeViewModelFactory( mealDatabase )
        ViewModelProvider( this, homeViewModelProviderFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val navController = Navigation.findNavController( this, R.id.hostFragment)

        NavigationUI.setupWithNavController( bottomNavigation, navController )
    }
}