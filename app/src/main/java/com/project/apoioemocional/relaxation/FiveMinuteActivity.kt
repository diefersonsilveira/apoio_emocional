package com.project.apoioemocional.relaxation

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import com.project.apoioemocional.R

class FiveMinuteActivity : ComponentActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var tvMainInstruction: TextView
    private lateinit var tvDetailInstruction: TextView
    private lateinit var btnTogglePause: MaterialButton
    private lateinit var toolbarPause: MaterialToolbar

    private var timerRunning = false
    private var timeLeftInMillis = 5 * 60 * 1000L
    private var countDownTimer: CountDownTimer? = null

    private val steps = listOf(
        RelaxationStep(
            "Incline a cabeça suavemente",
            "Deixe cair a orelha direita em direção ao ombro direito. Mantenha por 30 segundos. Troque e repita no lado esquerdo.",
            0L
        ),
        RelaxationStep(
            "Rotacione os ombros",
            "Faça círculos com os ombros para frente e para trás lentamente.",
            60_000L
        ),
        RelaxationStep(
            "Alongue os braços",
            "Estenda os braços acima da cabeça e respire profundamente.",
            120_000L
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_five_minute)

        tvTimer = findViewById(R.id.tvTimer)
        tvMainInstruction = findViewById(R.id.tvMainInstruction)
        tvDetailInstruction = findViewById(R.id.tvDetailInstruction)
        btnTogglePause = findViewById(R.id.btnTogglePause)
        toolbarPause = findViewById(R.id.toolbarPause)

        toolbarPause.setNavigationOnClickListener {
            finish()
        }

        updateTimerText()
        updateInstruction(0)

        btnTogglePause.setOnClickListener {
            if (timerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
                updateInstructionByTime()
            }

            override fun onFinish() {
                timerRunning = false
                btnTogglePause.text = "INICIAR ALONGAMENTO"
            }
        }.start()

        timerRunning = true
        btnTogglePause.text = "PAUSAR ALONGAMENTO"
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        timerRunning = false
        btnTogglePause.text = "CONTINUAR ALONGAMENTO"
    }

    private fun updateTimerText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        tvTimer.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun updateInstruction(index: Int) {
        val step = steps.getOrNull(index) ?: steps.last()
        tvMainInstruction.text = step.title
        tvDetailInstruction.text = step.detail
    }

    private fun updateInstructionByTime() {
        val elapsed = 5 * 60 * 1000L - timeLeftInMillis
        val currentStep = steps.lastOrNull { elapsed >= it.startTimeMs } ?: steps.first()
        tvMainInstruction.text = currentStep.title
        tvDetailInstruction.text = currentStep.detail
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
