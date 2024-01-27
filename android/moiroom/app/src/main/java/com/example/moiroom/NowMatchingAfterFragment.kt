package com.example.moiroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.moiroom.adapter.CardAdapter
import com.example.moiroom.data.TestData
import com.example.moiroom.databinding.FragmentNowMatchingAfterBinding

class NowMatchingAfterFragment : Fragment() {
    private lateinit var binding: FragmentNowMatchingAfterBinding
    private val cardInfoList = TestData.cardInfoList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNowMatchingAfterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView의 레이아웃 매니저를 설정
        val gridLayoutManager = GridLayoutManager(context, 1) // 여기서 1은 한 줄에 표시될 아이템 수를 의미합니다.
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.viewPager2.visibility = View.VISIBLE
        binding.viewPager2.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL  // 방향을 수직으로 설정

        // 토글 버튼의 체크 상태에 따라 CardAdapter 설정
        setCardAdapter(binding.toggleButton.isChecked)

        binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.viewPager2.visibility = View.VISIBLE
                binding.viewPager2.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL  // 방향을 수직으로 설정
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.viewPager2.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.recyclerView.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            }
            // 토글 버튼의 체크 상태가 변경될 때마다 CardAdapter를 다시 설정
            setCardAdapter(isChecked)
        }
    }




    private fun setCardAdapter(isToggleButtonChecked: Boolean) {
        val cardAdapter = CardAdapter(cardInfoList, isToggleButtonChecked)
        if (isToggleButtonChecked) {
            binding.viewPager2.adapter = cardAdapter
            binding.recyclerView.adapter = null // RecyclerView에 null을 설정하여 어댑터를 제거합니다.
        } else {
            binding.viewPager2.adapter = null // ViewPager2에 null을 설정하여 어댑터를 제거합니다.
            binding.recyclerView.adapter = cardAdapter
        }
    }
}
