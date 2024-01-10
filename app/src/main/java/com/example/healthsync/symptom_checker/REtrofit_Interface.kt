package com.example.healthsync.symptom_checker

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface REtrofit_Interface {

    @POST("symptomChecker")
    fun postSymptoms(@Body request: request): Call<response>

}