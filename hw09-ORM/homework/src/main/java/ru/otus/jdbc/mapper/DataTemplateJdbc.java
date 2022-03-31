package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData<T> entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData<T> entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        var selected = dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(),
                List.of(id), rs -> {
                    try {
                        if (rs.next()) {
                            return createObj(rs);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    return null;

                });
        return selected;
    }

    @Override
    public List<T> findAll(Connection connection) {
        List<T> selectedObjects = new ArrayList<>();
        dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql()
                , List.of(), resultSet -> {
                    if (resultSet != null) {
                        try {
                            while (resultSet.next()) {
                                selectedObjects.add(createObj(resultSet));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e.getNextException());
                        }
                    }
                    return null;
                });

        return selectedObjects;
    }

    @Override
    public long insert(Connection connection, T object) {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        List<Object> values = new ArrayList<>();
        for (Field inspectedField : fields) {
            inspectedField.setAccessible(true);
            try {

                values.add(inspectedField.get(object));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                Arrays.asList(values.toArray()));
    }

    @Override
    public void update(Connection connection, T object) {
        List<Field> withoutId = entityClassMetaData.getFieldsWithoutId();
        List<Object> values = new ArrayList<>();

        for (Field inspectedField : withoutId) {
            inspectedField.setAccessible(true);
            try {
                values.add(inspectedField.get(object).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            var idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            Long v = (Long) idField.get(object);
            values.add(v);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        var id = dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                values);
    }

    private T createObj(ResultSet rs) {
        try {
            T some = entityClassMetaData.getConstructor().newInstance();
            Class<?> clazz = some.getClass();
            List<Field> allField = entityClassMetaData.getAllFields();
            for (Field inspectedField : allField) {
                String fieldName = inspectedField.getName();
                Field declaredField = clazz.getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                declaredField.set(some, rs.getObject(fieldName));
            }
            return some;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchFieldException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

