package com.example.chatappforhasan

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatappforhasan.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = "Chat App"
        setSupportActionBar(binding.toolbar)

        mAuth = FirebaseAuth.getInstance()
        mDbRef= FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(userList)

        binding.userRecyclerView.layoutManager =LinearLayoutManager(this)
        binding.userRecyclerView.adapter =adapter

        mDbRef.child("User").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               // userList.clear()
                for (postSnapshot in snapshot.children){

                    // ilk önce if olmadan göster sonra if ekle
                    val currentUser = postSnapshot.getValue(User::class.java)

                    //currentUser?.let { userList.add(it) }

                    if(mAuth.currentUser?.uid != currentUser?.uid){

                        currentUser?.let { userList.add(it) }
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })





        // şimdi listenin içine verileri eklememiz gerek
        // we have to add the users to database we add them to athu only


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                // "Settings" öğesine tıklandığında yapılacak işlemi burada tanımlayın
                mAuth.signOut()
                 val intent = Intent(this,logIn::class.java)
                finish()
                startActivity(intent)
                true
            }
            // Diğer öğeler için gerekirse işlem ekle
            else -> super.onOptionsItemSelected(item)
        }
    }
}


