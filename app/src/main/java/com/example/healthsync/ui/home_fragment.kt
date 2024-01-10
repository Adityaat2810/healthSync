package com.example.healthsync.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.healthsync.R
import com.example.healthsync.activity.symptom_checker
import com.example.healthsync.databinding.FragmentHomeFragmentBinding

class home_fragment : Fragment() {

    private lateinit var binding: FragmentHomeFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeFragmentBinding.inflate(layoutInflater)

        binding.imageView3.setOnClickListener {
            startActivity(Intent(requireContext(),symptom_checker::class.java))
        }



        return binding.root
    }


}