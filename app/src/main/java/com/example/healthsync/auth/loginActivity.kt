package com.example.healthsync.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.healthsync.MainActivity
import com.example.healthsync.R
import com.example.healthsync.databinding.ActivityLoginBinding
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.google.firebase.auth.FirebaseAuth

class loginActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    private var verificationId:String? = null
    private lateinit var binding: ActivityLoginBinding
    private lateinit var dialog:AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        dialog = AlertDialog.Builder(this).
        setView(R.layout.loading_layout).create()

        binding.signUpTv.setOnClickListener {
            startActivity(Intent(this,registrationActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            binding.loginButton.startAnimation()
            val email = binding.userEmail.text.toString()
            val password= binding.userPassword.text.toString()



            loginUser(email,password)

        }

    }

    private fun loginUser(emaill: String, passwordd: String) {
        if (emaill.isNotEmpty() && passwordd.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(emaill, passwordd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val verification = FirebaseAuth.getInstance().currentUser?.isEmailVerified
                        if(verification==true){
                            Toast.makeText(this, "Authentication success", Toast.LENGTH_SHORT).show()
                            binding.loginButton.revertAnimation()

                            startActivity(Intent(this,MainActivity::class.java))

                        }else{
                            binding.loginButton.revertAnimation()


                            Toast.makeText(
                                this,
                                "Please verify your email",
                                Toast.LENGTH_SHORT
                            ).show()

                        }


                    } else {
                        binding.loginButton.revertAnimation()

                        // Login failed
                        val exception = task.exception
                        if (exception != null) {
                            Log.e(
                                "Authentication Error",
                                "Failed to log in: ${exception.message}",
                                exception
                            )
                            Toast.makeText(
                                this,
                                "Authentication failed: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.e("Authentication Error", "Failed to log in: Unknown error")
                            Toast.makeText(
                                this,
                                "Authentication failed: Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
    }


}
