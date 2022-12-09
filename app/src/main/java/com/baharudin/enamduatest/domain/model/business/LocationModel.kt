package com.baharudin.enamduatest.domain.model.business

data class LocationModel(
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    val country: String,
    val displayAddress: List<String>,
    val state: String,
    val zipCode: String
)
