package com.example.studentmanager.repository;

import com.example.studentmanager.model.Student;

import java.util.*;

public class StudentRepository {
    private final List<Student> students = new ArrayList<>();
    private final Map<String, Student> studentById = new HashMap<>();

    public void add(Student student) {
        students.add(student);
        studentById.put(student.getId(), student);
    }

    public Optional findById(String id) {
        return Optional.ofNullable(studentById.get(id));
    }

    public boolean existsById(String id) {
        return studentById.containsKey(id);
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public boolean deleteById(String id) {
        Student removed = studentById.remove(id);
        if (removed != null) {
            students.remove(removed);
            return true;
        }
        return false;
    }


    public int count() {
        return students.size();
    }
}
