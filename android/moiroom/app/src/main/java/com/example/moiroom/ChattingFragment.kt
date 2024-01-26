package com.example.moiroom

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.adapter.ChatRoomAdapter
import com.example.moiroom.data.ChatRoom
import com.example.moiroom.databinding.FragmentChattingBinding
import java.time.Instant

class ChattingFragment : Fragment() {

    // 레이아웃 바인딩 선언
    private lateinit var binding: FragmentChattingBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatRoomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 레이아웃 바인딩 초기화
        binding = FragmentChattingBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // 데이터를 가져오는 함수
        val data = getListOfChatRoomData()
        adapter = ChatRoomAdapter(data)
        recyclerView.adapter = adapter

        adapter.onItemClickListener = object : ChatRoomAdapter.OnItemClickListener {
            override fun onItemClick(chatRoomId: Int) {
                val intent = Intent(requireContext(), ChatActivity::class.java)
                intent.putExtra("chatRoomId", chatRoomId)
                startActivity(intent)
            }
        }

        return binding.root
    }

    private fun getListOfChatRoomData(): List<ChatRoom> {
        return listOf(
            ChatRoom(1, "안녕하세요. 룸메이트 구하고 있습니다.", Instant.parse("2024-01-23T12:34:56Z")),
            ChatRoom(2, "안녕하세요. 제가요 룸메이트를 구하고 있걸랑요? 그런데 이게 참 어렵습니다. 그 어려운 것이 그냥 어려운 것이 아니고 참 어렵다니까요?", Instant.parse("2024-01-24T12:34:56Z"))
        )
    }
}