package com.example.healthsync.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.healthsync.R
import com.example.healthsync.auth.loginActivity
import com.example.healthsync.databinding.FragmentProfileFragmentBinding
import com.example.healthsync.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class profile_fragment : Fragment() {

    private lateinit var binding: FragmentProfileFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileFragmentBinding.inflate(layoutInflater)


        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid!!).get()
            .addOnSuccessListener {
                if(it.exists()){
                    val data  = it.getValue(UserModel::class.java)
                    binding.userName.setText(data!!.name.toString())
                    binding.userCity.setText(data!!.city.toString())
                    binding.userEmail.setText(data!!.email.toString())
                    binding.userNumber.setText(data!!.number.toString())

                    Glide.with(this).load(data.image)
                        .placeholder(R.drawable.profile).into(binding.userPic)
                }
            }

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(),loginActivity::class.java))
            requireActivity().finish()

        }


        return binding.root

    }


}