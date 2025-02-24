package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.CategoryMeals
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var mRandomMealLivedata = MutableLiveData<Meal>()
    private var mPopularItemsLiveData = MutableLiveData< List<CategoryMeals> >()

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue( object : retrofit2.Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful && response.body() != null )
                {
                    val meal : Meal = response.body()!!.meals.get(0)
                    mRandomMealLivedata.value = meal
                    Log.d( "MyTag", "onResponse: ${meal.idMeal}")
                }
                else
                {
                    Log.d("MyTag"," empty")
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MyTag"," onFailure ")
            }

        })
    }

    fun observeRandomMealLivedata() : LiveData<Meal> {
        return mRandomMealLivedata
    }

    fun getPopularItems( ) {
        RetrofitInstance.api.getPopularItems( "seafood" ).enqueue( object : retrofit2.Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null )
                {
                    Log.d("MyTag","success")
                    mPopularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                TODO("Not yet implemented")
                Log.d("MyTag","failed")
            }

        })
    }

    fun observePopularItemsLiveData() : LiveData<List<CategoryMeals>> {
        return mPopularItemsLiveData
    }
}