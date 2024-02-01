import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.data.Interest
import com.example.moiroom.databinding.InterestItemLayoutBinding
import com.example.moiroom.utils.getColorInterest

class InterestAdapter(private val context: Context, private var dataList: List<Interest>) : RecyclerView.Adapter<InterestAdapter.InterestViewHolder>() {

    inner class InterestViewHolder(val binding: InterestItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestViewHolder {
        val binding = InterestItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return InterestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InterestViewHolder, position: Int) {
        val data = dataList[position]
        val color = getColorInterest(data.interestName, context)

        holder.binding.apply {
            interestName.text = data.interestName
            interestPercent.text = "${data.interestPercent}%"
            cardBackground.setCardBackgroundColor(color)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
