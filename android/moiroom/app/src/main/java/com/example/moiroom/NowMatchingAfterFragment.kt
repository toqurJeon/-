package com.example.moiroom

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.moiroom.adapter.CardAdapter
import com.example.moiroom.adapter.CardDetailPagerAdapter
import com.example.moiroom.adapter.CardItemClickListener
import com.example.moiroom.data.CardInfo
import com.example.moiroom.data.TestData
import com.example.moiroom.data.TestData.cardInfoList
import com.example.moiroom.databinding.FragmentNowMatchingAfterBinding
import com.google.android.material.button.MaterialButtonToggleGroup

interface OnDetailBackClickListener {
    fun onBackClick()
}

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

        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
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
                hideDetailFragment()
            }
        }

        // 백스택 변경 리스너 등록
        activity?.supportFragmentManager?.addOnBackStackChangedListener {
            // 현재 표시되고 있는 프래그먼트를 찾습니다.
            val fragment = parentFragmentManager.findFragmentById(R.id.cardDetail)

            // 프래그먼트가 없을 경우, RecyclerView나 ViewPager2를 다시 표시합니다.
            if (fragment == null) {
                if (binding.toggleButton.checkedButtonId == R.id.button1) {
                    binding.viewPager2.visibility = View.VISIBLE
                } else {
                    binding.recyclerView.visibility = View.VISIBLE
                }
                // 프래그먼트 컨테이너를 숨깁니다.
                binding.cardDetail.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // 현재 표시되고 있는 프래그먼트를 찾습니다.
        val fragment = parentFragmentManager.findFragmentById(R.id.cardDetail)

        // 프래그먼트가 없을 경우, RecyclerView나 ViewPager2를 다시 표시합니다.
        if (fragment == null) {
            if (binding.toggleButton.checkedButtonId == R.id.button1) {
                binding.viewPager2.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
            }
        }

        // 프래그먼트 컨테이너를 숨깁니다.
        binding.cardDetail.visibility = View.GONE
    }


    private fun hideDetailFragment() {
        // 현재 표시되고 있는 프래그먼트를 찾습니다.
        val fragment = parentFragmentManager.findFragmentById(R.id.cardDetail)

        // 프래그먼트가 있을 경우, 해당 프래그먼트를 제거합니다.
        if (fragment != null) {
            parentFragmentManager.beginTransaction().remove(fragment).commit()
        }

        if (binding.toggleButton.checkedButtonId == R.id.button1) {
            binding.viewPager2.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
        }

        setCardAdapter(binding.toggleButton.checkedButtonId == R.id.button1)
    }

    private fun setCardAdapter(isButton1Checked: Boolean) {
        val cardAdapter = CardAdapter(cardInfoList, isButton1Checked, object : CardItemClickListener {
            override fun onCardDetailClick(cardInfo: CardInfo) {
                showDetailFragment(cardInfo) // 이 메서드 내에서 프래그먼트 전환 로직을 처리합니다.
            }
        })
        if (isButton1Checked) {
            binding.viewPager2.adapter = cardAdapter
            binding.recyclerView.adapter = null
        } else {
            binding.viewPager2.adapter = null
            binding.recyclerView.adapter = cardAdapter
        }
    }

    private fun showDetailFragment(cardInfo: CardInfo) {
        val detailFragment = CardDetailFragment.newInstance(cardInfo)
        parentFragmentManager.beginTransaction().apply {
            // RecyclerView나 ViewPager2가 표시되는 뷰를 대체합니다.
            add(R.id.cardDetail, detailFragment)
            addToBackStack(null) // 사용자가 뒤로 가기를 눌렀을 때 이전 화면으로 돌아갈 수 있도록 합니다.
            commit()
        }
        // RecyclerView와 ViewPager2를 숨기고, 프래그먼트 컨테이너를 표시합니다.
        binding.recyclerView.visibility = View.GONE
        binding.viewPager2.visibility = View.GONE
        binding.cardDetail.visibility = View.VISIBLE // 프래그먼트 컨테이너를 표시합니다.
    }


}

