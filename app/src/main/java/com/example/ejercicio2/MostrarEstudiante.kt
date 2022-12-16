package com.example.ejercicio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.ejercicio2.databinding.ActivityIngresarEstudianteBinding
import com.example.ejercicio2.databinding.ActivityMostrarEstudianteBinding
import org.json.JSONArray

class MostrarEstudiante : AppCompatActivity() {
    private lateinit var binding: ActivityMostrarEstudianteBinding
    private lateinit var volleyAPI: VolleyAPI
    private lateinit var url: String
    private val ipPuerto = "192.168.100.21:8080"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMostrarEstudianteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        volleyAPI = VolleyAPI(this)

        studentJSON()
    }

    private fun studentJSON() {
        val urlJSON = "http://${ipPuerto}/estudiantesJSON"
        var cadena = ""
        val jsonRequest = object : JsonArrayRequest(
            urlJSON,
            Response.Listener<JSONArray> { response ->
                (0 until response.length()).forEach {
                    val estudiante = response.getJSONObject(it)
                    val materia = estudiante.getJSONArray("materias")

                    cadena += "{ \n\tcuenta: ${estudiante.get("cuenta")}\n\tedad: ${estudiante.get("nombre")}\n\tedad: ${estudiante.get("edad")} \n\t** "

                    (0 until materia.length()).forEach {
                        val datos = materia.getJSONObject(it)
                        cadena += datos.get("nombre").toString() + "\n\t** " + datos.get("creditos")
                            .toString() + "\n\t** "
                    }
                    cadena += "\n}\n"
                }
                binding.responseTv.text = cadena
                //Toast.makeText(applicationContext, cadena, Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                //binding.outText.text = "No se encuentra la información, revise la conexion "
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyAPI.add(jsonRequest)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}