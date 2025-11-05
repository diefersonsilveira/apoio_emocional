package com.project.apoioemocional.breathing

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.project.apoioemocional.R

private const val INHALE_TIME = 4000L
private const val HOLD_TIME = 7000L
private const val EXHALE_TIME = 8000L

class BreathingActivity : AppCompatActivity() {

    private lateinit var tvBreathingAction: TextView
    private lateinit var tvTimer: TextView
    private lateinit var btnStartBreathing: Button
    private lateinit var breathingVisualizer: View

    private var isRunning = false
    private var currentPhase = Phase.PREPARE
    private var cycleCount = 0

    private var timer: CountDownTimer? = null

    enum class Phase(val label: String, val duration: Long, val scale: Float) {
        PREPARE("Preparar", 3000L, 1.0f),
        INHALE("Inspire", INHALE_TIME, 1.5f),
        HOLD1("Segure", HOLD_TIME, 1.5f),
        EXHALE("Expire", EXHALE_TIME, 1.0f),
        HOLD2("Segure (Pausa)", 1000L, 1.0f)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breathing)

        supportActionBar?.apply {
            title = "Respiração 4-7-8"
            setDisplayHomeAsUpEnabled(true)
        }

        setupViews()

        btnStartBreathing.setOnClickListener {
            if (!isRunning) {
                startBreathingExercise()
            } else {
                stopBreathingExercise()
            }
        }

        updateUI(Phase.PREPARE)
    }

    private fun setupViews() {
        tvBreathingAction = findViewById(R.id.tvBreathingAction)
        tvTimer = findViewById(R.id.tvTimer)
        btnStartBreathing = findViewById(R.id.btnStartBreathing)
        breathingVisualizer = findViewById(R.id.breathingVisualizer)

        tvTimer.visibility = View.GONE
    }

    private fun startBreathingExercise() {
        isRunning = true
        cycleCount = 0
        btnStartBreathing.text = "Parar Exercício"

        currentPhase = Phase.PREPARE
        startPhaseTimer(currentPhase)
    }

    private fun stopBreathingExercise() {
        isRunning = false
        timer?.cancel()
        timer = null
        cycleCount = 0

        updateUI(Phase.PREPARE)
        btnStartBreathing.text = "Iniciar Exercício"

        breathingVisualizer.animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .setDuration(500)
            .start()

        tvTimer.visibility = View.GONE
    }

    private fun startPhaseTimer(phase: Phase) {
        currentPhase = phase
        updateUI(phase)

        tvTimer.visibility =
            if (phase == Phase.PREPARE || phase == Phase.HOLD2) View.INVISIBLE else View.VISIBLE

        animateBreathingVisualizer(phase)

        timer?.cancel()
        timer = object : CountDownTimer(phase.duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toInt()
                tvTimer.text = seconds.toString()
            }

            override fun onFinish() {
                moveToNextPhase()
            }
        }.start()
    }

    private fun moveToNextPhase() {
        val nextPhase = when (currentPhase) {
            Phase.PREPARE -> Phase.INHALE
            Phase.INHALE -> Phase.HOLD1
            Phase.HOLD1 -> Phase.EXHALE
            Phase.EXHALE -> {
                cycleCount++
                if (cycleCount < 4) {
                    Phase.HOLD2
                } else {
                    stopBreathingExercise()
                    return
                }
            }
            Phase.HOLD2 -> Phase.INHALE
        }
        startPhaseTimer(nextPhase)
    }

    private fun updateUI(phase: Phase) {
        tvBreathingAction.text = phase.label

        val title = if (phase == Phase.PREPARE && !isRunning) {
            "Sessão Concluída!"
        } else {
            "Instrução"
        }

        findViewById<TextView>(R.id.tvInstructionTitle).text = title
    }

    private fun animateBreathingVisualizer(phase: Phase) {
        ObjectAnimator.ofFloat(breathingVisualizer, "scaleX", phase.scale).apply {
            duration = phase.duration
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
        ObjectAnimator.ofFloat(breathingVisualizer, "scaleY", phase.scale).apply {
            duration = phase.duration
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (isRunning) {
            stopBreathingExercise()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (isRunning) {
            stopBreathingExercise()
        }
        finish()
        return true
    }
}
