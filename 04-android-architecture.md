4. Kiến trúc Android và các pattern phổ biến
4.1. Kiến trúc nền tảng Android (Android OS)
Có thể mô tả stack Android theo các lớp lớn sau, từ trên xuống dưới:
+--------------------------------------+
| Applications                         |
| Ứng dụng hệ thống và ứng dụng người dùng |
+--------------------------------------+
| Android Framework                    |
| Activity Manager, Window Manager,     |
| Notification, Resource, Content...   |
+--------------------------------------+
| Android Runtime (ART) + Native       |
| Libraries                            |
| Thực thi bytecode và thư viện native |
+--------------------------------------+
| Hardware Abstraction Layer (HAL)     |
| Giao diện chuẩn để truy cập phần cứng |
+--------------------------------------+
| Linux Kernel                         |
| Tiến trình, bộ nhớ, bảo mật, driver   |
+--------------------------------------+
Giải thích:
1. Applications: Các ứng dụng chạy trên thiết bị, bao gồm ứng dụng
   người dùng và ứng dụng hệ thống.
2. Android Framework: Cung cấp API cấp cao để ứng dụng làm việc với
   Activity, thông báo, tài nguyên, cửa sổ, vị trí và nhiều dịch vụ hệ
   thống khác.
3. Android Runtime (ART): Môi trường chạy mã ứng dụng Android; thực
   thi mã đã được biên dịch theo cơ chế của nền tảng.
4. Native Libraries: Các thư viện hệ thống viết bằng C/C++ phục vụ
   đồ họa, đa phương tiện, cơ sở dữ liệu và các chức năng nền tảng.
5. HAL: Chuẩn hóa giao tiếp giữa framework và phần cứng, giảm phụ
   thuộc vào chi tiết triển khai của từng nhà sản xuất.
6. Linux Kernel: Nền tảng quản lý tiến trình, bộ nhớ, bảo mật, mạng
   và driver thiết bị.
Sơ đồ trên là mô hình khái quát; một số tài liệu Android mô tả ART,
native libraries và các tiến trình native ở những nhóm chi tiết hơn.
4.2. Kiến trúc bên trong một ứng dụng Android
Với ứng dụng hiện đại, Android Developers khuyến nghị tổ chức theo các
lớp để dễ mở rộng, kiểm thử và bảo trì.
Người dùng
    |
    v
UI Layer (Activity / Compose)
    |
    v
ViewModel / UI State
    |
    v
Domain Layer (Use Cases, nếu cần)
    |
    v
Data Layer (Repository)
    |
    +----------+-----------+
    v          v           v
  API mạng   Database   DataStore / nguồn khác
- UI Layer: Hiển thị dữ liệu và nhận tương tác người dùng.
- ViewModel hoặc state holder: Quản lý trạng thái giao diện và
  chuẩn bị dữ liệu cho UI; không nên chứa tham chiếu trực tiếp đến
  Activity theo cách gây rò rỉ bộ nhớ.
- Domain Layer: Chứa các use case hoặc logic nghiệp vụ dùng chung
  khi độ phức tạp của ứng dụng cần lớp này. Đây là lớp tùy chọn.
- Data Layer: Quản lý dữ liệu, thường thông qua Repository và các
  nguồn dữ liệu như API, database hoặc DataStore.
Một luồng phổ biến là Unidirectional Data Flow (UDF): người dùng tạo
sự kiện, sự kiện được xử lý, trạng thái được cập nhật và UI hiển thị
trạng thái mới.
4.3. Các design pattern phổ biến
a. MVC (Model--View--Controller)
- Model: Dữ liệu và logic liên quan đến dữ liệu.
- View: Thành phần hiển thị giao diện.
- Controller: Tiếp nhận thao tác và điều phối giữa Model với View.
MVC là mô hình nền tảng dễ học, nhưng trong Android, nếu đặt quá nhiều
logic trong Activity thì Activity dễ trở nên lớn và khó kiểm thử.
b. MVP (Model--View--Presenter)
- Model: Dữ liệu và nghiệp vụ.
- View: Giao diện, thường giao tiếp qua interface.
- Presenter: Xử lý tương tác và quyết định dữ liệu cần hiển thị.
Ưu điểm là có thể kiểm thử Presenter riêng; nhược điểm là cần thêm
interface và mã kết nối.
c. MVVM (Model--View--ViewModel)
- Model/Data: Dữ liệu và logic lấy dữ liệu.
- View: Activity, Fragment hoặc giao diện Compose.
- ViewModel: Giữ và cung cấp trạng thái UI; tiếp nhận sự kiện từ
  UI.
MVVM kết hợp tốt với AndroidX ViewModel, Kotlin Flow/StateFlow và các
thành phần lifecycle-aware. Đây là cách tổ chức phổ biến trong ứng dụng
Android hiện đại, nhưng không phải mọi dự án đều bắt buộc phải dùng cùng
một cấu trúc.
d. Repository Pattern
Repository cung cấp một giao diện thống nhất cho tầng UI hoặc domain
truy cập dữ liệu, che giấu chi tiết dữ liệu đến từ API, database hay
nguồn khác.

Ví dụ:
data class User(val id: Int, val name: String)

class UserRepository {
    fun getUser(): User {
        // Ví dụ đơn giản; thực tế có thể đọc từ API hoặc database.
        return User(1, "An")
    }
}

class UserViewModel(
    private val repository: UserRepository
) : androidx.lifecycle.ViewModel() {

    fun loadUserName(): String {
        return repository.getUser().name
    }
}
Ví dụ này chỉ minh họa cách tách trách nhiệm, chưa phải triển khai bất
đồng bộ đầy đủ cho ứng dụng thực tế.
e. Dependency Injection (DI)
DI là cách cung cấp dependency cho một lớp từ bên ngoài thay vì để lớp
đó tự tạo tất cả đối tượng cần dùng. Điều này giúp giảm phụ thuộc cứng
và dễ thay thế dependency khi kiểm thử.

Ví dụ constructor injection:
class UserRepository {
    fun getName(): String = "An"
}

class UserViewModel(
    private val repository: UserRepository
) {
    fun name(): String = repository.getName()
}
Ở đây, UserViewModel nhận UserRepository qua constructor. Trong ứng
dụng lớn, có thể dùng thư viện DI như Hilt để quản lý việc tạo và cung
cấp đối tượng.
f. Observer Pattern
Observer cho phép một đối tượng theo dõi và phản ứng khi trạng thái hoặc
dữ liệu thay đổi. Trong Android, các cơ chế như LiveData, Flow/StateFlow
và quan sát trạng thái UI áp dụng ý tưởng tương tự.

Ví dụ khái niệm: ViewModel phát ra trạng thái mới; giao diện thu thập
trạng thái và tự cập nhật khi có thay đổi, thay vì phải tự hỏi dữ liệu
đã đổi hay chưa.
g. Singleton Pattern
Singleton đảm bảo một kiểu đối tượng có một instance dùng chung trong
phạm vi thiết kế nhất định. Nó có thể hữu ích cho đối tượng không trạng
thái hoặc thành phần dùng chung, nhưng lạm dụng singleton có thể gây khó
kiểm thử và tạo trạng thái toàn cục khó quản lý.