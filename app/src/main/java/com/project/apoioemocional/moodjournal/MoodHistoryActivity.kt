package com.project.apoioemocional.moodjournal

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.project.apoioemocional.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MoodHistoryActivity : AppCompatActivity() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val moodRecordsByDate = mutableMapOf<String, List<Map<String, Any>>>()

    private lateinit var calendarView: CalendarView
    private lateinit var tvSelectedDayMood: TextView

    private val displayDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    private val dbDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_history)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarHistory)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Histórico"
            setDisplayHomeAsUpEnabled(true)
        }

        calendarView = findViewById(R.id.calendarView)
        tvSelectedDayMood = findViewById(R.id.tvSelectedDayMood)

        loadMoodData()

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDateKey = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth)

            displayMoodForSelectedDate(selectedDateKey)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun loadMoodData() {
        val userId = auth.currentUser?.uid ?: run {
            Toast.makeText(this, "Erro: Usuário não autenticado.", Toast.LENGTH_LONG).show()
            return
        }

        val moodRef = database.getReference("mood_journal").child(userId)

        moodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    processMoodSnapshot(snapshot)

                    val todayKey = dbDateFormat.format(Date())
                    displayMoodForSelectedDate(todayKey)

                } else {
                    Toast.makeText(this@MoodHistoryActivity, "Nenhum registro de humor encontrado.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MoodHistoryActivity, "Falha ao carregar dados: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun processMoodSnapshot(snapshot: DataSnapshot) {
        val t: GenericTypeIndicator<Map<String, Any>> = object : GenericTypeIndicator<Map<String, Any>>() {}

        val groupedRecords = snapshot.children
            .mapNotNull { it.getValue(t) }
            .groupBy {
                val timestamp = it["timestamp"] as? Long ?: 0L
                dbDateFormat.format(Date(timestamp))
            }

        moodRecordsByDate.clear()
        groupedRecords.forEach { (dateKey, records) ->
            moodRecordsByDate[dateKey] = records
        }
    }

    private fun displayMoodForSelectedDate(dateKey: String) {
        val records = moodRecordsByDate[dateKey]

        val displayDate = try {
            val date = dbDateFormat.parse(dateKey)
            date?.let { displayDateFormat.format(it) } ?: dateKey
        } catch (e: Exception) {
            dateKey
        }

        if (records.isNullOrEmpty()) {
            tvSelectedDayMood.text = "Dia $displayDate: Nenhum registro de humor."
            return
        }

        val latestRecord = records.maxByOrNull { it["timestamp"] as? Long ?: 0L }
        val latestMoodLabel = latestRecord?.get("moodLabel") as? String

        if (latestMoodLabel != null) {
            tvSelectedDayMood.text = "Dia $displayDate (Último Registro): Sentindo-se $latestMoodLabel."
        } else {
            tvSelectedDayMood.text = "Dia $displayDate: ${records.size} registros de humor."
        }
    }
}