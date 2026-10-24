package com.example.studentmanager.service;

import com.example.studentmanager.exception.DuplicateStudentException;
import com.example.studentmanager.exception.StudentNotFoundException;
import com.example.studentmanager.exception.ValidationException;
import com.example.studentmanager.model.Student;
import com.example.studentmanager.repository.StudentRepository;
import com.example.studentmanager.validation.Validator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class StudentService {
    private final StudentRepository repository;

    public StudentService (StudentRepository repository) {
        this.repository = repository;
    }

    public void addStudent (String id, String name, int age, double gpa) {
        String cleanId = id == null ? "" : id.trim();
        String cleanName = name == null ? "" : name.trim();

        Student student = new Student(cleanId, cleanName, age, gpa);
        validate(student);

        if (repository.existsById(cleanId)) {
            throw new DuplicateStudentException(cleanId);
        }

        repository.add(student);
    }

    public void updateStudent (String id, String newName, int newAge, double newGpa) {
        String cleanId = id == null ? "" : id.trim();
        Student existing = getStudentById(cleanId);

        String cleanName = newName == null ? "" : newName.trim();

        Student candidate = new Student(existing.getId(), cleanName, newAge, newGpa);
        validate(candidate);

        existing.setName(cleanName);
        existing.setAge(newAge);
        existing.setGpa(newGpa);
    }

    public void deleteStudent (String id) {
        String cleanId = id == null ? "" : id.trim();
        if (!repository.deleteById(cleanId)) {
            throw new StudentNotFoundException(cleanId);
        }
    }

    public Student getStudentById(String id) {
        String cleanId = id == null ? "" : id.trim();
        return repository.findById(cleanId)
                .orElseThrow(() -> new StudentNotFoundException(cleanId));
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public List<Student> searchByName(String keyword) {
        String cleanKeyword = keyword == null ? "" : keyword.trim();
        return repository.findAll().stream()
                .filter(s -> s.getName().toLowerCase(Locale.ROOT).contains(keyword))
                .toList();
    }

    public List<Student> sortByName() {
        List<Student> list = repository.findAll();
        list.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
        return list;
    }

    public List<Student> sortByGpaDesc() {
        List<Student> list = repository.findAll();
        list.sort(Comparator.comparingDouble(Student::getGpa).reversed());
        return list;
    }

    private void validate(Student student) {
        List<String> errors = Validator.collectErrors(student);

        int age = student.getAge();
        if (age < 18 || age > 60) {
            errors.add("age: Tuối phải từ 18 đến 60");
        }

        double gpa = student.getGpa();
        if (gpa < 0.0 || gpa > 10.0) {
            errors.add("gpa: Gpa phải từ 0.0 đến 10.0");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

}
