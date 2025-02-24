package com.example.foodapp.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.fragment.HomeFragment
import com.example.foodapp.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMealBinding
    lateinit var mMealName : String
    lateinit var mMealId : String
    lateinit var mMealThumb : String
    lateinit var mMealViewModel: MealViewModel
    lateinit var mMealYoutubeLink : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMealBinding.inflate( layoutInflater )
        setContentView( mBinding.root )
        mMealViewModel = ViewModelProvider.create( this )[ MealViewModel::class.java]
        getMealFromIntent()
        onLoadingCase()
        mMealViewModel.getMealDetails( mMealId )
        observeMealDetails()
        setInformationInView()
        setClinkEventOnYouTube()
    }

    private fun setClinkEventOnYouTube() {
        mBinding.youTube.setOnClickListener(){
            val intent = Intent( Intent.ACTION_VIEW, Uri.parse( mMealYoutubeLink ) )
            startActivity( intent )
        }
    }

    private fun observeMealDetails() {
        mMealViewModel.observerMealDetailsLivedata().observe( this ){
            mBinding.category.text = "${getString( R.string.category)} : ${it.strCategory}"
            mBinding.location.text = "${getString( R.string.area)} : ${it.strArea}"
            mBinding.details.text = it.strInstructions
            mMealYoutubeLink = it.strYoutube!!
            onResponse()
        }
    }

    private fun setInformationInView() {
        Glide.with( this)
            .load( mMealThumb )
            .into( mBinding.imgMealDetail )
        mBinding.collapsingToolbarLayout.title = mMealName
    }

    private fun getMealFromIntent() {
        mMealId = intent.getStringExtra( HomeFragment.MEAL_ID )!!
        mMealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mMealThumb = intent.getStringExtra( HomeFragment.MEAL_THUMB )!!
    }

    private fun onLoadingCase()
    {
        mBinding.favoriteButton.visibility = View.INVISIBLE
        mBinding.youTube.visibility = View.INVISIBLE
        mBinding.instruction.visibility = View.INVISIBLE
        mBinding.category.visibility = View.INVISIBLE
        mBinding.location.visibility = View.INVISIBLE
        mBinding.progressBar.visibility = View.VISIBLE
    }

    private fun onResponse()
    {
        mBinding.favoriteButton.visibility = View.VISIBLE
        mBinding.youTube.visibility = View.VISIBLE
        mBinding.instruction.visibility = View.VISIBLE
        mBinding.category.visibility = View.VISIBLE
        mBinding.location.visibility = View.VISIBLE
        mBinding.progressBar.visibility = View.INVISIBLE
    }
}