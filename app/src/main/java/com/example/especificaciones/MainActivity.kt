package com.example.especificaciones

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.especificaciones.R
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mostrarInfoBasica()
    }

    private fun mostrarInfoBasica() {
        // 1. INFORMACIÓN DEL DISPOSITIVO
        findViewById<TextView>(R.id.tvMarca).text = Build.MANUFACTURER
        findViewById<TextView>(R.id.tvModelo).text = Build.MODEL
        findViewById<TextView>(R.id.tvAndroid).text = "Android ${Build.VERSION.RELEASE}"

        // 2. PANTALLA
        val displayMetrics = resources.displayMetrics
        val ancho = displayMetrics.widthPixels
        val alto = displayMetrics.heightPixels
        val densidad = displayMetrics.density

        findViewById<TextView>(R.id.tvPantalla).text = "${ancho}x${alto} px"
        findViewById<TextView>(R.id.tvDensidad).text = "Densidad: ${String.format("%.1f", densidad)}"

        // 3. ALMACENAMIENTO (simplificado)
        try {
            val stat = android.os.StatFs(android.os.Environment.getDataDirectory().path)
            val totalGB = stat.totalBytes / 1_000_000_000f
            findViewById<TextView>(R.id.tvAlmacenamiento).text = "${String.format("%.1f", totalGB)} GB"
        } catch (e: Exception) {
            findViewById<TextView>(R.id.tvAlmacenamiento).text = "N/A"
        }

        // 4. MEMORIA RAM (aproximada)
        val memoriaInfo = android.app.ActivityManager.MemoryInfo()
        (getSystemService(android.app.ActivityManager::class.java))?.getMemoryInfo(memoriaInfo)
        val ramGB = memoriaInfo.totalMem / 1_000_000_000f
        findViewById<TextView>(R.id.tvRAM).text = "${String.format("%.1f", ramGB)} GB"

        // 5. PROCESADOR
        findViewById<TextView>(R.id.tvProcesador).text = Build.HARDWARE

        // 6. RESUMEN
        val resumen = """
            Dispositivo: ${Build.MANUFACTURER} ${Build.MODEL}
            Android: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})
            Pantalla: ${ancho}x${alto} px
            RAM: ${String.format("%.1f", ramGB)} GB
            Procesador: ${Build.HARDWARE}
        """.trimIndent()

        findViewById<TextView>(R.id.tvResumen).text = resumen
    }
}