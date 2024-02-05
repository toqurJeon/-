package com.example.moiroom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.moiroom.data.CardInfo

class NewCardDetailDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_new_card_detail_dialog, null)

        val cardInfo = arguments?.getSerializable("cardInfo") as? CardInfo
        cardInfo?.let { info ->
            // 뷰에 대한 참조를 얻고, 뷰를 사용해서 카드 정보를 화면에 표시
            view.findViewById<TextView>(R.id.textViewName).text = info.name
            view.findViewById<TextView>(R.id.textViewLocation).text = info.location
            view.findViewById<TextView>(R.id.textViewSummary).text = info.summary
        }

        // 돌아가기 버튼에 대한 참조를 얻고, 클릭 리스너를 설정
        val buttonBack = view.findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            // 버튼 클릭 시 다이얼로그 종료
            dismiss()
        }

        builder.setView(view)
        return builder.create()
    }

    override fun onStart() {
        super.onStart()

        // 다이얼로그의 창을 가져옵니다.
        val window = dialog?.window

        // 창의 레이아웃 파라미터를 가져옵니다.
        val params = window?.attributes

        // 레이아웃 파라미터를 변경하여 너비와 높이를 설정합니다.
        // 여기서는 화면 너비와 화면 높이로 설정하였습니다.
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        params?.height = ViewGroup.LayoutParams.MATCH_PARENT

        // 변경된 레이아웃 파라미터를 다시 설정합니다.
        window?.attributes = params
    }


    companion object {
        fun newInstance(cardInfo: CardInfo): NewCardDetailDialogFragment {
            val fragment = NewCardDetailDialogFragment()
            val args = Bundle()
            args.putSerializable("cardInfo", cardInfo)
            fragment.arguments = args
            return fragment
        }
    }
}
