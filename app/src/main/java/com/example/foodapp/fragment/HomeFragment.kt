package com.example.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.activity.MealActivity
import com.example.foodapp.adapters.MostPopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.CategoryMeals
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var mBinding:FragmentHomeBinding
    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mRandomMeal : Meal
    private lateinit var mPopularItemsAdapter : MostPopularAdapter

    companion object{
        const val MEAL_ID = "meal_id"
        const val MEAL_NAME = "meal_name"
        const val MEAL_THUMB = "meal_thumb"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHomeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        mPopularItemsAdapter = MostPopularAdapter()
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

        mHomeViewModel.getRandomMeal()
        observeRandomMeal()
        randomMealClickEvent()

        preparePopularItemRecyclerView()
        getPopularItems()
        observePopularItems()
        popularItemsClick()
    }

    private fun popularItemsClick() {
        mPopularItemsAdapter.onClick = { categoryList ->
            val intent = Intent( activity, MealActivity::class.java )
            intent.putExtra( MEAL_ID, mRandomMeal.idMeal )
            intent.putExtra( MEAL_NAME, mRandomMeal.strMeal )
            intent.putExtra( MEAL_THUMB, mRandomMeal.strMealThumb )
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
        mHomeViewModel.observeRandomMealLivedata().observe( viewLifecycleOwner) {
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
        mHomeViewModel.getPopularItems()
    }

    private fun observePopularItems() {
        mHomeViewModel.observePopularItemsLiveData().observe( viewLifecycleOwner) { mealList ->

            mPopularItemsAdapter.setMeals( mealList as ArrayList<CategoryMeals> )

        }
    }
}