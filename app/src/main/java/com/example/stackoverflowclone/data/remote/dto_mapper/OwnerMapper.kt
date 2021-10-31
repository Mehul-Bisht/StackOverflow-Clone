package com.example.stackoverflowclone.data.remote.dto_mapper

import com.example.stackoverflowclone.data.remote.dto.OwnerDto
import com.example.stackoverflowclone.domain.models.Owner

/**
 * Created by Mehul Bisht on 31-10-2021
 */

fun OwnerDto.toOwner(): Owner =
    Owner(
        display_name = this.display_name,
        link = this.link,
        profile_image = this.profile_image,
        reputation = this.reputation
    )