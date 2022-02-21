package com.shoparty.android.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater

import com.shoparty.android.R


object ProgressDialog {

    private lateinit var alertDialog: AlertDialog

    fun showProgressBar(context: Context, cancelable: Boolean = false) {
        try {
            if (::alertDialog.isInitialized) {
                if (alertDialog.isShowing) {
                    alertDialog.hide()
                    alertDialog.dismiss()
                } else {
                    val alertDialogBuilder = AlertDialog.Builder(context)
                    val view =
                        LayoutInflater.from(context).inflate(R.layout.layout_progress_dialog, null)
                    alertDialogBuilder.setView(view)
                    alertDialog = alertDialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    alertDialog.show()
                }
            } else {
                val alertDialogBuilder = AlertDialog.Builder(context)
                val view =
                    LayoutInflater.from(context).inflate(R.layout.layout_progress_dialog, null)
                alertDialogBuilder.setView(view)
                alertDialog = alertDialogBuilder.create()
                alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                if (cancelable) {
                    alertDialog.setCanceledOnTouchOutside(true)
                    alertDialog.setCancelable(true)
                }
                alertDialog.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideProgressBar() {
        try {
            if (::alertDialog.isInitialized) {
                if (alertDialog.isShowing) {
                    alertDialog.hide()
                    alertDialog.dismiss()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setCancelable() {
        try {
            if (::alertDialog.isInitialized) {
                if (alertDialog.isShowing) {
                    alertDialog.setCanceledOnTouchOutside(false)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}