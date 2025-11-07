package com.project.apoioemocional.moodjournal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.project.apoioemocional.R

class MoodJournalActivity : AppCompatActivity() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    enum class Mood(val label: String, val dbValue: Int) {
        ANGRY("Irritado/Péssimo", 1),
        SAD("Desanimado/Mal", 2),
        ANXIOUS("Ansioso/Preocupado", 3),
        CALM("Neutro/Calmo", 4),
        HAPPY("Feliz/Excelente", 5)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_journal)

        supportActionBar?.apply {
            title = null
            setDisplayHomeAsUpEnabled(true)
        }

        setupMoodButtons()

        findViewById<MaterialButton>(R.id.btnViewHistory).setOnClickListener {
            startActivity(Intent(this, MoodHistoryActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setupMoodButtons() {
        val moodMap = mapOf(
            R.id.cardAngry to Mood.ANGRY,
            R.id.cardSad to Mood.SAD,
            R.id.cardAnxious to Mood.ANXIOUS,
            R.id.cardCalm to Mood.CALM,
            R.id.cardHappy to Mood.HAPPY
        )

        moodMap.forEach { (cardId, mood) ->
            val card = findViewById<MaterialCardView>(cardId)

            card.setOnClickListener {
                recordMood(mood)
            }
        }
    }

    private fun recordMood(mood: Mood) {
        val userId = auth.currentUser?.uid ?: run {
            Toast.makeText(this, "Usuário não autenticado. Faça login novamente.", Toast.LENGTH_LONG).show()
            return
        }

        val timestamp = System.currentTimeMillis()
        val moodRef = database.getReference("mood_journal").child(userId).child(timestamp.toString())

        val moodRecord = mapOf(
            "moodLabel" to mood.label,
            "moodLevel" to mood.dbValue,
            "timestamp" to timestamp
        )

        moodRef.setValue(moodRecord)
            .addOnSuccessListener {
                Toast.makeText(this, "Humor registrado como ${mood.label}!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Falha ao registrar humor. Tente novamente.", Toast.LENGTH_SHORT).show()
            }
    }
}