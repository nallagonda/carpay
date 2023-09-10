package com.pclabs.carpay.repository

import com.pclabs.carpay.data.api.methods.UserApi
import com.pclabs.carpay.data.api.response.UploadResponse
import retrofit2.Response

class UserRepository {

   suspend fun loginUser(name:String, licpla:String, phone:String): Response<UploadResponse>? {
      return  UserApi.getApi()?.loginUser(name,licpla, phone)
    }

    suspend fun isAuthorized(owner_token:String, isAuthorized:Boolean): Response<UploadResponse>? {
        return  UserApi.getApi()?.authorize(owner_token,isAuthorized)
    }
}