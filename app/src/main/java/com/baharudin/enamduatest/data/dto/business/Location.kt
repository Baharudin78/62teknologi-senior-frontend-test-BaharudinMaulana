package com.baharudin.enamduatest.data.dto.business

data class Location(
    val address1: String? = null,
    val address2: String? = null,
    val address3: String? = null,
    val city: String? = null,
    val country: String? = null,
    val display_address: List<String?>,
    val state: String? = null,
    val zip_code: String? = null
)