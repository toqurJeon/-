package com.example.moiroom.adapter

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moiroom.data.CardInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.example.moiroom.CardDetailFragment


class CardDetailPagerAdapter(
    private val cardInfoList: List<CardInfo>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = cardInfoList.size

    override fun createFragment(position: Int): Fragment {
        return CardDetailFragment.newInstance(cardInfoList[position])
    }
}
