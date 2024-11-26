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

class PetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet)

        val edtRgaPet = findViewById<EditText>(R.id.edtRgaPet)
        val edtNomePet = findViewById<EditText>(R.id.edtNomePet)
        val edtRacaPet = findViewById<EditText>(R.id.edtRacaPet)
        val edtIdadePet = findViewById<EditText>(R.id.edtIdadePet)
        val btnRegistrarPet = findViewById<Button>(R.id.btnRegistrarPet)

        btnRegistrarPet.setOnClickListener {
            val rgaPet = edtRgaPet.text.toString()
            val nomePet = edtNomePet.text.toString()
            val racaPet = edtRacaPet.text.toString()
            val idadePet = edtIdadePet.text.toString().toIntOrNull()

            if (rgaPet.isNotBlank() && nomePet.isNotBlank() && racaPet.isNotBlank() && idadePet != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    val success = DatabaseHelper.registrarPet(rgaPet, nomePet, racaPet, idadePet)
                    withContext(Dispatchers.Main) {
                        if (success) {
                            Toast.makeText(this@PetActivity, "Pet registrado com sucesso!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@PetActivity, "Erro ao registrar pet.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
