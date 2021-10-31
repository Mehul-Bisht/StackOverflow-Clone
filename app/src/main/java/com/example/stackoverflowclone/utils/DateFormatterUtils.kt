package com.example.stackoverflowclone.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Mehul Bisht on 31-10-2021
 */

@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("SimpleDateFormat")
fun getDate(timestamp: Long): String {
    val unix_timestamp = timestamp * 1000L // UNIX TIMESTAMP
    val date = Date(unix_timestamp)
    val sdf = SimpleDateFormat("dd-MM-YYYY")
    val time = sdf.format(date)

    return time
}