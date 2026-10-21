package com.example.studentmanager.exception;

public class DuplicateStudentException extends RuntimeException {
    public DuplicateStudentException(String id) {
        super("Sinh viên với id: " + id + " đã tồn tại");
    }
}
