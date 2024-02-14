package com.example.moiroom.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.R
import com.example.moiroom.data.Interest
import com.example.moiroom.databinding.InterestChartItemLayoutBinding
import com.example.moiroom.databinding.InterestItemLayoutBinding
import com.example.moiroom.utils.getColorInterest

class InterestChartAdapter(private val context: Context, private var dataList: List<Interest>) : RecyclerView.Adapter<InterestChartAdapter.InterestChartViewHolder>() {

    private var totalWidth: Int = 0

    inner class InterestChartViewHolder(val binding: InterestChartItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestChartViewHolder {
        val binding = InterestChartItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return InterestChartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InterestChartViewHolder, position: Int) {
        val data = dataList[position]
        val color = getColorInterest(data.interestName, context)

        holder.binding.apply {
            interestItem.setCardBackgroundColor(color)
            val layoutParams = interestItem.layoutParams
            layoutParams.width = calculateItemWidth(data.interestPercent)
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
}
