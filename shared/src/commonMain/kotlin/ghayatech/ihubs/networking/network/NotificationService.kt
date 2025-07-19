package ghayatech.ihubs.networking.network

import kotlinx.coroutines.channels.Channel
import kotlinx.serialization.json.Json
import ghayatech.ihubs.networking.models.Notification

class NotificationService {

//    private val ably = AblyRealtime("YOUR_ABLY_API_KEY")
//    private var channel: Channel? = null
//
//    fun connect(userId: Int, onNotificationReceived: (Notification) -> Unit) {
//        val channelName = "notifications:$userId"
//        channel = ably.channels.get(channelName)
//
//        channel?.subscribe { message ->
//            try {
//                val notification = Json.decodeFromString<Notification>(message.data.toString())
//                onNotificationReceived(notification)
//            } catch (e: Exception) {
//                println("Error decoding notification: ${e.message}")
//            }
//        }
//    }
//
//    fun disconnect() {
//        channel?.unsubscribe()
//        ably.close()
//    }
}
