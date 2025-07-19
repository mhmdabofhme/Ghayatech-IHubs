package ghayatech.ihubs.networking.network

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import ghayatech.ihubs.networking.models.Message


class ChatService(
    private val client: HttpClient,
    private val apiKey: String = "YOUR_ABLY_API_KEY",
    private val channelName: String = "chat"
) {
    private var session: WebSocketSession? = null
    private val json = Json { ignoreUnknownKeys = true }

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    suspend fun connect() {
        val url = "wss://realtime.ably.io?key=$apiKey"

        client.webSocket(urlString = url) {
            session = this
            send(Frame.Text(buildSubscribeMessage()))

            for (frame in incoming) {
                if (frame is Frame.Text) {
                    handleIncoming(frame.readText())
                }
            }
        }
    }

    suspend fun sendMessage(message: Message) {
        val payload = json.encodeToString(message)
        val ablyMessage = AblyMessage(15, channelName, payload)
        val jsonText = json.encodeToString(ablyMessage)
        session?.send(Frame.Text(jsonText))
    }

    private fun buildSubscribeMessage(): String {
        val ablyMessage = AblyMessage(10, channelName)
        return json.encodeToString(ablyMessage)
    }

    private fun handleIncoming(jsonText: String) {
        try {
            val jsonElement = json.parseToJsonElement(jsonText)
            val messagesArray = jsonElement.jsonObject["messages"]?.jsonArray ?: return
            for (msg in messagesArray) {
                val data = msg.jsonObject["data"]?.jsonPrimitive?.content ?: continue
                val message = json.decodeFromString<Message>(data)
                _messages.value = _messages.value + message
            }
        } catch (e: Exception) {
            println("Error parsing Ably message: $e")
        }
    }

    @Serializable
    private data class AblyMessage(
        val action: Int,
        val channel: String,
        val data: String? = null
    )
}




//class ChatService(
//    private val client: HttpClient = HttpClient(), // استخدم نفس الـ client الموجود لديك
//    private val apiKey: String = "YOUR_ABLY_API_KEY", // 🔁 ضع مفتاح Ably الخاص بك هنا
//    private val channelName: String = "chat"
//) {
//    private var session: WebSocketSession? = null
//
//    private val json = Json { ignoreUnknownKeys = true }
//
//    private val _messages = MutableStateFlow<List<Message>>(emptyList())
//    val messages: StateFlow<List<Message>> = _messages.asStateFlow()
//
//    suspend fun connect() {
//        val url = "wss://realtime.ably.io?key=$apiKey"
//
//        client.webSocket(urlString = url) {
//            session = this
//            send(Frame.Text(buildSubscribeMessage()))
//
//            for (frame in incoming) {
//                if (frame is Frame.Text) {
//                    handleIncoming(frame.readText())
//                }
//            }
//        }
//    }
//
//    suspend fun sendMessage(message: Message) {
//        val payload = json.encodeToString(message)
//        val ablyMessage = AblyMessage(
//            action = 15, // publish
//            channel = channelName,
//            data = payload
//        )
//        val jsonText = json.encodeToString(ablyMessage)
//        session?.send(Frame.Text(jsonText))
//    }
//
//    private fun buildSubscribeMessage(): String {
//        val ablyMessage = AblyMessage(
//            action = 10, // subscribe
//            channel = channelName
//        )
//        return json.encodeToString(ablyMessage)
//    }
//
//    private fun handleIncoming(jsonText: String) {
//        try {
//            val jsonElement = json.parseToJsonElement(jsonText)
//            val messagesArray = jsonElement.jsonObject["messages"]?.jsonArray ?: return
//            for (msg in messagesArray) {
//                val data = msg.jsonObject["data"]?.jsonPrimitive?.content ?: continue
//                val message = json.decodeFromString<Message>(data)
//                _messages.value = _messages.value + message
//            }
//        } catch (e: Exception) {
//            println("❌ Error parsing message: $e")
//        }
//    }
//
//    @Serializable
//    private data class AblyMessage(
//        val action: Int,
//        val channel: String,
//        val data: String? = null
//    )
//}