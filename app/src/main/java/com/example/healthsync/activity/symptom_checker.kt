package com.example.healthsync.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.healthsync.R
import com.example.healthsync.databinding.ActivitySymptomCheckerBinding

class symptom_checker : AppCompatActivity() {
    private lateinit var binding: ActivitySymptomCheckerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySymptomCheckerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // getting data from form
        //medical history
        var existingSymptom =binding.editExistingSymptoms.text.toString()
        var chronicDisease =binding.editChronicDisease.text.toString()
        var allergies =binding.editAllergies.text.toString()
        var previousSurrgeries =binding.editPreviousSurgeries.text.toString()
        var currentMedication =binding.editCurrentMedication.text.toString()

        // social history
        var smokingStatus =binding.editSmokingStatus.text.toString()
        var AlcoholIntake =binding.editAlcoholIntake.text.toString()
        var drugUse =binding.editDrugUsage.text.toString()

        
    }
}