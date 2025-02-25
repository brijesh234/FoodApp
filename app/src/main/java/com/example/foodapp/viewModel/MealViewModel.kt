package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MealViewModel( val mealDatabase: MealDatabase ) : ViewModel() {

    private var mMealDetailLivedata = MutableLiveData<Meal>()

    fun getMealDetails( mealId : String ) {

        RetrofitInstance.api.getMealDetails( mealId ).enqueue( object : retrofit2.Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if ( response.body() != null ) {
                    mMealDetailLivedata.value = response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MyTag","failed")
            }

        })
    }

    fun observerMealDetailsLivedata() : LiveData<Meal> {
        return mMealDetailLivedata
    }

    fun upsert( meal: Meal ) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun insertMeal() {
        TODO("Not yet implemented")
    }

}