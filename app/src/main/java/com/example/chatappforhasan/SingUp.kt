package com.example.chatappforhasan

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.example.chatappforhasan.databinding.ActivitySingUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SingUp : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mAuth=FirebaseAuth.getInstance()


        binding.btnSignUpPage.setOnClickListener {
            val name = binding.edtNameSignUp.text.toString()
            val email = binding.edtEmailSignUp.text.toString()
            val password = binding.edtPasswordSignUp.text.toString()
            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                // Kullanıcıya hata mesajını göster
                Toast.makeText(this@SingUp, "Name, Email or password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                // Değerler boş değilse, giriş işlemini yap
                singUp(name,email,password)
            }


        }
    }

    private fun singUp(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(name,email, mAuth.currentUser?.uid)
                    val intent = Intent(this@SingUp,MainActivity::class.java)
                    finish()
                    finishAffinity()
                    startActivity(intent)

                } else {
                    val errorMessage = task.exception?.message ?: "Unknown error"
                    Log.e("hata1",errorMessage.toString())
                    Toast.makeText(this@SingUp,"Something is rong",Toast.LENGTH_SHORT).show()

                }
            }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String?) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("User").child(uid.toString()).setValue(User(name,email,mAuth.currentUser?.uid))
    }
}