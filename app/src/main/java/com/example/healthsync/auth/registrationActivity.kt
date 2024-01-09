package com.example.healthsync.auth

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.healthsync.R
import com.example.healthsync.databinding.ActivityRegistrationBinding
import com.example.healthsync.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class registrationActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRegistrationBinding
    private var imageUri: Uri?=null

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri  =it
        binding.profileImage.setImageURI(imageUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.profileImage.setOnClickListener {
            selectImage.launch("image/*")

        }

        supportActionBar?.hide()

        binding.nextFields.setOnClickListener {

            validateData()
        }

        binding.register.setOnClickListener {
            binding.register.startAnimation()
            validateSecondaryData()

        }


    }

    private fun validateSecondaryData() {
        if(binding.userNumber.text.toString().isEmpty()
            ||binding.userAge.text.toString().isEmpty()
            ||binding.userGender.text.toString().isEmpty()
            ||binding.bodyWeight.text.toString().isEmpty()
        ){
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()


        }else{
            signUp()
        }
    }

    private fun validateData() {
        if(binding.userName.text.toString().isEmpty()
            || binding.userCity.text.toString().isEmpty()
            ||binding.userCity.text.toString().isEmpty()||
            imageUri==null
            ||binding.userPassword.text.toString().isEmpty()
        ){
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()

        }else{

            binding.primaryInformation.visibility= View.GONE
            binding.secondaryInformation.visibility = View.VISIBLE

        }

    }


    private fun signUp() {


        val email = binding.userEmail.text.toString()
        val password = binding.userPassword.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User created successfully
                    FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                        ?.addOnSuccessListener {
                            // Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show()
                            uploadImage()
                        }
                        ?.addOnFailureListener { exception ->
                            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
                            Log.e("Registration", "Failed to send email verification", exception)
                        }
                } else {
                    // Failed to create user
                    Toast.makeText(this, "User creation failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    Log.e("Registration", "User creation failed", task.exception)
                }
            }
    }

    private fun uploadImage() {
        val storageRef = FirebaseStorage.getInstance().getReference("profile")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("profile.jpg")

        storageRef.putFile(imageUri!!)
            .addOnSuccessListener { taskSnapshot ->
                storageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                    Log.d("Upload Image", "Image uploaded successfully. Image URL: $imageUrl")
                    storeData(imageUrl)
                }.addOnFailureListener { exception ->
                    Log.e("Upload Image", "Failed to get download URL: ${exception.message}", exception)
                    Toast.makeText(this, "Failed to get download URL", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Upload Image", "Image upload failed: ${exception.message}", exception)
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
    }


    private fun storeData(imageUrl: Uri?) {
        val data = FirebaseAuth.getInstance().currentUser?.let {
            UserModel(
                number = binding.userNumber.text.toString(),
                name = binding.userName.text.toString(),
                city = binding.userCity.text.toString(),
                image = imageUrl.toString(),
                email = binding.userEmail.text.toString(),
                uid =
                it.uid
                ,
                weight = binding.bodyWeight.text.toString(),

                age = binding.userAge.text.toString(),
                gender = binding.userGender.text.toString(),







                )
        }

        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid!!)
            .setValue(data).addOnCompleteListener{
                if(it.isSuccessful){

                    binding.register.revertAnimation()
                    Toast.makeText(this, "user registered successfully", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "please verify your email", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,loginActivity::class.java))
                    finish()
                }else{


                    binding.register.revertAnimation()
                    Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT).show()


                }
            }


    }



}