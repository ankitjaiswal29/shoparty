package com.shoparty.android.ui.contactus

data class ContactUsResponse(
    val message: String,
    val response_code: Int,
    val result: Data
)
{
    data class Data(
        val contact_day_end: String,
        val contact_day_start: String,
        val contact_no: String,
        val contact_time_end: String,
        val contact_time_start: String,
        val facebook_url: String,
        val id: Int,
        val instagram_url: String,
        val status: Int,
        val twitter_url: String,
        val whatsapp_day_end: String,
        val whatsapp_day_start: String,
        val whatsapp_no: String,
        val whatsapp_time_end: String,
        val whatsapp_time_start: String,
        val youtube_url: String,
        val email: String,
    )
}