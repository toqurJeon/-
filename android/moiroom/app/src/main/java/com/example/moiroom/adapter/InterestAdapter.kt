package com.example.moiroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.R
import com.example.moiroom.data.Interest
import com.example.moiroom.databinding.LayoutInterestItemBinding
import com.example.moiroom.utils.getColorInterest
import com.example.moiroom.utils.getInterestName
import java.text.DecimalFormat

class InterestAdapter(private val context: Context, private var dataList: List<Interest>) : RecyclerView.Adapter<InterestAdapter.InterestViewHolder>() {

    inner class InterestViewHolder(val binding: LayoutInterestItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestViewHolder {
        val binding = LayoutInterestItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return InterestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InterestViewHolder, position: Int) {
        val data = dataList[position]
        val color = getColorInterest(data.interestName, context)
        val decimalFormat = DecimalFormat("#.#")

        holder.binding.apply {
            interestName.text = getInterestName(data.interestName)
            interestPercent.text = "${decimalFormat.format(data.interestPercent/100)}%"
            cardBackground.setCardBackgroundColor(color)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
