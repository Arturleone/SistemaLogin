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
import java.lang.ref.WeakReference

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.register_btn_register)
        val edtName = findViewById<EditText>(R.id.register_edt_name)
        val edtPassword = findViewById<EditText>(R.id.register_edt_password)

        btnRegister.setOnClickListener {
            val nome = edtName.text.toString()
            val senha = edtPassword.text.toString()

            if (nome.isNotBlank() && senha.isNotBlank()) {
                // Passando o contexto corretamente para a AsyncTask
                val task = RegisterTask(this)
                task.execute(nome, senha)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loginAccount(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    // RegisterTask agora utiliza um WeakReference para evitar vazamento de memória
    private class RegisterTask(activity: RegisterActivity) : AsyncTask<String, Void, Boolean>() {
        private val activityReference = WeakReference(activity)

        override fun doInBackground(vararg params: String?): Boolean {
            val nome = params[0] ?: return false
            val senha = params[1] ?: return false

            return try {
                DatabaseHelper.addUsuario(nome, senha)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        override fun onPostExecute(result: Boolean) {
            super.onPostExecute(result)
            val activity = activityReference.get() // Obtém a referência para a atividade
            if (activity != null && !activity.isFinishing) {
                if (result) {
                    Toast.makeText(activity, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show()
                    // Usa um método seguro para iniciar a nova atividade
                    activity.startActivity(Intent(activity, MainActivity::class.java))
                    activity.finish() // Finaliza a atividade de registro
                } else {
                    Toast.makeText(activity, "Erro ao registrar usuário.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
