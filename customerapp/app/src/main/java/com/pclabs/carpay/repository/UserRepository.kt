package com.pclabs.carpay.repository

import com.pclabs.carpay.data.api.methods.UserApi
import com.pclabs.carpay.data.api.request.LoginRequest
import com.pclabs.carpay.data.api.response.LoginResponse
import retrofit2.Response

class UserRepository {

   suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
      return  UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }
}