package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.MealsByCategoryList
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel( val mealDatabase: MealDatabase ) : ViewModel() {

    private var mRandomMealLivedata = MutableLiveData<Meal>()
    private var mPopularItemsLiveData = MutableLiveData< List<MealsByCategory> >()
    private var mCategoryListLiveData = MutableLiveData<List<Category>>()
    private var mFavoritesMealsLiveData = mealDatabase.mealDao().getAllMeal()
    private var mSearchMeals = MutableLiveData<List<Meal>>()

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
        RetrofitInstance.api.getPopularItems( "seafood" ).enqueue( object : retrofit2.Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.body() != null )
                {
                    Log.d("MyTag","success")
                    mPopularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("MyTag","failed")
            }

        })
    }

    fun observePopularItemsLiveData() : LiveData<List<MealsByCategory>> {
        return mPopularItemsLiveData
    }

    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue( object : retrofit2.Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    mCategoryListLiveData.postValue( categoryList.categories )
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("MyTag","failed")
            }

        })
    }

    fun observeCategoryListLiveData() : LiveData<List<Category>> {
        return mCategoryListLiveData
    }

    fun observeFavoritesMealsLiveData(): LiveData<List<Meal>>{
        return mFavoritesMealsLiveData
    }

    fun delete( meal: Meal ) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete( meal )
        }
    }

    fun getSearchMeals( query : String ) = RetrofitInstance.api.getSearchMeal( query ).enqueue( object : Callback<MealList> {
        override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
           val mealsList = response.body()?.meals
            mealsList.let {
                mSearchMeals.postValue( it )
            }
        }

        override fun onFailure(call: Call<MealList>, t: Throwable) {
            Log.d("MyTag","Failed")
        }

    } )

    fun observeSearchMealsLiveData() : LiveData<List<Meal>> {
        return mSearchMeals
    }
}