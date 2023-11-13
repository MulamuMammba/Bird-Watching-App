package com.am.bird_watching_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)


        val emailEditText = findViewById<EditText>(R.id.resetEmail)
        val button =findViewById<Button>(R.id.resetPassword_button)

        button.setOnClickListener{
            val email = emailEditText.text.toString().trim()
            if(email.isNotEmpty()){
                sendEmail(email)
            }else{
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun sendEmail(email : String){

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Password reset email sent successfully
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                    // Navigate back to login page
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Password reset email send failed
                    Toast.makeText(this, "Failed to send password reset email", Toast.LENGTH_SHORT).show()
                }
            }


    }
}