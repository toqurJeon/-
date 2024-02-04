package com.example.moiroom

//import OnBackButtonClickListener
import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.system.Os.remove
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moiroom.adapter.CardAdapter
import com.example.moiroom.adapter.CardDetailPagerAdapter
import com.example.moiroom.adapter.CardItemClickListener
import com.example.moiroom.data.CardInfo
import com.example.moiroom.data.TestData
import com.example.moiroom.data.TestData.cardInfoList
import com.example.moiroom.databinding.FragmentNowMatchingAfterBinding
import com.google.android.material.button.MaterialButtonToggleGroup
import com.example.moiroom.OnBackButtonClickListener

class NowMatchingAfterFragment : Fragment(), OnBackButtonClickListener {
    private lateinit var binding: FragmentNowMatchingAfterBinding
    private val cardInfoList = TestData.cardInfoList
    private var currentPage: Int = 0

    // OnBackButtonClickListener 인터페이스의 메서드 구현
    override fun onBackButtonClicked() {
        hideDetailFragment()
    }

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
        val gridLayoutManager = GridLayoutManager(context, 1)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.viewPager2.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL

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

        activity?.supportFragmentManager?.addOnBackStackChangedListener {
            val fragment = parentFragmentManager.findFragmentByTag("cardDetail")
            if (fragment == null) {
                if (binding.toggleButton.checkedButtonId == R.id.button1) {
                    binding.viewPager2.visibility = View.VISIBLE
                } else {
                    binding.recyclerView.visibility = View.VISIBLE
                }
                binding.cardDetail.visibility = View.GONE
            }
        }

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage = position
            }
        })

        savedInstanceState?.let {
            val restoredPage = it.getInt("current_page", 0)
            binding.viewPager2.setCurrentItem(restoredPage, false)
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("NowMatchingAfterFragment", "onResume")
        val fragment = parentFragmentManager.findFragmentById(R.id.cardDetail)
        if (fragment == null) {
            if (binding.toggleButton.checkedButtonId == R.id.button1) {
                binding.viewPager2.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.viewPager2.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("NowMatchingAfterFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("NowMatchingAfterFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("NowMatchingAfterFragment", "onDestroyView")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("current_page", currentPage)
    }

    private fun hideDetailFragment() {
        val fragment = parentFragmentManager.findFragmentByTag("cardDetail")
        if (fragment != null) {
            parentFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
                remove(fragment)
                commit()
            }
        }

        if (binding.toggleButton.checkedButtonId == R.id.button1) {
            binding.viewPager2.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
        }

//        setCardAdapter(binding.toggleButton.checkedButtonId == R.id.button1)
    }

    private fun setCardAdapter(isButton1Checked: Boolean) {
        val cardAdapter = CardAdapter(cardInfoList, isButton1Checked, object : CardItemClickListener {
            override fun onCardDetailClick(cardInfo: CardInfo) {
                showDetailFragment(cardInfo)
            }
        })
        if (isButton1Checked) {
            binding.viewPager2.adapter = cardAdapter
            binding.viewPager2.post { binding.viewPager2.setCurrentItem(currentPage, false) }
            binding.recyclerView.adapter = null
        } else {
            binding.viewPager2.adapter = null
            binding.recyclerView.adapter = cardAdapter
        }
    }

    private fun showDetailFragment(cardInfo: CardInfo) {
        val detailFragment = NewCardDetailDialogFragment.newInstance(cardInfo)
        val oldFragment = parentFragmentManager.findFragmentByTag("cardDetail")
        oldFragment?.let {
            parentFragmentManager.beginTransaction().remove(it).commit()
        }
        detailFragment.show(parentFragmentManager, "cardDetail") // 변경된 부분입니다.
        binding.recyclerView.visibility = View.GONE
        binding.viewPager2.visibility = View.GONE
    }
}
