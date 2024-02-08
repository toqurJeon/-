package com.example.moiroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.moiroom.R

class DialogAdapter(private val context: Context, private var data: List<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            // 뷰가 null인 경우 인플레이트
            view = LayoutInflater.from(context).inflate(R.layout.dialog_item_layout, parent, false)
            viewHolder = ViewHolder()
            viewHolder.textView = view.findViewById(R.id.dialogText)
            view.tag = viewHolder
        } else {
            // 뷰가 null이 아닌 경우 재사용
            viewHolder = view.tag as ViewHolder
        }

        // 데이터 설정
        viewHolder.textView.text = data[position]

        return view!!
    }

    // 뷰 홀더 클래스
    private class ViewHolder {
        lateinit var textView: TextView
    }

}
