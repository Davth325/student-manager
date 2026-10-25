# Student Manager (Java core and Gradle)

Ứng dụng Quản lý sinh viên chạy trên giao diện console, được xậy dựng theo kiến trúc phân tầng (**UI -> Service -> Repository**) làm nền tảng chuyển tiếp ln **Spring Boot**

## Tính năng chính
- **Thêm, sửa, xóa** sinh viên (kiểm tra trùng ID, kiểm tra tồn tại).
- **Tìm kiếm** theo mã sinh viên ($O(1)$ với `HashMap`) hoặc theo từ khóa tên (không phân biệt hoa thường).
- **Sắp xếp** danh sách theo tên (A-Z) hoặc theo điểm GPA giảm dần bằng `Comparator`.
- **Tự động kiểm tra dữ liệu (Validation):** Sử dụng Custom Annotation `@NotEmpty` kết hợp kỹ thuật **Reflection** và gom nhóm nhiều lỗi cùng lúc qua `ValidationException`.

## Cấu trúc thư mục
```text
student-manager/
├── build.gradle
├── settings.gradle
└── src/main/java/com/example/studentmanager/
    ├── Main.java
    ├── annotation/   NotEmpty.java
    ├── model/        Student.java
    ├── exception/    StudentNotFoundException, DuplicateStudentException, ValidationException
    ├── validation/   Validator.java
    ├── repository/   StudentRepository.java
    ├── service/      StudentService.java
    └── ui/           ConsoleMenu.java