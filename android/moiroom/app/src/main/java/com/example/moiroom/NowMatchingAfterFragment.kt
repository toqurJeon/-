package com.example.moiroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moiroom.adapter.CardAdapter
import com.example.moiroom.adapter.CardItemClickListener
import com.example.moiroom.data.CardInfo
import com.example.moiroom.databinding.FragmentNowMatchingAfterBinding
import com.example.moiroom.data.MatchedMember
import com.example.moiroom.data.MatchedMemberList
import com.example.moiroom.data.Member
import com.example.moiroom.utils.cacheMatchedMemberList
import com.example.moiroom.utils.cacheUserInfo

class NowMatchingAfterFragment : Fragment() {
    private lateinit var binding: FragmentNowMatchingAfterBinding

    val cachedUserInfo: Member? = cacheUserInfo.get("userInfo")
    val cachedMatchedMemberList: MatchedMemberList? = cacheMatchedMemberList.get("matchedMemberList")

//    private val cardInfoList = TestData.cardInfoList

    // 프래그먼트 뷰 생성 : XML 레이아웃을 이용하여 프래그먼트 뷰 생성
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNowMatchingAfterBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 뷰 생성 이후 동작
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView의 레이아웃 매니저를 설정
        val gridLayoutManager = GridLayoutManager(context, 1)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.viewPager2.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

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
            }
        }

    }

    private fun setCardAdapter(isButton1Checked: Boolean) {
        if (cachedMatchedMemberList != null) {
            val cardAdapter = CardAdapter(cachedMatchedMemberList.content, isButton1Checked, object : CardItemClickListener {
                override fun onCardDetailClick(cardInfo: MatchedMember) {
//                    showDetailFragment(cardInfo)
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
    }

//    private fun showDetailFragment(cardInfo: CardInfo) {
//        val detailFragment = NewCardDetailDialogFragment.newInstance(cardInfo)
//        val oldFragment = parentFragmentManager.findFragmentByTag("cardDetail")
//        oldFragment?.let {
//            parentFragmentManager.beginTransaction().remove(it).commit()
//        }
//        detailFragment.show(parentFragmentManager, "cardDetail")
//        binding.recyclerView.visibility = View.GONE
//        binding.viewPager2.visibility = View.GONE
//    }
}
