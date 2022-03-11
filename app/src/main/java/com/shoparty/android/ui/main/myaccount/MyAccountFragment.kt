package com.shoparty.android.ui.main.myaccount

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.shoparty.android.R
import com.shoparty.android.app.MyApp.Companion.application
import com.shoparty.android.databinding.FragmentMyAccountBinding


import com.shoparty.android.ui.main.myaccount.aboutus.AboutUsActivity
import com.shoparty.android.ui.address.addaddress.getaddress.AddressActivity
import com.shoparty.android.ui.contactus.ContactUsActivity
import com.shoparty.android.ui.myorders.myorder.MyOrdersActivity
import com.shoparty.android.ui.main.myaccount.privacypolicy.PrivacyPolicyActivity
import com.shoparty.android.ui.main.myaccount.returnpolicy.ReturnPolicyActivity
import com.shoparty.android.ui.main.myaccount.termandcondition.TermAndConditionActivity
import com.shoparty.android.ui.vouchers.VouchersActivity
import com.shoparty.android.ui.wishlist.WishListActivity
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.main.myaccount.getprofile.GetProfileResponse
import com.shoparty.android.ui.main.myaccount.myprofileupdate.MyProfileActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.PrefManager.clearAllPref
import com.shoparty.android.utils.Utils
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class MyAccountFragment : Fragment(), RecyclerViewClickListener {

    private lateinit var binding: FragmentMyAccountBinding
    private lateinit var myaccountAdapter: MyAccountAdapter
    var dialog: Dialog? = null
    private lateinit var viewModel: MyAccountViewModel
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onAttach(activity: Activity) {
        super.onAttach(activity!!)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
           binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false)
           viewModel = ViewModelProvider(this, ViewModalFactory(application))[MyAccountViewModel::class.java]
           setObserver()
           return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiCall()
    }

    private fun apiCall()
    {
        viewModel.getProfle()      //api call
        dataaddsetAdapter()
    }

    private fun setObserver()
    {
        viewModel.logout.observe(requireActivity(), { response ->
            when (response)
            {
                is Resource.Success -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()

                    clearAllPref()
                    dialog!!.dismiss()

                    val intent = Intent(activity, MainActivity::class.java)
                    activity?.startActivity(intent)

                    Toast.makeText(
                        requireActivity(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    com.shoparty.android.utils.ProgressDialog.showProgressBar(requireContext())
                }
                is Resource.Error -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireActivity(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireActivity(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })


        viewModel.getprofile.observe(requireActivity(), { response ->
            when (response)
            {
                is Resource.Success -> {
                  //  com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    setupUI(response.data)
                }

                is Resource.Loading -> {
                  //  com.shoparty.android.utils.ProgressDialog.showProgressBar(requireContext())
                }
                is Resource.Error -> {
                 //   com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireActivity(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                //   com.shoparty.android.utils.ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireActivity(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }



    private fun setupUI(data: GetProfileResponse.User?)
    {
        Glide.with(context!!).load(data?.image).error(R.drawable.person_img).into(binding.imgProfile)
        binding.tvName.text = data?.name?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        binding.tvMobile.text = data?.mobile.toString()

        PrefManager.write(PrefManager.NAME, binding.tvName.text.toString())
        PrefManager.write(PrefManager.IMAGE,data?.image.toString())
        PrefManager.write(PrefManager.MOBILE, data?.mobile.toString())
        PrefManager.write(PrefManager.EMAIL, data?.email.toString())
        PrefManager.write(PrefManager.DOB, data?.dob.toString())
        PrefManager.write(PrefManager.GENDER, data?.gender.toString())
       /* PrefManager.write(PrefManager.STREET, data?..toString())
        PrefManager.write(PrefManager.HOUSENO, data?.gender.toString())*/

    }


    private fun dataaddsetAdapter()
    {
        val data = ArrayList<MyAccountCustomModel>()
        data.add(MyAccountCustomModel(R.drawable.ic_myorder_icon,"idmyorder",getString(R.string.my_order) ))
        data.add(MyAccountCustomModel(R.drawable.ic_vouchers_icon,"idvoucher",getString(R.string.vouchers) ))
        data.add(MyAccountCustomModel(R.drawable.ic_wishlist,"idwishlist",getString(R.string.wishlist)))
        data.add(MyAccountCustomModel(R.drawable.ic_myaccountlist_icon,"idmyprofile",getString(R.string.my_profile) ))
        data.add(MyAccountCustomModel(R.drawable.ic_address_icon,"idaddress",getString(R.string.address_book) ))
        data.add(MyAccountCustomModel(R.drawable.ic_rate_our_icon,"idrate",getString(R.string.rate_our_app) ))
        data.add(MyAccountCustomModel(R.drawable.ic_contact_icon,"idcontact",getString(R.string.contact_us) ))
        data.add(MyAccountCustomModel(R.drawable.ic_aboutus_icon,"idabout",getString(R.string.about_us) ))
        data.add(MyAccountCustomModel(R.drawable.ic_term_and_conditon_icon,"idtermcondition",getString(R.string.terms_and_conditions) ))
        data.add(MyAccountCustomModel(R.drawable.ic_privacy_policy_icon,"iprivacypolicy",getString(R.string.privacy_policy) ))
        data.add(MyAccountCustomModel(R.drawable.ic_return_policy_icon,"idreturnpolicy",getString(R.string.return_policy) ))

        if(PrefManager.read(PrefManager.AUTH_TOKEN, "").isEmpty())
        { }
        else
        {
            data.add(MyAccountCustomModel(R.drawable.ic_sign_out_icon,"idsignout",getString(R.string.sign_out) ))
        }
        myaccountAdapter = MyAccountAdapter(requireContext(),data,this)
        binding.recyProfile.layoutManager = LinearLayoutManager(requireContext())
        binding.recyProfile.adapter = myaccountAdapter
    }


    override fun click(pos: String) {
        when(pos) {
            "idmyorder" ->{
                val intent = Intent (getActivity(), MyOrdersActivity::class.java)
                getActivity()?.startActivity(intent)
            }


            "idvoucher" ->{
                val intent = Intent (getActivity(), VouchersActivity::class.java)
                getActivity()?.startActivity(intent)
            }

            "idwishlist" -> {
                val intent = Intent (getActivity(), WishListActivity::class.java)
                getActivity()?.startActivity(intent)
            }


            "idmyprofile" -> {
                if(PrefManager.read(PrefManager.AUTH_TOKEN,"").isEmpty())
                {
                    Utils.showShortToast(activity,getString(R.string.pleaselogin))
                }
                else
                {
                    val intent = Intent (activity, MyProfileActivity::class.java)
                    startActivityForResult(intent, Constants.EDIT_PROFILE_CODE)
                }
            }
            "idaddress" ->
            {
                val intent = Intent (getActivity(), AddressActivity::class.java)
                getActivity()?.startActivity(intent)
            }
            "idrate" -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(Constants.MARKETPLACE+Constants.PACKAGENAME)
                try
                {
                    startActivity(intent)
                } catch (e: Exception) {
                    intent.data =
                        Uri.parse(Constants.PLAYSTORE + Constants.PACKAGENAME)
                }
            }
            "idcontact" ->{
                val intent = Intent (activity, ContactUsActivity::class.java)
                getActivity()?.startActivity(intent)
            }
            "idabout" ->{
                val intent = Intent (getActivity(), AboutUsActivity::class.java)
                getActivity()?.startActivity(intent)
            }

            "idtermcondition" -> {
                val intent = Intent (getActivity(), TermAndConditionActivity::class.java)
                getActivity()?.startActivity(intent)
            }

            "iprivacypolicy" ->{
                val intent = Intent (getActivity(), PrivacyPolicyActivity::class.java)
                getActivity()?.startActivity(intent)
            }
            "idreturnpolicy" ->{
                val intent = Intent (activity, ReturnPolicyActivity::class.java)
                getActivity()?.startActivity(intent)
            }
            "idsignout" ->{
                opendialog()
            }
        }
    }

    fun opendialog()
    {
        dialog = Dialog(requireContext())
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog!!.setContentView(R.layout.alert_dialog_signout2)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog!!.getWindow()?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog!!.window?.attributes = lp
        dialog!!.setCanceledOnTouchOutside(true)
        val btn_cancel = dialog!!.findViewById<Button>(R.id.btn_cancel)
        val btn_yes = dialog!!.findViewById<Button>(R.id.btn_yes)
        btn_yes.setOnClickListener {
            viewModel.postLogout()  //api call
        }
        btn_cancel.setOnClickListener {
            dialog!!.dismiss()
        }
        dialog!!.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.EDIT_PROFILE_CODE && resultCode == Activity.RESULT_OK)
        {
            Glide.with(this).load(PrefManager.read(PrefManager.IMAGE,"")).error(R.drawable.person_img).into(binding.imgProfile)
            binding.tvMobile.text = PrefManager.read(PrefManager.MOBILE,"")
            binding.tvName.text = PrefManager.read(PrefManager.NAME,"")
        }
    }


}