package com.example.studentmanager;

import com.example.studentmanager.model.Student;
import com.example.studentmanager.repository.StudentRepository;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        StudentRepository repo = new StudentRepository();
        repo.add(new Student("SV01", "Nguyễn Văn A", 20, 8.5));
        repo.add(new Student("SV02", "Trần Thị B", 21, 9.0));

        System.out.println("Tổng số SV sau khi thêm: " + repo.count()); // Phải ra 2
        System.out.println("Tìm SV01: " + repo.findById("SV01").orElse(null));

        repo.deleteById("SV01");
        System.out.println("Tổng số SV sau khi xóa SV01: " + repo.count()); // Phải ra 1
        System.out.println("SV01 còn tồn tại không? " + repo.existsById("SV01")); // Phải ra false
    }
}