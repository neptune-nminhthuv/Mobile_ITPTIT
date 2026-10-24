3. Vòng đời ứng dụng Android
3.1. Phân biệt vòng đời ứng dụng và Activity
Trong Android, cần phân biệt:
- Vòng đời tiến trình ứng dụng: hệ thống có thể tạo hoặc kết thúc
  tiến trình tùy nhu cầu tài nguyên và trạng thái ứng dụng.
- Vòng đời Activity: mô tả các trạng thái của một màn hình và
  những callback Android gọi khi màn hình thay đổi trạng thái.
Không có một chuỗi trạng thái duy nhất áp dụng cho toàn bộ mọi ứng dụng.

3.2. Các callback chính của Activity
Callback	Ý nghĩa	Ví dụ
onCreate()	Được gọi khi Activity được tạo; thường khởi tạo giao diện và biến.	Mở màn hình lần đầu.
onStart()	Activity bắt đầu hiển thị với người dùng.	Màn hình xuất hiện.
onResume()	Activity ở trạng thái hoạt động, người dùng có thể tương tác.	Bấm nút, nhập dữ liệu.
onPause()	Activity mất trạng thái tương tác trực tiếp; có thể vẫn còn nhìn thấy một phần.	Một cửa sổ khác xuất hiện phía trước.
onStop()	Activity không còn hiển thị trên màn hình.	Chuyển sang ứng dụng khác che toàn bộ màn hình.
onRestart()	Được gọi khi Activity đã dừng và chuẩn bị bắt đầu lại.	Quay lại ứng dụng sau khi nó bị che hoàn toàn.
onDestroy()	Được gọi khi Activity bị hủy, chẳng hạn do finish() hoặc thay đổi cấu hình.	Đóng màn hình.

Quan trọng: Android có thể kết thúc tiến trình ứng dụng khi cần bộ nhớ mà không gọi onDestroy(). Vì vậy, không nên chỉ dựa vào callback này để lưu dữ liệu quan trọng.
3.3. Luồng cơ bản
Mở màn hình
    |
    v
 onCreate()
    |
    v
  onStart()
    |
    v
 onResume()
    |
    |  Người dùng rời màn hình
    v
  onPause()
    |
    v
  onStop()
    |
    +------ Quay lại ------> onRestart()
    |                            |
    |                            v
    |                         onStart()
    |                            |
    |                            v
    |                         onResume()
    |
    +------ Activity kết thúc --> onDestroy()
Đây là luồng giản lược. Callback thực tế phụ thuộc thao tác của người
dùng, Activity khác, đa cửa sổ, thay đổi cấu hình và hành vi hệ thống.
Tiến trình ứng dụng cũng có thể bị hệ thống kết thúc khi Activity đã ở
nền; không nên coi onDestroy() là callback chắc chắn luôn được gọi khi
tiến trình bị kết thúc.
3.4. Ví dụ ghi log vòng đời
package com.example.lifecycle

import android.app.Activity
import android.os.Bundle
import android.util.Log

class MainActivity : Activity() {

    private val tag = "ActivityLifecycle"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
    }
}
Chạy ứng dụng và quan sát Logcat trong Android Studio với bộ lọc
ActivityLifecycle. Thử mở app, chuyển sang ứng dụng khác rồi quay lại
để quan sát thứ tự callback.
3.5. Các trạng thái cần hiểu
- Created: Activity vừa được tạo; thường đi qua onCreate().
- Started: Activity đang hiển thị, nhưng chưa chắc đã nhận tương
  tác trực tiếp.
- Resumed: Activity ở tiền cảnh và thường là nơi người dùng đang
  tương tác.
- Paused: Activity mất trạng thái tương tác tiền cảnh; trong một
  số trường hợp vẫn có thể nhìn thấy một phần.
- Stopped: Activity không còn hiển thị.
- Destroyed: Instance Activity đã bị hủy.
Các tên trên mô tả vòng đời Activity; chúng không đồng nghĩa với việc
tiến trình ứng dụng chắc chắn còn tồn tại hoặc đã bị kết thúc.
