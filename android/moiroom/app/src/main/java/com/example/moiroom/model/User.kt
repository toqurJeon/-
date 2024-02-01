import android.util.Log
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class User(
    val nickname: String,
    val imageUrl: String,
    val birthyear: String,
    val birthday: String,
    val name: String,
    val gender: String,
    val accessToken: String,
    val refreshToken: String
)

private const val TAG = "UserInfoFetcher"

fun fetchUserInfo(accessToken: String, refreshToken: String) {
    UserApiClient.instance.me { user, error ->
        if (error != null) {
            Log.e(TAG, "사용자 정보 요청 실패", error)
        }
        else if (user != null) {
            Log.i(TAG, "사용자 정보 요청 성공" +
                    "\n회원번호: ${user.id}" +
                    "\n이메일: ${user.kakaoAccount?.email}" +
                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                    "\n프로필 링크: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

            val userInfo = User(
                nickname = user.kakaoAccount?.profile?.nickname ?: "",
                imageUrl = user.kakaoAccount?.profile?.thumbnailImageUrl ?: "",
                birthyear = user.kakaoAccount?.birthyear ?: "",
                birthday = user.kakaoAccount?.birthday ?: "",
                name = user.kakaoAccount?.profile?.nickname ?: "",
                gender = user.kakaoAccount?.gender?.toString() ?: "",
                accessToken = accessToken,
                refreshToken = refreshToken
            )

            val jsonString = Json.encodeToString(userInfo)
            Log.i(TAG, jsonString)

            // 백엔드 서버로 POST 요청 보내기
            GlobalScope.launch(Dispatchers.Main) {
                val response = withContext(Dispatchers.IO) {
                    NetworkModule.apiService.postUser(userInfo)
                }

                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        // 응답 처리
                        Log.d("Success", "Code: ${response.code()}, Body: ${responseData.string()}")
                    } else {
                        // 응답 본문이 null인 경우 처리
                        Log.e("Error", "Response body is null")
                    }
                } else {
                    // 요청 실패 처리
                    Log.e("Error", "Request failed with status code: ${response.code()}")
                }
            }
        }
    }
}



