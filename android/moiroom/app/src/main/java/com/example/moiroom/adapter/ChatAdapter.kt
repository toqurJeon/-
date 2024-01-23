package com.example.moiroom.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.R
import com.example.moiroom.data.Chat
import com.example.moiroom.databinding.ChatItemLayoutBinding

class ChatAdapter(private val dataList: List<Chat>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val binding: ChatItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val data = dataList[position]

        holder.binding.apply {
            chatMemberName.text = "${data.member_id}"
            chatContent.text = data.content
            chatCreatedAt.text = "${data.created_at}"

            if (data.member_id == 1) {
                holder.binding.chatContent.setBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.lightorange))
                holder.binding.root.gravity = Gravity.END
            } else {
                holder.binding.chatContent.setBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.darkorange))
                holder.binding.root.gravity = Gravity.START
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}