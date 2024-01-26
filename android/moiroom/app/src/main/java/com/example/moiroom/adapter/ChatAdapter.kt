import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
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

        val currentMemberId = data.member_id

        holder.binding.apply {
            chatMemberName.text = "${data.member_id}"
            chatContent.text = data.content
            chatCreatedAt.text = time

            val chatBallonDrawable = ContextCompat.getDrawable(holder.binding.root.context, R.drawable.chat_ballon_shape)
            val chatBallonDrawableFlipped = ContextCompat.getDrawable(holder.binding.root.context, R.drawable.chat_ballon_shape_flipped)

            Log.d("current_position", "current_position: $position, ${chatContent.text}")

            if (currentMemberId == 1) {
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

                // 이전 데이터와 현재 데이터를 비교하여 이전 RecyclerView에 영향을 줍니다.
                if (position > dataList.size - 1) {
                    val previousData = dataList[position - 1]
                    val previousMemberId = previousData.member_id
                    val previousTime = formatting(previousData.created_at)

                    val previousViewHolder = holder.binding

                    if (currentMemberId == previousMemberId && time == previousTime) {
                        previousViewHolder.imageCard.visibility = View.INVISIBLE
                        previousViewHolder.chatCreatedAt.visibility = View.VISIBLE
                        previousViewHolder.chatMemberName.visibility = View.GONE
                    } else {
                        previousViewHolder.imageCard.visibility = View.VISIBLE
                        previousViewHolder.chatCreatedAt.visibility = View.GONE
                        previousViewHolder.chatMemberName.visibility = View.VISIBLE
                    }
                }
            }
        }
        // 최상단의 RecyclerView 마진 설정
        if (position == 0) {
            val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            val marginTopPx = dpToPx(holder.itemView.context, 72) // dp를 px로 변환
            layoutParams.setMargins(layoutParams.leftMargin, marginTopPx, layoutParams.rightMargin, layoutParams.bottomMargin)
            holder.itemView.layoutParams = layoutParams
        } else {
            // 마지막 아이템이 아니면 기본 marginTop을 설정
            val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            layoutParams.setMargins(layoutParams.leftMargin, 0, layoutParams.rightMargin, layoutParams.bottomMargin)
            holder.itemView.layoutParams = layoutParams
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
        return localDateTime.format(formatter)
    }

    //margin 설정을 위해 dp를 px로 변환
    fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}
