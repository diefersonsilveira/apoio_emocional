package com.project.apoioemocional.quiz

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.project.apoioemocional.R

class QuizActivity : AppCompatActivity() {

    companion object { const val EXTRA_TYPE = "extra_type" }

    private lateinit var tvTitle: TextView
    private lateinit var tvProgress: TextView
    private lateinit var cardQuestion: MaterialCardView
    private lateinit var cardResult: MaterialCardView
    private lateinit var tvQuestion: TextView
    private lateinit var rgOptions: RadioGroup
    private lateinit var rb1: RadioButton
    private lateinit var rb2: RadioButton
    private lateinit var rb3: RadioButton
    private lateinit var rb4: RadioButton
    private lateinit var btnNext: MaterialButton
    private lateinit var btnFinish: MaterialButton
    private lateinit var tvResultTitle: TextView
    private lateinit var tvResultMessage: TextView

    private lateinit var type: String
    private var step = 0
    private val total = 5
    private lateinit var questions: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        tvTitle = findViewById(R.id.tvQuizTitle)
        tvProgress = findViewById(R.id.tvProgress)
        cardQuestion = findViewById(R.id.cardQuestion)
        cardResult = findViewById(R.id.cardResult)
        tvQuestion = findViewById(R.id.tvQuestion)
        rgOptions = findViewById(R.id.rgOptions)
        rb1 = findViewById(R.id.rb1)
        rb2 = findViewById(R.id.rb2)
        rb3 = findViewById(R.id.rb3)
        rb4 = findViewById(R.id.rb4)
        btnNext = findViewById(R.id.btnNext)
        btnFinish = findViewById(R.id.btnFinish)
        tvResultTitle = findViewById(R.id.tvResultTitle)
        tvResultMessage = findViewById(R.id.tvResultMessage)

        rb1.setText(R.string.option_never)
        rb2.setText(R.string.option_rarely)
        rb3.setText(R.string.option_sometimes)
        rb4.setText(R.string.option_often)

        type = intent.getStringExtra(EXTRA_TYPE) ?: "ansiedade"

        val topic = when (type) {
            "depressao" -> getString(R.string.depression)
            "meditacao" -> getString(R.string.meditation)
            else -> getString(R.string.anxiety)
        }
        tvTitle.text = getString(R.string.quiz_title_format, topic)

        questions = when (type) {
            "depressao" -> resources.getStringArray(R.array.questions_depression)
            "meditacao" -> resources.getStringArray(R.array.questions_meditation)
            else -> resources.getStringArray(R.array.questions_anxiety)
        }

        updateQuestion()

        btnNext.setOnClickListener {
            if (rgOptions.checkedRadioButtonId == -1) return@setOnClickListener
            step++
            if (step >= total) showResult() else updateQuestion()
        }

        btnFinish.setOnClickListener { finish() }
    }

    private fun updateQuestion() {
        tvProgress.text = getString(R.string.quiz_progress_format, step + 1, total)
        rgOptions.clearCheck()
        tvQuestion.text = questions[step]
    }

    private fun showResult() {
        cardQuestion.visibility = View.GONE
        cardResult.visibility = View.VISIBLE
        tvResultTitle.setText(R.string.result_thanks_title)

        val msgId = when (type) {
            "depressao" -> R.string.result_msg_depression_html
            "meditacao" -> R.string.result_msg_meditation_html
            else -> R.string.result_msg_anxiety_html
        }
        tvResultMessage.text = HtmlCompat.fromHtml(getString(msgId), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
