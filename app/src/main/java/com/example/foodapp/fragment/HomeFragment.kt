package com.example.foodapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate( inflater, container, false )
        return binding.root
//        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RetrofitInstance.api.getRandomMeal().enqueue( object : retrofit2.Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful && response.body() != null )
                {
                    val meal : Meal = response.body()!!.meals.get(0)
                    Log.d( "MyTag", "onResponse: ${meal.idMeal}")
                    Glide.with( this@HomeFragment )
                        .load( meal.strMealThumb )
                        .into( binding.cardViewImage)
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

}