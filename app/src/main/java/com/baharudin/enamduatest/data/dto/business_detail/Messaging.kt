package com.baharudin.enamduatest.data.dto.business_detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Messaging(
    val url: String,
    val use_case_text: String
) : Parcelable