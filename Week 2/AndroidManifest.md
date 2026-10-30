# TÌM HIỂU ANDROIDMANIFEST.XML TRONG ANDROID DEVELOPMENT


## 1. Tổng quan về AndroidManifest.xml

### 1.1. AndroidManifest.xml là gì?

AndroidManifest.xml là một file cấu hình bắt buộc trong mỗi ứng dụng Android. File này cung cấp những thông tin quan trọng để hệ điều hành Android có thể nhận biết, cài đặt và khởi chạy ứng dụng.

Có thể hiểu AndroidManifest.xml giống như một bản khai báo thông tin của ứng dụng. Thông qua file này, hệ thống Android biết được:

- Ứng dụng có những thành phần nào.
- Ứng dụng cần sử dụng những quyền truy cập nào.
- Activity nào là màn hình khởi chạy chính.
- Các thành phần có thể tiếp nhận những loại Intent nào.
- Những thông tin cấu hình chung của ứng dụng.

### 1.2. Vai trò của AndroidManifest.xml

AndroidManifest.xml có những vai trò chính sau:

**1. Khai báo các thành phần của ứng dụng**

Android có bốn thành phần ứng dụng cơ bản:

- Activity: Đại diện cho một màn hình giao diện.
- Service: Thành phần thực hiện công việc mà không cần giao diện trực tiếp.
- BroadcastReceiver: Tiếp nhận và xử lý các sự kiện phát sóng.
- ContentProvider: Quản lý và chia sẻ dữ liệu giữa các ứng dụng.

**2. Khai báo quyền truy cập**

Nếu ứng dụng cần truy cập Internet, camera hoặc vị trí thiết bị, Manifest có thể khai báo các quyền tương ứng.

**3. Xác định điểm khởi chạy ứng dụng**

Manifest giúp hệ thống xác định Activity nào sẽ được mở khi người dùng nhấn vào biểu tượng ứng dụng.

**4. Cung cấp thông tin cấu hình**

Khai báo tên ứng dụng, biểu tượng, theme và những thông tin cần thiết khác.

### 1.3. Tại sao Android cần AndroidManifest.xml?

Khi cài đặt ứng dụng, hệ điều hành Android cần biết ứng dụng cung cấp những thành phần nào và cách tương tác với chúng.

Thay vì phải đọc và phân tích toàn bộ mã nguồn Kotlin, Android sử dụng Manifest để nắm bắt các thông tin khai báo cần thiết.

Vì vậy, Manifest đóng vai trò trung gian giữa ứng dụng và hệ điều hành Android trong việc khai báo cấu hình và các thành phần ứng dụng.

---

## 2. Vị trí của AndroidManifest.xml trong dự án

Trong Android Studio, file AndroidManifest.xml thường nằm tại:

```text
Project
└── app
    └── src
        └── main
            └── AndroidManifest.xml
```

Đường dẫn tương ứng:

```text
app/src/main/AndroidManifest.xml
```

Ngoài thư mục `main`, một dự án có thể có Manifest riêng cho các build variant hoặc source set khác nhau, ví dụ `debug` và `release`.

Khi xây dựng ứng dụng, Android Gradle Plugin sẽ thực hiện quá trình Manifest Merger để kết hợp các file Manifest từ những nguồn khác nhau, bao gồm module ứng dụng, thư viện phụ thuộc và các build variant.

File Manifest cuối cùng được đóng gói vào ứng dụng sau quá trình xây dựng.

---

## 3. Cấu trúc tổng thể của file

### 3.1. Cấu trúc cơ bản

Một file AndroidManifest.xml có cấu trúc cơ bản như sau:

```xml
<?xml version="1.0" encoding="utf-8"?>

<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- Khai báo quyền truy cập -->
    <uses-permission
        android:name="android.permission.INTERNET" />

    <!-- Thông tin cấu hình ứng dụng -->
    <application
        android:allowBackup="true"
        android:label="My Application"
        android:theme="@style/Theme.MyApplication">

        <!-- Khai báo Activity -->
        <activity
            android:name=".MainActivity"
            android:exported="true">

            <intent-filter>
                <action
                    android:name="android.intent.action.MAIN" />

                <category
                    android:name="android.intent.category.LAUNCHER" />
            </intent-filter>

        </activity>

    </application>

</manifest>
```

### 3.2. Phân tích cấu trúc

Có thể chia cấu trúc trên thành các phần:

