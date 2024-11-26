package com.example.sistemalogin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DoarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doar)

        val edtDataDoacao = findViewById<EditText>(R.id.edtDataDoacao)
        val edtHorarioDoacao = findViewById<EditText>(R.id.edtHorarioDoacao)
        val edtCpfUsuario = findViewById<EditText>(R.id.edtCpfUsuario)
        val edtRgaPet = findViewById<EditText>(R.id.edtRgaPet)
        val btnRegistrarDoacao = findViewById<Button>(R.id.btnRegistrarDoacao)

        btnRegistrarDoacao.setOnClickListener {
            val dataDoacao = edtDataDoacao.text.toString()
            val horarioDoacao = edtHorarioDoacao.text.toString()
            val cpfUsuario = edtCpfUsuario.text.toString()
            val rgaPet = edtRgaPet.text.toString()

            if (dataDoacao.isNotBlank() && horarioDoacao.isNotBlank() &&
                cpfUsuario.isNotBlank() && rgaPet.isNotBlank()
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    val success = DatabaseHelper.registrarDoacao(dataDoacao, horarioDoacao, cpfUsuario, rgaPet)
                    withContext(Dispatchers.Main) {
                        if (success) {
                            Toast.makeText(this@DoarActivity, "Doação registrada com sucesso!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@DoarActivity, "Erro ao registrar doação.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
