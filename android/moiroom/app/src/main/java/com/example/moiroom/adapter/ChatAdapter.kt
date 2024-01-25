package com.example.moiroom.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Matrix
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.R
import com.example.moiroom.data.Chat
import com.example.moiroom.databinding.ChatItemLayoutBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ChatAdapter(private var dataList: MutableList<Chat>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val binding: ChatItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val data = dataList[position]
        val time: String = formatting(data.created_at)
        val sample_name = "김민수"

        holder.binding.apply {
            chatMemberName.text = "${data.member_id}"
            chatContent.text = data.content
            chatCreatedAt.text = time

            val chatBallonDrawable = ContextCompat.getDrawable(holder.binding.root.context, R.drawable.chat_ballon_shape)
            val chatBallonDrawableFlipped = ContextCompat.getDrawable(holder.binding.root.context, R.drawable.chat_ballon_shape_flipped)

            if (data.member_id == 1) {
                chatBallonDrawableFlipped?.setColorFilter(ContextCompat.getColor(holder.binding.root.context, R.color.lightorange), PorterDuff.Mode.SRC_ATOP)
                chatContent.background = chatBallonDrawableFlipped

                holder.binding.root.gravity = Gravity.END
                chatLayout.gravity = Gravity.END
                chatMemberName.visibility = View.GONE
                imageCard.visibility = View.GONE
            } else {
                chatBallonDrawable?.setColorFilter(ContextCompat.getColor(holder.binding.root.context, R.color.darkorange), PorterDuff.Mode.SRC_ATOP)
                chatContent.background = chatBallonDrawable

                holder.binding.root.gravity = Gravity.START
                chatLayout.gravity = Gravity.START
                chatMemberName.visibility = View.VISIBLE
                imageCard.visibility = View.VISIBLE
                chatMemberName.text = sample_name
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(newData: List<Chat>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    // Instant를 포맷된 String으로 바꾸기
    private fun formatting(timeInstant: Instant): String {
        val localDateTime = LocalDateTime.ofInstant(timeInstant, ZoneId.of("Asia/Seoul"))

        val formatter = DateTimeFormatter.ofPattern("HH:mm")

        val formattedDateTime = localDateTime.format(formatter)

        return formattedDateTime
    }

}
