package com.example.moiroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.data.ChatRoom
import com.example.moiroom.databinding.ChatroomItemLayoutBinding

class ChatRoomAdapter(private val dataList: List<ChatRoom>) : RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(chatRoomId: Int)
    }

    // 클릭 리스너
    var onItemClickListener: OnItemClickListener? = null

    inner class ChatRoomViewHolder(val binding: ChatroomItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // 아이템 뷰에 클릭 리스너 설정
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val chatRoomId = dataList[position].id
                    onItemClickListener?.onItemClick(chatRoomId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        val binding = ChatroomItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatRoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        val data = dataList[position]

        holder.binding.apply {
            chatRoomName.text = "${data.id}"
            chatRoomLastMsg.text = data.last_message
            chatRoomCreatedAt.text = "${data.created_at}"
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}