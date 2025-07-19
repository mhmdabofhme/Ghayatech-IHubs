package ghayatech.ihubs.utils

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class UserPreferences(private val settings: Settings) {

    fun saveToken(token: String) {
        settings["auth_token"] = token
    }

    fun getToken(): String? {
        return settings["auth_token"]
    }

    fun clearToken() {
        settings.remove("auth_token")
    }
}