package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemBinding
import com.example.foodapp.pojo.MealsByCategory

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    private var mealList = ArrayList<MealsByCategory>()
    lateinit var onClick : ( ( MealsByCategory ) -> Unit)

    fun setMeals( list : ArrayList<MealsByCategory> )
    {
        mealList = list
        notifyDataSetChanged()
    }
    class PopularMealViewHolder( val binding: PopularItemBinding ) : RecyclerView.ViewHolder( binding.root )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder( PopularItemBinding.inflate( LayoutInflater.from( parent.context), parent, false))
    }

    override fun getItemCount(): Int {
       return mealList.size
    }

    override fun onBindViewHolder( holder: PopularMealViewHolder, position: Int) {
        Glide.with( holder.itemView)
            .load( mealList[position].strMealThumb )
            .into( holder.binding.imageView)
        holder.binding.imageView.setOnClickListener{
            onClick.invoke( mealList.get( position ) )
        }
    }
}