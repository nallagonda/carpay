package com.pclabs.carpay.ui

import android.R
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.pclabs.carpay.data.api.response.BaseResponse
import com.pclabs.carpay.databinding.ActivityMainBinding
import com.pclabs.carpay.utils.SessionManager
import com.pclabs.carpay.viewmodel.LoginViewModel
import okhttp3.internal.notifyAll


class MainActivity : AppCompatActivity() {
    private val pic_id = 123
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<LoginViewModel>()
    var click_image_id: ImageView? = null
    var deviceToken =   ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener<String?> { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                deviceToken =   token
                //Log.v("deviceToken","deviceToken:Succesful:"+deviceToken)
                Toast.makeText(this@MainActivity, deviceToken, Toast.LENGTH_SHORT).show()
            })

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        click_image_id = binding.imageView3
        setContentView(view)
        val token = SessionManager.getToken(this)
        if (!token.isNullOrBlank()) {
           navigateToHome()
        }

        viewModel.loginResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success<*> -> {
                    stopLoading()
                    processLogin(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                    stopLoading()
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            doLogin()

        }

        binding.btnRegister.setOnClickListener {
            //doSignup()
            dispatchTakePictureIntent()
        }

        binding.imageView3.setOnClickListener{
            dispatchTakePictureIntent()
        }

    }

    private fun navigateToHome() {
        val intent = Intent(this, LogoutActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    fun doLogin() {
        val name = binding.txtName.text.toString()
        val phone = binding.txtPass.text.toString()
        val licPlate = binding.licPlate.text.toString()
        viewModel.loginUser(name, licPlate,phone )

    }

    fun doSignup() {

    }

    fun showLoading() {
        binding.prgbar.visibility = View.VISIBLE
    }

    fun stopLoading() {
        binding.prgbar.visibility = View.GONE
    }

    fun processLogin(data: Any?) {
        //showToast("Success:" + data?.message)
        //if (!data?.data?.token.isNullOrEmpty()) {
            //data?.data?.token?.let { SessionManager.saveAuthToken(this, it) }

            navigateToHome()


    }

    fun processError(msg: String?) {
        //showToast("Error:" + msg)
        navigateToHome()
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    private fun dispatchTakePictureIntent() {
        val camera_intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Start the activity with camera_intent, and request pic id
        // Start the activity with camera_intent, and request pic id
        startActivityForResult(camera_intent, pic_id)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //        super.onActivityResult(requestCode, resultCode, data)
        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {
            // BitMap is data structure of image file which store the image in memory
            val photo = data?.extras!!["data"] as Bitmap?
            // Set the image in imageview for display
            click_image_id?.setImageBitmap(photo)
            binding.licPlate.setText("SKN1010")
        }
    }
}
