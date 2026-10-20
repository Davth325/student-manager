package com.example.studentmanager;

import com.example.studentmanager.model.Student;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Student a = new Student("SV01", "Nguyễn Văn A", 20, 8.5);
        Student b = new Student("SV01", "Trần Thị B", 21, 9.0);

        System.out.println("a.equals(b) = " + a.equals(b)); // Phải ra true

        Set<Student> set = new HashSet<>();
        set.add(a);
        set.add(b);
        System.out.println("Số phần tử trong HashSet = " + set.size()); // Phải ra 1
    }
}