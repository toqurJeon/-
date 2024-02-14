import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moiroom.R
import com.example.moiroom.data.Chat
import com.example.moiroom.data.UserResponse
import com.example.moiroom.databinding.ChatItemLayoutBinding
import com.example.moiroom.utils.CachedUserInfoLiveData
import com.example.moiroom.utils.getUserInfo
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ChatAdapter(
    private var dataList: MutableList<Chat>,
    private final var context: Context
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    var cachedUserInfo: UserResponse.Data.Member? = CachedUserInfoLiveData.cacheUserInfo.get("userInfo")

    inner class ChatViewHolder(val binding: ChatItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var nextViewHolder: ChatItemLayoutBinding? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        if (cachedUserInfo == null) {
            getUserInfo(context)
            cachedUserInfo = CachedUserInfoLiveData.cacheUserInfo.get("userInfo")
        }

        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val data = dataList[position]

        val time: String = formatting(data.createdAt)
        val sample_name = "김민수"

        val currentMemberId = data.memberId

        holder.binding.apply {
            chatMemberName.text = "${data.memberId}"
            chatContent.text = data.content
            chatCreatedAt.text = time

            // Glide를 사용하여 이미지 로드
            Glide.with(root.context)
                .load(data.memberProfileImage)
                .into(chatMemberImage)

            // 이전 메세지와 비교

            val chatBallonDrawable = ContextCompat.getDrawable(holder.binding.root.context, R.drawable.chat_ballon_shape)
            val chatBallonDrawableFlipped = ContextCompat.getDrawable(holder.binding.root.context, R.drawable.chat_ballon_shape_flipped)

            Log.d("current_position", "current_position: $position, ${chatContent.text}")

            if (currentMemberId == cachedUserInfo?.memberId) {
                chatBallonDrawableFlipped?.setColorFilter(ContextCompat.getColor(holder.binding.root.context, R.color.lightorange), PorterDuff.Mode.SRC_ATOP)
                chatContent.background = chatBallonDrawableFlipped

                holder.binding.root.gravity = Gravity.END
                chatLayout.gravity = Gravity.END
                chatMemberName.visibility = View.GONE
                imageCard.visibility = View.GONE
                chatCreatedAt.visibility = View.GONE

                if (position == (dataList.size - 1)) {
                    chatCreatedAt.visibility = View.VISIBLE

                } else if (position == 0) {
                    val downData = dataList[position + 1]
                    val downMemberId = downData.memberId
                    val downTime = formatting(downData.createdAt)

                    if (currentMemberId != downMemberId || time != downTime) {
                        chatCreatedAt.visibility = View.VISIBLE
                    }
                } else {
                    val downData = dataList[position + 1]
                    val downMemberId = downData.memberId
                    val downTime = formatting(downData.createdAt)

                    if (currentMemberId != downMemberId || time != downTime) {
                        chatCreatedAt.visibility = View.VISIBLE
                    }
                }

            } else {
                chatBallonDrawable?.setColorFilter(ContextCompat.getColor(holder.binding.root.context, R.color.darkorange), PorterDuff.Mode.SRC_ATOP)
                chatContent.background = chatBallonDrawable

                holder.binding.root.gravity = Gravity.START
                chatLayout.gravity = Gravity.START
                chatMemberName.visibility = View.GONE
                imageCard.visibility = View.INVISIBLE
                chatCreatedAt.visibility = View.GONE

                chatMemberName.text = sample_name


                // dataList.size 는 data의 갯수
                // position은 0부터 dataList.size - 1 까지 (size == 15, position -> 14,13,...,1,0)
                if (position == (dataList.size - 1)) {
                    val upData = dataList[position - 1]
                    val upMemberId = upData.memberId
                    val upTime = formatting(upData.createdAt)

                    chatCreatedAt.visibility = View.VISIBLE

                    if (currentMemberId != upMemberId || time != upTime) {
                        imageCard.visibility = View.VISIBLE
                        chatMemberName.visibility = View.VISIBLE
                    }
                } else if (position == 0) {
                    val downData = dataList[position + 1]
                    val downMemberId = downData.memberId
                    val downTime = formatting(downData.createdAt)

                    imageCard.visibility = View.VISIBLE
                    chatMemberName.visibility = View.VISIBLE

                    if (currentMemberId != downMemberId || time != downTime) {
                        chatCreatedAt.visibility = View.VISIBLE
                    }
                } else {
                    val upData = dataList[position - 1]
                    val downData = dataList[position + 1]

                    val upMemberId = upData.memberId
                    val downMemberId = downData.memberId

                    val upTime = formatting(upData.createdAt)
                    val downTime = formatting(downData.createdAt)

                    if (currentMemberId != upMemberId || time != upTime) {
                        imageCard.visibility = View.VISIBLE
                        chatMemberName.visibility = View.VISIBLE
                    }
                    if (currentMemberId != downMemberId || time != downTime) {
                        chatCreatedAt.visibility = View.VISIBLE
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

        holder.nextViewHolder = holder.binding
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
    private fun formatting(timeString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val localDateTime = LocalDateTime.parse(timeString, formatter)
        return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    //margin 설정을 위해 dp를 px로 변환
    fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}
