package com.shoparty.android.ui.myaccount

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import com.shoparty.android.R
import com.shoparty.android.ui.aboutus.AboutUsActivity
import com.shoparty.android.ui.address.addaddress.getaddress.AddressActivity
import com.shoparty.android.ui.contactus.ContactUsActivity
import com.shoparty.android.ui.myorders.myorder.MyOrdersActivity
import com.shoparty.android.ui.privacypolicy.PrivacyPolicyActivity
import com.shoparty.android.ui.returnpolicy.ReturnPolicyActivity
import com.shoparty.android.ui.termsandcondition.TermAndConditionActivity
import com.shoparty.android.ui.vouchers.VouchersActivity
import com.shoparty.android.ui.wishlist.WishListActivity
import com.shoparty.android.ui.main.myaccount.MyAccountAdapter
import com.shoparty.android.interfaces.RecyclerViewClickListener


class MyAccountActivity : AppCompatActivity(), RecyclerViewClickListener {
    private lateinit var myaccountAdapter: MyAccountAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<MyAccountModel>()
        data.add(MyAccountModel(R.drawable.ic_myorder_icon,"idmyorder",getString(R.string.my_order) ))
        data.add(MyAccountModel(R.drawable.ic_vouchers_icon,"idvoucher",getString(R.string.vouchers) ))

        data.add(MyAccountModel(R.drawable.ic_wishlist_icon,"idwishlist",getString(R.string.wishlist) ))
        data.add(MyAccountModel(R.drawable.ic_myprofile_icon,"idmyprofile",getString(R.string.my_profile) ))

        data.add(MyAccountModel(R.drawable.ic_address_icon,"idaddress",getString(R.string.address_book) ))
        data.add(MyAccountModel(R.drawable.ic_rate_our_icon,"idrate",getString(R.string.rate_our_app) ))


        data.add(MyAccountModel(R.drawable.ic_contact_icon,"idcontact",getString(R.string.contact_us) ))
        data.add(MyAccountModel(R.drawable.ic_aboutus_icon,"idabout",getString(R.string.about_us) ))

        data.add(MyAccountModel(R.drawable.ic_term_and_conditon_icon,"idtermcondition",getString(R.string.terms_and_conditions) ))
        data.add(MyAccountModel(R.drawable.ic_privacy_policy_icon,"iprivacypolicy",getString(R.string.privacy_policy) ))

        data.add(MyAccountModel(R.drawable.ic_return_policy_icon,"idreturnpolicy",getString(R.string.return_policy) ))
        data.add(MyAccountModel(R.drawable.ic_sign_out_icon,"idsignout",getString(R.string.sign_out) ))



    }


    override fun click(pos: String) {
        when(pos) {
            "idmyorder" -> startActivity(Intent(this, MyOrdersActivity::class.java))
            "idvoucher" ->startActivity(Intent(this, VouchersActivity::class.java))
            "idwishlist" -> startActivity(Intent(this, WishListActivity::class.java))

            "idmyprofile" -> startActivity(Intent(this, MyProfileActivity::class.java))
            "idaddress" ->startActivity(Intent(this, AddressActivity::class.java))
          //  "idrate" -> startActivity(Intent(this, Ra::class.java))
            "idcontact" -> startActivity(Intent(this, ContactUsActivity::class.java))
            "idabout" ->startActivity(Intent(this, AboutUsActivity::class.java))

            "idtermcondition" -> startActivity(Intent(this, TermAndConditionActivity::class.java))
            "iprivacypolicy" ->startActivity(Intent(this, PrivacyPolicyActivity::class.java))

            "idreturnpolicy" -> startActivity(Intent(this, ReturnPolicyActivity::class.java))
            "idsignout" ->{
                val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
                val inflater = layoutInflater
                val dialogLayout: View =
                    inflater.inflate(R.layout.alert_dialog_signout2, null)
                val btn_cancel = dialogLayout.findViewById<Button>(R.id.btn_cancel)
                val btn_yes = dialogLayout.findViewById<Button>(R.id.btn_yes)
                builder.setView(dialogLayout)
                builder.show()

                btn_yes.setOnClickListener {

                }
                btn_cancel.setOnClickListener {

                }
               /* builder.setView(dialogLayout)
                builder.show()*/
            }


        }
    }

    /* override fun itemclick(position: String) {

     }*/


}