| Thành phần | Chức năng |
|---|---|
| `<?xml ... ?>` | Khai báo phiên bản XML và kiểu mã hóa |
| `manifest` | Thẻ gốc của file |
| `xmlns:android` | Khai báo namespace Android |
| `uses-permission` | Khai báo quyền ứng dụng cần sử dụng |
| `application` | Chứa thông tin cấu hình ứng dụng |
| `activity` | Khai báo Activity |
| `intent-filter` | Khai báo loại Intent mà Activity tiếp nhận |
| `action` | Xác định hành động của Intent |
| `category` | Phân loại Intent |

Lưu ý: Đây là cấu trúc minh họa cơ bản. Trong dự án thực tế, không phải ứng dụng nào cũng cần khai báo tất cả các thành phần trên.

---

## 4. Phân tích các thành phần trong Manifest

### 4.1. Thẻ manifest

```xml
<manifest
    xmlns:android="http://schemas.android.com/apk/res/android">
</manifest>
```

Đây là thẻ gốc (Root Element), chứa toàn bộ nội dung của file AndroidManifest.xml.

Thuộc tính:

- `xmlns:android`: Khai báo XML namespace dành cho Android.
- Namespace cho phép sử dụng các thuộc tính có tiền tố `android:`.

Ví dụ:

```xml
android:name=".MainActivity"
android:exported="true"
```

Các thuộc tính trên đều thuộc namespace Android đã khai báo.

### 4.2. Thẻ uses-permission

Dùng để khai báo quyền mà ứng dụng muốn sử dụng trên thiết bị.

Ví dụ:

```xml
<uses-permission
    android:name="android.permission.INTERNET" />
```

Một số quyền thường gặp:

| Quyền | Chức năng |
|---|---|
| INTERNET | Truy cập Internet |
| CAMERA | Sử dụng camera |
| ACCESS_FINE_LOCATION | Truy cập vị trí chính xác |
| RECORD_AUDIO | Ghi âm |
| READ_CONTACTS | Đọc danh bạ |

**Phân biệt hai loại quyền:**

- Normal Permission: Quyền có mức độ rủi ro thấp, thường được hệ thống cấp tự động khi ứng dụng cài đặt. Ví dụ: INTERNET.
- Dangerous Permission: Quyền liên quan đến dữ liệu hoặc chức năng nhạy cảm, cần được người dùng cấp quyền trong thời gian chạy ứng dụng. Ví dụ: CAMERA, vị trí.

Như vậy, việc khai báo quyền trong Manifest không đồng nghĩa với việc ứng dụng luôn được phép sử dụng quyền đó. Với quyền cần cấp trong runtime, ứng dụng phải kiểm tra và yêu cầu người dùng cấp quyền khi cần thiết.

### 4.3. Thẻ application

Đây là thẻ chứa cấu hình chung của ứng dụng và khai báo các thành phần như Activity, Service, BroadcastReceiver và ContentProvider.

Ví dụ:

```xml
<application
    android:allowBackup="true"
    android:label="My Application"
    android:icon="@mipmap/ic_launcher"
    android:theme="@style/Theme.MyApplication">

</application>
```

Ý nghĩa các thuộc tính:

| Thuộc tính | Ý nghĩa |
|---|---|
| `allowBackup` | Cấu hình khả năng sao lưu dữ liệu |
| `label` | Tên hiển thị của ứng dụng |
| `icon` | Biểu tượng ứng dụng |
| `theme` | Theme mặc định của ứng dụng |

Các thuộc tính này giúp cấu hình cách ứng dụng được nhận diện và hiển thị trên thiết bị.

### 4.4. Thẻ activity

Activity là một trong bốn thành phần cơ bản của Android, đại diện cho một màn hình giao diện mà người dùng có thể tương tác.

Ví dụ:

```xml
<activity
    android:name=".MainActivity"
    android:exported="true" />
```

Ý nghĩa:

- `android:name`: Tên lớp Activity trong mã nguồn Kotlin.
- `android:exported`: Xác định Activity có thể được thành phần bên ngoài ứng dụng khởi chạy hay không.

Một ứng dụng có thể có nhiều Activity.

Ví dụ:

```xml
<activity
    android:name=".MainActivity"
    android:exported="true" />

<activity
    android:name=".DetailActivity"
    android:exported="false" />
```

