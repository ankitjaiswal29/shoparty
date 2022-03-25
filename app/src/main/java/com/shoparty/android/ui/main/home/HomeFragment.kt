package com.shoparty.android.ui.main.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.app.MyApp
import com.shoparty.android.databinding.FragmentHomeBinding
import com.shoparty.android.interfaces.RecyclerViewItemClickListener
import com.shoparty.android.ui.contactus.ContactUsViewModel
import com.shoparty.android.ui.contactus.WebViewActivity
import com.shoparty.android.ui.main.deals.TopSellingHomeModel
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.main.product_list.ProductListActivity
import com.shoparty.android.ui.search.SearchActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import kotlinx.android.synthetic.main.activity_contact_us.*
import kotlinx.android.synthetic.main.dashboard_toolbar.*
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModel_contactus: ContactUsViewModel
    private var facebookurl: String?=""
    private var twitter_url: String?=""
    private var youtube_url: String?=""
    private var phone_number: String?=""
    private var whatsapp: String?=""
    private var instagram_url: String?=""

    private val listTopBanner: ArrayList<HomeResponse.Home.Banner> = ArrayList()
    private val listMiddleBanner: ArrayList<HomeResponse.Home.Banner> = ArrayList()
    private val listBottomBanner: ArrayList<HomeResponse.Home.Banner> = ArrayList()
    private lateinit var adapterBannerMiddle: BannerAdapter
    private lateinit var adapterBannerBottom: BannerAdapter

    private val listCategory: ArrayList<HomeResponse.Home.Category> = ArrayList()
    private lateinit var adapterCategory: HomeCategoriesAdapter

    private lateinit var adapterBrands: BrandsAdapter
    private val listBrand: ArrayList<HomeResponse.Home.Brand> = ArrayList()

    private lateinit var adapterSeason: HomeSeasonsAdapter
    private val listSeason: ArrayList<HomeResponse.Home.Season> = ArrayList()

    private lateinit var adapterTheme: ThemeAdapter
    private val listTheme: ArrayList<HomeResponse.Home.Theme> = ArrayList()

    private lateinit var adapterArrival: NewArrivalsHomeAdapter
    private val listArrival: ArrayList<HomeResponse.Home.Arrival> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, com.shoparty.android.R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModalFactory(activity?.application!!)
        )[HomeViewModel::class.java]
        viewModel_contactus = ViewModelProvider(this, ViewModalFactory(MyApp.application))[ContactUsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         initialise()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).manageUi(ivLogo = true, ivBag = true)
    }

    private fun initialise() {
        binding.tvSearch.setOnClickListener(this)
        binding.ivFacebook.setOnClickListener(this)
        binding.ivTwitter.setOnClickListener(this)
        binding.ivYoutube.setOnClickListener(this)
        binding.txtPhone.setOnClickListener(this)
        binding.txtinstagram.setOnClickListener(this)
        binding.txtEmail.setOnClickListener(this)
        binding.txtwhatsapp.setOnClickListener(this)

        setTopSelling()
        setBanner2()
        setHomeCategory()
        season()
        setTheme()
        setBanner3()
        setNewArrival()
        TopsellingSubcategories()
        OfferDiscoutItem()
        setBrands()

        setObserver()
        val request = HomeRequestModel("1")
        viewModel.getDashboardData(request)
        viewModel_contactus.getContactus()
    }

    private fun setObserver() {
        viewModel.dashboardResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    try {
                        ProgressDialog.hideProgressBar()
                        listTopBanner.clear()
                        listTopBanner.addAll(response.data?.top_banner!! as ArrayList<HomeResponse.Home.Banner>)
                        setImageInSlider(response.data?.top_banner!! as ArrayList<HomeResponse.Home.Banner>)

                        listMiddleBanner.clear()
                        listMiddleBanner.addAll(response.data?.bottom_banner!! as ArrayList<HomeResponse.Home.Banner>)
                        adapterBannerMiddle.notifyDataSetChanged()

                        listBottomBanner.clear()
                        listBottomBanner.addAll(response.data?.upcoming_banner!! as ArrayList<HomeResponse.Home.Banner>)
                        adapterBannerBottom.notifyDataSetChanged()

                        listBrand.clear()
                        listBrand.addAll(response.data?.brand_response!! as ArrayList<HomeResponse.Home.Brand>)
                        adapterBrands.notifyDataSetChanged()

                        listCategory.clear()
                        listCategory.addAll(response.data?.category_response!! as ArrayList<HomeResponse.Home.Category>)
                        adapterCategory.notifyDataSetChanged()

                        listSeason.clear()
                        listSeason.addAll(response.data?.season_response!! as ArrayList<HomeResponse.Home.Season>)
                        adapterSeason.notifyDataSetChanged()

                        listTheme.clear()
                        listTheme.addAll(response.data?.theame_response!! as ArrayList<HomeResponse.Home.Theme>)
                        adapterTheme.notifyDataSetChanged()

                        listArrival.clear()
                        listArrival.addAll(response.data?.arrival_response!! as ArrayList<HomeResponse.Home.Arrival>)
                        adapterArrival.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
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

        viewModel_contactus.contactus.observe(this, { response ->
            when (response)
            {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    facebookurl=response.data?.facebook_url
                    twitter_url=response.data?.twitter_url
                    youtube_url=response.data?.youtube_url
                    phone_number=response.data?.contact_no
                    whatsapp=response.data?.whatsapp_no
                    instagram_url=response.data?.instagram_url

                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(requireActivity())
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                }
                else -> {
                    ProgressDialog.hideProgressBar()
                }
            }
        })
    }



    private fun setImageInSlider(list: ArrayList<HomeResponse.Home.Banner>) {
        val adapter = MySliderImageAdapter(requireContext())
          adapter.renewItems(list)
        binding.imageSlider.setSliderAdapter(adapter)
        binding.imageSlider.isAutoCycle = true
        binding.imageSlider.startAutoCycle()
    }

    private fun setTopSelling() {
        val topSellingItemList = listOf<TopSellingHomeModel>(
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
        )
        binding.topSellingRecycler.adapter =
            TopSellingHomeAdapter(topSellingItemList, requireContext())
    }

    private fun setBanner2() {
        adapterBannerMiddle = BannerAdapter(listMiddleBanner, requireContext())
        val gridLayoutManager =
            GridLayoutManager(requireActivity(), 1, RecyclerView.HORIZONTAL, false)
        binding.rvBanner2.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterBannerMiddle
        }
        adapterBannerMiddle.onItemClick(object : RecyclerViewItemClickListener {
            override fun onClick(pos: String, view: View?) {
                //TODO("Not yet implemented")
            }
        })
    }

    private fun setHomeCategory() {
        val gridLayoutManager =
            GridLayoutManager(requireActivity(), 1, RecyclerView.HORIZONTAL, false)
        adapterCategory = HomeCategoriesAdapter(listCategory, requireContext())

        binding.rvCategories.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterCategory
        }
        adapterCategory.onItemClick(object : RecyclerViewItemClickListener {
            override fun onClick(pos: String, view: View?) {
                //TODO("Not yet implemented")
            }
        })
    }

    private fun season() {
        adapterSeason = HomeSeasonsAdapter(listSeason, requireContext())
        val gridLayoutManager =
            GridLayoutManager(requireActivity(), 1, RecyclerView.HORIZONTAL, false)
        binding.rvSeason.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterSeason
        }
        adapterSeason.onItemClick(object : RecyclerViewItemClickListener {
            override fun onClick(pos: String, view: View?) {
                //TODO("Not yet implemented")
            }
        })
    }

    private fun setTheme() {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        adapterTheme = ThemeAdapter(listTheme, requireContext())
        binding.rvThemes.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterTheme
        }
    }

    private fun setBanner3() {
        adapterBannerBottom = BannerAdapter(listBottomBanner, requireContext())
        val gridLayoutManager =
            GridLayoutManager(requireActivity(), 1, RecyclerView.HORIZONTAL, false)
        binding.rvBanner3.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterBannerBottom
        }
        adapterBannerBottom.onItemClick(object : RecyclerViewItemClickListener {
            override fun onClick(pos: String, view: View?) {
                //TODO("Not yet implemented")
            }
        })
    }

    private fun setNewArrival() {
        adapterArrival = NewArrivalsHomeAdapter(listArrival, requireContext())
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.homeNewArrivalsRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterArrival
        }
        adapterArrival.onItemClick(object : RecyclerViewItemClickListener {
            override fun onClick(pos: String, view: View?) {
                //TODO("Not yet implemented")
            }
        })
    }

    private fun setBrands() {
        adapterBrands = BrandsAdapter(listBrand, requireContext())
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvBrands.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterBrands
        }
    }

    private fun OfferDiscoutItem() {
        val homeOffersList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Up To 10% Off"),
            HomeCategoriesModel("Up To 10% Off")
        )

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.homeOffersRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = HomeOffersAdapter(homeOffersList)
        }
    }

    private fun TopsellingSubcategories() {
        val tsSubCategoriesList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Mask"),
            HomeCategoriesModel("Mask"),
            HomeCategoriesModel("Mask"),
            HomeCategoriesModel("Mask")
        )

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.tsSubcategoriesRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = TopSellingSubcategoriesAdapter(tsSubCategoriesList)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            com.shoparty.android.R.id.tvSearch -> {
                val intent = Intent(activity, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.tvViewAllTopSelling -> {
                val intent = Intent(activity, ProductListActivity::class.java)
                intent.putExtra(Constants.PRODUCTID,"")
                intent.putExtra(Constants.TOP20SELLING,"2")
                startActivity(intent)
            }
            R.id.ivFacebook -> {
                val intent = Intent (requireActivity(), WebViewActivity::class.java)
                intent.putExtra(Constants.LINKSTATUS,"1")
                intent.putExtra(Constants.FACEBOOKLINK,facebookurl)
                startActivity(intent)
            }
            R.id.ivTwitter -> {
                val intent = Intent (requireActivity(), WebViewActivity::class.java)
                intent.putExtra(Constants.LINKSTATUS,"2")
                intent.putExtra(Constants.TWITTERLINK,twitter_url)
                startActivity(intent)
            }
            R.id.ivYoutube -> {
                val intent = Intent (requireActivity(), WebViewActivity::class.java)
                intent.putExtra(Constants.LINKSTATUS,"3")
                intent.putExtra(Constants.YOUTUBELINK,youtube_url)
                startActivity(intent)
            }
            R.id.txtinstagram -> {
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra(Constants.LINKSTATUS,"4")
                intent.putExtra(Constants.INSTAGRAMELINK,instagram_url)
                startActivity(intent)
            }
            R.id.txtPhone -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phone_number")
                startActivity(intent)
            }
            R.id.txtwhatsapp-> {
                openWhatsAppConversation(whatsapp.toString(),"hi")
            }
            R.id.txtEmail -> {
               Utils.showLongToast(requireContext(),context?.getString(R.string.comingsoon))
            }



        }
    }

    private fun openWhatsAppConversation(number: String, message: String?) {
        var number = number
        number = number.replace(" ", "").replace("+", "")
        val sendIntent = Intent("android.intent.action.MAIN")
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net")
        try {
            startActivity(sendIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(requireContext(),
                "Whatsapp have not been installed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}