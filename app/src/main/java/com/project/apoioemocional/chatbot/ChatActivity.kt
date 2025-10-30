package com.project.apoioemocional.chatbot

import com.project.apoioemocional.databinding.ActivityChatbotBinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.Locale


class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatbotBinding

    private val messageList = mutableListOf<Message>()
    private val adapter = MessageAdapter(messageList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o binding e define o layout
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o RecyclerView
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChat.adapter = adapter

        // Evento de clique do botão "Enviar"
        binding.buttonSend.setOnClickListener {
            val userMessage = binding.editTextMessage.text.toString().trim()

            if (userMessage.isNotEmpty()) {
                addMessage(userMessage, true) // adiciona a msg do usuário
                binding.editTextMessage.text.clear()

                // Gera e mostra a resposta do "bot"
                val botResponse = getBotResponse(userMessage)
                addMessage(botResponse, false)
            }
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }

    }

    // Função pra adicionar mensagens na lista e atualizar o RecyclerView
    private fun addMessage(text: String, isUser: Boolean) {
        messageList.add(Message(text, isUser))
        adapter.notifyItemInserted(messageList.size - 1)
        binding.recyclerViewChat.scrollToPosition(messageList.size - 1)
    }

    // Função simples de respostas automáticas
    private fun getBotResponse(message: String): String {
        val lower = message.lowercase(Locale.ROOT)

        return when {
            "ansioso" in lower -> "Entendo, ansiedade pode ser difícil. Quer conversar sobre o que te deixa assim?"
            "triste" in lower -> "Sinto muito por isso. Às vezes desabafar ajuda, quer me contar o motivo?"
            "feliz" in lower -> "Fico muito feliz em ouvir isso! 😄 O que te deixou assim?"
            "estresse" in lower || "cansado" in lower -> "Tente fazer uma pausa e respirar fundo. Isso ajuda a acalmar a mente."
            else -> "Entendo. Pode me contar mais sobre como você está se sentindo?"
        }
    }
}