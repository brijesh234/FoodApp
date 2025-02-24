package com.example.foodapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val baseURL = "https://www.themealdb.com/api/json/v1/1/"
    val api : MealApi by lazy {
        Retrofit.Builder()
            .baseUrl( baseURL )
            .addConverterFactory( GsonConverterFactory.create() )
            .build()
            .create( MealApi::class.java )
    }
}