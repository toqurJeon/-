package com.example.moiroom

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.moiroom.databinding.ActivityNaviBinding


private const val TAG_NOW_MATCHING_AFTER = "now_matching_after_fragment"
private const val TAG_CHATTING = "chatting_fragment"
private const val TAG_MY_PAGE = "my_page_fragment"

class NaviActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNaviBinding

    //뒤로가기 버튼 누른 시간
    var backPressedTime: Long = 0

    companion object {
        var isUpdateCalled = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if (isUpdateCalled) {
//            setFragment(TAG_MY_PAGE, MyPageFragment())
//            isUpdateCalled = false
//        } else {
//            setFragment(TAG_NOW_MATCHING_AFTER, NowMatchingAfterFragment())
//        }

        setFragment(TAG_NOW_MATCHING_AFTER, NowMatchingAfterFragment())

        binding.navigationView.background.alpha = (255 * 0.7).toInt()

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nowMatchingFragment -> setFragment(TAG_NOW_MATCHING_AFTER, NowMatchingAfterFragment())
                R.id.chattingFragment -> setFragment(TAG_CHATTING, ChattingFragment())
                R.id.myPageFragment-> setFragment(TAG_MY_PAGE, MyPageFragment())
            }
            true
        }
    }

    override fun onBackPressed() {

        //현재시간보다 크면 종료
        if(backPressedTime + 3000 > System.currentTimeMillis()){

            super.onBackPressed()
            finish()//액티비티 종료
        }else{
            Toast.makeText(applicationContext, "한번 더 뒤로가기 버튼을 누르면 종료됩니다.",
                Toast.LENGTH_SHORT).show()
        }

        //현재 시간 담기
        backPressedTime = System.currentTimeMillis()
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
