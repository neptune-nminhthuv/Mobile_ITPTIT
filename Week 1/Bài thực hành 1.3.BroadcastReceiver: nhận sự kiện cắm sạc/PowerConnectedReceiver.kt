package com.example.a3service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class PowerConnectedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_POWER_CONNECTED) {

            Toast.makeText(
                context,
                "Thiết bị đã được cắm sạc!",
                Toast.LENGTH_SHORT
            ).show()

            Log.d(
                "PowerReceiver",
                "ACTION_POWER_CONNECTED received"
            )
        }
    }
}
