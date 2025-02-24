package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.MealsByCategoryList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class CategoryMealsViewModel : ViewModel()
{
    var mCategoryMealsOfLiveData = MutableLiveData< List<MealsByCategory> >()

    fun getCategoryMeals( category: String ) {
        RetrofitInstance.api.getCategoryMeals( category ).enqueue( object : retrofit2.Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                response.body()?.let { mealList ->
                    mCategoryMealsOfLiveData.postValue( mealList.meals )
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("MyTag","failed")
            }

        })
    }

    fun observeCategoryMealsOfLiveData() : LiveData<List<MealsByCategory>> {
        return mCategoryMealsOfLiveData
    }
}