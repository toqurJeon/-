package com.example.moiroom.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.R
import com.example.moiroom.data.CombinedInterest
import com.example.moiroom.databinding.InterestTableItemLayoutBinding
import com.example.moiroom.utils.getColorInterest
import com.example.moiroom.utils.getInterestName
import java.text.DecimalFormat

class InterestTableItemAdapter(
    private val context: Context,
    private var dataList: List<CombinedInterest>,
    private val onItemClick: ((CombinedInterest) -> Unit)? = null
) : RecyclerView.Adapter<InterestTableItemAdapter.InterestTableItemViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION
    private var selectedInterestName: String? = null

    inner class InterestTableItemViewHolder(val binding: InterestTableItemLayoutBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick?.invoke(dataList[position])
                selectedPosition = position
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestTableItemViewHolder {
        val binding = InterestTableItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return InterestTableItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InterestTableItemViewHolder, position: Int) {
        val data = dataList[position]
        val color = getColorInterest(data.interestName, context)
        val decimalFormat = DecimalFormat("#.#")

        holder.binding.apply {
            interestTableName.text = getInterestName(data.interestName)
            // interestTableCard.setCardBackgroundColor(color)
            if (position == selectedPosition) {
                interestTableCard.setCardBackgroundColor(color)
            } else {
                interestTableCard.setCardBackgroundColor(context.getColor(R.color.transparency))
            }

            if (data.myInterestPercent != null && data.roommateInterestPercent != null) {

                interestTableRoommatePercent.text = "${decimalFormat.format(data.roommateInterestPercent/100)}%"
                interestTableMyPercent.text = "${decimalFormat.format(data.myInterestPercent/100)}%"

            } else if (data.roommateInterestPercent != null) {

                interestTableRoommatePercent.text = "${decimalFormat.format(data.roommateInterestPercent/100)}%"
                interestTableMyPercent.text = ""

            } else if (data.myInterestPercent != null) {

                interestTableRoommatePercent.text = ""
                interestTableMyPercent.text = "${decimalFormat.format(data.myInterestPercent/100)}%"

            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    // 클릭한 아이템의 데이터를 외부로 전달하는 메서드
    fun getSelectedItem(): CombinedInterest? {
        return if (selectedPosition != RecyclerView.NO_POSITION) {
            dataList[selectedPosition]
        } else {
            null
        }
    }

    // 초기에 첫 번째 아이템을 선택하도록 하는 메서드
    fun selectFirstItem() {
        if (dataList.isNotEmpty()) {
            selectedPosition = 0
            notifyDataSetChanged()
        }
    }
    // 특정 String과 일치하는 interestName을 가진 아이템을 클릭하도록 하는 메서드
    fun selectInterestByName(interestName: String) {
        selectedInterestName = interestName
        selectedPosition = dataList.indexOfFirst { it.interestName == interestName }
        if (selectedPosition != RecyclerView.NO_POSITION) {
            notifyDataSetChanged()
        }
    }
}
