package com.example.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodapp.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.activity.CategoryMealsActivity
import com.example.foodapp.activity.MainActivity
import com.example.foodapp.activity.MealActivity
import com.example.foodapp.adapters.CategoryAdapter
import com.example.foodapp.adapters.MostPopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var mBinding:FragmentHomeBinding
    private lateinit var mViewModel: HomeViewModel
    private lateinit var mRandomMeal : Meal
    private lateinit var mPopularItemsAdapter : MostPopularAdapter
    private lateinit var  mCategoryAdapter : CategoryAdapter

    companion object{
        const val MEAL_ID = "meal_id"
        const val MEAL_NAME = "meal_name"
        const val MEAL_THUMB = "meal_thumb"
        const val CATEGORY_NAME = "category_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ( activity as MainActivity ).viewModel
        mPopularItemsAdapter = MostPopularAdapter()
        mCategoryAdapter = CategoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate( inflater, container, false )
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.getRandomMeal()
        observeRandomMeal()
        randomMealClickEvent()

        preparePopularItemRecyclerView()
        getPopularItems()
        observePopularItems()
        popularItemsClick()

        prepareCategoryItemRecyclerView()
        getCategory()
        observeCategoryItems()
        categoryItemsClick()

        searchOnClick()

    }

    private fun searchOnClick() {
        mBinding.searchIcon.setOnClickListener{
            findNavController().navigate( R.id.action_homeFragment_to_searchFragment )
        }
    }

    private fun popularItemsClick() {
        mPopularItemsAdapter.onClick = { categoryList ->
            val intent = Intent( activity, MealActivity::class.java )
            intent.putExtra( MEAL_ID, categoryList.idMeal )
            intent.putExtra( MEAL_NAME, categoryList.strMeal )
            intent.putExtra( MEAL_THUMB, categoryList.strMealThumb )
            startActivity( intent )
        }
    }

    private fun randomMealClickEvent() {
        mBinding.cardViewImage.setOnClickListener{
            val intent = Intent( activity, MealActivity::class.java )
            intent.putExtra( MEAL_ID, mRandomMeal.idMeal )
            intent.putExtra( MEAL_NAME, mRandomMeal.strMeal )
            intent.putExtra( MEAL_THUMB, mRandomMeal.strMealThumb )
            startActivity( intent )
        }
    }

    private fun observeRandomMeal() {
        mViewModel.observeRandomMealLivedata().observe( viewLifecycleOwner) {
            Glide.with(this@HomeFragment)
                .load(it.strMealThumb)
                .into(mBinding.cardViewImage)
            mRandomMeal = it
        }
    }

    private fun preparePopularItemRecyclerView() {
        mBinding.recyclerViewMealsPopular.apply {
            layoutManager = LinearLayoutManager( activity, LinearLayoutManager.HORIZONTAL, false )
            adapter = mPopularItemsAdapter
        }
    }

    private fun getPopularItems() {
        mViewModel.getPopularItems()
    }

    private fun observePopularItems() {
        mViewModel.observePopularItemsLiveData().observe( viewLifecycleOwner) { mealList ->

            mPopularItemsAdapter.setMeals( mealList as ArrayList<MealsByCategory> )

        }
    }

    private fun prepareCategoryItemRecyclerView() {
        mBinding.recyclerViewCategory.apply {
            layoutManager = GridLayoutManager( context, 3, GridLayoutManager.VERTICAL, false )
            adapter = mCategoryAdapter
        }
    }

    private fun getCategory() {
        mViewModel.getCategories()
    }

    private fun observeCategoryItems() {
        mViewModel.observeCategoryListLiveData().observe( viewLifecycleOwner){ categories ->
            mCategoryAdapter.setCategoryList( categories as ArrayList )
        }
    }

    private fun categoryItemsClick() {
        mCategoryAdapter.onClick = { category ->
            val intent =  Intent( activity, CategoryMealsActivity::class.java )
            intent.putExtra( CATEGORY_NAME, category.strCategory )
            startActivity( intent )
        }
    }
}