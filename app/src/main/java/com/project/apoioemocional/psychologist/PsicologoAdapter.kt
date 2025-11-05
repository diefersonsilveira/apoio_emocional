package com.project.apoioemocional.psychologist

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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

        holder.nome.text = psicologo.nome ?: "Profissional sem nome"
        holder.especialidade.text = "Especialidade: ${psicologo.especialidade ?: "Não informada"}"
        holder.contato.text = "Contato: ${psicologo.contato ?: "Não informado"}"

        holder.itemView.setOnClickListener {
            val contato = psicologo.contato

            if (!contato.isNullOrEmpty()) {
                abrirWhatsApp(holder.itemView.context, contato)
            } else {
                Toast.makeText(holder.itemView.context,
                    "Contato não disponível para ${psicologo.nome}",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = psicologos.size
    private fun abrirWhatsApp(context: Context, contato: String) {
        try {
            val numeroLimpo = contato.replace("[^0-9]".toRegex(), "")

            val numeroCompleto = if (numeroLimpo.startsWith("55") || numeroLimpo.length > 11) {
                numeroLimpo
            } else {
                "55$numeroLimpo"
            }

            val url = "https://api.whatsapp.com/send?phone=$numeroCompleto"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            context.startActivity(intent)

        } catch (e: Exception) {
            Toast.makeText(context,
                "Erro ao abrir o WhatsApp. Verifique o número ou se o aplicativo está instalado.",
                Toast.LENGTH_LONG).show()
        }
    }
}