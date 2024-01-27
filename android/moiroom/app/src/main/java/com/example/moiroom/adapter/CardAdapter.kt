package com.example.moiroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.R
import com.example.moiroom.data.CardInfo

class CardAdapter(private val cardInfoList: List<CardInfo>, private val isToggleButtonChecked: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val matchingRate: TextView = view.findViewById(R.id.matchingRate)
        val name: TextView = view.findViewById(R.id.name)
        val location: TextView = view.findViewById(R.id.location)
    }

    class CardViewHolder1(view: View) : CardViewHolder(view) {
        val summary: TextView = view.findViewById(R.id.summary)
        val profileImage: ImageView = view.findViewById(R.id.profileImage)
    }

    class CardViewHolder2(view: View) : CardViewHolder(view) {
        val introduction: TextView = view.findViewById(R.id.introduction)
        val profileImage: ImageView = view.findViewById(R.id.profileImage)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isToggleButtonChecked) 1 else 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 1) {
            val view = inflater.inflate(R.layout.card_layout, parent, false)
            CardViewHolder1(view)
        } else {
            val view = inflater.inflate(R.layout.card_layout_several, parent, false)
            CardViewHolder2(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cardInfo = cardInfoList[position]
        if (holder is CardViewHolder1) {
            holder.matchingRate.text = "${cardInfo.matchingRate}%"
            holder.summary.text = cardInfo.summary
            holder.profileImage.setImageResource(cardInfo.profileImage)  // 이미지 설정하는 코드
            holder.name.text = cardInfo.name
            holder.location.text = cardInfo.location
        } else if (holder is CardViewHolder2) {
            holder.matchingRate.text = "${cardInfo.matchingRate}%"
            holder.introduction.text = cardInfo.introduction
            holder.profileImage.setImageResource(cardInfo.profileImage)  // 이미지 설정하는 코드 추가
            holder.name.text = cardInfo.name
            holder.location.text = cardInfo.location
        }
    }


    override fun getItemCount() = cardInfoList.size
}
