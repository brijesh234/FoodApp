package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.CategoryItemBinding
import com.example.foodapp.pojo.Category

class CategoryAdapter : RecyclerView.Adapter< CategoryAdapter.CategoryViewHolder>() {

    private var mCategoryList = ArrayList<Category>()

    fun setCategoryList( categories : ArrayList<Category> ) {
        mCategoryList = categories
        notifyDataSetChanged()
    }

    class CategoryViewHolder( val binding: CategoryItemBinding ) : RecyclerView.ViewHolder( binding.root )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with( holder.itemView)
            .load( mCategoryList[position].strCategoryThumb)
            .into( holder.binding.categoryImageView)
        holder.binding.categoryTextView.text = mCategoryList[position].strCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder( CategoryItemBinding.inflate( LayoutInflater.from( parent.context ) ) )
    }

    override fun getItemCount(): Int {
        return mCategoryList.size
    }
}