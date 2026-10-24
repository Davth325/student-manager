package com.example.studentmanager;

import com.example.studentmanager.exception.DuplicateStudentException;
import com.example.studentmanager.exception.StudentNotFoundException;
import com.example.studentmanager.exception.ValidationException;
import com.example.studentmanager.repository.StudentRepository;
import com.example.studentmanager.service.StudentService;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        StudentRepository repo = new StudentRepository();
        StudentService service = new StudentService(repo);

        // 1. Thêm hợp lệ
        service.addStudent("SV01", "Nguyễn Văn An", 20, 8.5);
        service.addStudent("SV02", "Trần Thị Bình", 19, 9.2);
        System.out.println("Thêm thành công 2 sinh viên!");

        // 2. Thử thêm trùng ID -> Phải ném DuplicateStudentException
        try {
            service.addStudent("SV01", "Lê Văn C", 21, 7.0);
        } catch (DuplicateStudentException e) {
            System.out.println("Bắt đúng lỗi trùng ID: " + e.getMessage());
        }

        // 3. Thử sửa ID không tồn tại -> Phải ném StudentNotFoundException
        try {
            service.updateStudent("SV99", "Tên Mới", 20, 8.0);
        } catch (StudentNotFoundException e) {
            System.out.println("Bắt đúng lỗi không tìm thấy: " + e.getMessage());
        }

        // 4. Thử nhập sai cả 4 trường -> Phải gom đủ 4 lỗi
        try {
            service.addStudent("", "   ", 5, 11.0);
        } catch (ValidationException e) {
            System.out.println("Bắt đúng ValidationException (" + e.getErrors().size() + " lỗi): " + e.getMessage());
        }
    }
}