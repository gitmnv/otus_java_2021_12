package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;
import ru.otus.jdbc.mapper.reflection.EntityClassMetaDataImpl;

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
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        var client = dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(),
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
        return client;
    }

    @Override
    public List<T> findAll(Connection connection) {
        List<T> clients = new ArrayList<>();
        dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql()
                , List.of(), resultSet -> {
                    if (resultSet != null) {
                        try {
                            while (resultSet.next()) {
                                clients.add(createObj(resultSet));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e.getNextException());
                        }
                    }
                    return null;
                });

        return clients;
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        List<Object> values = new ArrayList<>();
        for (Field inspectedField : fields) {
            inspectedField.setAccessible(true);
            try {

                values.add(inspectedField.get(client));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Field field = client.getClass().getDeclaredField("name");
        //  field.setAccessible(true);
        System.out.println(entitySQLMetaData.getInsertSql());
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                Arrays.asList(values.toArray()));
    }

    @Override
    public void update(Connection connection, T client) {
        List<Field> withoutId = entityClassMetaData.getFieldsWithoutId();
        List<Object> values = new ArrayList<>();

        for (int i = 0; i < withoutId.size(); i++) {
            var inspectedField = withoutId.get(i);
            inspectedField.setAccessible(true);
            try {
                values.add(inspectedField.get(client).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            var idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            Long v = (Long) idField.get(client);
            values.add(v);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        var id = dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                values);
    }

    private T createObj(ResultSet rs) {
        try {
            Object some = entityClassMetaData.getConstructor().newInstance();
            Class<?> clazz = some.getClass();
            List<Field> allField = entityClassMetaData.getAllFields();
            for (Field inspectedField : allField) {
                String fieldName = inspectedField.getName();
                Field declaredField = clazz.getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                declaredField.set(some, rs.getObject(fieldName));
            }
            return (T) some;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchFieldException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

