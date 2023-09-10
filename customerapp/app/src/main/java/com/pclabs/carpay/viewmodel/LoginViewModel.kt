package com.pclabs.carpay.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.pclabs.carpay.data.api.response.AuthorizeResponse
import com.pclabs.carpay.data.api.response.BaseResponse
import com.pclabs.carpay.data.api.response.UploadResponse
import com.pclabs.carpay.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val userRepo = UserRepository()
    val loginResult: MutableLiveData<BaseResponse<UploadResponse>> = MutableLiveData()
    val authorizeResult: MutableLiveData<BaseResponse<AuthorizeResponse>> = MutableLiveData()
    fun loginUser(name: String, licencePlate: String, phone:String) {

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val response = userRepo.loginUser(name, licencePlate,phone)
                if (response?.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun authorize(owner_tokenme: String, isAuthorize: Boolean) {

        authorizeResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val response = userRepo.isAuthorized(owner_tokenme, isAuthorize)
                if (response?.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}