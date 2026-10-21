package com.example.studentmanager.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String id) {
        super("Không tìm thấy sinh viên với id: " + id);
    }
}

