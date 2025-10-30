package com.project.apoioemocional.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.project.apoioemocional.R
import com.project.apoioemocional.auth.LoginActivity
import com.project.apoioemocional.chatbot.ChatActivity
import com.project.apoioemocional.quiz.QuizActivity
import java.util.Calendar
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        findViewById<MaterialCardView>(R.id.cardQuizAnxiety).setOnClickListener { openQuiz("ansiedade") }
        findViewById<MaterialCardView>(R.id.cardQuizDepression).setOnClickListener { openQuiz("depressao") }
        findViewById<MaterialCardView>(R.id.cardQuizMeditation).setOnClickListener { openQuiz("meditacao") }
        findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.bntChat)
            .setOnClickListener {
                startActivity(Intent(this, ChatActivity::class.java))
            }

        val user = auth.currentUser
        val displayName = user?.displayName?.takeIf { it.isNotBlank() }
            ?: user?.email?.substringBefore("@")
            ?: "amigo(a)"

        findViewById<TextView>(R.id.tvGreetingTitle).text = greetingForNow()
        findViewById<TextView>(R.id.tvGreetingName).text = "${firstName(displayName)} \uD83D\uDC4B"

        findViewById<MaterialButton>(R.id.btnLogout)?.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            })
            finish()
        }
    }

    private fun openQuiz(type: String) {
        startActivity(Intent(this, QuizActivity::class.java).apply {
            putExtra(QuizActivity.Companion.EXTRA_TYPE, type)
        })
    }

    private fun greetingForNow(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> "Bom dia,"
            in 12..18 -> "Boa tarde,"
            else -> "Boa noite,"
        }
    }

    private fun firstName(full: String): String =
        full.trim().split(" ").firstOrNull()?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        } ?: full
}