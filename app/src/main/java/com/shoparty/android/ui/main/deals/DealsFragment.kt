package com.shoparty.android.ui.main.deals

import android.app.Activity
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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shoparty.android.R
import com.shoparty.android.common_modal.Product
import com.shoparty.android.databinding.FragmentDealsBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import com.shoparty.android.ui.filter.FilterActivity
import com.shoparty.android.ui.filter.age.AgeRequest
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.main.product_list.ProductListAdapters
import com.shoparty.android.ui.main.product_list.ProductListRequestModel
import com.shoparty.android.ui.main.product_list.ProductListSortingBottomSheetAdapter
import com.shoparty.android.ui.main.wishlist.WishListViewModel
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class DealsFragment : Fragment(),View.OnClickListener,RecyclerViewClickListener{
    private var sortclickName: String=""
    lateinit var binding: FragmentDealsBinding
    lateinit var dialog: BottomSheetDialog
    private lateinit var viewModel: DealsViewModel
    private var bottomlist: ArrayList<String> = ArrayList()
    private lateinit var viewModeladdwishlist: WishListViewModel
    private var newproductlist: ArrayList<Product> = ArrayList()
    private lateinit var layoutManager: GridLayoutManager
    var color = false
    var size = false
    var age = false
    var gender = false
    var progressshow = true
    private lateinit var adapter:ProductListAdapters
    var pageOffset=0
    var pageLimit=10
    var fav_position:Int = 0
    var fav_type:Int = 0
    var filter_applied=0
    var sort_applied=0
    var sort_type=0
    private var selectedColorList:ArrayList<String> =  ArrayList()
    private var selectedSizeList:ArrayList<String> =  ArrayList()
    private var selectedAgeList:ArrayList<AgeRequest> =  ArrayList()
    private var selectedGenderList:ArrayList<String> =  ArrayList()
    private var selectedminprice = 0
    private var selectedmaxprice = 0

    private var recyclerViewFavouriteListener=object :RecyclerViewFavouriteListener{
          override fun favourite(
              position: Int,
              producat_id: String,
              type: String,
              product_detail_id: String,
              product_sizeId: String,
              product_colorId: String)
          {
              fav_position=position
              fav_type=type.toInt()
            if(PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty())
            {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
            else
            {
                viewModeladdwishlist.addremoveWishlist(producat_id,type.toInt(),product_detail_id.toInt(),product_sizeId,product_colorId)
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).manageUi(ivLogo = true, ivBag = true,ivSearch = true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deals, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModalFactory(activity?.application!!)
        )[DealsViewModel::class.java]
        viewModeladdwishlist = ViewModelProvider(this, ViewModalFactory(activity?.application!!))[WishListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initilize()
        setObserver()
        setupRecylarview()
        callApi(filter_applied, ProductListRequestModel.Filter(), sort_applied, sort_type) //api call
    }

    private fun saveSortLocal()
    {
        PrefManager.write(PrefManager.SORTSELECTEDNAME,sortclickName)
    }


    private fun callApi(
           filter_applied: Int,
           filterList: ProductListRequestModel.Filter,
           sort_applied: Int,
           sort_type: Int)
       {
           val request =
               DealsRequestModel(PrefManager.read(PrefManager.LANGUAGEID, 1).toString(),
               pageOffset.toString(),
               pageLimit.toString(),PrefManager.read(PrefManager.USER_ID, ""),
               filter_applied.toString(),filterList,
               sort_applied,
               sort_type)
               viewModel.getDeals(request)
       }

    private fun setObserver() {
        viewModel.deals.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    if(progressshow)
                    {
                        ProgressDialog.hideProgressBar()
                    }
                    progressshow=false
                    val productlist = response.data as ArrayList<Product>
                    if(productlist.isNullOrEmpty())
                    {
                        binding.linearNoData.visibility= View.VISIBLE
                        binding.dealsItemRecycler.visibility= View.GONE
                    }
                    else
                    {
                        binding.linearNoData.visibility= View.GONE
                        binding.dealsItemRecycler.visibility= View.VISIBLE
                        setupData(productlist)
                        if(sort_applied!=0)  //sort applied
                        {
                            dialog.dismiss()
                            saveSortLocal()
                        }
                    }
                }
                is Resource.Loading -> {
                    if(progressshow)
                    {
                        ProgressDialog.showProgressBar(requireContext())
                    }
                }
                is Resource.Error -> {
                    if(progressshow)
                    {
                        ProgressDialog.hideProgressBar()
                    }
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

        viewModeladdwishlist.addremovewishlist.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    newproductlist[fav_position].fav_status = fav_type
                    adapter.notifyDataSetChanged()
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

    private fun initilize() {
        binding.tvSort.setOnClickListener(this)
        binding.tvFilter.setOnClickListener(this)
        saveSortLocal()
    }

    override fun onClick(v: View?)
    {
        when (v?.id) {
            R.id.tv_filter -> {
                val intent = Intent(requireContext(), FilterActivity::class.java)
                startActivityForResult(intent, 101)
            }
            R.id.tv_sort -> {
                showBottomsheetDialog()
            }
        }
    }

    private fun showBottomsheetDialog()
    {
        val view = layoutInflater.inflate(R.layout.top_selling_bottomsheet_layout, null)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_top_selling_bottomsheetrecyclar)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bottomlist = ArrayList<String>()
        bottomlist.add(getString(R.string.newest_to_oldest))
        bottomlist.add(getString(R.string.oldest_to_newest))
        bottomlist.add(getString(R.string.price_low_to_high))
        bottomlist.add(getString(R.string.price_high_to_low))
        val adapter = ProductListSortingBottomSheetAdapter(bottomlist, this)
        recyclerView.adapter = adapter
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun setupData(mproductlist: ArrayList<Product>)
    {
        mproductlist.let {
            newproductlist.addAll(it)
        }
        if(newproductlist.size > 0) {
            val newList = newproductlist.distinctBy { it.id }
            withpaginationAdapterSet(newList)
            //adapter.updateItems(newList as ArrayList<Product>)
        }
        else { }
    }

    private fun setupRecylarview()
    {
        withpaginationAdapterSet(newproductlist)
        binding.dealsItemRecycler.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                pageOffset=newproductlist.size
                callApi(filter_applied, ProductListRequestModel.Filter(), sort_applied, sort_type)  //api call
            }
        })
    }

    private fun withpaginationAdapterSet(newList: List<Product>) {
        layoutManager = GridLayoutManager(requireContext(),2)
        binding. dealsItemRecycler.layoutManager = layoutManager
        adapter = ProductListAdapters(requireContext(), newList as ArrayList<Product>,
            recyclerViewFavouriteListener)
        binding.dealsItemRecycler.adapter = adapter
    }


    override fun click(pos: String)
    {
        sortclickName=bottomlist[pos.toInt()]
        if(bottomlist[pos.toInt()]  == getString(R.string.newest_to_oldest))
        {
            sort_type=1
        }
        else if(bottomlist[pos.toInt()]  == getString(R.string.oldest_to_newest))
        {
            sort_type=2
        }
        else if(bottomlist[pos.toInt()]  == getString(R.string.price_low_to_high))
        {
            sort_type=3
        }
        else if(bottomlist[pos.toInt()]  == getString(R.string.price_high_to_low))
        {
            sort_type=4
        }
        sort_applied=1
        progressshow=true
        newproductlist.clear()
        pageOffset = 1
        callApi(filter_applied,ProductListRequestModel.Filter(),sort_applied,sort_type) //api call
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 101) {
            selectedColorList = data?.getStringArrayListExtra("colorList") as ArrayList<String>
            selectedAgeList = data.getParcelableArrayListExtra<AgeRequest>("ageList") as ArrayList<AgeRequest>
            selectedSizeList = data.getStringArrayListExtra("sizeList") as ArrayList<String>
            selectedGenderList = data.getStringArrayListExtra("genderList") as ArrayList<String>
            selectedminprice = data.getIntExtra("selectedminprice",0)
            selectedmaxprice = data.getIntExtra("selectedmaxprice",0)

            val filterList = ProductListRequestModel.Filter()
            val priceObj = ProductListRequestModel.Filter.Price()
            newproductlist.clear()
            pageOffset = 1
            newproductlist.clear()
            if(selectedmaxprice != 0) {
                priceObj.from = selectedminprice
                priceObj.to = selectedmaxprice
            }
            filterList.price = priceObj
            filterList.color.addAll( selectedColorList)
            filterList.size.addAll( selectedSizeList)
            filterList.age.addAll( selectedAgeList)
            filterList.gender.addAll( selectedGenderList)
            filter_applied =1
            progressshow=true
            callApi(filter_applied, filterList, sort_applied, sort_type) //api call
        }
    }
   }