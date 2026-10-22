package com.example.studentmanager;

import com.example.studentmanager.model.Student;
import com.example.studentmanager.validation.Validator;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        // Tạo sinh viên có id rỗng và name chỉ chứa khoảng trắng
        Student invalidStudent = new Student("", "   ", 20, 8.0);

        List<String> errors = Validator.collectErrors(invalidStudent);
        System.out.println("Số lỗi phát hiện: " + errors.size());
        for (String err : errors) {
            System.out.println("- " + err);
        }
    }
}