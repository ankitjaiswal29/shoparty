package com.shoparty.android.ui.vouchers

data class VoucherListResponse(
    val message: String,
    val response_code: Int,
    val result: List<Data>
)
{
    data class Data(
        val coupon_code: String,
        val discount: Int,
        val end_date: String,
        val min_purchase: Int,
        val start_date: String,
        val voucher_id: Int
    )
}