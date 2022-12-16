package com.example.ejercicio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.ejercicio2.databinding.ActivityIngresarEstudianteBinding
import com.example.ejercicio2.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject

class IngresarEstudiante : AppCompatActivity() {
    private lateinit var binding: ActivityIngresarEstudianteBinding
    private lateinit var volleyAPI: VolleyAPI
    private lateinit var url: String
    private val ipPuerto = "192.168.100.21:8080"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngresarEstudianteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        volleyAPI = VolleyAPI(this)

        binding.ingresaJsonBtn.setOnClickListener {
            studentAdd()
        }
    }

    private fun cleanInputTexts() {
        with(binding){
            /*[cuentaEt,
            nombreEt].forEach {
                it.text.clear()
            }*/
            cuentaEt.text.clear()
            nombreEt.text.clear()
            edadEt.text.clear()
            materiaIdEt.text.clear()
            materiaCreditosEt.text.clear()
            materiaNombreEt.text.clear()
        }
    }

    private fun studentAdd() {
        val urlJSON = "http://${ipPuerto}/agregarestudiante"
        var cadena = ""
        val jsonRequest = object : JsonArrayRequest(
            urlJSON,
            null,
            Response.ErrorListener {
                //binding.outText.text = "No se encuentra la información, revise la conexion "
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }

            override fun getBody(): ByteArray {
                val cuenta = binding.cuentaEt.text.toString()
                val nombre = binding.nombreEt.text.toString()
                val edad = binding.edadEt.text.toString()

                val estudiante = JSONObject()
                estudiante.put("cuenta", cuenta)
                estudiante.put("nombre",nombre)
                estudiante.put("edad", edad)

                val materia_id = binding.materiaIdEt.text.toString()
                val materia_creditos = binding.materiaCreditosEt.text.toString()
                val materia_nombre = binding.materiaNombreEt.text.toString()
                val materias = JSONArray()
                val itemMaterias = JSONObject()
                itemMaterias.put("id", materia_id)
                itemMaterias.put("nombre", materia_nombre)
                itemMaterias.put("creditos", materia_creditos)
                materias.put(itemMaterias)

                estudiante.put("materias", materias)
                cleanInputTexts()
                return estudiante.toString().toByteArray(charset = Charsets.UTF_8)
            }

            override fun getMethod(): Int {
                return Method.POST
            }
        }
        volleyAPI.add(jsonRequest)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}