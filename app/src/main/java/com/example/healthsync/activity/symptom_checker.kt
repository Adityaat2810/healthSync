package com.example.healthsync.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.healthsync.R
import com.example.healthsync.databinding.ActivitySymptomCheckerBinding
import com.example.healthsync.model.UserModel
import com.example.healthsync.symptom_checker.REtrofit_Interface
import com.example.healthsync.symptom_checker.request
import com.example.healthsync.symptom_checker.response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class symptom_checker : AppCompatActivity() {
    private lateinit var data:UserModel
    private lateinit var binding: ActivitySymptomCheckerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySymptomCheckerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.PredictDisease.setOnClickListener {
            binding.PredictDisease.startAnimation()
            getResponse()
        }




        
    }

    private fun getResponse() {
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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://aisymchecker.onrender.com/api/v1/") // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService =retrofit.create(REtrofit_Interface::class.java)

        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).get()
            .addOnSuccessListener {
                if(it.exists()){
                     data  = it.getValue(UserModel::class.java)!!

                    val request =request(age = data!!.age!!.toInt(), alcohol_intake=AlcoholIntake,
                        allergies =allergies ,chronic_diseases =chronicDisease , current_medications = currentMedication ,
                        existing_medical_symptoms =existingSymptom , gender = data.gender!!.toString(), health_insurance = true,
                        nationality = "Indian" ,occasional_drug_use =drugUse, occupation = "engineer", previous_surgeries = listOf(previousSurrgeries) ,
                        smoking_status = smokingStatus , weight = data.weight!!.toInt()
                    )

                    val call =apiService.postSymptoms(request)

                    call.enqueue(object : Callback<response> {
                        override fun onResponse(call: Call<response>, response: Response<response>) {
                            if (response.isSuccessful && response.body() != null && response.body()?.message.equals("Success")) {
                                Toast.makeText(this@symptom_checker, "ho gya kaam", Toast.LENGTH_SHORT).show()
                                binding.PredictDisease.revertAnimation()

                                val responseMessage = response.body()!!.result
                                val intent = Intent(this@symptom_checker, Predict_disesse::class.java)
                                intent.putExtra("successMessage", responseMessage)
                                startActivity(intent)

                            }else{
                                Toast.makeText(this@symptom_checker, "api call mai dikkat hai", Toast.LENGTH_SHORT).show()
                                binding.PredictDisease.revertAnimation()



                            }
                        }

                        override fun onFailure(call: Call<response>, t: Throwable) {
                            Toast.makeText(this@symptom_checker, t.message, Toast.LENGTH_SHORT).show()
                            binding.PredictDisease.revertAnimation()




                        }
                    })


                }
                else{
                    Toast.makeText(this@symptom_checker, "cannot find user data", Toast.LENGTH_SHORT).show()
                    binding.PredictDisease.revertAnimation()

                }
            }








    }
}