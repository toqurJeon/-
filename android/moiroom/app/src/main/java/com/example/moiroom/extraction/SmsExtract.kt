package com.example.moiroom.extraction

import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moiroom.databinding.ActivityJaeeontestBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SmsExtract: AppCompatActivity() {
    private lateinit var binding: ActivityJaeeontestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJaeeontestBinding.inflate(layoutInflater)
//        // 바인딩된 레이아웃의 최상위 뷰를 현재 액티비티의 뷰로 설정
        setContentView(binding.root)
        val smss = readSmsMessages(this)
        binding.textview.text = smss
        postFuel(smss)
    }

    fun smsExtract() {
        val smss = readSmsMessages(this)
        return postFuel(smss)
    }

    fun readSmsMessages(context: Context): String {
        // SMS 수신함에서 메시지 가져오기
        val smsUri = Telephony.Sms.Inbox.CONTENT_URI
        val stringBuilder = StringBuilder()
        stringBuilder.append("[")
        val projection = arrayOf(
            Telephony.Sms._ID,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY,
            Telephony.Sms.DATE,
            Telephony.Sms.TYPE
        )
        var a = 0
        val contentResolver: ContentResolver = context.contentResolver
        val cursor = contentResolver.query(smsUri, projection, null, null, "${Telephony.Sms.DATE} DESC")
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // SMS 메시지에서 필요한 정보 추출
                val address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                val body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                val gson = Gson()
                val jsonBody = gson.toJson(body)
                val dateInMillis = cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
                val type = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE))

//                if (address.startsWith("010")) {
                    stringBuilder.append("{ \"number\": \"$address\", \"body\": ${jsonBody}, \"date\": $dateInMillis, \"type\": $type }, ")
//                }
                a += 1
//                if ( a > 5 ) {
//                    break
//                }
            } while (cursor.moveToNext())
            cursor.close()
        }
        stringBuilder.deleteCharAt(stringBuilder.length - 1)
        stringBuilder.deleteCharAt(stringBuilder.length - 1)
        stringBuilder.append("]")
        return stringBuilder.toString()
    }

    private fun postFuel(data: String) {
        val stringBuilder = StringBuilder()
        stringBuilder.append("{ \"sms\": ")
        stringBuilder.append("$data")
        stringBuilder.append("}")
        // FuelManager 설정 (선택사항)
        FuelManager.instance.basePath = "http://i10a308.p.ssafy.io:5000"
        Log.d("최종 전송 데이터", stringBuilder.toString())
        binding.textview.text = stringBuilder.toString()
        // 코루틴 사용
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = Fuel.post("/calling_history")
                    .header("Content-Type" to "application/json")
                    .jsonBody(
                        stringBuilder.toString()
                    )
                    .responseString()

                // 응답 확인
                response.third.fold(
                    success = { data ->
                        Log.d("서버 응답", "$data")
                    },
                    failure = { error -> Log.d("서버 에러", "에러: $error") }
                )
            } catch (e: Exception) {
                println("에러: $e")
            }
        }
    }
}