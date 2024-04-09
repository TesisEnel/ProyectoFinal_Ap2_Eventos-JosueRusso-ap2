package com.ucne.instantticket

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("NotificationReceiver", "onReceive called")
        val notificationManager =
            ContextCompat.getSystemService(
                context!!,
                NotificationManager::class.java
            ) as NotificationManager

        val eventoId = intent?.getIntExtra("evento_id", -1)
        val nombreEvento = intent?.getStringExtra("nombre_evento")
        val descripcionEvento = intent?.getStringExtra("descripcion_evento")

        val builder = NotificationCompat.Builder(context, "channel_id")
            .setContentTitle(nombreEvento)
            .setContentText(descripcionEvento)
            .setSmallIcon(R.drawable.calendar_regular)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(eventoId ?: 0, builder.build())
    }
}