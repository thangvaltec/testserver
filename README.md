# API Test Script - Java Version

Đây là script để test API server, viết bằng Java để bạn có thể tham khảo code khi phát triển Android app.

**Đơn giản hóa:** Chỉ 1 test case với `policeId = "null"` giống `test.py`.

## 🚀 Cách chạy nhanh nhất

```powershell
.\run-java.bat
```

## 📋 Yêu cầu

- **Java JDK 11+** (đã có sẵn)

## 📁 Cấu trúc thư mục

```
test/
├── TestApiJava.java          # Java source code
├── run-java.bat              # Script chạy
├── tan.jpg                   # File ảnh để test
├── park.jpg                  # File ảnh khác
├── test.py                   # Python version (tham khảo)
├── test_api.py               # Python version (nhiều test cases)
├── README.md                 # File này
├── HUONG_DAN_NHANH.md        # クイックスタートガイド (日本語)
└── .gitignore                # Git ignore
```

## 📝 Kết quả

Output:
```
Request
Sending data: timestamp=1767948039631, deviceId=2222222, policeId=null
Response
status_code: 200
response result: {"status":2,"deviceId":"2222222","policeId":"null","similarity":"96.3","name":"PHAN　VAN THANG","real_id":"24024","data":"","message":"認証成功\n勤怠連携：無効\n"}
```

### 📊 Giải thích:

- **status_code: 200** → ✅ Request thành công
- **status: 2** → Authentication successful
- **similarity: 96.3** → Độ tương đồng khuôn mặt 96.3%
- **name: "PHAN　VAN THANG"** → Tên người được nhận diện
- **real_id: "24024"** → ID trong database
- **message: "認証成功\n勤怠連携：無効\n"** → "Authentication successful, Attendance linkage: Disabled"

## 🔧 Tùy chỉnh

### Thay đổi file ảnh

```java
String imagePath = "tan.jpg";  // Đổi thành "park.jpg"
```

### Thay đổi URL hoặc Device ID

```java
private static final String API_URL = "https://mot-recog.facet-cloud.com/recv";
private static final String DEVICE_ID = "2222222";
```

### Thay đổi policeId

```java
String policeId = "null";  // Đổi thành giá trị khác
```

## 💡 Sử dụng trong Android App

Code trong `TestApiJava.java` có thể copy trực tiếp vào Android project:

1. **Copy toàn bộ logic** vào Android project
2. **Thêm dependencies** (nếu muốn dùng OkHttp):
   ```gradle
   implementation 'com.squareup.okhttp3:okhttp:4.12.0'
   ```
3. **Hoặc dùng HttpURLConnection** như trong code (không cần dependencies)
4. **Chạy trong background thread**:
   ```java
   new Thread(() -> {
       // Copy code từ main() vào đây
   }).start();
   ```

## ❓ Troubleshooting

### Lỗi "Java not found"
- Cài đặt Java JDK 11+
- Kiểm tra: `java -version`

### Lỗi "Image file not found"
- Đảm bảo file `tan.jpg` hoặc `park.jpg` có trong thư mục
- Hoặc sửa đường dẫn trong code

### Ký tự tiếng Nhật hiển thị sai

**Cách 1: Dùng batch file (tự động fix)**
```powershell
.\run-java.bat
```

**Cách 2: Set UTF-8 thủ công**
```powershell
chcp 65001
javac -encoding UTF-8 TestApiJava.java
java TestApiJava
```

**Trong Android:** Không cần lo - Android tự động xử lý UTF-8!

## 📚 So sánh với Python

| Feature | Python (test.py) | Java |
|---------|------------------|------|
| **Cài đặt** | Python + requests | Chỉ Java JDK |
| **HTTP Library** | `requests` | `HttpURLConnection` |
| **Compile** | Không | `javac` |
| **Chạy** | `python test.py` | `java TestApiJava` |
| **Dùng cho Android** | ❌ | ✅ |
| **Đơn giản** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ |

## License

Free to use for testing purposes.