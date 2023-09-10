package com.pclabs.carpay.ui

import android.R
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.pclabs.carpay.databinding.ActivityHomeBinding
import com.pclabs.carpay.utils.SessionManager
import com.pclabs.carpay.viewmodel.LoginViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        AlertDialog.Builder(this)
            .setTitle("Costco , Santa Clara")
            .setMessage("Would you like to authorize transaction using Car Pay?") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(
                R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->
                    val ownerToken = "d1Xz3bSLTM2-VEKkMLhgU3:APA91bGlK9v-34W4AhBaH0kPOGqUoHEQpT4SukhyS7uM7D4HcJ0rB_RMRPQSCVyb5bQFBvesuruRAMC3rHyiGgE5FtidywDctQaonO0v61-NBBy7BFKlXzpcDMykN3N-eDL8xPcKox15"
                    viewModel.authorize("AUTHORIZE", true )
                }) // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(R.string.no, null)
            .setIcon(R.drawable.ic_dialog_alert)
            .show()
        binding.btnLogout.setOnClickListener {
            SessionManager.clearData(this)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }
    }
}