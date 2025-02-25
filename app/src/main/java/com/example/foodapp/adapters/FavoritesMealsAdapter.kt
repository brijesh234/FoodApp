package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealsByCategory

class FavoritesMealsAdapter() : RecyclerView.Adapter< FavoritesMealsAdapter.FavoritesMealsViewHolder>() {

    private val  diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer( this, diffUtil )


    class FavoritesMealsViewHolder( var mbinding : MealItemBinding ) : RecyclerView.ViewHolder( mbinding.root )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMealsViewHolder {
        return FavoritesMealsViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from( parent.context ), parent,false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesMealsViewHolder, position: Int) {
        val meal = differ.currentList.get( position )
        Glide.with( holder.itemView ).load( meal.strMealThumb ).into( holder.mbinding.imageMeal)
    }
}