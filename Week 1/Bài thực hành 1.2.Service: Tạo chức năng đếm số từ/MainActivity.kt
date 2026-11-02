
package com.example.a2service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    // Lưu tham chiếu đến Service
    private var wordCountService: WordCountService? = null

    // Kiểm tra đã kết nối Service hay chưa
    private var isBound = false

    // Trạng thái kết nối để cập nhật giao diện
    private val serviceConnected = mutableStateOf(false)

    // Định nghĩa ServiceConnection
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            // Lấy đối tượng Service từ Binder
            val binder = service as WordCountService.LocalBinder
            wordCountService = binder.getService()

            isBound = true
            serviceConnected.value = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            wordCountService = null
            isBound = false
            serviceConnected.value = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var note by rememberSaveable { mutableStateOf("") }
            var result by rememberSaveable { mutableStateOf("") }

            MaterialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Ghi chú học tập",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text("Nội dung ghi chú") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4
                    )

                    Button(
                        onClick = {
                            val service = wordCountService

                            if (service != null) {
                                val count = service.countWords(note)
                                result = "Số từ trong ghi chú: $count"
                            }
                        },
                        enabled = serviceConnected.value,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Đếm số từ")
                    }

                    Text(
                        text = if (serviceConnected.value) {
                            "Service đã kết nối"
                        } else {
                            "Đang kết nối Service..."
                        }
                    )

                    Text(text = result)
                    Button(
                        onClick = {
                            result = "Chức năng chia sẻ sẽ được thực hiện bằng FileProvider."
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Chia sẻ ghi chú")
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, WordCountService::class.java)
        isBound = bindService(
            intent,
            connection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onStop() {
        super.onStop()

        if (isBound) {
            unbindService(connection)
            isBound = false
            wordCountService = null
            serviceConnected.value = false
        }
    }
}

