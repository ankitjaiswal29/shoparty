package com.shoparty.android.ui.main.deals


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.shoparty.android.interfaces.RecyclerViewItemClickListener
import com.shoparty.android.ui.filter.*
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.categories.NewArrivalItemLIstAdapter
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.main.product_list.ProductListAdapters
import com.shoparty.android.ui.main.product_list.ProductListResponse
import com.shoparty.android.ui.main.product_list.ProductListSortingBottomSheetAdapter
import com.shoparty.android.ui.main.wishlist.WishListViewModel
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.utils.*
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import kotlinx.android.synthetic.main.bottomsheet_filter_layout.view.*
import kotlinx.android.synthetic.main.fragment_deals.*

class DealsFragment : Fragment(),View.OnClickListener
   {
    lateinit var binding: FragmentDealsBinding
    lateinit var dialog: BottomSheetDialog
    private lateinit var viewModel: DealsViewModel
    private lateinit var viewModeladdwishlist: WishListViewModel
    private var productlist: ArrayList<Product> = ArrayList()
    var color = false
    var size = false
    var age = false
    var gender = false
    var price = false


    private var recyclerViewFavouriteListener=object :RecyclerViewFavouriteListener{
        override fun favourite(producat_id: String, type: String, product_detail_id: String)
        {
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
        val request = DealsRequestModel("1", "0", "10",PrefManager.read(PrefManager.USER_ID, ""))
        viewModel.getDeals(request)

    }

    private fun setObserver() {

        viewModel.deals.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    productlist.clear()
                    productlist = response.data as ArrayList<Product>
                    if(productlist.isNullOrEmpty())
                    {
                        binding.linearNoData.visibility= View.VISIBLE
                        binding.dealsItemRecycler.visibility= View.GONE
                    }
                    else
                    {
                        binding.linearNoData.visibility= View.GONE
                        binding.dealsItemRecycler.visibility= View.VISIBLE
                        setProductListAdapter(productlist)
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
                    val request = DealsRequestModel("1", "0", "10",PrefManager.read(PrefManager.USER_ID, ""))
                    viewModel.getDeals(request)
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

    private fun setProductListAdapter(data: ArrayList<Product>) {
           val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            deals_item_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = ProductListAdapters(requireContext(),data!!,recyclerViewFavouriteListener)
        }

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



}


