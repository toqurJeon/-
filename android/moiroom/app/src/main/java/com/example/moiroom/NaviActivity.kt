package com.example.moiroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.moiroom.databinding.ActivityNaviBinding


private const val TAG_NOW_MATCHING_AFTER = "now_matching_after_fragment"
private const val TAG_CHATTING = "chatting_fragment"
private const val TAG_MY_PAGE = "my_page_fragment"

class NaviActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setFragment(TAG_NOW_MATCHING_AFTER, NowMatchingAfterFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nowMatchingFragment -> setFragment(TAG_NOW_MATCHING_AFTER, NowMatchingAfterFragment())
                R.id.chattingFragment -> setFragment(TAG_CHATTING, ChattingFragment())
                R.id.myPageFragment-> setFragment(TAG_MY_PAGE, MyPageFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        val nowMatchingAfter = manager.findFragmentByTag(TAG_NOW_MATCHING_AFTER)
        val chatting = manager.findFragmentByTag(TAG_CHATTING)
        val myPage = manager.findFragmentByTag(TAG_MY_PAGE)

        if (nowMatchingAfter != null){
            fragTransaction.hide(nowMatchingAfter)
        }

        if (chatting != null){
            fragTransaction.hide(chatting)
        }

        if (myPage != null) {
            fragTransaction.hide(myPage)
        }

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.replace(R.id.mainFrameLayout, fragment, tag)
        } else {
            when(tag) {
                TAG_NOW_MATCHING_AFTER -> nowMatchingAfter?.let { fragTransaction.show(it) }
                TAG_CHATTING -> chatting?.let { fragTransaction.show(it) }
                TAG_MY_PAGE -> myPage?.let { fragTransaction.show(it) }
            }
        }

        fragTransaction.commit()
    }
}
