package com.example.chatappforhasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatappforhasan.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class logIn : AppCompatActivity() {

    private lateinit var binding:ActivityLogInBinding
    private lateinit var mAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth=FirebaseAuth.getInstance()


        binding.btnSingUp.setOnClickListener {
            val intent = Intent(this,SingUp::class.java)
            startActivity(intent)
        }


        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                // Kullanıcıya hata mesajını göster
                Toast.makeText(this@logIn, "Email or password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                // Değerler boş değilse, giriş işlemini yap
                login(email, password)
            }

        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@logIn,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(applicationContext,"User does not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}