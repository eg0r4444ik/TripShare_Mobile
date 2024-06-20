package ru.vsu.tripshare_mobile.services

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vsu.tripshare_mobile.api.dto.chats.ChatDTO
import ru.vsu.tripshare_mobile.api.dto.chats.MessageDTO
import ru.vsu.tripshare_mobile.config.AppConfig
import ru.vsu.tripshare_mobile.models.ChatModel
import ru.vsu.tripshare_mobile.models.MessageModel
import java.time.Instant
import java.util.Date

object ChatService {

    suspend fun getMyChats(): Result<List<ChatModel>>{
        return withContext(Dispatchers.IO) {
            try {
                val chatsDTO = AppConfig.retrofitAPI.getMyChats()
                val chats = mutableListOf<ChatModel>()
                chatsDTO.forEach{
                    var chat: ChatModel? = null
                    val user = ValidationService.validate(UserService.getUser(it.user_id_1), "Пользователя не существует")
                    val companion = ValidationService.validate(UserService.getUser(it.user_id_2), "Пользователя не существует")
                    if (user != null && companion != null){
                        chat = ChatModel(
                            it.id,
                            user,
                            companion,
                            getChatMessages(it.id).getOrElse { mutableListOf() }
                        )
                    }
                    if(chat != null) {
                        chats.add(chat)
                    }
                }

                var unread = mutableListOf<ChatModel>()

                if(AppConfig.currentChats != null && AppConfig.currentChats!!.isNotEmpty()) {
                    chats.forEach { new ->
                        var exist = false
                        AppConfig.currentChats?.forEach { curr ->
                            if (new.id == curr.id) {
                                exist = true
                                if (new.messages.size > curr.messages.size) {
                                    unread.add(new)
                                }
                            }
                        }
                        if (!exist) {
                            unread.add(new)
                        }
                    }
                }

                AppConfig.currentChats = chats
                Result.success(unread)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun addMessage(receiverId: Int, message: MessageModel){
        try {
            AppConfig.retrofitAPI.addMessage(fromMessageModelToDTO(receiverId, message))
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    suspend fun getChatMessages(chatId: Int): Result<MutableList<MessageModel>>{
        return withContext(Dispatchers.IO) {
            try {
                val messagesDTO = AppConfig.retrofitAPI.getChatMessages(chatId)
                val messages = mutableListOf<MessageModel>()
                messagesDTO.forEach{
                    messages.add(fromMessageDTOtoModel(it))
                }
                Result.success(messages)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun fromMessageModelToDTO(receiverId: Int, message: MessageModel): MessageDTO {
        val messageDTO = MessageDTO(
            message.text,
            null,
            receiverId
        )
        return messageDTO
    }

    private fun fromMessageDTOtoModel(messageDTO: MessageDTO): MessageModel {
        val isoString = messageDTO.created_at!! + "Z"
        val instant = Instant.parse(isoString)
        val date =  Date.from(instant)
        val message = MessageModel(
            messageDTO.sender_id!!,
            if(messageDTO.text == null) "" else messageDTO.text,
            date
        )
        return message
    }

    private fun fromChatDTOtoModel(chatDTO: ChatDTO): ChatModel? {
        var chat: ChatModel? = null

        CoroutineScope(Dispatchers.Main).launch {
            val user = ValidationService.validate(UserService.getUser(chatDTO.user_id_1), "Пользователя не существует")
            val companion = ValidationService.validate(UserService.getUser(chatDTO.user_id_2), "Пользователя не существует")
            if (user != null && companion != null){
                chat = ChatModel(
                    chatDTO.id,
                    user,
                    companion,
                    mutableListOf()
                )
            }
        }

        return chat
    }

}