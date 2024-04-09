package com.ucne.instantticket

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ucne.instantticket.data.entity.EventoEntity
import com.ucne.instantticket.data.repository.EventoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltWorker
class NotificationService @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val eventoRepository: EventoRepository
) : Worker(appContext, workerParams) {
        override fun doWork(): Result {

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val eventos = obtenerEventosParaNotificar(eventoRepository).first()
                    eventos.forEach { evento ->
                        programarNotificacion(evento)
                    }
                } catch (e: Exception) {
                }
            }
            return Result.success()
        }

    @SuppressLint("MissingPermission")
    private fun programarNotificacion(evento: EventoEntity) {
        val alarmManager =
            applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, NotificationReceiver::class.java).apply {
            putExtra("evento_id", evento.idevento)
            putExtra("nombre_evento", evento.nombreEvento)
            putExtra("descripcion_evento", evento.descripcion)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            evento.idevento,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val tiempoNotificacion =
            calcularTiempoNotificacion(evento.fecha.toLong(), evento.recordatorio.toLong())
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            tiempoNotificacion,
            pendingIntent
        )
    }


    private fun calcularTiempoNotificacion(fechaEvento: Long, tiempoRecordatorio: Long): Long {
        return fechaEvento - tiempoRecordatorio
    }

    private fun obtenerEventosParaNotificar(eventoRepository: EventoRepository): Flow<List<EventoEntity>> {

        return eventoRepository.getEventosProximos()
    }
}
