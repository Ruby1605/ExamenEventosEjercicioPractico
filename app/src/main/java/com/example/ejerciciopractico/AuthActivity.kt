package com.example.ejerciciopractico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.common.SignInButton

private const val RC_SIGN_IN = 9001

class AuthActivity : AppCompatActivity() {
    private lateinit var btnregister: Button
    private lateinit var btnlogin: Button
    private lateinit var imputEmail: EditText
    private lateinit var imputPassword: EditText
    private lateinit var singInButton: SignInButton
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        btnregister = findViewById(R.id.register)
        btnlogin = findViewById(R.id.login)
        imputEmail = findViewById(R.id.email)
        imputPassword = findViewById(R.id.password)
        singInButton = findViewById(R.id.sign_in_button)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)




        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        btnlogin.setOnClickListener {
            val email = imputEmail.text.toString()
            val password = imputPassword.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(baseContext, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Productos::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Error de autenticación.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        btnregister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        singInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Handle the exception
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                } else {
                    // If sign in fails, display a message to the user.
                }
            }
    }

}