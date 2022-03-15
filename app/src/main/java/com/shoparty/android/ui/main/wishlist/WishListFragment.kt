package com.shoparty.android.ui.main.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.FragmentWishListBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.interfaces.RecyclerViewWishListClickListener
import com.shoparty.android.ui.main.categories.CategoryAdapter1
import com.shoparty.android.ui.main.categories.CategoryRequestModel
import com.shoparty.android.ui.main.categories.CategoryResponse
import com.shoparty.android.ui.main.categories.CategoryViewModel
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory


class WishListFragment : Fragment(),RecyclerViewClickListener {

    private lateinit var binding: FragmentWishListBinding
    private lateinit var viewModel: WishListViewModel
    private lateinit var adapterWishlist: WishListAdapters
    private val listWishlistt: ArrayList<WishListResponse.Data> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_wish_list, container, false
        )
        viewModel = ViewModelProvider(
            this,
            ViewModalFactory(activity?.application!!)
        )[WishListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
        setObserver()
        val request = WishListRequestModel("1")
        viewModel.getWishlist(request)


    }

    fun initialise() {
        adapterWishlist = WishListAdapters(listWishlistt, this,requireActivity())

        val gridLayoutManager = GridLayoutManager(requireActivity(), 1)
        binding.wishlistRecyclerview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterWishlist
        }


    }

    private fun setObserver() {

        viewModel.wishlist.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    listWishlistt.clear()
                    listWishlistt.addAll(response.data!! as ArrayList<WishListResponse.Data>)
                    adapterWishlist.notifyDataSetChanged()
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(requireContext())
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun WishListListing() {
        binding.wishlistRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        // ArrayList of class ItemsViewModel
        val data = ArrayList<String>()
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        // binding.wishlistRecyclerview.adapter = WishListAdapter(data)
        val adapter = WishListDummyAdapter(data)
        binding.wishlistRecyclerview.adapter = adapter
    }



    override fun click(pos: String) {

    }


}