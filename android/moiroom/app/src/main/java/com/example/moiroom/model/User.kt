import android.content.Context
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Serializable
data class User(
    val socialId : Long,
    val provider : String,
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

suspend fun fetchUserInfo(context: Context, accessToken: String, refreshToken: String): User? = suspendCoroutine { cont ->
    val apiService = NetworkModule.provideRetrofit(context)

    UserApiClient.instance.me { user, error ->
        if (error != null) {
            Log.e(TAG, "사용자 정보 요청 실패", error)
            cont.resumeWithException(error) // error가 있을 경우 예외를 발생시키고 코루틴을 종료합니다.
        } else if (user != null) {
            Log.i(
                TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필 링크: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
            )

            val userInfo = User(
                socialId = user.id ?: 0L,
                provider = "kakao",
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

            cont.resume(userInfo) // userInfo를 반환하고 코루틴을 종료합니다.
        }
    }
}

