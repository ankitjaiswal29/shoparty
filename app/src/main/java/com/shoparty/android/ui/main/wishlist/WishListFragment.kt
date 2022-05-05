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
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.FragmentWishListBinding
import com.shoparty.android.interfaces.RecyclerViewFavouriteListener
import com.shoparty.android.interfaces.WishListAddBagClickListener
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.productdetails.ProducatDetailsViewModel
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import okhttp3.MultipartBody

class WishListFragment : Fragment(), RecyclerViewFavouriteListener, WishListAddBagClickListener {
    private val comment: String=""
    private var operationalPos: Int = -1
    private lateinit var binding: FragmentWishListBinding
    private lateinit var viewModel: WishListViewModel
    private lateinit var productdetailsViewModel: ProducatDetailsViewModel
    private lateinit var adapterWishlist: WishListAdapters
    private var listWishlistt: ArrayList<WishListResponse.Result> = ArrayList()
    var quantity: Int = 0
    private var position: Int = 0
    private var action_type: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).manageUi(ivLogo = true, ivBag = true, ivSearch = true)
        viewModel.getWishlist(PrefManager.read(PrefManager.LANGUAGEID, 1).toString())  //api call
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wish_list, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModalFactory(activity?.application!!)
        )[WishListViewModel::class.java]
        productdetailsViewModel = ViewModelProvider(
            this,
            ViewModalFactory(activity?.application!!)
        )[ProducatDetailsViewModel::class.java]
        setObserver()
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
                    listWishlistt = response.data!! as ArrayList<WishListResponse.Result>
                    setwishlistAdapter()
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

                    if (listWishlistt.isNotEmpty()) {
                        listWishlistt.removeAt(operationalPos)
                        adapterWishlist.notifyItemRemoved(operationalPos)
                        if (listWishlistt.isEmpty()) {
                            setwishlistAdapter()
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


        productdetailsViewModel.addbag.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    adapterWishlist.updateData(position, quantity)
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

    private fun setwishlistAdapter() {
        if (listWishlistt.isNullOrEmpty()) {
            binding.clNoData.visibility = View.VISIBLE
            binding.wishlistRecyclerview.visibility = View.GONE
        } else {
            binding.clNoData.visibility = View.GONE
            binding.wishlistRecyclerview.visibility = View.VISIBLE
            adapterWishlist = WishListAdapters(requireContext(), listWishlistt!!, this, this)
            binding.wishlistRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            binding.wishlistRecyclerview.adapter = adapterWishlist
        }
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
        viewModel.addremoveWishlist(
            producat_id,
            type.toInt(),
            product_detail_id.toInt(),
            product_sizeId,
            product_colorId
        )
    }


    override fun twoParameterPassClick(pos: Int, actiontype: Int) {
        if (PrefManager.read(PrefManager.AUTH_TOKEN, "") == "") {
            /* lifecycleScope.launch(Dispatchers.IO) {
                 MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                     .insertCartProduct(CartProduct(productDetails.en_name, productDetails.id.toString(), productDetails.images[0].image.toString(), "1"))

                 MyDatabase.getInstance(this@ProductDetailsActivity).getProductDao()
                     .insertCartProduct(CartProduct(productDetails.en_name, productDetails.id.toString(), productDetails.images[0].image.toString(), "1"))

                 val intent = Intent(this@ProductDetailsActivity, ShoppingBagActivity::class.java)
                 startActivity(intent)
             }*/
        } else {

            position = pos
            action_type = actiontype
            quantity = listWishlistt[pos].cart_quantity!!

            if (action_type == Constants.CARTACTIONMINUSTYPE)   //for minus
            {
                if (listWishlistt[pos].is_customizable == 1) {
                    val intent =
                        Intent(requireContext(), ProductDetailsActivity::class.java)
                    intent.putExtra(
                        Constants.IDPRODUCT,
                        listWishlistt[pos].product_id.toString()
                    )
                    intent.putExtra(
                        Constants.PRODUCATDETAILSID,
                        listWishlistt[pos].product_detail_id.toString()
                    )
                    intent.putExtra(Constants.PRODUCATNAME, listWishlistt[pos].product_name)
                    intent.putExtra(
                        Constants.PRODUCTSIZEID,
                        listWishlistt[pos].product_size_id.toString()
                    )
                    intent.putExtra(
                        Constants.PRODUCTCOLORID,
                        listWishlistt[pos].product_color_id.toString()
                    )
                    startActivity(intent)
                } else {
                    quantity -= 1
                    if (listWishlistt[pos].cart_quantity != 0) {
                        addToBagApi(pos)
                    }
                }
            } else {
                if (listWishlistt[pos].is_customizable == 1) {
                    val intent =
                        Intent(requireContext(), ProductDetailsActivity::class.java)
                    intent.putExtra(
                        Constants.IDPRODUCT,
                        listWishlistt[pos].product_id.toString()
                    )
                    intent.putExtra(
                        Constants.PRODUCATDETAILSID,
                        listWishlistt[pos].product_detail_id.toString()
                    )
                    intent.putExtra(Constants.PRODUCATNAME, listWishlistt[pos].product_name)
                    intent.putExtra(
                        Constants.PRODUCTSIZEID,
                        listWishlistt[pos].product_size_id.toString()
                    )
                    intent.putExtra(
                        Constants.PRODUCTCOLORID,
                        listWishlistt[pos].product_color_id.toString()
                    )
                    startActivity(intent)
                } else {
                    quantity += 1
                    addToBagApi(pos)
                }
            }
        }
    }

    private fun addToBagApi(pos: Int) {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        if(listWishlistt[pos].is_customizable.toString() == "0")
        {
            builder.addFormDataPart(
                "customized_image",listWishlistt[pos].image)
        }
        else
        {
            builder.addFormDataPart(
                "customized_image",listWishlistt[pos].image)
        }
        builder.addFormDataPart("is_customizable", listWishlistt[pos].is_customizable.toString())
        builder.addFormDataPart("comment",comment)
        builder.addFormDataPart("product_id", listWishlistt[pos].product_id.toString())
        builder.addFormDataPart(
            "product_detail_id",
            listWishlistt[pos].product_detail_id.toString()
        )
        builder.addFormDataPart("product_size_id", listWishlistt[pos].product_size_id.toString())
        builder.addFormDataPart("product_color_id", listWishlistt[pos].product_color_id.toString())
        builder.addFormDataPart("quantity", quantity.toString())
        builder.addFormDataPart("price", listWishlistt[pos].sale_price)
        val body = builder.build()
        productdetailsViewModel.postAddProduct(body)
    }
}