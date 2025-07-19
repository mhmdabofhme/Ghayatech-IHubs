package ghayatech.ihubs.utils

import com.russhwolf.settings.Settings
import kotlinx.datetime.LocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import network.chaintech.kmp_date_time_picker.ui.date_range_picker.changeDateFormat
import ghayatech.ihubs.networking.models.User
import ghayatech.ihubs.networking.network.ApiRoutes
import ghayatech.ihubs.networking.util.NetworkError

fun getErrorMessage(error: NetworkError):String{
    return when (error) {
//        is NetworkError.HttpError -> error.message
//        is NetworkError.Unauthorized -> "يجب تسجيل الدخول"
//        is NetworkError.Conflict -> "يوجد تعارض في البيانات"
//        is NetworkError.TooManyRequests -> "عدد كبير من المحاولات"
//        is NetworkError.NoInternet -> "لا يوجد اتصال بالإنترنت"
//        is NetworkError.ServerError -> "خطأ في الخادم"
//        is NetworkError.Serialization -> "خطأ في تحويل البيانات"
//        is NetworkError.RequestTimeout -> "انتهت مهلة الاتصال"
//        is NetworkError.Unknown -> "حدث خطأ غير معروف"
        is NetworkError.HttpError -> error.message
        is NetworkError.Unauthorized -> "يجب تسجيل الدخول"
        is NetworkError.Conflict -> "يوجد تعارض في البيانات"
        is NetworkError.TooManyRequests -> "عدد كبير من المحاولات"
        is NetworkError.NoInternet -> "لا يوجد اتصال بالإنترنت"
        is NetworkError.ServerError -> "خطأ في الخادم"
        is NetworkError.Serialization -> "خطأ في تحويل البيانات"
        is NetworkError.RequestTimeout -> "انتهت مهلة الاتصال"
        is NetworkError.Unknown -> "حدث خطأ غير معروف"
        else -> {
            ""
        }
    }
}



fun isValid(list: List<String>): Boolean {
    return list.all { it.isNotBlank() }
}


fun saveUser(settings: Settings, user: User?) {
    val userJson = Json.encodeToString(user)
    settings.putString("user_data", userJson)
}

fun loadUser(settings: Settings): User? {
    val userJson = settings.getStringOrNull("user_data")
    return userJson?.let {
        Json.decodeFromString<User>(it)
    }
}

fun handleTime(rawTime: String): String {
    return try {
        val parts = rawTime.split(":")
        val hour = parts[0].padStart(2, '0')
        val minute = parts[1].padStart(2, '0')
        "$hour:$minute"
    } catch (e: Exception) {
        "00:00"
    }
}


fun showErrorPage(message:String){
    showErrorPage(message)
}


//fun formatLocalDateToIso(localDate: LocalDate): String {
//    return "%04d-%02d-%02d".changeDateFormat(localDate.year, localDate.monthNumber, localDate.dayOfMonth)
//}

