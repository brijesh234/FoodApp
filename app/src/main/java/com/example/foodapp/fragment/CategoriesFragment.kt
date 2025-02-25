package com.example.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.activity.CategoryMealsActivity
import com.example.foodapp.activity.MainActivity
import com.example.foodapp.adapters.CategoryAdapter
import com.example.foodapp.databinding.FragmentCategoriesBinding
import com.example.foodapp.fragment.HomeFragment.Companion.CATEGORY_NAME
import com.example.foodapp.viewModel.CategoryMealsViewModel
import com.example.foodapp.viewModel.HomeViewModel

class CategoriesFragment : Fragment() {

    lateinit var mViewModel : HomeViewModel
    lateinit var mCategoryAdapter : CategoryAdapter

    lateinit var mBinding: FragmentCategoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCategoriesBinding.inflate( inflater )
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ( activity as MainActivity ).viewModel
        prepareRecyclerView()
        observeCategories()
        onclick()
    }

    private fun onclick() {
        mCategoryAdapter.onClick = { category ->
            val intent =  Intent( activity, CategoryMealsActivity::class.java )
            intent.putExtra( CATEGORY_NAME, category.strCategory )
            startActivity( intent )
        }
    }

    private fun observeCategories() {
        mViewModel.observeCategoryListLiveData().observe( viewLifecycleOwner, { categoryList ->
            mCategoryAdapter.setCategoryList( categoryList as ArrayList )
        } )
    }

    private fun prepareRecyclerView() {
        mCategoryAdapter = CategoryAdapter()
        mBinding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false )
            adapter = mCategoryAdapter
        }
    }
}