package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.pojo.MealsByCategory
import kotlin.collections.ArrayList

class CategoryMealsAdapter() : RecyclerView.Adapter< CategoryMealsAdapter.CategoryMealsViewHolder>() {

    var mMeals = ArrayList<MealsByCategory>()

    fun setMealsList( mealList : ArrayList<MealsByCategory> ) {
        mMeals = mealList
        notifyDataSetChanged()
    }

    class CategoryMealsViewHolder( var binding: MealItemBinding ) : RecyclerView.ViewHolder( binding.root )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder( MealItemBinding.inflate( LayoutInflater.from( parent.context ) ) )
    }

    override fun getItemCount(): Int {
        return mMeals.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with( holder.itemView)
            .load( mMeals.get(position).strMealThumb )
            .into( holder.binding.imageMeal )

        holder.binding.textMeal.text = mMeals.get(position).strMeal
    }
}