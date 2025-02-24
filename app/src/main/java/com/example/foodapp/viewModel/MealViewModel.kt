package com.example.foodapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class MealViewModel() : ViewModel() {

    private var mMealDetailLivedata = MutableLiveData<Meal>()

    fun getMealDetails( mealId : String ) {

        RetrofitInstance.api.getMealDetails( mealId ).enqueue( object : retrofit2.Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if ( response.body() != null ) {
                    mMealDetailLivedata.value = response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun observerMealDetailsLivedata() : LiveData<Meal> {
        return mMealDetailLivedata
    }

}