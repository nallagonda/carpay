package com.pclabs.carpay.data.api.methods

import com.pclabs.carpay.data.api.ApiClient
import com.pclabs.carpay.data.api.request.LoginRequest
import com.pclabs.carpay.data.api.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/upload")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }
}