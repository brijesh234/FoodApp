package com.example.foodapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Snackbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.activity.MainActivity
import com.example.foodapp.adapters.FavoritesMealsAdapter
import com.example.foodapp.databinding.FragmentFavoritesBinding
import com.example.foodapp.viewModel.HomeViewModel

class FavoritesFragment : Fragment() {

    lateinit var mBinding :FragmentFavoritesBinding
    lateinit var mViewModel : HomeViewModel
    lateinit var mFavoritesMealsAdapter: FavoritesMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentFavoritesBinding.inflate( inflater )
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                mViewModel.delete( mFavoritesMealsAdapter.differ.currentList[position])
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView( mBinding.favoritesRecyclerView )
    }

    private fun prepareRecyclerView() {
        mFavoritesMealsAdapter = FavoritesMealsAdapter()
        mBinding.favoritesRecyclerView.apply {
            layoutManager = GridLayoutManager( context, 2, GridLayoutManager.VERTICAL, false )
            adapter = mFavoritesMealsAdapter
        }
    }

    private fun observeFavorites() {
        mViewModel.observeFavoritesMealsLiveData().observe( requireActivity(), Observer { meals ->
            mFavoritesMealsAdapter.differ.submitList( meals )

        } )
    }
}