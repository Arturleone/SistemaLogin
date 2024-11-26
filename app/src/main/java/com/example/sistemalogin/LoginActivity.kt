package com.example.sistemalogin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.AsyncTask
import android.view.View

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtNome: EditText = findViewById(R.id.edtName)
        val edtSenha: EditText = findViewById(R.id.edtPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val nome = edtNome.text.toString()
            val senha = edtSenha.text.toString()

            if (nome.isNotBlank() && senha.isNotBlank()) {
                val task = LoginTask(this)  // Passando o contexto corretamente
                task.execute(nome, senha)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // AsyncTask para verificar as credenciais de login
    private class LoginTask(val context: Context) : AsyncTask<String, Void, Boolean>() {
        override fun doInBackground(vararg params: String?): Boolean {
            val nome = params[0] ?: return false
            val senha = params[1] ?: return false

            return try {
                DatabaseHelper.verificarUsuario(nome, senha)
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        override fun onPostExecute(result: Boolean) {
            super.onPostExecute(result)
            if (result) {
                // Redirecionar usuário para a tela principal
                Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                // Inicie a MainActivity aqui
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Credenciais inválidas.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun registerAccount(view: View): Unit {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}
