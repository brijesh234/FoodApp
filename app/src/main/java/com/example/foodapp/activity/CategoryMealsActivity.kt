package com.example.foodapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adapters.CategoryMealsAdapter
import com.example.foodapp.databinding.ActivityCategoryBinding
import com.example.foodapp.fragment.HomeFragment
import com.example.foodapp.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var mBinding : ActivityCategoryBinding
    lateinit var mCategoryMealsViewModel: CategoryMealsViewModel
    lateinit var mCategoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCategoryBinding.inflate( layoutInflater )
        setContentView( mBinding.root )
        mBinding.categoryHeading.text = intent.getStringExtra( HomeFragment.CATEGORY_NAME )!!
        mCategoryMealsViewModel = ViewModelProvider.create( this)[CategoryMealsViewModel::class.java]
        prepareRecyclerView()
        getCategoryMeals()
        observeCategoryMeals()
    }

    private fun prepareRecyclerView() {
        mCategoryMealsAdapter = CategoryMealsAdapter()
        mBinding.recyclerView.apply {
            layoutManager = GridLayoutManager( context, 2, GridLayoutManager.VERTICAL, false )
            adapter = mCategoryMealsAdapter
        }
    }

    private fun observeCategoryMeals() {
        mCategoryMealsViewModel.observeCategoryMealsOfLiveData().observe( this, { mealList ->
            mCategoryMealsAdapter.setMealsList( mealList as ArrayList)
        })
    }

    private fun getCategoryMeals() {
        mCategoryMealsViewModel.getCategoryMeals( intent.getStringExtra( HomeFragment.CATEGORY_NAME )!! )
    }


}