package com.example.moiroom

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.moiroom.extraction.AppExtract
import com.example.moiroom.extraction.CallExtract
import com.example.moiroom.extraction.InstagramExtract
import com.example.moiroom.extraction.PhotoExtract
import com.example.moiroom.extraction.YoutubeExtract
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoadingActivity : AppCompatActivity() {
    var finalInsta = "\"instagram\": { \"accessToken\": \"\" }"
    var finalCall = ""
    var finalPhoto = ""
    var finalGoogle = ""
    var accessToken: String? = null
    var refreshToken: String? = null
    var apps = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("오스", "${NowMatchingActivity.callAuth}")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        accessToken = sharedPreferences.getString("accessToken", null)
        refreshToken = sharedPreferences.getString("refreshToken", null)

        val dot1 = findViewById<View>(R.id.dot_1)
        val dot2 = findViewById<View>(R.id.dot_2)
        val dot3 = findViewById<View>(R.id.dot_3)
        val dot4 = findViewById<View>(R.id.dot_4)
        val dot5 = findViewById<View>(R.id.dot_5)

        val animationDuration = 2000L

        val animator1 = getAnimator(dot1, animationDuration, 0)
        val animator2 = getAnimator(dot2, animationDuration, animationDuration / 5)
        val animator3 = getAnimator(dot3, animationDuration, animationDuration * 2 / 5)
        val animator4 = getAnimator(dot4, animationDuration, animationDuration * 3 / 5)
        val animator5 = getAnimator(dot5, animationDuration, animationDuration * 4 / 5)

        animator1.start()
        animator2.start()
        animator3.start()
        animator4.start()
        animator5.start()
        Log.d("인스타데이터", "${InstagramExtract.instadata}")

        instagramData(InstagramExtract.instadata)
//        apps = AppExtract().appExtract()
        callData()
        postFlask1()

//        finalCall =
//        finalPhoto =
//        finalGoogle =
    }

    private fun getAnimator(view: View, duration: Long, delay: Long): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, "alpha", 0.2f, 1f, 0.2f).apply {
            this.duration = duration
            repeatCount = ValueAnimator.INFINITE
            startDelay = delay
        }
    }

    fun instagramData(res: String) {
        // FuelManager 설정 (선택사항)
        // 경로 바꾸기
        FuelManager.instance.basePath = "http://i10a308.p.ssafy.io:5000"
        var accessToken: String? = ""
        var userId: String? = ""
        var code: String? = ""
        val gson = Gson()
        val type = object : TypeToken<Map<String, String>>() {}.type
        if ( res != "") {
            val instaMap: Map<String, String> = gson.fromJson(res, type)
            accessToken = instaMap.get("access_token")
            userId = instaMap.get("user_id")
//            val uri = Uri.parse(res)
//            code = uri.getQueryParameter("code")
        }

        Log.d("전달 정보", "{ \"accessToken\": \"$accessToken\" }")
        finalInsta = "\"instagram\": { \"accessToken\": \"$accessToken\" }"
//        if ( finalInsta != "" && finalCall != "" && finalPhoto != "" && finalGoogle != "") {
//            postFuel()
//        }
    }

    fun callData() {
        val isReadCallGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CALL_LOG
        ) == PackageManager.PERMISSION_GRANTED

        // 저장소 접근 허용 여부
        val isExternalStorageGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        if ( isExternalStorageGranted ) {
            Log.d("전화", "전화 권한 허용")
            finalCall = CallExtract().getCallLog(this)
        } else {
            finalCall = "\"calls\": []"
        }

        if ( isReadCallGranted ) {
            finalPhoto = PhotoExtract().getAllPhotos(this)
        } else {
            finalPhoto = "[]"
        }
        apps = AppExtract().appExtract(this)

//        if ( finalInsta != "" && finalCall != "" && finalPhoto != "" && finalGoogle != "") {
//            postFuel()
//        }
    }

    private fun postFlask1() {
        val stringBuilder = StringBuilder()
        stringBuilder.append("{ ")
        stringBuilder.append(finalCall)
        stringBuilder.append(", ")
        stringBuilder.append("\"accessToken\":\"")
        stringBuilder.append(accessToken)  // 여기서 accessToken은 SharedPreferences에서 불러온 값입니다.
        stringBuilder.append("\", ")
        stringBuilder.append(finalInsta)
        stringBuilder.append(", ")
        stringBuilder.append(" \"youtube\": { \"accessToken\": \"${YoutubeExtract.youtubeToken}\"} ")
        stringBuilder.append(", ")
        stringBuilder.append("\"apps\": \"$apps\"")
        stringBuilder.append(", ")
        stringBuilder.append("\"images\": $finalPhoto }")

        // FuelManager 설정 (선택사항)
        FuelManager.instance.basePath = "https://moiroom.r-e.kr"
        Log.d("최종 전송 데이터", stringBuilder.toString())
        // 코루틴 사용
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = Fuel.post("/init")
                    .header("Content-Type" to "application/json")
                    .jsonBody(
                        stringBuilder.toString()
                    )
                    .responseString()

                // 응답 확인
                response.third.fold(
                    success = { data ->
                        Log.d("서버 응답", "$data")
                        postFlask2(accessToken)
//                        val intent = Intent(this@LoadingActivity, NaviActivity::class.java)
//                        startActivity(intent)
                    },
                    failure = { error -> Log.d("서버 에러", "에러: $error") }
                )
            } catch (e: Exception) {
                println("에러: $e")
            }
        }
    }

    private fun postFlask2(token: String?) {
        // FuelManager 설정 (선택사항)
        FuelManager.instance.basePath = "https://moiroom.r-e.kr"
        Log.d("플라스트2", "{ \"accessToken\": \"$token\" }")
        // 코루틴 사용
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = Fuel.post("/match")
                    .header("Content-Type" to "application/json")
                    .jsonBody(
                        """
                            { "accessToken": "$token" } 
                        """.trimIndent()
                    )
                    .responseString()

                // 응답 확인
                response.third.fold(
                    success = { data ->
                        Log.d("서버 응답2", "$data")


//                        val intent = Intent(this@LoadingActivity, NaviActivity::class.java)
//                        startActivity(intent)

                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this@LoadingActivity, NaviActivity::class.java)
                            startActivity(intent)
                        }, 2000)
                    },
                    failure = { error -> Log.d("서버 에러", "에러: $error") }
                )
            } catch (e: Exception) {
                println("에러: $e")
            }
        }
    }
}
