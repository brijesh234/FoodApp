package com.example.foodapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.activity.MainActivity
import com.example.foodapp.adapters.MealsAdapter
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    lateinit var mBinding: FragmentSearchBinding
    lateinit var mViewModel: HomeViewModel
    lateinit var mealsAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_search, container, false)
        mBinding = FragmentSearchBinding.inflate( inflater )
        mViewModel = (activity as MainActivity).viewModel
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pepareRecyclerView()
        mBinding.imageSearch.setOnClickListener{
            searchMeal()
        }

        observeSaerchMeals()

        var searchJob: Job? = null

        mBinding.edSearchBox.addTextChangedListener { text ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500 )
                text?.let { mViewModel.getSearchMeals(it.toString()) }
            }
        }

    }

    private fun observeSaerchMeals() {
        mViewModel.observeSearchMealsLiveData().observe( viewLifecycleOwner, { searchmeals ->
            mealsAdapter.differ.submitList( searchmeals )

        })
    }

    private fun searchMeal() {
        val searchQuery = mBinding.edSearchBox.text.toString()
        if ( searchQuery.isNotEmpty() )
        {
            mViewModel.getSearchMeals( searchQuery )
        }
    }

    private fun pepareRecyclerView() {
        mealsAdapter = MealsAdapter()
        mBinding.rvSearchMeals.apply {
            layoutManager = GridLayoutManager( context,2, GridLayoutManager.VERTICAL, false )
            adapter = mealsAdapter
        }
    }

}