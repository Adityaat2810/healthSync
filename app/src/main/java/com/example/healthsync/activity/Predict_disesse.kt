package com.example.healthsync.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.healthsync.R

class Predict_disesse : AppCompatActivity() {
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predict_disesse)

        // Retrieve the message from the intent
        val successMessage = intent.getStringExtra("successMessage")
        Toast.makeText(this, successMessage, Toast.LENGTH_LONG).show()

        textView =findViewById(R.id.tv)
        if (successMessage  !=null){
        textView.setText(successMessage.toString())
        }else{
            textView.setText("message not recieved")
        }


    }
}