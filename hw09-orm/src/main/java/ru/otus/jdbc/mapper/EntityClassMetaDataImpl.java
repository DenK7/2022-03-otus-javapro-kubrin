package ru.otus.jdbc.mapper;

import ru.otus.jdbc.annotations.TableData;

import java.io.FileDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl implements EntityClassMetaData{
    private final String className;

    public EntityClassMetaDataImpl(String className) {
        this.className = className;
    }

    @Override
    public String getName() {
        try {
            Class<?> clazz = Class.forName(className);
            var annotation = clazz.getAnnotation(TableData.class);
            if ("##default".equals(annotation.name())) {
                return clazz.getSimpleName().toLowerCase();
            } else {
                return annotation.name().toLowerCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("class not found");
        }
    }

    @Override
    public Constructor getConstructor() {
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.getConstructor();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("class or constructor not found");
        }
    }

    @Override
    public Field getIdField() {
        try {
            Class<?> clazz = Class.forName(className);
            for (Field field: clazz.getFields()) {
                if (checkAnnotation(field, "PrimaryKey")) {
                    return field;
                }
            }
            throw new RuntimeException("Field id not found");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Class or field not found");
        }
    }

    @Override
    public List<Field> getAllFields() {
        List<Field> fields = new ArrayList<>();
        try {
            Class<?> clazz = Class.forName(className);
            //можно было просто все вытащить, но хотелось только те поля, которые есть в бд
            for (Field field: clazz.getFields()) {
                if (checkAnnotation(field, "PrimaryKey") || checkAnnotation(field, "ColumnData")) {
                    fields.add(field);
                }
            }
            return fields;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Class or field not found");
        }
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> fields = new ArrayList<>();
        try {
            Class<?> clazz = Class.forName(className);
            //можно было просто все вытащить, но хотелось только те поля, которые есть в бд
            for (Field field: clazz.getFields()) {
                if (checkAnnotation(field, "ColumnData")) {
                    fields.add(field);
                }
            }
            return fields;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Class or field not found");
        }
    }

    private static Boolean checkAnnotation (Field field, String annotationName) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        for (Annotation annotation: annotations) {
            if (annotationName.equals(annotation.annotationType().getSimpleName())){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
