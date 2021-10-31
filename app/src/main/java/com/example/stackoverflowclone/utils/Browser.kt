package com.example.stackoverflowclone.utils

import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Created by Mehul Bisht on 31-10-2021
 */

class Browser: WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean{

        url?.let {
            view?.loadUrl(it)
        }
        return false
    }
}