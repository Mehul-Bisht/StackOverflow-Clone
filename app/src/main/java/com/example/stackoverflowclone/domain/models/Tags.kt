package com.example.stackoverflowclone.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Mehul Bisht on 31-10-2021
 */

@Parcelize
data class Tags(
    val tags: List<String>
): Parcelable
