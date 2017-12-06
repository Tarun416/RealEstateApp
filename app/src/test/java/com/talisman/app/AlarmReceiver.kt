package com.talisman.app

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Tarun on 12/6/17.
 */
class AlarmReceiver : BroadcastReceiver()
{

    companion object {
        var NOTIFICATION = "notification"
        var NOTIFICATION_ID = "notification_id"
    }

    override fun onReceive(p0: Context, p1: Intent) {
        val notificationManager = p0!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification :  Notification = p1.getParcelableExtra(NOTIFICATION)
        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL
        val id = p1.getIntExtra(NOTIFICATION_ID, 0)
        notificationManager.notify(id, notification)

    }

}