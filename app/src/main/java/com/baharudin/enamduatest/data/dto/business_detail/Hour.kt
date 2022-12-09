package com.baharudin.enamduatest.data.dto.business_detail

data class Hour(
    val hours_type: String,
    val is_open_now: Boolean,
    val `open`: List<Open>
)