Trong đó:
- MainActivity có thể được ứng dụng khác khởi chạy nếu đáp ứng điều kiện phù hợp.
- DetailActivity chỉ có thể được sử dụng từ bên trong ứng dụng.

### 4.5. Thẻ intent-filter

Intent là cơ chế Android sử dụng để yêu cầu một thành phần khác thực hiện một hành động hoặc truyền thông tin giữa các thành phần.

Intent-filter dùng để khai báo những loại Intent mà một thành phần có khả năng tiếp nhận.

Ví dụ:

```xml
<intent-filter>

    <action
        android:name="android.intent.action.MAIN" />

    <category
        android:name="android.intent.category.LAUNCHER" />

</intent-filter>
```

Giải thích:

- `action`: Xác định hành động cần thực hiện.
- `category`: Bổ sung thông tin phân loại cho Intent.

Trong ví dụ trên:

- MAIN: Xác định Activity là điểm khởi chạy chính.
- LAUNCHER: Cho phép Activity xuất hiện trong danh sách ứng dụng của thiết bị.

Khi kết hợp hai khai báo này, Android xác định đây là Activity được mở khi người dùng nhấn vào biểu tượng ứng dụng.

### 4.6. Các thành phần khác trong Manifest

Ngoài Activity, AndroidManifest.xml còn hỗ trợ khai báo ba thành phần ứng dụng khác.

**a. Service**

Service là thành phần thực hiện công việc mà không cần giao diện trực tiếp.

Ví dụ:

```xml
<service
    android:name=".WordCountService"
    android:exported="false" />
```

Trong đó:
- `name`: Tên lớp Service.
- `exported`: Xác định Service có thể được ứng dụng khác khởi chạy hay không.

**b. BroadcastReceiver**

BroadcastReceiver là thành phần tiếp nhận các thông báo sự kiện từ hệ thống hoặc ứng dụng khác.

Ví dụ khai báo receiver đăng ký tĩnh:

```xml
<receiver
    android:name=".MyReceiver"
    android:exported="false" />
```

**c. ContentProvider**

ContentProvider được sử dụng để quản lý và chia sẻ dữ liệu có cấu trúc giữa các ứng dụng.

Ví dụ:

```xml
<provider
    android:name=".MyProvider"
    android:authorities="${applicationId}.provider"
    android:exported="false" />
```

Lưu ý: Không phải ứng dụng nào cũng cần sử dụng cả bốn thành phần trên.

---

## 5. Các thuộc tính quan trọng

### 5.1. android:name

Dùng để chỉ định tên lớp của một thành phần Android.

Ví dụ:

```xml
<activity android:name=".MainActivity" />
```

Android sẽ nhận biết lớp MainActivity được khai báo trong ứng dụng.

### 5.2. android:exported

Xác định thành phần có được phép khởi chạy hoặc truy cập trực tiếp từ bên ngoài ứng dụng hay không.

Có hai giá trị:

- `true`: Cho phép thành phần được truy cập từ bên ngoài nếu Intent phù hợp.
- `false`: Chỉ cho phép thành phần được sử dụng từ bên trong ứng dụng, trừ một số cơ chế truy cập đặc biệt của hệ thống.

Đặc biệt, từ Android 12 trở lên, các thành phần có khai báo intent-filter cần chỉ định rõ giá trị `android:exported`.

### 5.3. android:permission

Dùng để giới hạn quyền truy cập vào một thành phần ứng dụng.

Ví dụ:

```xml
<service
    android:name=".MyService"
    android:permission="android.permission.BIND_JOB_SERVICE"
    android:exported="true" />
```

Thuộc tính này yêu cầu bên muốn tương tác với thành phần phải có quyền tương ứng.

### 5.4. android:enabled

Xác định thành phần có được bật hay không.

- `true`: Thành phần được bật.
- `false`: Thành phần bị vô hiệu hóa.

---

## 6. Mối quan hệ giữa Manifest và Kotlin

AndroidManifest.xml không chứa logic xử lý của ứng dụng. Logic được viết trong các file Kotlin, còn Manifest khai báo những thành phần và cấu hình để hệ thống Android nhận biết.

Ví dụ:

**Bước 1: Tạo Activity trong Kotlin**

```kotlin
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Text("Hello Android")
        }
    }
}
```

**Bước 2: Khai báo Activity trong Manifest**

```xml
<application>

    <activity
        android:name=".MainActivity"
        android:exported="true">

        <intent-filter>
            <action
                android:name="android.intent.action.MAIN" />

            <category
                android:name="android.intent.category.LAUNCHER" />
        </intent-filter>

    </activity>

</application>
```

