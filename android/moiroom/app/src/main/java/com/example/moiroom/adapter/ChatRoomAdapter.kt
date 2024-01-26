package com.example.moiroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.R
import com.example.moiroom.data.ChatRoom
import com.example.moiroom.databinding.ChatroomItemLayoutBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
        val time: String = formatting(data.created_at)
        val sample_name1 = "김민수"
        val sample_name2 = "김민지"

        holder.binding.apply {
            chatRoomName.text = sample_name1
            chatRoomLastMsg.text = data.last_message
            chatRoomCreatedAt.text = time

            if (data.id == 2) {
                chatRoomName.text = sample_name2
                chatMemberImage.setImageResource(R.drawable.sample_profile2)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun formatting(timeInstant: Instant): String {
        val localDateTime = LocalDateTime.ofInstant(timeInstant, ZoneId.of("Asia/Seoul"))

        val formatter = DateTimeFormatter.ofPattern("MM월 DD일 HH:mm")

        val formattedDateTime = localDateTime.format(formatter)

        return formattedDateTime
    }
}