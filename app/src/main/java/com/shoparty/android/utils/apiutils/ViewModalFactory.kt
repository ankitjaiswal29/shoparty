package com.shoparty.android.utils.apiutils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shoparty.android.ui.address.addaddress.AddressViewModel
import com.shoparty.android.ui.contactus.ContactUsViewModel
import com.shoparty.android.ui.filter.FilterViewModel
import com.shoparty.android.ui.login.LoginViewModel
import com.shoparty.android.ui.main.categories.CategoryViewModel
import com.shoparty.android.ui.main.deals.DealsViewModel
import com.shoparty.android.ui.main.home.HomeViewModel
import com.shoparty.android.ui.main.mainactivity.MainViewModal
import com.shoparty.android.ui.main.myaccount.MyAccountViewModel
import com.shoparty.android.ui.main.product_list.ProductListViewModel
import com.shoparty.android.ui.main.wishlist.WishListViewModel
import com.shoparty.android.ui.myorders.MyOrderViewModel
import com.shoparty.android.ui.notification.NotificationListViewModel
import com.shoparty.android.ui.payment.orderplaced.OrderPlacedViewModel
import com.shoparty.android.ui.productdetails.ProducatDetailsViewModel
import com.shoparty.android.ui.register.RegisterViewModel
import com.shoparty.android.ui.search.SearchViewModel
import com.shoparty.android.ui.shoppingbag.ShoppingBagViewModel
import com.shoparty.android.ui.verificationotp.VerifiyViewModel
import com.shoparty.android.ui.vouchers.VoucherViewModel

class ViewModalFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(app) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(VerifiyViewModel::class.java)) {
            return VerifiyViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(MyAccountViewModel::class.java)) {
            return MyAccountViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(AddressViewModel::class.java)) {
            return AddressViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(app) as T
        }
        if (modelClass.isAssignableFrom(MyOrderViewModel::class.java)) {
            return MyOrderViewModel(app) as T
        }
        if (modelClass.isAssignableFrom(WishListViewModel::class.java)) {
            return WishListViewModel(app) as T
        }


        if (modelClass.isAssignableFrom(ContactUsViewModel::class.java)) {
            return ContactUsViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(VoucherViewModel::class.java)) {
            return VoucherViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            return ProductListViewModel(app) as T
        }
        if (modelClass.isAssignableFrom(FilterViewModel::class.java)) {
            return FilterViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(DealsViewModel::class.java)) {
            return DealsViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(MainViewModal::class.java)) {
            return MainViewModal(app) as T
        }

        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(ProducatDetailsViewModel::class.java)) {
            return ProducatDetailsViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(ShoppingBagViewModel::class.java)) {
            return ShoppingBagViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(OrderPlacedViewModel::class.java)) {
            return OrderPlacedViewModel(app) as T
        }

        if (modelClass.isAssignableFrom(NotificationListViewModel::class.java)) {
            return NotificationListViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
