package com.project.apoioemocional.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.project.apoioemocional.R
import com.project.apoioemocional.home.HomeActivity

class RegisterActivity : AppCompatActivity() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilName: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etName: TextInputEditText
    private lateinit var cbWantName: MaterialCheckBox
    private lateinit var btnRegister: MaterialButton
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        tilName = findViewById(R.id.tilName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etName = findViewById(R.id.etName)
        cbWantName = findViewById(R.id.cbWantName)
        btnRegister = findViewById(R.id.btnRegister)
        progress = findViewById(R.id.progress)

        etEmail.addTextChangedListener(afterTextChanged = { tilEmail.error = null })
        etPassword.addTextChangedListener(afterTextChanged = { tilPassword.error = null })
        etName.addTextChangedListener(afterTextChanged = { tilName.error = null })

        val wantName = savedInstanceState?.getBoolean("want_name") ?: cbWantName.isChecked
        setNameEnabled(wantName)
        cbWantName.isChecked = wantName

        cbWantName.setOnCheckedChangeListener { _, checked -> setNameEnabled(checked) }

        btnRegister.setOnClickListener { createAccount() }
    }

    private fun setNameEnabled(enabled: Boolean) {
        tilName.isEnabled = enabled
        etName.isEnabled = enabled
        if (!enabled) etName.text?.clear()
    }

    private fun createAccount() {
        val email = etEmail.text?.toString()?.trim().orEmpty()
        val pass = etPassword.text?.toString().orEmpty()
        val includeName = cbWantName.isChecked
        val name = etName.text?.toString()?.trim().orEmpty()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = "E-mail inválido"
            return
        }
        if (pass.length < 6) {
            tilPassword.error = "Mínimo de 6 caracteres"
            return
        }
        if (includeName && name.isBlank()) {
            tilName.error = "Informe o nome ou desmarque a opção"
            return
        }

        setLoading(true)

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    setLoading(false)
                    val msg = task.exception?.localizedMessage ?: "Erro ao registrar."
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                    return@addOnCompleteListener
                }

                val user = auth.currentUser
                if (includeName && user != null) {
                    val update = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    user.updateProfile(update)
                        .addOnCompleteListener {
                            setLoading(false)
                            goToHome()
                        }
                } else {
                    setLoading(false)
                    goToHome()
                }
            }
    }

    private fun setLoading(loading: Boolean) {
        progress.visibility = if (loading) View.VISIBLE else View.GONE
        val enabled = !loading
        tilEmail.isEnabled = enabled
        tilPassword.isEnabled = enabled
        tilName.isEnabled = enabled && cbWantName.isChecked
        cbWantName.isEnabled = enabled
        btnRegister.isEnabled = enabled
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("want_name", cbWantName.isChecked)
    }
}
