package com.example.moiroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.R
import com.example.moiroom.data.Interest
import com.example.moiroom.databinding.LayoutInterestChartItemBinding
import com.example.moiroom.utils.getColorInterest

class InterestChartAdapter(
    private val context: Context,
    private var dataList: List<Interest>,
    private var onItemClick: ((Interest) -> Unit)? = null
    ) : RecyclerView.Adapter<InterestChartAdapter.InterestChartViewHolder>() {

    private var totalWidth: Int = 0
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION
    private var selectedInterestName: String? = null

    inner class InterestChartViewHolder(val binding: LayoutInterestChartItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
            init {
                itemView.setOnClickListener(this)
            }

        override fun onClick(v: View?) {
            onItemClick?.invoke(dataList[adapterPosition])
            selectedItemPosition = adapterPosition
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestChartViewHolder {
        val binding = LayoutInterestChartItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return InterestChartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InterestChartViewHolder, position: Int) {
        val data = dataList[position]
        val color = getColorInterest(data.interestName, context)

        holder.binding.apply {
            if (data.interestName == selectedInterestName) {
                interestItem.setCardBackgroundColor(color)
            } else {
                interestItem.setCardBackgroundColor(context.getColor(R.color.gray_high_brightness_max))
            }
            val layoutParams = interestItem.layoutParams
            layoutParams.width = calculateItemWidth((data.interestPercent / 100).toInt())
            interestItem.layoutParams = layoutParams
            // Log.d("MYTAG", "onBindViewHolder: ${calculateItemWidth(data.interestPercent)}")
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setTotalWidth(width: Int) {
        totalWidth = width
        notifyDataSetChanged()
    }

    private fun calculateItemWidth(percent: Int): Int {
        val margin = context.resources.getDimensionPixelSize(R.dimen.interest_item_margin)
        val totalMargin = margin * (itemCount)
        val availableWidth = totalWidth - totalMargin
        return (availableWidth * percent / 100)
    }

    // 클릭한 아이템의 데이터를 외부로 전달하는 메서드
    fun getSelectedItem(): Interest? {
        return if (selectedItemPosition != RecyclerView.NO_POSITION) {
            dataList[selectedItemPosition]
        } else {
            null
        }
    }

    fun selectInterestByName(interestName: String) {
        selectedInterestName = interestName
        notifyDataSetChanged()
    }
}
