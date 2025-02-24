package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var randomMealLivedata = MutableLiveData<Meal>()

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue( object : retrofit2.Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful && response.body() != null )
                {
                    val meal : Meal = response.body()!!.meals.get(0)
                    randomMealLivedata.value = meal
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
        return randomMealLivedata
    }
}