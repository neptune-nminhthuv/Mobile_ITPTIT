
package com.example.a2service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class WordCountService : Service() {

    // Binder giúp Activity lấy được đối tượng Service
    inner class LocalBinder : Binder() {
        fun getService(): WordCountService {
            return this@WordCountService
        }
    }

    private val binder = LocalBinder()
    // Trả Binder khi Activity kết nối
    override fun onBind(intent: Intent): IBinder {
        return binder
    }
    // Hàm đếm số từ
    fun countWords(note: String): Int {
        if (note.isBlank()) {
            return 0
        }

        return note.trim().split(Regex("\\s+")).size
    }
}


