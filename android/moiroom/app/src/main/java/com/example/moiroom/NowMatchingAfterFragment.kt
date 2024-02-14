package com.example.moiroom

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moiroom.adapter.CardAdapter
import com.example.moiroom.data.MatchedMember
import com.example.moiroom.data.MatchedMemberData
import com.example.moiroom.databinding.FragmentNowMatchingAfterBinding
import com.example.moiroom.data.ResponseData
import com.example.moiroom.data.UserResponse
import com.example.moiroom.databinding.DialogCharacterInformationBinding
import com.example.moiroom.utils.CachedMatchedMemberListLiveData
import com.example.moiroom.utils.CachedMatchedMemberListLiveData.cacheMatchedMemberList
import com.example.moiroom.utils.CachedUserInfoLiveData
import com.example.moiroom.utils.CachedUserInfoLiveData.cacheUserInfo
import com.example.moiroom.utils.getCharacterDetailDescription
import com.example.moiroom.utils.getMatchedMember

class NowMatchingAfterFragment : Fragment(), CardAdapter.OnCharcterClickListener {
    private lateinit var binding: FragmentNowMatchingAfterBinding
    private var toggled: Boolean = true

    var cachedUserInfo: UserResponse.Data.Member? = cacheUserInfo.get("userInfo")
    var cachedMatchedMemberList: ResponseData? = cacheMatchedMemberList.get("matchedMemberList")

    var matchedMemberListForAdapter = mutableListOf<MatchedMemberData>()

    var currentPageNumber: Int = 0
    var currentPageItems: Int = 0
    var totalPage: Int = 0
    var totalItems: Int = 0

    var currentScrollPosition: Int = 0
    var currentViewPagerPosition: Int = 0

    companion object {
        var isLoading: Boolean = false
    }

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

        Log.d("MYTAG", "Now Matching After Fragment View Created.")
        Log.d("MYTAG", "Member: ${cachedMatchedMemberList?.data}")

        CachedUserInfoLiveData.observe(viewLifecycleOwner) { userInfo ->
            Log.d("MYTAG", "onCreateView: 캐시 데이터 변경 감지 in 매칭 결과 페이지 of 사용자 데이터")
            cachedUserInfo = cacheUserInfo.get("userInfo")

            if (cachedUserInfo != null) {
                setCardAdapter(toggled)
            }
        }

        CachedMatchedMemberListLiveData.observe(viewLifecycleOwner) {matchedMemberList ->
            Log.d("MYTAG", "onCreateView: 캐시 데이터 변경 감지 in 매칭 결과 페이지 of 매칭 멤버 리스트")
            cachedMatchedMemberList = cacheMatchedMemberList.get("matchedMemberList")

            if (cachedMatchedMemberList != null) {
                Log.d("MYTAG", "nowMatchingAfterFragment: 매칭 멤버 리스트가 null이 아님.")
                Log.d("MYTAG", "nowMatchingAfterFragment: 현재 페이지 $currentPageNumber, 받아온 페이지 ${cachedMatchedMemberList!!.data.currentPage}, 총페이지 ${cachedMatchedMemberList!!.data.totalPages}")
                totalPage = cachedMatchedMemberList!!.data.totalPages
                totalItems = cachedMatchedMemberList!!.data.totalElements
                currentPageItems = cachedMatchedMemberList!!.data.pageSize
                binding.totalCard.text = "$totalItems"

                if (currentPageNumber < cachedMatchedMemberList!!.data.currentPage) {
                    Log.d("MYTAG", "nowMatchingAfterFragment: 새로운 페이지 받기")
                    for (member in cachedMatchedMemberList!!.data.content) {
                        matchedMemberListForAdapter.add(member)
                        currentPageNumber = cachedMatchedMemberList!!.data.currentPage
                    }
                    Log.d("MYTAG", "nowMatchingAfterFragment: 새로운 페이지를 받아서 새롭게 어댑터 세팅합니다.")
                    setCardAdapter(toggled)
                    (binding.recyclerView.layoutManager as LinearLayoutManager).scrollToPosition(currentScrollPosition)
                    binding.viewPager2.currentItem = currentViewPagerPosition
                }
            }
        }

        // RecyclerView의 레이아웃 매니저를 설정
//        val gridLayoutManager = GridLayoutManager(context, 1)
//        binding.recyclerView.layoutManager = gridLayoutManager
        binding.viewPager2.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        // 체크된 상태에 따른 초기 화면 설정
        binding.viewPager2.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        setCardAdapter(toggled)

