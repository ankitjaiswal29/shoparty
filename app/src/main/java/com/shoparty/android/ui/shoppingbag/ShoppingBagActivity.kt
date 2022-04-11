package com.shoparty.android.ui.shoppingbag

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.common_modal.CartProduct
import com.shoparty.android.common_modal.Product
import com.shoparty.android.database.MyDatabase
import com.shoparty.android.databinding.ActivityShopingBagBinding
import com.shoparty.android.interfaces.RVCartItemClickListener
import com.shoparty.android.ui.login.LoginActivity
import com.shoparty.android.ui.main.home.HomeCategoriesModel
import com.shoparty.android.ui.productdetails.ProducatDetailsViewModel
import com.shoparty.android.ui.productdetails.ProductDetailsActivity
import com.shoparty.android.ui.shipping.ShippingActivity
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.apiutils.ViewModalFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingBagActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityShopingBagBinding
    private lateinit var viewModel: ProducatDetailsViewModel

    private lateinit var adapterShoppingBag: ShoppingBagItemAdapter
    private val listCartProduct: ArrayList<CartProduct> = ArrayList()

    private lateinit var cartProduct: CartProduct
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shoping_bag)
        viewModel = ViewModelProvider(
            this,
            ViewModalFactory(application)
        )[ProducatDetailsViewModel::class.java]

        initialise()
    }

    private fun initialise() {
        binding.infoTool.tvTitle.text = getString(R.string.shippingbag)
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.btnProcessTocheckOut.setOnClickListener(this)
        binding.cbPickupBranch.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                binding.bagItemPickupRecycler.visibility = View.VISIBLE
            } else {
                binding.bagItemPickupRecycler.visibility = View.GONE
            }
        }

        shoppingBagList()
        shoppingBagPickup()
    }

    private fun shoppingBagPickup() {
        val bagItemList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Bend the Trend"),
            HomeCategoriesModel("Bend the Trend"),
            HomeCategoriesModel("Bend the Trend")
        )

        binding.bagItemPickupRecycler.adapter = ShopingBagPickupAdapter(bagItemList)
    }

    private fun shoppingBagList() {
        lifecycleScope.launch(Dispatchers.IO) {
            listCartProduct.clear()
            val dbList =
                MyDatabase.getInstance(this@ShoppingBagActivity).getProductDao().getAllCartProduct()
            listCartProduct.addAll(dbList)
        }
        adapterShoppingBag = ShoppingBagItemAdapter(this@ShoppingBagActivity, listCartProduct)
        val gridLayoutManager = GridLayoutManager(this, 1)
        binding.rvShopingitem.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterShoppingBag
        }

        adapterShoppingBag.onItemClick(object : RVCartItemClickListener {
            override fun onClick(pos: Int, view: View?) {
                startActivity(Intent(this@ShoppingBagActivity,ProductDetailsActivity::class.java))
            }

            override fun onPlus(pos: Int, view: View?) {
                lifecycleScope.launch(Dispatchers.IO) {
                    listCartProduct[pos].quantity =
                        (listCartProduct[pos].quantity.toInt() + 1).toString()
                    MyDatabase.getInstance(this@ShoppingBagActivity).getProductDao()
                        .updateCartProduct(listCartProduct[pos])
                    lifecycleScope.launch(Dispatchers.Main) {
                        adapterShoppingBag.notifyDataSetChanged()
                    }
                }

            }

            override fun onMinus(pos: Int, view: View?) {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (listCartProduct.size > 0) {
                        if (listCartProduct[pos].quantity.toInt() >= 2) {
                            listCartProduct[pos].quantity =
                                (listCartProduct[pos].quantity.toInt() - 1).toString()
                            MyDatabase.getInstance(this@ShoppingBagActivity).getProductDao()
                                .updateCartProduct(listCartProduct[pos])
                        } else {
                            MyDatabase.getInstance(this@ShoppingBagActivity).getProductDao()
                                .deleteCartProduct(listCartProduct[pos])
                            listCartProduct.removeAt(pos)
                        }
                        lifecycleScope.launch(Dispatchers.Main) {
                            adapterShoppingBag.notifyDataSetChanged()
                        }
                    }
                }

            }

            override fun onClear(pos: Int, view: View?) {
                lifecycleScope.launch(Dispatchers.IO) {
                    MyDatabase.getInstance(this@ShoppingBagActivity).getProductDao()
                        .deleteCartProduct(listCartProduct[pos])
                    listCartProduct.removeAt(pos)
                    lifecycleScope.launch(Dispatchers.Main) {
                        adapterShoppingBag.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
            R.id.btn_ProcessTocheckOut -> {
                if (PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty()) {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    PrefManager.write(PrefManager.IS_SHIPPING_PAGE, "1")
                    startActivity(intent)
                } else {
                    val intent = Intent(applicationContext, ShippingActivity::class.java)
                    PrefManager.write(PrefManager.IS_SHIPPING_PAGE, "2")
                    startActivity(intent)
                }
            }
        }
    }

}