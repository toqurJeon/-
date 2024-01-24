package com.example.moiroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.R
import com.example.moiroom.data.CardInfo

class CardAdapter(private val cardInfoList: List<CardInfo>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val matchingRate: TextView = view.findViewById(R.id.matchingRate)
        val summary: TextView = view.findViewById(R.id.summary)
        val profileImage: ImageView = view.findViewById(R.id.profileImage)
        val name: TextView = view.findViewById(R.id.name)
        val location: TextView = view.findViewById(R.id.location)
        val introduction: TextView = view.findViewById(R.id.introduction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cardInfo = cardInfoList[position]
        holder.matchingRate.text = "${cardInfo.matchingRate}%"
        holder.summary.text = cardInfo.summary
        holder.profileImage.setImageResource(cardInfo.profileImage)
        holder.name.text = cardInfo.name
        holder.location.text = cardInfo.location
        holder.introduction.text = cardInfo.introduction
    }

    override fun getItemCount() = cardInfoList.size
}