        // 무한 스크롤
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // Log.d("MYTAG", "nowMatchingAfterFragment: 스크롤 감지")

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && currentPageNumber != totalPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        // 다음 페이지 데이터 요청
                        // 여기에서는 예를 들어 API 호출을 통해 새로운 데이터를 가져올 수 있습니다.
                        Log.d("MYTAG", "nowMatchingAfterFragment: 무한 스크롤, 데이터 추가받기")
                        currentScrollPosition = (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                        isLoading = true
                        getMatchedMember(requireContext(), currentPageNumber + 1)
                    }
                }
            }
        })

        // 현재 몇번째 뷰페이저를 보고 있는지 확인
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // 새 페이지가 선택되었을 때 호출됩니다.
                Log.d("MYTAG", "nowMatchingAfterFragment: viewPager $position, total Items: $totalItems")
                binding.currentCard.text = "${position + 1}"

                if (position == 0) {
                    binding.pagerLeft.visibility = View.GONE
                    binding.pagerRight.visibility = View.VISIBLE
                } else if (position + 1 == totalItems) {
                    binding.pagerLeft.visibility = View.VISIBLE
                    binding.pagerRight.visibility = View.GONE
                } else {
                    binding.pagerLeft.visibility = View.VISIBLE
                    binding.pagerRight.visibility = View.VISIBLE
                }

                Log.d("MYTAG", "nowMatchingAfterFragment: viewPager, ${position + 1}, ${currentPageItems * currentPageNumber}")
                if (!isLoading && position + 1 == currentPageItems * currentPageNumber) {
                    if (currentPageNumber != totalPage) {
                        Log.d("MYTAG", "nowMatchingAfterFragment: viewPager, 추가 데이터 요청 예정")
                        isLoading = true
                        getMatchedMember(requireContext(), currentPageNumber + 1)
                        currentViewPagerPosition = position
                        // 로직 구현할 위치
                    }
                }
            }
        })

        binding.layoutChanger.setOnClickListener {
            toggled = !toggled
            if (toggled) {
                setToViewPager()
            } else {
                setToRecyclerView()
            }
            setCardAdapter(toggled)
        }

        binding.reMatchButton.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isRematching", true)
            editor.apply()

            currentPageNumber = 0
            currentPageItems = 0
            totalPage = 0
            totalItems = 0

            currentScrollPosition = 0
            currentViewPagerPosition = 0

            val intent = Intent(context, NowMatchingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setToViewPager() {
        val iconListDrawable: Drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_chat_several)!!

        binding.viewPager2.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.cardIndicator.visibility = View.VISIBLE

        binding.layoutChangerIcon.setImageDrawable(iconListDrawable)
    }

    private fun setToRecyclerView() {
        val iconDrawable: Drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_chat_one)!!

        binding.viewPager2.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.cardIndicator.visibility = View.GONE

        binding.pagerLeft.visibility = View.GONE
        binding.pagerRight.visibility = View.GONE

        binding.layoutChangerIcon.setImageDrawable(iconDrawable)
    }

    private fun setCardAdapter(isButton1Checked: Boolean) {
        Log.d("MYTAG", "Now Matching After Fragment , 새롭게 데이터 어댑터 세팅")
        if (matchedMemberListForAdapter.isNotEmpty() && cachedUserInfo != null) {
            // CardAdapter 생성자에 UserResponse.Data.Member 타입의 cachedUserInfo를 전달
            val cardAdapter = CardAdapter(requireContext(), matchedMemberListForAdapter, cachedUserInfo!!, isButton1Checked)

            cardAdapter.setOnCharacterClickListener(this)

            if (isButton1Checked) {
                binding.viewPager2.adapter = cardAdapter
                binding.recyclerView.adapter = null
            } else {
                binding.viewPager2.adapter = null
                binding.recyclerView.adapter = cardAdapter

                cardAdapter.setOnItemClickListener { position ->
                    Log.d("MYTAG", "setCardAdapter: $position")
                    val cardAdapter2 = CardAdapter(requireContext(), matchedMemberListForAdapter, cachedUserInfo!!, !isButton1Checked)
                    setToViewPager()

                    binding.viewPager2.adapter = cardAdapter2
                    binding.recyclerView.adapter = null
                    toggled = !toggled
                    binding.viewPager2.currentItem = position

                }
            }
        }
    }

    override fun onCharacterDescriptionClicked(description: String) {
        Log.d("TAG", "onCharacterDescriptionClicked: in Fragment $description")

        val dialog = Dialog(requireContext(), R.style.DialogTheme)
        val dialogBinding = DialogCharacterInformationBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.characterTitle.text = "$description"
        val detailDescription = getCharacterDetailDescription(description)
        dialogBinding.characterDescription.text = detailDescription

        dialogBinding.confirmButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
