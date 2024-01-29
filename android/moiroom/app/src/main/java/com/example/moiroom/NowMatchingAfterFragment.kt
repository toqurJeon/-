package com.example.moiroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.moiroom.adapter.CardAdapter
import com.example.moiroom.data.TestData
import com.example.moiroom.databinding.FragmentNowMatchingAfterBinding
import com.google.android.material.button.MaterialButtonToggleGroup

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
        binding.viewPager2.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL  // 방향을 수직으로 설정

        // 체크된 상태로 시작하도록 설정
        binding.toggleButton.check(R.id.button1)

        // 체크된 상태에 따른 초기 화면 설정
        binding.viewPager2.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        setCardAdapter(true)

        // 토글 버튼의 체크 상태에 따라 RecyclerView나 ViewPager2를 보여줌
        binding.toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            if (isChecked) { // 버튼이 체크된 경우에만 화면 전환을 수행합니다.
                when (checkedId) {
                    R.id.button1 -> {
                        binding.viewPager2.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                    R.id.button2 -> {
                        binding.viewPager2.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                    }
                }
                setCardAdapter(checkedId == R.id.button1)
            }
        }
    }

    private fun setCardAdapter(isButton1Checked: Boolean) {
        val cardAdapter = CardAdapter(cardInfoList, isButton1Checked)
        if (isButton1Checked) {
            binding.viewPager2.adapter = cardAdapter
            binding.recyclerView.adapter = null
        } else {
            binding.viewPager2.adapter = null
            binding.recyclerView.adapter = cardAdapter
        }
    }
}
