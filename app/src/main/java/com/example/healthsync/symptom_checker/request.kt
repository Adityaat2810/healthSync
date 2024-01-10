package com.example.healthsync.symptom_checker

data class request(
    val age: Int,
    val alcohol_intake: String,
    val allergies: Any,
    val chronic_diseases: Any,
    val current_medications: Any,
    val existing_medical_symptoms: String,
    val gender: String,
    val health_insurance: Boolean,
    val nationality: String,
    val occasional_drug_use: String,
    val occupation: String,
    val previous_surgeries: List<String>,
    val smoking_status: String,
    val weight: Int
)