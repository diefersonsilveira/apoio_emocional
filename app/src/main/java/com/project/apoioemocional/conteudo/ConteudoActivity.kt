package com.project.apoioemocional.content

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.project.apoioemocional.R

class ConteudosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_conteudo) // seu layout estilo Instagram

        // Botão Voltar do header
        findViewById<MaterialButton>(R.id.btnBack)?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Botões "Ler mais/Assistir"
        findViewById<MaterialButton>(R.id.btnOpen1)?.setOnClickListener {
            // TODO: abrir detalhe, player ou WebView
        }
        findViewById<MaterialButton>(R.id.btnOpen2)?.setOnClickListener {
            // TODO
        }
        findViewById<MaterialButton>(R.id.btnOpen3)?.setOnClickListener {
            // TODO
        }
    }
}
