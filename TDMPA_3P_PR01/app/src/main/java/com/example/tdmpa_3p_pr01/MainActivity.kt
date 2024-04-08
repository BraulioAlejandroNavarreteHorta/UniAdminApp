package com.example.tdmpa_3p_pr01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtUsuario = findViewById<EditText>(R.id.txtUsernameLogin)
        val txtPassword = findViewById<EditText>(R.id.txtPasswordLogin)
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)

        btnIniciarSesion.setOnClickListener {

            if (txtUsuario.text.toString() == "admin" && txtPassword.text.toString() == "admin") {
                startActivity(Intent(this@MainActivity, AdminActivity::class.java))
            } else {

                val loginCorrecto = iniciar(txtUsuario.text.toString(), txtPassword.text.toString())
                if (loginCorrecto) {

                    val retrievedLogin = dbHelper.getLoginByUsername(txtUsuario.text.toString())


                    val inicioDeSesionUser = "inicioDeSesionUser"
                    val intent =
                        Intent(this@MainActivity, AgregarActualizarActivity::class.java).apply {

                            putExtra("id", retrievedLogin?.id)
                            putExtra("nombre", retrievedLogin?.name)
                            putExtra("apellidos", retrievedLogin?.lastName)
                            putExtra("carrera", retrievedLogin?.career)
                            putExtra("creditosDeportivos", retrievedLogin?.creditSports)
                            putExtra("creditosCulturales", retrievedLogin?.creditCultural)
                            putExtra("inicioDeSesionUser", inicioDeSesionUser)
                        }
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Usuario y/o contraseña incorrecto",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    val dbHelper = DatabaseHelper(this)
    fun iniciar(usuario: String, password: String): Boolean {

        if (usuario != "admin") {
            val retrievedLogin = dbHelper.getLoginByUsername(usuario)
            return retrievedLogin?.password == password
        }
        return false
    }
}