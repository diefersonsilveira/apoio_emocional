package com.project.apoioemocional.psychologist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.apoioemocional.R

class PsicologoAdapter(private val psicologos: List<Psicologo>) :
    RecyclerView.Adapter<PsicologoAdapter.PsicologoViewHolder>() {
    inner class PsicologoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.text_nome_psicologo)
        val especialidade: TextView = itemView.findViewById(R.id.text_especialidade)
        val contato: TextView = itemView.findViewById(R.id.text_contato)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PsicologoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_psicologo, parent, false)
        return PsicologoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PsicologoViewHolder, position: Int) {
        val psicologo = psicologos[position]
        holder.nome.text = psicologo.nome
        holder.especialidade.text = "Especialidade: ${psicologo.especialidade}"
        holder.contato.text = "Contato: ${psicologo.contato}"
    }

    override fun getItemCount(): Int = psicologos.size
}