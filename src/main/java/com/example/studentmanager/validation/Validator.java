package com.example.studentmanager.validation;

import com.example.studentmanager.annotation.NotEmpty;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Validator {
    public static List<String> collectErrors(Object obj) {
        List<String> errors = new ArrayList<>();
        if (obj == null) {
            errors.add("Đối tượng cần kiểm tra không được null");
            return errors;
        }

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            NotEmpty anotation = field.getAnnotation(NotEmpty.class);
            if (anotation == null) {
                continue;
            }

            try {
                field.setAccessible(true);
                Object value = field.get(obj);

                if (value == null || value.toString().isBlank()) {
                    errors.add(field.getName() + ": " + anotation.message());
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException("Không thể đọc thuộc tính: " + field.getName(), e);
            }


        }

        return  errors;
    }
}
