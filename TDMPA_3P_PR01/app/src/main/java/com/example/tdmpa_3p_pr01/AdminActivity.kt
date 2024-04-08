package com.example.tdmpa_3p_pr01

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val btnAgregarEstudiantes = findViewById<Button>(R.id.btnAgregarEstudiantes)
        val btnActualizasDatosEstudiantes = findViewById<Button>(R.id.btnActualizasDatosEstudiantes)
        val spnEstudiantes = findViewById<Spinner>(R.id.spnEstudiantes)

        spnEstudiantes.visibility = View.INVISIBLE

        btnAgregarEstudiantes.setOnClickListener{
            val intento = Intent(this@AdminActivity, AgregarActualizarActivity::class.java)
            spnEstudiantes.visibility = View.INVISIBLE
            startActivity(intento)
        }

        btnActualizasDatosEstudiantes.setOnClickListener{
            val nombresUsuarios = obtenerNombresUsuarios()

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresUsuarios)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnEstudiantes.adapter = adapter

            spnEstudiantes.visibility = View.VISIBLE
        }

        spnEstudiantes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                val selectedName = parent.getItemAtPosition(position).toString()
                val loginModel = dbHelper.getLoginByUsername(selectedName)

                loginModel?.let {
                    val isUpdating = true
                    val intento = Intent(this@AdminActivity, AgregarActualizarActivity::class.java).apply {
                        putExtra("LoginModel", it)
                        putExtra("isUpdating", isUpdating)
                    }
                    startActivity(intento)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    var dbHelper = DatabaseHelper(this)

    @SuppressLint("Range")
    private fun obtenerNombresUsuarios(): List<String> {
        val nombres = mutableListOf<String>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_NAME,
            arrayOf(DatabaseHelper.KEY_NAME),
            null,
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val nombre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NAME))
            nombres.add(nombre)
        }
        cursor.close()
        db.close()
        return nombres
    }
}