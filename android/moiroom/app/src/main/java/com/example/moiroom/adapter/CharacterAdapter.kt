package com.example.moiroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.data.RadarChartData
import com.example.moiroom.databinding.CharacteristicItemLayoutBinding
import com.example.moiroom.utils.getBGColorCharacter
import com.example.moiroom.utils.getColorCharacter

class CharacterAdapter(
    private val context: Context,
    private var dataList: MutableList<RadarChartData>,
    private val onItemClick: (RadarChartData, Int) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class CharacterViewHolder(val binding: CharacteristicItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        // 어댑터가 생성될 때 첫 번째 아이템을 클릭하는 메서드를 호출
        if (dataList.isNotEmpty()) {
            onItemClick(dataList[0], 0)
            selectedPosition = 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacteristicItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val data = dataList[position]
        val color = getBGColorCharacter(data.type.value, context)
        val defaultColor = getColorCharacter("default", context)

        holder.binding.apply {
            characterName.text = data.type.value
            characterPercentage.text = "${data.value}%"

            // 아이템 클릭 시
            root.setOnClickListener {
                onItemClick(data, holder.adapterPosition)
                // 선택한 RecyclerView의 배경색 변경
                characterCard.setCardBackgroundColor(color)
                // 이전에 선택한 RecyclerView의 배경색을 기본 값으로 변경
                if (selectedPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(selectedPosition)
                }
                // 선택한 위치를 저장하여 추적
                selectedPosition = holder.adapterPosition
            }
        }

        // 현재 위치가 이전에 선택한 위치와 같은지 확인하여 배경색을 설정
        holder.binding.characterCard.setCardBackgroundColor(
            if (position == selectedPosition) color else defaultColor
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
