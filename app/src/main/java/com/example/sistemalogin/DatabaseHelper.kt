package com.example.sistemalogin

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement

//127.0.0.1 or LocalHost
object DatabaseHelper {
    private const val DB_URL = "jdbc:sqlserver://172.20.190.112:1433;databaseName=SistemaLogin;encrypt=false"
    val DB_USER = "sa"
    val DB_PASSWORD = "123"
    fun getConnection(): Connection {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
    }

    // Função para adicionar um novo usuário no banco de dados
    fun addUsuario(nome: String, senha: String) {
        val connection = getConnection()
        val statement = connection.prepareStatement("INSERT INTO Usuarios (nome, senha) VALUES (?, ?)")
        statement.setString(0, nome)
        statement.setString(1, senha)

        statement.executeUpdate()

        statement.close()
        connection.close()
    }

    // Função para verificar se o usuário já existe (login)
    fun verificarUsuario(nome: String, senha: String): Boolean {
        val connection = getConnection()
        val statement = connection.prepareStatement("SELECT * FROM Usuarios WHERE nome = ? AND senha = ?")
        statement.setString(0, nome)
        statement.setString(1, senha)

        val resultSet = statement.executeQuery()
        val usuarioEncontrado = resultSet.next()

        resultSet.close()
        statement.close()
        connection.close()

        return usuarioEncontrado
    }

    fun registrarPet(rga: String, nome: String, raca: String, idade: Int): Boolean {
        val connection = getConnection()
        return try {
            val statement: PreparedStatement = connection.prepareStatement(
                "INSERT INTO Pet (rga, nome, raca, idade) VALUES (?, ?, ?, ?)"
            )
            statement.setString(1, rga)
            statement.setString(2, nome)
            statement.setString(3, raca)
            statement.setInt(4, idade)
            statement.executeUpdate() > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            connection.close()
        }
    }

    // Função para registrar uma nova doação
    fun registrarDoacao(data: String, horario: String, cpfUsuario: String, rgaPet: String): Boolean {
        val connection = getConnection()
        return try {
            val statement: PreparedStatement = connection.prepareStatement(
                "INSERT INTO Doa (data_doa, horario_doa, fk_usuario_cpf, fk_pet_rga) VALUES (?, ?, ?, ?)"
            )
            statement.setString(1, data)
            statement.setString(2, horario)
            statement.setString(3, cpfUsuario)
            statement.setString(4, rgaPet)
            statement.executeUpdate() > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            connection.close()
        }
    }
}

// Código Kristyson


//private fun sendDataToSqlServer(onCompletion: Runnable?) {
//    // Obtenha a data atual no formato "dd-MM-yyyy"
//    val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
//
//    // Crie uma string de conexão JDBC para o SQL Server
//    val DB_URL = "jdbc:jtds:sqlserver://10.0.0.148:1433/AdventureWorksDW2019"
//    val DB_USER = "sa"
//    val DB_PASSWORD = "Kristyson"
//
//    // Crie um objeto Connection para se conectar ao banco de dados
//    try {
//        DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD).use { conn ->
//            // Crie um objeto PreparedStatement para inserir os dados na tabela
//            val query =
//                "INSERT INTO dbo.AdventureWorksDWBuildVersion (palet, data) VALUES (?, ?)"
//            val statement = conn.prepareStatement(query)
//            statement.setString(1, paletCode)
//            statement.setString(2, currentDate)
//
//            // Execute a inserção de dados
//            val rowsInserted = statement.executeUpdate()
//
//            // Verifique se a inserção foi bem-sucedida
//            if (rowsInserted > 0) {
//                // Ação bem-sucedida, por exemplo, exibir um Toast
//                // ou realizar outras operações após o sucesso
//                showToast("Dados enviados para o SQL Server: '$currentDate'!")
//                onCompletion?.run()
//            } else {
//                // Tratamento de falha em caso de falha na inserção
//                showToast("Erro ao enviar dados para o SQL Server.")
//            }
//        }
//    } catch (e: SQLException) {
//        // Tratamento de exceção em caso de erro de conexão ou consulta SQL
//        showToast("Erro ao conectar ao SQL Server: " + e.getMessage())
//    }
//
//    // Limpe as variáveis após o envio
//    paletCode = null
//    posicaoCode = null
//    updateTextView("palet", "")
//    updateTextView("posicao", "")
//}