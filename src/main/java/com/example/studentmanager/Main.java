package com.example.studentmanager;

import com.example.studentmanager.repository.StudentRepository;
import com.example.studentmanager.service.StudentService;
import com.example.studentmanager.ui.ConsoleMenu;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        StudentRepository repository = new StudentRepository();
        StudentService service = new StudentService(repository);

        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            ConsoleMenu menu = new ConsoleMenu(service, scanner);
            menu.start();
        }
    }
}