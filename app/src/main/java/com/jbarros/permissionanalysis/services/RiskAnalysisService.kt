package com.jbarros.permissionanalysis.services

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.widget.Toast
import com.jbarros.permissionanalysis.domain.permissionanalysis.NewPermissionAnalysis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("SpecifyJobSchedulerIdRange")
@AndroidEntryPoint
class RiskAnalysisService(
) : JobService() {
    @Inject
    lateinit var newPermissionAnalysis: NewPermissionAnalysis

    private val handler = Handler(object : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            // Realiza tu caso de uso aquí
            showToast("Caso de uso ejecutado a las 3 a.m.")
            jobFinished(msg.obj as JobParameters, false)
            return true
        }
    })

    override fun onStartJob(params: JobParameters?): Boolean {
        CoroutineScope(Dispatchers.IO).launch {
            // Simula un trabajo que lleva tiempo ejecutarse
            try {
                newPermissionAnalysis.invoke()
                // Simula un trabajo que lleva tiempo ejecutarse (5 segundos)
                kotlinx.coroutines.delay(5000)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Envía un mensaje al handler cuando el trabajo está completo
            val message = handler.obtainMessage()
            message.obj = params
            handler.sendMessage(message)
        }

        // Retorna true para indicar que hay trabajo en curso
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        // Retorna false para indicar que no necesitas reprogramar la tarea
        return false
    }

    private fun showToast(message: String) {
        // Este método es solo para ilustrar que la tarea se ha ejecutado
        // Puedes reemplazar esto con tu propia lógica de caso de uso
        // (por ejemplo, ejecutar un caso de uso real en lugar de mostrar un Toast)
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}