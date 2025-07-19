package ghayatech.ihubs.networking.repository

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ghayatech.ihubs.networking.models.Message
import ghayatech.ihubs.networking.network.ChatService

//class ChatRepository(private val service: ChatService) {
//    val messages: StateFlow<List<Message>> = service.messages
//
//    suspend fun connect() = service.connect()
//    suspend fun sendMessage(message: Message) = service.sendMessage(message)
//}
class ChatRepository(private val service: ChatService) {
    val messages = service.messages
    suspend fun connect() = service.connect()
    suspend fun sendMessage(message: Message) = service.sendMessage(message)
}
