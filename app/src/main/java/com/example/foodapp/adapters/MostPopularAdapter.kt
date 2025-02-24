package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemBinding
import com.example.foodapp.pojo.CategoryMeals
import com.example.foodapp.pojo.MealList

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    private var mealList = ArrayList<CategoryMeals>()
    lateinit var onClick : ( ( CategoryMeals ) -> Unit)

    fun setMeals( list : ArrayList<CategoryMeals> )
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