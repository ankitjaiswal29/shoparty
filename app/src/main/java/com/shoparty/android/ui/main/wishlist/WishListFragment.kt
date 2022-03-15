package com.shoparty.android.ui.main.wishlist

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
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
class WishListFragment : Fragment(),RecyclerViewClickListener {
    private lateinit var binding: FragmentWishListBinding
    private lateinit var viewModel: WishListViewModel
    private lateinit var adapterWishlist: WishListAdapters
    private var listWishlistt: ArrayList<WishListResponse.Data> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wish_list, container, false)
        viewModel = ViewModelProvider(this, ViewModalFactory(activity?.application!!))[WishListViewModel::class.java]
        setObserver()
        binding.clNoData.visibility=View.VISIBLE
        binding.constraintRecycler.visibility=View.GONE
        //   viewModel.getWishlist("1")                          //api call
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObserver() {
        viewModel.wishlist.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    listWishlistt.clear()
                    listWishlistt=response.data!! as ArrayList<WishListResponse.Data>
                    setWishListAdapter(listWishlistt)
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


        viewModel.removewishlist.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                  //  ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.getWishlist("1")                          //api call
                }
                is Resource.Loading -> {
                 //   ProgressDialog.showProgressBar(requireContext())
                }
                is Resource.Error -> {
                 //   ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                //    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }




    private fun setWishListAdapter(data: List<WishListResponse.Data>?)
    {
        adapterWishlist = WishListAdapters(requireContext(),data!!,this)
        binding.wishlistRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.wishlistRecyclerview.adapter = adapterWishlist
    }

    override fun click(product_id: String) {
     viewModel.removeWishlist(product_id,"0")
    }
}