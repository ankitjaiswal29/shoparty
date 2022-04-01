package com.shoparty.android.ui.main.deals

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
import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import com.shoparty.android.ui.filter.*
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.main.product_list.ProductListAdapters
import com.shoparty.android.ui.main.product_list.ProductListSortingBottomSheetAdapter
import com.shoparty.android.ui.main.wishlist.WishListViewModel
import com.shoparty.android.utils.*
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class DealsFragment : Fragment(),View.OnClickListener {
    lateinit var binding: FragmentDealsBinding
    lateinit var dialog: BottomSheetDialog
    private lateinit var viewModel: DealsViewModel
    private lateinit var viewModeladdwishlist: WishListViewModel
    private var newproductlist: ArrayList<Product> = ArrayList()
    var color = false
    var size = false
    var age = false
    var gender = false
    private lateinit var adapter:ProductListAdapters
    var pageOffset=0
    var pageLimit=10
    var fav_position:Int = 0
    var fav_type:Int = 0

    private var recyclerViewFavouriteListener=object :RecyclerViewFavouriteListener{
          override fun favourite(
              position: Int,
              producat_id: String,
              type: String,
              product_detail_id: String)
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
                viewModeladdwishlist.addremoveWishlist(producat_id,type.toInt(),product_detail_id.toInt())
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
        callApi() //api call
    }

       private fun callApi()
       {
           val request = DealsRequestModel("1",
               pageOffset.toString(),
               pageLimit.toString(),PrefManager.read(PrefManager.USER_ID, ""))
               viewModel.getDeals(request)
       }

    private fun setObserver() {
        viewModel.deals.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    val productlist = response.data as ArrayList<Product>
                    if(productlist.isNullOrEmpty() && newproductlist.isNullOrEmpty())
                    {
                        binding.linearNoData.visibility= View.VISIBLE
                        binding.dealsItemRecycler.visibility= View.GONE
                    }
                    else
                    {
                        binding.linearNoData.visibility= View.GONE
                        binding.dealsItemRecycler.visibility= View.VISIBLE
                        setupData(productlist)
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

        viewModeladdwishlist.addremovewishlist.observe(this, { response ->
            when (response) {
                is Resource.Success -> {
                    //  com.shoparty.android.utils.ProgressDialog.hideProgressBar()

                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    newproductlist[fav_position].fav_status=fav_type
                    adapter.notifyDataSetChanged()
                }
                is Resource.Loading -> {
                    //   com.shoparty.android.utils.ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    //  com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    //  com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }



    private fun initilize() {
        binding.tvSort.setOnClickListener(this)
        binding.tvFilter.setOnClickListener(this)
    }





    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_filter -> {
                val intent = Intent(requireContext(), FilterActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_sort -> {
                showBottomsheetDialog()
            }
        }
    }

    private fun showBottomsheetDialog() {
        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.top_selling_bottomsheet_layout, null)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        // on below line we are creating a variable for our button
        // which we are using to dismiss our dialog.
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_top_selling_bottomsheetrecyclar)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val data = ArrayList<String>()
        data.add("Newest To Oldest")
        data.add("Oldest To Newest")
        data.add("Price - Low To High")
        data.add("Price - High To Low")
        val adapter = ProductListSortingBottomSheetAdapter(data)
        recyclerView.adapter = adapter

        // below line is use to set cancelable to avoid
        // closing of dialog box when clicking on the screen.
        dialog.setCancelable(true)

        // on below line we are setting
        // content view to our view.
        dialog.setContentView(view)

        // on below line we are calling
        // a show method to display a dialog.
        dialog.show()
    }


    private fun setupData(mproductlist: ArrayList<Product>)
    {
        mproductlist.let {
            newproductlist.addAll(it)
        }
        if(newproductlist.size>0){
            var newList = newproductlist.distinctBy { it.id}
            adapter.updateItems(newList as ArrayList<Product>)
            adapter.notifyDataSetChanged()
            //   binding.rvContestLeaderBoard.visibility = View.VISIBLE
            //   binding.noResult.visibility = View.GONE
        }else{
            //   binding.rvContestLeaderBoard.visibility = View.GONE
            //   binding.noResult.visibility = View.VISIBLE
        }
    }

    private fun setupRecylarview()
    {
        val layoutManager = GridLayoutManager(requireContext(),2)
        binding. dealsItemRecycler.layoutManager = layoutManager
        adapter = ProductListAdapters(requireContext(), newproductlist,recyclerViewFavouriteListener)
        binding.dealsItemRecycler.adapter = adapter

        binding.dealsItemRecycler.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                pageOffset=newproductlist.size
                callApi()  //api call
                /*  if (listSize > productlist.size) {
                 }*/
            }
        })
    }
   }


