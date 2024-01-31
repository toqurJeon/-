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

//interface OnBackButtonClickListener {
//    fun onBackButtonClicked()
//}
class CardDetailFragment : Fragment() {

//    private var backButtonListener: OnBackButtonClickListener? = null
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnBackButtonClickListener) {
//            backButtonListener = context
//        } else {
//            throw RuntimeException("$context must implement OnBackButtonClickListener")
//        }
//    }
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

        // "돌아가기" 버튼 클릭 리스너
        binding.buttonBack.setOnClickListener {
            // Fragment를 종료합니다
            parentFragmentManager.popBackStack()
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
}