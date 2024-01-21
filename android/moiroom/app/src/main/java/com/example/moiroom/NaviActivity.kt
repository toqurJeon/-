package com.example.moiroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.moiroom.databinding.ActivityNaviBinding


private const val TAG_NOW_MATCHING = "now_matching_fragment"
private const val TAG_CHATTING = "chatting_fragment"
private const val TAG_MY_PAGE = "my_page_fragment"

class NaviActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setFragment(TAG_NOW_MATCHING, NowMatchingFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nowMatchingFragment -> setFragment(TAG_NOW_MATCHING, NowMatchingFragment())
                R.id.chattingFragment -> setFragment(TAG_CHATTING, ChattingFragment())
                R.id.myPageFragment-> setFragment(TAG_MY_PAGE, MyPageFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val nowMatching = manager.findFragmentByTag(TAG_NOW_MATCHING)
        val chatting = manager.findFragmentByTag(TAG_CHATTING)
        val myPage = manager.findFragmentByTag(TAG_MY_PAGE)

        if (nowMatching != null){
            fragTransaction.hide(nowMatching)
        }

        if (chatting != null){
            fragTransaction.hide(chatting)
        }

        if (myPage != null) {
            fragTransaction.hide(myPage)
        }

        if (tag == TAG_NOW_MATCHING) {
            if (nowMatching!=null){
                fragTransaction.show(nowMatching)
            }
        }
        else if (tag == TAG_CHATTING) {
            if (chatting != null) {
                fragTransaction.show(chatting)
            }
        }

        else if (tag == TAG_MY_PAGE){
            if (myPage != null){
                fragTransaction.show(myPage)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}