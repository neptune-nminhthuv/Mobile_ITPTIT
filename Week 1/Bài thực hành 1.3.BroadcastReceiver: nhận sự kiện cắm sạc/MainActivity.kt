
package com.example.a3service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    // Tạo BroadcastReceiver
    private val powerReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {

            if (intent.action == Intent.ACTION_POWER_CONNECTED) {

                Toast.makeText(
                    context,
                    "Thiết bị đã được cắm sạc!",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("PowerReceiver", "Đã nhận sự kiện cắm sạc")
            }
        }
    }

    // Kiểm tra trạng thái đăng ký
    private var isReceiverRegistered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "BroadcastReceiver",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Đang chờ sự kiện cắm sạc...")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (!isReceiverRegistered) {

            val filter = IntentFilter(
                Intent.ACTION_POWER_CONNECTED
            )

            ContextCompat.registerReceiver(
                this,
                powerReceiver,
                filter,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
            isReceiverRegistered = true

            Log.d("PowerReceiver", "Receiver đã được đăng ký")
        }
    }

    override fun onStop() {
        if (isReceiverRegistered) {

            unregisterReceiver(powerReceiver)

            isReceiverRegistered = false

            Log.d("PowerReceiver", "Receiver đã được hủy")
        }
        super.onStop()
    }
}
