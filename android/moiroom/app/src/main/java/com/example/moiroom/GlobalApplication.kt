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

        KakaoSdk.init(this, "694a967e5e200f2b2342046d25fdcd7e")

        retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // API의 기본 URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}