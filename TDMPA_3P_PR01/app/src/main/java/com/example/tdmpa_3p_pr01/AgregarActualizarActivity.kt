package com.example.tdmpa_3p_pr01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class AgregarActualizarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_actualizar)

        val dbHelper = DatabaseHelper(this)

        val txtBienvenidaAgregarActualizar = findViewById<TextView>(R.id.txtBienvenidaAgregarActualizar)
        val txtNombreAdd = findViewById<EditText>(R.id.txtNombreAdd)
        val txtApellidosAdd = findViewById<EditText>(R.id.txtApellidosAdd)
        val txtCarreraAdd = findViewById<EditText>(R.id.txtCarreraAdd)
        val txtCreditosDeportivosAdd = findViewById<EditText>(R.id.txtCreditosDeportivosAdd)
        val txtCreditosCulturalesAdd = findViewById<EditText>(R.id.txtCreditosCulturalesAdd)
        val txtContraseñaAdd = findViewById<EditText>(R.id.txtContraseñaAdd)
        val txtConfirmarContraseña = findViewById<EditText>(R.id.txtConfirmarContraseña)
        val btnAgregarAdd = findViewById<Button>(R.id.btnAgregarAdd)
        val btnActualizarAdd = findViewById<Button>(R.id.btnActualizarAdd)
        val txtContraseñaView = findViewById<TextView>(R.id.txtContraseñaView)
        val txtConfirmarContraseñaView = findViewById<TextView>(R.id.txtConfirmarContraseñaView)
        val imgSalle = findViewById<ImageView>(R.id.imgSalle)

        imgSalle.visibility = View.INVISIBLE


        val loginModel = intent.getSerializableExtra("LoginModel") as? LoginModel
        val isUpdating = intent.getBooleanExtra("isUpdating", false)

        if (loginModel != null) {
            txtNombreAdd.setText(loginModel.name)
            txtApellidosAdd.setText(loginModel.lastName)
            txtCarreraAdd.setText(loginModel.career)
            txtCreditosDeportivosAdd.setText(loginModel.creditSports.toString())
            txtCreditosCulturalesAdd.setText(loginModel.creditCultural.toString())
            txtContraseñaAdd.setText(loginModel.password)
        }


        if (!isUpdating) {
            btnActualizarAdd.visibility = View.INVISIBLE
            btnAgregarAdd.visibility = View.VISIBLE
            btnAgregarAdd.setOnClickListener {

                if (!txtNombreAdd.text.isEmpty() &&
                    !txtApellidosAdd.text.isEmpty() &&
                    !txtCarreraAdd.text.isEmpty() &&
                    !txtCreditosDeportivosAdd.text.isEmpty() &&
                    !txtCreditosCulturalesAdd.text.isEmpty() &&
                    !txtContraseñaAdd.text.isEmpty() &&
                    !txtConfirmarContraseña.text.isEmpty() &&
                    txtContraseñaAdd.text.toString() == txtConfirmarContraseña.text.toString()
                ) {

                    val loginModel = LoginModel(
                        0,
                        txtNombreAdd.text.toString(),
                        txtApellidosAdd.text.toString(),
                        txtCarreraAdd.text.toString(),
                        txtCreditosDeportivosAdd.text.toString().toInt(),
                        txtCreditosCulturalesAdd.text.toString().toInt(),
                        txtContraseñaAdd.text.toString()
                    )
                    dbHelper.addLogin(loginModel)
                    val toast = Toast.makeText(
                        this@AgregarActualizarActivity,
                        "Usuario agregado",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    val toast = Toast.makeText(
                        this@AgregarActualizarActivity,
                        "No coinciden las contraseñas o rellena todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }else{
            btnActualizarAdd.visibility = View.VISIBLE
            btnAgregarAdd.visibility = View.INVISIBLE

            btnActualizarAdd.setOnClickListener {
                if (
                    !txtNombreAdd.text.isEmpty() &&
                    !txtApellidosAdd.text.isEmpty() &&
                    !txtCarreraAdd.text.isEmpty() &&
                    !txtCreditosDeportivosAdd.text.isEmpty() &&
                    !txtCreditosCulturalesAdd.text.isEmpty() &&
                    !txtContraseñaAdd.text.isEmpty() &&
                    !txtConfirmarContraseña.text.isEmpty() &&
                    txtContraseñaAdd.text.toString() == txtConfirmarContraseña.text.toString()
                ) {
                    val updatedLoginModel = LoginModel(
                        loginModel?.id ?: 0,
                        txtNombreAdd.text.toString(),
                        txtApellidosAdd.text.toString(),
                        txtCarreraAdd.text.toString(),
                        txtCreditosDeportivosAdd.text.toString().toInt(),
                        txtCreditosCulturalesAdd.text.toString().toInt(),
                        txtContraseñaAdd.text.toString()
                    )
                    dbHelper.updateLogin(updatedLoginModel)
                    Toast.makeText(
                        this@AgregarActualizarActivity,
                        "Datos actualizados",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@AgregarActualizarActivity,
                        "No coinciden las contraseñas o rellena todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        val inicioDeSesionUser = intent.getStringExtra("inicioDeSesionUser")
        val id = intent.getIntExtra("id", -1)
        val nombre = intent.getStringExtra("nombre") ?: ""
        val apellidos = intent.getStringExtra("apellidos") ?: ""
        val carrera = intent.getStringExtra("carrera") ?: ""
        val creditosDeportivos = intent.getIntExtra("creditosDeportivos", 0)
        val creditosCulturales = intent.getIntExtra("creditosCulturales", 0)

        if (inicioDeSesionUser == "inicioDeSesionUser"){
            txtNombreAdd.isEnabled = false
            txtApellidosAdd.isEnabled = false
            txtCarreraAdd.isEnabled = false
            txtCreditosDeportivosAdd.isEnabled = false
            txtCreditosCulturalesAdd.isEnabled = false

            txtContraseñaAdd.visibility = View.INVISIBLE
            txtConfirmarContraseña.visibility = View.INVISIBLE
            txtContraseñaView.visibility = View.INVISIBLE
            txtConfirmarContraseñaView.visibility = View.INVISIBLE
            imgSalle.visibility = View.VISIBLE

            txtNombreAdd.setText(nombre)
            txtApellidosAdd.setText(apellidos)
            txtCarreraAdd.setText(carrera)
            txtCreditosDeportivosAdd.setText(creditosDeportivos.toString())
            txtCreditosCulturalesAdd.setText(creditosCulturales.toString())

            btnAgregarAdd.setText("Cerrar sesión")
            txtBienvenidaAgregarActualizar.setText("Bienvenido(a) ${nombre}")
        }

        if (btnAgregarAdd.text == "Cerrar sesión"){
            btnAgregarAdd.setOnClickListener {
                finish()
            }
        }
    }
}