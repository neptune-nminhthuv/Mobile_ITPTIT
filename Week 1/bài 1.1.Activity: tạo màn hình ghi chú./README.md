
package com.example.myapplication

import android.os.Bundle // sử dụng kiểu dữ liệu Bundle để nhận dữ liệu trạng thái

import androidx.activity.ComponentActivity // lớp activity cơ sở mà màn hình của bạn kế thừa
import androidx.activity.compose.setContent // thiết lập giao diện compose

import androidx.compose.foundation.layout.*  // các thành phần bố cục như column, row, padding
import androidx.compose.material3.*  // các thành phần giao diện như button, text, textfield
import androidx.compose.runtime.* // quản lý trạng thái, biến quan sát được của compose
import androidx.compose.runtime.saveable.rememberSaveable // lưu và khôi phục trạng thái giao diện

import androidx.compose.ui.Modifier // điều chỉnh kích thước, vị trí, khoảng cách của giao diện
import androidx.compose.ui.unit.dp // đơn vị đo kích thước của android
// dấu '*' là willcard, có nghĩa  là nhập các thành phần có thể truy cập trong package đó, thay vì import từng cái

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){ //dấu ? cho phép giá trị null
        super.onCreate(savedInstanceState)
        setContent{
            // lưu nội dung người nhập
            var note by rememberSaveable { // giữ và khôi phục trạng thái khi Activity được tạo lại trong các trường hợp được hỗ trợ ( do xoay màn hình)
                mutableStateOf("") // tạo ra một State có thể thay đổi và được Compose quan sát
            }
            // lưu kết quả hiển thị sau khi nhấn nút
            var result by rememberSaveable {
                mutableStateOf("")
            }
            // xây dựng giao diện
            MaterialTheme{
                // sắp xếp các thành phần theo chiều dọc
                Column(
                    //Modifier laf công cụ điều chỉnh giao diện
                    modifier = Modifier
                        .fillMaxSize() //yêu cầu Column chiếm toàn bộ không gian có thể sử dụng của vùng cha.
                        .padding(24.dp), //tạo khoảng cách bên trong bố cục so với các cạnh của nó
                    verticalArrangement = Arrangement.spacedBy(12.dp) //tạo khoảng cách 12dp giữa các thành phần con trong Column
                ){
                    Text(
                        text = "Ghi chú học tập",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    // một ô nhập văn bản có đường liền
                    OutlinedTextField(
                        value = note,
                        // callback được gọi mỗi khi người dùng thay đổi nội dung
                        onValueChange = {
                            note = it
                        },
                        // hiện thị nhãn cho ô nhập
                        label = {
                            Text("Nội dung ghi chú")
                        },
                        // yêu cầu ô nhập chiếm toàn bộ chiều rộng có thể sử dụng
                        modifier = Modifier.fillMaxWidth(),
                        //Quy định chiều cao tối thiểu tương ứng với 4 dòng văn bản
                        minLines = 4
                    )

                    // tạo nút bấm và xử  lý sự kiện
                    Button(
                        onClick = {
                            val count = if(note.isBlank()){ // isBlank() là hàm ktra chuõi có rỗng or chỉ chứa khoảng trắng hay ko
                                0
                            }
                            else {
                                note.trim() // loại bỏ khoảng trắng ở đầu và cuối chuỗi
                                    .split(Regex("\\s+"))
                                    //split() : chia một chuỗi thành nhiều phần dựa vào ký tự phân tách
                                    //Regex: tạo một biểu thức chính quy
                                    // \\s: đaị diện cho một ký tự khoảng trắng trong biểu thức chính quy
                                    // +: một hoặc nhiều lần xuất hiện
                                    .size // trả về só phàn tử
                            }

                            result = "Số từ trong ghi chú: $count"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text("Đếm số từ")
                    }

                    Text(
                        text = result,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Button(
                        onClick = {
                            result = "Chức năng chia sẻ sẽ được bổ sung bằng FileProvider."
                        },
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text("Chia sẻ ghi chú")
                    }

                }

            }
        }
    }

}
