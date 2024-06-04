package com.example.chatappforhasan

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatappforhasan.databinding.UserLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class UserAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserLayoutBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.binding.tvUser.text = currentUser.name


        // Diğer view'lere erişimi buradan sağlayabilirsiniz.
         // bunu sonra ekle
        holder.itemView.setOnClickListener {
            val intent=Intent(holder.itemView.context,ChatActivity::class.java)
            intent.putExtra("Name", currentUser.name)
            intent.putExtra("uid", currentUser.uid)

            Log.e("befor1", currentUser.name.toString())
            Log.e("befor2", currentUser.uid.toString())

            holder.itemView.context.startActivity(intent)
        }



    }

  inner class UserViewHolder(val binding: UserLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}