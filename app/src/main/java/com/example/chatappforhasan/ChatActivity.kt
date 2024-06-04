package com.example.chatappforhasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatappforhasan.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION")
class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    // chat için bir oda OLUŞTURMAK İÇİN KULLAN
    var receiverRoom: String? = null
    var senderRoom: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mDbRef = FirebaseDatabase.getInstance().getReference()

        val intent =intent
        val name = intent.getStringExtra("Name")
        val receiverUid = intent.getStringExtra("uid")

       // Log.e("veri1",name.toString())
       // Log.e("veri2",receverUid.toString())

        setSupportActionBar(binding.toolbar2)
        supportActionBar?.title = name.toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid



        // mesajlama işlemi
        messageList = ArrayList()
        messageAdapter = MessageAdapter( messageList)

        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messageAdapter


        // recycler view kodlar bunu en sona bırak

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear() // önceki mesaj listesini temizliyoruz

                    for (postSnapshot in snapshot.children) {

                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })





        // button işlemi
        binding.sentButton.setOnClickListener {

            // ilk önce mesajlar data base içine ekleyeceğiz
           /* val message = binding.messageBox.text.toString()
            val messageObject = Message(message,senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            binding.messageBox.setText("")*/



            val message = binding.messageBox.text.toString()
            val messageObject = Message(message,senderUid)

            senderRoom?.let { senderRoom ->
                mDbRef.child("chats").child(senderRoom).child("messages").push()
                    .setValue(messageObject).addOnSuccessListener {
                        receiverRoom?.let { receiverRoom ->
                            mDbRef.child("chats").child(receiverRoom).child("messages").push()
                                .setValue(messageObject)
                        }
                    }
            }
            binding.messageBox.setText("")



        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Geri düğmesine basıldığında buraya gelecek kodu ekleyin
                onBackPressed() // Geri dönüş işlemini gerçekleştirmek için bu metodu kullanabilirsiniz
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}