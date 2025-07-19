package ghayatech.ihubs.networking.viewmodel

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ghayatech.ihubs.networking.models.Message
import ghayatech.ihubs.networking.repository.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class ChatViewModel(
    private val repository: ChatRepository
) {
    private val vmScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    val messages: StateFlow<List<Message>> = repository.messages

    fun connectToChat() {
        vmScope.launch {
            repository.connect()
        }
    }

    fun sendMessage(body: String, senderId: Int, attachment: String? = null) {
        val message = Message(
            id = 0, // سيتم تعيينه من السيرفر لاحقًا
            body = body,
            senderId = senderId,
            attachment = attachment
        )
        vmScope.launch {
            repository.sendMessage(message)
        }
    }
}


//// 4. ViewModel
//class ChatViewModel(private val repository: ChatRepository) {
//    val messages = repository.messages
//
//    fun connect() {
//        vm.launch {
//            repository.connect()
//        }
//    }
//
//    fun sendMessage(body: String, senderId: Int, attachment: String? = null) {
//        val message = Message(0, body, attachment, senderId)
//        viewModelScope.launch {
//            repository.sendMessage(message)
//        }
//    }
//}