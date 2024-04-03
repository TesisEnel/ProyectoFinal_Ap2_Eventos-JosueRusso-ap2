package com.ucne.instantticket

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Aquí maneja la notificación
        val notificationManager =
            ContextCompat.getSystemService(
                context!!,
                NotificationManager::class.java
            ) as NotificationManager

        // Crea la notificación
        val builder = NotificationCompat.Builder(context, "channel_id")
            .setContentTitle("Título de la notificación")
            .setContentText("Descripción de la notificación")
            .setSmallIcon(R.drawable.calendar_regular)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Muestra la notificación
        notificationManager.notify(123, builder.build())
    }
}