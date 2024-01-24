package com.example.moiroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

        // RecyclerView에 대한 Adapter 설정
        val CardAdapter = CardAdapter(cardInfoList)
        binding.recyclerView.adapter = CardAdapter

        // 토글 버튼의 체크 상태에 따라 LayoutManager 변경
        binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // 토글 버튼이 체크되면 GridLayoutManager를 설정하여 여러 카드를 보여줌
                binding.recyclerView.layoutManager = GridLayoutManager(context, 2) // 2는 한 줄에 보여줄 아이템의 수
            } else {
                // 토글 버튼이 체크 해제되면 LinearLayoutManager를 설정하여 한 카드씩 보여줌
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
            }
            CardAdapter.notifyDataSetChanged() // LayoutManager 변경 후 Adapter에 알림
        }

        // 초기 상태는 한 명씩 보기로 설정 (필요에 따라 변경 가능)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }
}
