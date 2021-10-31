package com.example.stackoverflowclone.extensions

import android.widget.Button

/**
 * Created by Mehul Bisht on 31-10-2021
 */

fun Button.enable() {
    this.isEnabled = true
}

fun Button.disable() {
    this.isEnabled = false
}