package com.example.moiroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.content.Context
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moiroom.adapter.ChatRoomAdapter
import com.example.moiroom.data.ChatRoom
import com.example.moiroom.databinding.FragmentChattingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import kotlin.math.log

class ChattingFragment : Fragment() {

    // 레이아웃 바인딩 선언
    private lateinit var binding: FragmentChattingBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatRoomAdapter
    private val apiService by lazy { NetworkModule.provideRetrofit(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 바인딩 초기화
        binding = FragmentChattingBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // 데이터를 가져오는 함수를 코루틴 스코프 내에서 실행
        lifecycleScope.launch {
            val data = getListOfChatRoomData()
            adapter = ChatRoomAdapter(data)
            recyclerView.adapter = adapter

            adapter.onItemClickListener = object : ChatRoomAdapter.OnItemClickListener {
                override fun onItemClick(memberId: Long, chatRoomId: Long, memberNickname: String, memberProfileImage: String) {
                    // 채팅방 생성 요청
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = apiService.createChatRoom(memberId)

                        if (response.isSuccessful) {
                            // 채팅방 생성 성공
                            withContext(Dispatchers.Main) {
                                // SharedPreferences에 데이터 저장
                                val sharedPref = requireActivity().getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
                                with (sharedPref.edit()) {
                                    putString("memberNickname", memberNickname)
                                    putString("memberProfileImage", memberProfileImage)
                                    putLong("memberId", memberId)
                                    apply()
                                    Log.d("ChattingFragment", "Saved data to SharedPreferences")
                                }

                                // Intent 생성 및 Activity 시작
                                val intent = Intent(requireContext(), ChatActivity::class.java)
                                intent.putExtra("memberId", memberId)
                                intent.putExtra("chatRoomId", chatRoomId)

                                // 데이터 저장 후에 startActivity 호출
                                startActivity(intent)
                            }
                            Log.d("Chatting", "$memberId")
                            Log.d("Chatting", "$chatRoomId")
                            Log.d("Chatting", "$memberNickname")
                            Log.d("Chatting", "$memberProfileImage")
                            Log.d("Chatting", "$memberId")
                        } else {
                            // 채팅방 생성 실패
                            // 에러 처리를 여기서 하세요.
                            // 예: Toast 메시지를 출력하거나, 로그를 기록하는 등
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "채팅방 생성 실패: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private suspend fun getListOfChatRoomData(): List<ChatRoom> {
        val response = apiService.getChatRooms(1)  // 1페이지의 채팅방 목록을 가져옵니다.
        if (response.isSuccessful) {
            return response.body()?.data?.content ?: emptyList()
        } else {
            throw Exception("Failed to load chat rooms")
        }
    }
}
