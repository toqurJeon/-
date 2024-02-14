package com.example.moiroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moiroom.R
import com.example.moiroom.data.ChatRoom
import com.example.moiroom.databinding.ChatroomItemLayoutBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ChatRoomAdapter(private val dataList: List<ChatRoom>) : RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(memberId: Long, chatRoomId: Long)  // 파라미터 타입을 Int로 변경했습니다.
    }

    // 클릭 리스너
    var onItemClickListener: OnItemClickListener? = null

    inner class ChatRoomViewHolder(val binding: ChatroomItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // 아이템 뷰에 클릭 리스너 설정
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val memberId = dataList[position].memberId
                    val chatRoomId = dataList[position].chatRoomId
                    onItemClickListener?.onItemClick(memberId, chatRoomId)
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
        val time: String = data.updatedAt

        holder.binding.apply {
            chatRoomName.text = data.memberNickname
            chatRoomLastMsg.text = data.lastMessage
            chatRoomCreatedAt.text = time
            // 프로필 이미지 URL로부터 이미지를 로드하는 코드
            Glide.with(chatMemberImage.context)
                .load(data.profileImageUrl)
                .into(chatMemberImage)
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