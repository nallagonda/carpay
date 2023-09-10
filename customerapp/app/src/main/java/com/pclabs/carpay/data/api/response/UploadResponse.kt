package com.pclabs.carpay.data.api.response

data class UploadResponse(
    val bc_token: String,
    val licnum: String,
    val name: String,
    val phone: String
)