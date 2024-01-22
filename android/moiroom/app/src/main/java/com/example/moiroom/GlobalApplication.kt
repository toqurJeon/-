// 네이티브 앱 키 작성을 위한 코드(성현)
package com.example.moiroom

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GlobalApplication : Application() {

    lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "1cbfb61d83c4ab599a7d1b1771485417")

        retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // API의 기본 URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}