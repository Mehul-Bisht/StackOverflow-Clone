package com.example.stackoverflowclone.domain.models

/**
 * Created by Mehul Bisht on 31-10-2021
 */

data class Owner(
    val display_name: String,
    val link: String,
    val profile_image: String,
    val reputation: Int
)
