package com.shoparty.android.ui.vouchers

import com.shoparty.android.network.RetrofitBuilder
import com.shoparty.android.ui.address.addaddress.addaddress.AddAddressRequestModel
import com.shoparty.android.ui.address.addaddress.addaddress.GetCityRequestModel
import com.shoparty.android.ui.address.addaddress.addaddress.UpdateAddressRequestModel
import com.shoparty.android.ui.address.addaddress.getaddress.DeleteAddressRequestModel


class VoucherRepository {
    suspend fun getvoucher() =
        RetrofitBuilder.apiService?.VoucherAsync()
}