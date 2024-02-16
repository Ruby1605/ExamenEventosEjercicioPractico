package com.example.ejerciciopractico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent


class RegisterActivity : AppCompatActivity() {
    private lateinit var btnregister: Button
    private lateinit var imputEmail: EditText
    private lateinit var imputPassword: EditText
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        btnregister = findViewById(R.id.registrarse)
        imputEmail = findViewById(R.id.email)
        imputPassword = findViewById(R.id.password)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        btnregister.setOnClickListener {
            val email = imputEmail.text.toString()
            val password = imputPassword.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(baseContext, "Registro exitoso.", Toast.LENGTH_SHORT).show()

                        // Redirect to the main activity
                        val intent = Intent(this, AuthActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Error de autenticación.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}