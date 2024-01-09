package com.example.healthsync

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.healthsync.activity.symptom_checker
import com.example.healthsync.databinding.ActivityMainBinding
import com.example.healthsync.ui.guide_fragment
import com.example.healthsync.ui.home_fragment
import com.example.healthsync.ui.profile_fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        binding =ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        binding.symptom.setOnClickListener {
//            startActivity(Intent(this,symptom_checker::class.java))
//        }
        bottomNavigationView=binding.bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId){
                R.id.home_fragment->{
                    replaceFragment(home_fragment())
                    true
                }
                R.id.guide_fragment->{
                    replaceFragment(guide_fragment())
                    true
                }
                R.id.profile_fragment->{
                    replaceFragment(profile_fragment())
                    true
                }
                else->false
            }
        }
        replaceFragment(home_fragment())






    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,fragment).commit()
    }
}