**Phân tích:**

- File Kotlin định nghĩa lớp MainActivity và xử lý giao diện.
- File Manifest khai báo MainActivity là một thành phần của ứng dụng.
- Intent-filter xác định MainActivity là màn hình khởi chạy.

Qua đó có thể thấy hai file đảm nhận hai nhiệm vụ khác nhau nhưng có mối liên hệ trực tiếp trong quá trình xây dựng ứng dụng Android.

---

## 7. Thực hành với AndroidManifest.xml

### 7.1. Quan sát Manifest trong Android Studio

Các bước thực hiện:

1. Mở Android Studio.
2. Mở một dự án Android đã tạo.
3. Chọn chế độ xem Project.
4. Truy cập `app/src/main/`.
5. Mở file AndroidManifest.xml.
6. Quan sát các thẻ và thuộc tính được khai báo.

### 7.2. Thực hành khai báo quyền Internet

Thêm khai báo sau bên ngoài thẻ application:

```xml
<uses-permission
    android:name="android.permission.INTERNET" />
```

Ví dụ:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission
        android:name="android.permission.INTERNET" />

    <application>
        ...
    </application>

</manifest>
```

Mục đích: Cho phép ứng dụng sử dụng quyền truy cập Internet.

### 7.3. Thực hành thay đổi tên ứng dụng

Tìm thuộc tính `android:label` trong thẻ application:

```xml
<application
    android:label="My First App">
```

Thay đổi giá trị thành:

```xml
<application
    android:label="Mobile Learning">
```

Sau đó chạy lại ứng dụng và quan sát tên hiển thị trên thiết bị hoặc Emulator.

### 7.4. Tìm hiểu việc đăng ký BroadcastReceiver

Trong quá trình học Android, BroadcastReceiver có thể được đăng ký theo hai cách:

**Đăng ký tĩnh:**

Khai báo receiver trong AndroidManifest.xml.

```xml
<receiver
    android:name=".MyReceiver"
    android:exported="false" />
```

**Đăng ký động:**

Đăng ký receiver trực tiếp trong code Kotlin thông qua `registerReceiver()` hoặc API tương ứng của AndroidX.

Ví dụ:

```kotlin
val filter = IntentFilter(Intent.ACTION_POWER_CONNECTED)

ContextCompat.registerReceiver(
    this,
    receiver,
    filter,
    ContextCompat.RECEIVER_NOT_EXPORTED
)
```

Với cách đăng ký động, không cần khai báo receiver tương ứng trong Manifest.

Việc lựa chọn phương pháp đăng ký phụ thuộc vào mục đích sử dụng và loại sự kiện cần tiếp nhận.

---

## 8. Một số lỗi thường gặp

| Lỗi | Nguyên nhân | Cách khắc phục |
|---|---|---|
| Activity không khởi chạy | Khai báo sai tên lớp Activity | Kiểm tra `android:name` |
| Ứng dụng không truy cập được Internet | Thiếu quyền INTERNET | Kiểm tra `uses-permission` |
| Lỗi android:exported | Thành phần có intent-filter nhưng chưa khai báo exported trên Android 12+ | Bổ sung `android:exported` |
| Thành phần không được tìm thấy | Khai báo sai tên lớp hoặc package | Kiểm tra đường dẫn và tên lớp |
| Lỗi Manifest Merger | Các Manifest có khai báo xung đột | Kiểm tra Merged Manifest trong Android Studio |

---

## 9. Kết luận

Qua quá trình tìm hiểu, có thể thấy AndroidManifest.xml là một thành phần quan trọng trong quá trình phát triển ứng dụng Android.

Nội dung quan trọng cần nắm được:

- Cấu trúc XML và vai trò của thẻ manifest.
- Cách khai báo quyền bằng uses-permission.
- Vai trò của application trong việc cấu hình ứng dụng.
- Cách khai báo Activity, Service, BroadcastReceiver và ContentProvider.
- Ý nghĩa của các thuộc tính như name, exported, permission và enabled.
- Mối quan hệ giữa file Manifest và các lớp Kotlin.

Việc hiểu AndroidManifest.xml giúp lập trình viên biết cách cấu hình ứng dụng, khai báo các thành phần và xử lý những vấn đề liên quan đến quyền truy cập cũng như hoạt động của ứng dụng Android.

