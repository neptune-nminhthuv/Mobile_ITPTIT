1. Các Components của Android
1.1. Khái niệm
Android có bốn thành phần ứng dụng cốt lõi:
1. Activity -- điểm tương tác với người dùng, thường đại diện cho
   một màn hình.
2. Service -- thực hiện công việc không cần giao diện trực tiếp.
3. Broadcast Receiver -- tiếp nhận và xử lý các thông điệp
   broadcast.
4. Content Provider -- quản lý và cung cấp dữ liệu thông qua giao
   diện chuẩn, thường dùng khi cần chia sẻ dữ liệu giữa các ứng dụng.
Các thành phần được khai báo phù hợp trong AndroidManifest.xml.
Intent thường được dùng để yêu cầu hệ thống kích hoạt Activity,
Service hoặc Broadcast Receiver. Content Provider thường được truy cập
thông qua ContentResolver.


1.2. Activity
Tác dụng: Hiển thị giao diện và xử lý tương tác của người dùng, ví
dụ màn hình đăng nhập hoặc màn hình danh sách sản phẩm.

Ví dụ MainActivity.kt với giao diện tạo bằng code:
package com.example.componentdemo

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val title = TextView(this)
        title.text = "Xin chào Android"

        val button = Button(this)
        button.text = "Nhấn vào đây"
        button.setOnClickListener {
            Toast.makeText(this, "Bạn vừa nhấn nút", Toast.LENGTH_SHORT).show()
        }

        layout.addView(title)
        layout.addView(button)
        setContentView(layout)
    }
}
Trong ví dụ, MainActivity là một màn hình; onCreate() khởi tạo giao
diện và setOnClickListener xử lý sự kiện nhấn nút.


1.3. Service
Tác dụng: Thực hiện một công việc không có giao diện riêng, chẳng
hạn phát nhạc hoặc duy trì một tác vụ đang hoạt động. Service không tự
động tạo ra một luồng nền; công việc nặng không nên chạy trực tiếp
trên luồng giao diện. Với tác vụ nền có thể trì hoãn, nên cân nhắc
WorkManager.

Ví dụ một Service đơn giản:

package com.example.componentdemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class DemoService : Service() {
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        Log.d("DemoService", "Service đã được yêu cầu chạy")
        stopSelf(startId)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
Khai báo bên trong thẻ <application> của AndroidManifest.xml:
<service
    android:name=".DemoService"
    android:exported="false" />
Có thể yêu cầu chạy Service từ Activity:
val intent = Intent(this, DemoService::class.java)
startService(intent)
Đây là ví dụ học tập. Android hiện đại giới hạn việc chạy Service từ
nền; các tác vụ dài cần tuân thủ quy định foreground service hoặc dùng
WorkManager tùy trường hợp.


1.4. Broadcast Receiver
Tác dụng: Nhận thông điệp broadcast từ hệ thống hoặc ứng dụng, ví dụ
thông điệp do ứng dụng gửi để thông báo một sự kiện.

Ví dụ receiver nhận broadcast tùy chỉnh:
package com.example.componentdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DemoReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("DemoReceiver", "Đã nhận broadcast")
    }
}
Khai báo trong Manifest:
<receiver
    android:name=".DemoReceiver"
    android:exported="false">
    <intent-filter>
        <action android:name="com.example.componentdemo.MY_EVENT" />
    </intent-filter>
</receiver>
Gửi broadcast tường minh trong ứng dụng:
val intent = Intent("com.example.componentdemo.MY_EVENT")
intent.setPackage(packageName)
sendBroadcast(intent)
onReceive() nên xử lý nhanh; không thực hiện công việc kéo dài trực
tiếp trong callback. Các quy định đăng ký receiver và nhận broadcast hệ
thống phụ thuộc phiên bản Android.


1.5. Content Provider
Tác dụng: Cung cấp dữ liệu có cấu trúc thông qua URI và các thao tác
như truy vấn, thêm, sửa, xóa. Ví dụ, Android có Contacts Provider để ứng
dụng được cấp quyền có thể truy cập danh bạ.

Ví dụ khung Content Provider tùy chỉnh (chỉ minh họa cấu trúc):
package com.example.componentdemo

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

class DemoProvider : ContentProvider() {
    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        // Thực tế cần truy vấn nguồn dữ liệu và trả về Cursor.
        return null
    }

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0
}
Khai báo mẫu:
<provider
    android:name=".DemoProvider"
    android:authorities="com.example.componentdemo.provider"
    android:exported="false" />
Đây chỉ là bộ khung minh họa; provider thực tế cần định nghĩa URI, dữ
liệu, truy vấn và cơ chế phân quyền.


