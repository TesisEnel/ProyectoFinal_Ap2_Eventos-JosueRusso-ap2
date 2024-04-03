package com.ucne.instantticket

import android.app.IntentService
import android.content.Intent
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationService(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        // Aquí deberías recuperar la información del evento para determinar la fecha del evento y el recordatorio

        // Supongamos que tienes una función para obtener la fecha del evento
        val fechaEvento = obtenerFechaEvento()

        // Supongamos que también tienes una función para obtener el tiempo de recordatorio
        val tiempoRecordatorio = obtenerTiempoRecordatorio()

        // Calcula el tiempo para mostrar la notificación
        val tiempoNotificacion = fechaEvento - tiempoRecordatorio

        // Programa la notificación
        programarNotificacion(tiempoNotificacion)

        return Result.success()
    }

    private fun programarNotificacion(tiempoNotificacion: Long) {
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        // Aquí debes configurar adecuadamente el tiempo de la notificación según tu lógica
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, tiempoNotificacion, pendingIntent)
    }

    private fun obtenerFechaEvento(): Long {
        // Aquí debes implementar la lógica para obtener la fecha del evento en milisegundos
        // Por ejemplo, puedes obtener la fecha de la entidad EventoEntity
        return System.currentTimeMillis() + 86400000 // Supongamos que la fecha es dentro de 24 horas
    }

    private fun obtenerTiempoRecordatorio(): Long {
        // Aquí debes implementar la lógica para obtener el tiempo de recordatorio en milisegundos
        // Por ejemplo, puedes obtener el tiempo de recordatorio de la entidad EventoEntity
        return 3600000 // Supongamos que el recordatorio es una hora antes del evento
    }
}
