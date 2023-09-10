package com.pclabs.carpay.data.api.methods

import com.pclabs.carpay.data.api.ApiClient
import com.pclabs.carpay.data.api.response.UploadResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
//https://72cf-147-92-91-31.ngrok-free.app/signup_with_image_test?name=ere&licnum=rere&phone=ere
    @POST("/signup_with_image_test")
    suspend fun loginUser(@Query("name")  zipCode:String,
                          @Query("licnum")  sensor:String,
                          @Query("phone") phone:String): Response<UploadResponse>

    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }
}