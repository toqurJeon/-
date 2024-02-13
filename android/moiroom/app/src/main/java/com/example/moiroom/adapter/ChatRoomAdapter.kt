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
                    val chatRoomId = dataList[position].chatRoomId
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
        val time: String = formatting(data.updatedAt)

        holder.binding.apply {
            chatRoomName.text = data.memberNickname
            chatRoomLastMsg.text = data.lastMessage
            chatRoomCreatedAt.text = time
            // 프로필 이미지 URL로부터 이미지를 로드하는 코드를 추가해야 합니다.
            // Glide 라이브러리를 사용할 경우 아래와 같이 코드를 작성할 수 있습니다.
            // Glide.with(parent.context).load(data.profileImageUrl).into(chatMemberImage)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun formatting(timeInstant: Instant): String {
        val localDateTime = LocalDateTime.ofInstant(timeInstant, ZoneId.of("Asia/Seoul"))
        val formatter = DateTimeFormatter.ofPattern("MM월 dd일 HH:mm")
        val formattedDateTime = localDateTime.format(formatter)
        return formattedDateTime
    }
}