package ru.otus.jdbc.mapper.reflection;

import ru.otus.crm.model.annotations.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class aClass;

    public EntityClassMetaDataImpl(Class aClass) {
        this.aClass = aClass;
    }

    @Override
    public String getName() {
        return aClass.getSimpleName();
    }

    @Override
    public Constructor getConstructor() {
        try {
            return aClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Field getIdField() {
        List<Field> fieldList = getAllFields().stream().toList();
        List<Field> fieldsWithId = new ArrayList<>();
        for (Field inspectedField : fieldList) {
            if (inspectedField.getAnnotation(Id.class) != null) {
                fieldsWithId.add(inspectedField);
            }
        }
        if (fieldsWithId.size() > 1) {
            throw new RuntimeException("Много @Id");
        }
        return fieldsWithId.get(0);
    }

    @Override
    public List<Field> getAllFields() {

        return Arrays.stream(aClass.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> fieldList = getAllFields().stream().toList();
        List<Field> fieldsWithoutId = new ArrayList<>();
        for (Field inspectedField : fieldList) {
            if (inspectedField.getAnnotation(Id.class) == null) {
                fieldsWithoutId.add(inspectedField);
            }
        }
        return fieldsWithoutId;
    }
}
