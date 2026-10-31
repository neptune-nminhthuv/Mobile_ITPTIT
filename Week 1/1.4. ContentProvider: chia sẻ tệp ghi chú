
package com.example.a4service

import android.os.Bundle
import android.content.Intent
import android.content.ClipData
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var note by rememberSaveable {
                mutableStateOf("")
            }

            var result by rememberSaveable {
                mutableStateOf("")
            }

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
                        onValueChange = {
                            note = it
                        },
                        label = {
                            Text("Nội dung ghi chú")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4
                    )

                    Button(
                        onClick = {
                            val count = if (note.isBlank()) {
                                0
                            } else {
                                note.trim()
                                    .split(Regex("\\s+"))
                                    .size
                            }

                            result = "Số từ trong ghi chú: $count"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Đếm số từ")
                    }

                    Text(
                        text = result,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Button(
                        onClick = {
                            try {
                                shareNote(note)
                                result = "Đang mở ứng dụng chia sẻ..."
                            } catch (e: Exception) {
                                result = "Lỗi chia sẻ: ${e.message}"
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Chia sẻ ghi chú")
                    }
                }
            }
        }
    }

    private fun shareNote(note: String) {

        val sharedDir = File(cacheDir, "shared")

        if (!sharedDir.exists()) {
            sharedDir.mkdirs()
        }

        val file = File(sharedDir, "note.txt")

        file.writeText(note)

        //  Lấy URI bằng FileProvider
        val uri = FileProvider.getUriForFile(
            this,
            "$packageName.fileprovider",
            file
        )

        //  Tạo Intent chia sẻ file
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"

            putExtra(Intent.EXTRA_STREAM, uri)
            // Cấp quyền đọc URI cho ứng dụng nhận
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            // Đính kèm URI để đảm bảo quyền được truyền

            clipData = ClipData.newUri(
                contentResolver,
                "Ghi chú học tập",
                uri
            )
        }
        // Mở danh sách ứng dụng chia sẻ
        val chooser = Intent.createChooser(
            sendIntent,
            "Chia sẻ ghi chú"
        )
        startActivity(chooser)
    }
}
