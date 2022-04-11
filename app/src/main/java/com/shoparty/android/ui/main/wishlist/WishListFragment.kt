package com.shoparty.android.ui.main.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.FragmentWishListBinding

import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
class WishListFragment : Fragment(), RecyclerViewFavouriteListener {
    private var operationalPos: Int = -1
    private lateinit var binding: FragmentWishListBinding
    private lateinit var viewModel: WishListViewModel
    private lateinit var adapterWishlist: WishListAdapters
    private var listWishlistt: ArrayList<WishListResponse.Result> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).manageUi(ivLogo = true, ivBag = true,ivSearch = true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wish_list, container, false)
        viewModel = ViewModelProvider(this, ViewModalFactory(activity?.application!!))[WishListViewModel::class.java]
        viewModel.getWishlist(PrefManager.read(PrefManager.LANGUAGEID, 1).toString())        //api call
        setObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObserver()
    {
        viewModel.wishlist.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    listWishlistt=response.data!! as ArrayList<WishListResponse.Result>
                    initFavResponse()
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


        viewModel.addremovewishlist.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    if(listWishlistt.isNotEmpty()){
                        listWishlistt.removeAt(operationalPos)
                        adapterWishlist.notifyItemRemoved(operationalPos)
                        if(listWishlistt.isEmpty()){
                            initFavResponse()
                        }
                    }
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

    private fun initFavResponse() {
        if(listWishlistt.isNullOrEmpty())
        {
            binding.clNoData.visibility=View.VISIBLE
            binding.wishlistRecyclerview.visibility=View.GONE
        }
        else
        {
            binding.clNoData.visibility=View.GONE
            binding.wishlistRecyclerview.visibility=View.VISIBLE
            setWishListAdapter(listWishlistt)
        }
    }


    private fun setWishListAdapter(data: List<WishListResponse.Result>?)
    {
        adapterWishlist = WishListAdapters(requireContext(),data!!,this)
        binding.wishlistRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.wishlistRecyclerview.adapter = adapterWishlist
    }

    override fun favourite(
        position: Int,
        producat_id: String,
        type: String,
        product_detail_id: String,
        product_sizeId: String,
        product_colorId: String
    ) {
        operationalPos = position
        viewModel.addremoveWishlist(producat_id,type.toInt(),product_detail_id.toInt(),product_sizeId,product_colorId)
    }
}