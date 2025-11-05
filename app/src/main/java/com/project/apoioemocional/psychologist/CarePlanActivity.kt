package com.project.apoioemocional.psychologist

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.apoioemocional.R
import androidx.appcompat.widget.Toolbar

class CarePlanActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val TAG = "CarePlanActivity"
    private val database = FirebaseDatabase.getInstance().getReference("psicologos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_plan)

        val toolbar: Toolbar = findViewById(R.id.toolbar_care_plan)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Profissionais de Apoio"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        recyclerView = findViewById(R.id.recycler_psicologos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadPsychologists()
    }

    private fun loadPsychologists() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val psicologoList = mutableListOf<Psicologo>()

                for (psicologoSnapshot in snapshot.children) {
                    val psicologo = psicologoSnapshot.getValue(Psicologo::class.java)
                    if (psicologo != null) {
                        psicologoList.add(psicologo)
                    }
                }

                val adapter = PsicologoAdapter(psicologoList)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Erro Realtime DB: ${error.message}")
                Toast.makeText(this@CarePlanActivity, "Falha ao carregar dados.", Toast.LENGTH_LONG).show()
            }
        })
    }
}
