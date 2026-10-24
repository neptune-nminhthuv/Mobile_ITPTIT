2. Cấu trúc cơ bản của dự án Android Studio
Cấu trúc thực tế phụ thuộc template, ngôn ngữ, hệ thống build và việc dự
án dùng XML Views hay Jetpack Compose. Một dự án Android thông thường có
thể có dạng:
MyAndroidApp/
├── settings.gradle.kts
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── gradle/
└── app/
    ├── build.gradle.kts
    ├── proguard-rules.pro
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml
        │   ├── java/com/example/myandroidapp/
        │   │   └── MainActivity.kt
        │   ├── res/
        │   │   ├── drawable/
        │   │   ├── mipmap/
        │   │   ├── values/
        │   │   │   ├── strings.xml
        │   │   │   ├── colors.xml
        │   │   │   └── themes.xml
        │   │   └── layout/
        │   │       └── activity_main.xml
        │   └── assets/
        ├── test/
        └── androidTest/
Một số thư mục có thể không xuất hiện nếu template không tạo chúng. Ví dụ, dự án Compose thường khai báo giao diện trong Kotlin và có thể không có thư mục res/layout.
2.1. Ý nghĩa các thành phần
  Thành phần                          Chức năng
  settings.gradle.kts               Khai báo cấu hình cấp dự án và các module được đưa vào build

  build.gradle.kts ở gốc            Cấu hình plugin hoặc thiết lập build dùng chung

  gradle.properties                 Các thuộc tính cấu hình cho Gradle

  gradlew, gradlew.bat            Gradle Wrapper để chạy build trên Unix/macOS và Windows

  gradle/                           Cấu hình Wrapper, gồm thông tin phiên bản Gradle

  app/                              Module ứng dụng Android thường gặp

  app/build.gradle.kts              Cấu hình module: plugin, SDK, phiên bản ứng dụng, dependencies và build types

  src/main/                         Mã nguồn và tài nguyên chính của ứng dụng

  AndroidManifest.xml               Khai báo component, quyền, intent filter và thông tin ứng dụng

  java/ hoặc kotlin/              Mã nguồn Kotlin/Java; giao diện và logic ứng dụng

  res/                              Tài nguyên được Android xử lý theo loại như layout, ảnh, chuỗi, theme

  res/layout/                       Layout XML nếu dùng hệ thống Views

  res/drawable/                     Hình ảnh và drawable

  res/mipmap/                       Tài nguyên icon ứng dụng

  res/values/                       Chuỗi, màu sắc, kích thước, theme và các giá trị dùng chung

  assets/                           Tệp cần đóng gói gần như nguyên dạng và đọc bằng AssetManager

  src/test/                         Unit test chạy trên JVM

  src/androidTest/                  Kiểm thử cần Android framework hoặc thiết bị/emulator

  build/                            Sản phẩm trung gian và đầu ra build; thường không đưa lên Git
2.2. Ví dụ AndroidManifest.xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <application
        android:allowBackup="true"
        android:label="My Android App"
        android:theme="@style/Theme.MyAndroidApp">

        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

    </application>
</manifest>
MAIN kết hợp với LAUNCHER đánh dấu Activity có thể được dùng làm
điểm khởi chạy từ launcher. Khi Activity có intent filter, cần khai báo
android:exported rõ ràng ở các phiên bản Android yêu cầu.
2.3. Ví dụ dependencies trong Gradle
dependencies {
    implementation("androidx.core:core-ktx:<version>")
    testImplementation("junit:junit:<version>")
}
Thay <version> bằng phiên bản tương thích được chọn cho dự án. Gradle
dùng dependencies để thêm thư viện vào quá trình biên dịch và chạy.
