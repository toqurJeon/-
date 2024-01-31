package com.example.moiroom

import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moiroom.data.CardInfo
import com.example.moiroom.databinding.FragmentCardDetailBinding
import android.os.Handler
import com.example.moiroom.OnBackButtonClickListener

class CardDetailFragment : Fragment() {
    private var listener: OnBackButtonClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 부모 액티비티 또는 프래그먼트가 OnBackButtonClickListener를 구현하고 있는지 확인
        if (context is OnBackButtonClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnBackButtonClickListener")
        }
    }

    private var _binding: FragmentCardDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cardInfo = arguments?.getSerializable("card_info") as? CardInfo
        cardInfo?.let { info ->
            with(binding) {
                textViewName.text = info.name
                textViewLocation.text = info.location
                textViewSummary.text = info.summary
            }
        }

        binding.buttonBack.setOnClickListener {
            listener?.onBackButtonClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(cardInfo: CardInfo): CardDetailFragment {
            val fragment = CardDetailFragment()
            val args = Bundle()
            args.putSerializable("card_info", cardInfo)
            fragment.arguments = args
            return fragment
        }
    }

    fun onSomeEvent() {
        // 뒤로 가기 버튼이 클릭되었을 때 listener를 통해 이벤트를 전달
        listener?.onBackButtonClicked()
    }
}
