package com.shoparty.android.ui.main.mainactivity

data class ChangeLanguageResponse(
    val message: String,
    val response_code: Int,
    val result: LangData)
{
    data class LangData(
        val language_id: String
    )
}
