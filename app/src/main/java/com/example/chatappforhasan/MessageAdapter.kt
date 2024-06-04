package com.example.chatappforhasan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.R
import androidx.recyclerview.widget.RecyclerView
import com.example.chatappforhasan.databinding.ReceiveBinding
import com.example.chatappforhasan.databinding.SentBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


        val SENT_MESSAGE_TYPE = 1
        val RECEIVE_MESSAGE_TYPE = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SENT_MESSAGE_TYPE -> {
                val binding = SentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SentMessageViewHolder(binding)
            }
            RECEIVE_MESSAGE_TYPE -> {
                val binding = ReceiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ReceiveMessageViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder.itemViewType) {
            SENT_MESSAGE_TYPE -> {
                val sentHolder = holder as SentMessageViewHolder
                sentHolder.bind(message)
            }
            RECEIVE_MESSAGE_TYPE -> {
                val receiveHolder = holder as ReceiveMessageViewHolder
                receiveHolder.bind(message)
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(message.senderId)){
            return SENT_MESSAGE_TYPE
        }
        else{
            return RECEIVE_MESSAGE_TYPE
        }
    }

    inner class SentMessageViewHolder(private val binding: SentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.txtSentMessage.text = message.toString()

        }
    }

   inner class ReceiveMessageViewHolder(private val binding: ReceiveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.txtReceiveMessage.text = message.toString()

        }
    }
}