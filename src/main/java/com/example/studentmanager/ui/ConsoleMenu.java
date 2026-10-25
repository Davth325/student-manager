package com.example.studentmanager.ui;

import com.example.studentmanager.exception.DuplicateStudentException;
import com.example.studentmanager.exception.StudentNotFoundException;
import com.example.studentmanager.exception.ValidationException;
import com.example.studentmanager.model.Student;
import com.example.studentmanager.service.StudentService;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleMenu {
    private final StudentService service;
    private final Scanner scanner;

    public ConsoleMenu(StudentService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = readString("Chọn chức năng (0-8): ").trim();

            try {
                switch (choice) {
                    case "1" -> handleAdd();
                    case "2" -> printTable(service.getAllStudents());
                    case "3" -> handleFindById();
                    case "4" -> handleSearchByName();
                    case "5" -> handleUpdate();
                    case "6" -> handleDelete();
                    case "7" -> {
                        System.out.println("\n--- DANH SÁCH SẮP XẾP THEO TÊN (A-Z) ---");
                        printTable(service.sortByName());
                    }
                    case "8" -> {
                        System.out.println("\n--- DANH SÁCH SẮP XẾP THEO GPA GIẢM DẦN ---");
                        printTable(service.sortByGpaDesc());
                    }
                    case "0" -> {
                        System.out.println("Đã thoát chương trình. Tạm biệt!");
                        running = false;
                    }
                    default -> System.out.println("Lựa chọn không hợp lệ, vui lòng chọn từ 0 đến 8.");
                }
            } catch (ValidationException e) {
                System.out.println("Dữ liệu không hợp lệ:");
                for (String err : e.getErrors()) {
                    System.out.println("  - " + err);
                }
            } catch (StudentNotFoundException | DuplicateStudentException e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n========= QUẢN LÝ SINH VIÊN =========");
        System.out.println("1. Thêm sinh viên mới");
        System.out.println("2. Hiển thị toàn bộ danh sách");
        System.out.println("3. Tìm sinh viên theo ID");
        System.out.println("4. Tìm sinh viên theo tên");
        System.out.println("5. Cập nhật thông tin sinh viên");
        System.out.println("6. Xóa sinh viên");
        System.out.println("7. Sắp xếp theo tên (A-Z)");
        System.out.println("8. Sắp xếp theo GPA (Giảm dần)");
        System.out.println("0. Thoát");
        System.out.println("=====================================");
    }

    private void handleAdd() {
        System.out.println("\n--- THÊM SINH VIÊN ---");
        String id = readString("Nhập ID: ");
        String name = readString("Nhập họ tên: ");
        int age = readInt("Nhập tuổi (16-60): ");
        double gpa = readDouble("Nhập GPA (0.0-10.0): ");

        service.addStudent(id, name, age, gpa);
        System.out.println("Đã thêm sinh viên thành công!");
    }

    private void handleFindById() {
        String id = readString("Nhập ID cần tìm: ");
        Student student = service.getStudentById(id);
        printTable(List.of(student));
    }

    private void handleSearchByName() {
        String keyword = readString("Nhập từ khóa tên cần tìm: ");
        List<Student> results = service.searchByName(keyword);
        printTable(results);
    }

    private void handleUpdate() {
        System.out.println("\n--- CẬP NHẬT SINH VIÊN ---");
        String id = readString("Nhập ID sinh viên cần sửa: ");
        // Kiểm tra tồn tại ngay từ đầu để người dùng đỡ phải nhập tiếp nếu sai ID
        Student existing = service.getStudentById(id);
        System.out.println("Thông tin hiện tại: " + existing);

        String newName = readString("Nhập họ tên mới: ");
        int newAge = readInt("Nhập tuổi mới (16-60): ");
        double newGpa = readDouble("Nhập GPA mới (0.0-10.0): ");

        service.updateStudent(id, newName, newAge, newGpa);
        System.out.println("Đã cập nhật sinh viên thành công!");
    }

    private void handleDelete() {
        String id = readString("Nhập ID sinh viên cần xóa: ");
        service.deleteStudent(id);
        System.out.println("Đã xóa sinh viên có ID: " + id.trim());
    }

    private void printTable(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("Danh sách trống (không có sinh viên nào).");
            return;
        }

        String line = "+------------+---------------------------+-------+--------+";
        System.out.println(line);
        System.out.printf(Locale.US, "| %-10s | %-25s | %5s | %6s |%n", "ID", "HỌ VÀ TÊN", "TUỔI", "GPA");
        System.out.println(line);
        for (Student s : students) {
            System.out.printf(Locale.US, "| %-10s | %-25s | %5d | %6.2f |%n",
                    s.getId(), s.getName(), s.getAge(), s.getGpa());
        }
        System.out.println(line);
        System.out.println("Tổng số: " + students.size() + " sinh viên.");
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên hợp lệ!");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số thực hợp lệ (VD: 8.5 hoặc 8,5)!");
            }
        }
    }
}