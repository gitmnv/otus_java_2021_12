package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        var client = dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(),
                List.of(id), rs -> {
                    try {
                        if (rs.next()) {
                            return new Client(rs.getLong("id"), rs.getString("name"));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e.getNextException());
                    }
                    return null;
                });
        return (Optional<T>) client;
    }

    @Override
    public List<T> findAll(Connection connection) {
        List<Client> clients = new ArrayList<>();
        dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql()
                , List.of(), resultSet -> {
                    if (resultSet != null) {
                        try {
                            while (resultSet.next()) {
                                clients.add(new Client(resultSet.getLong("id"), resultSet.getString("name")));
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e.getNextException());
                        }
                    }
                    return null;
                });

        return (List<T>) clients;
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            Field field = client.getClass().getDeclaredField("name");
            field.setAccessible(true);
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    Collections.singletonList(field.get(client).toString()));
        } catch (NoSuchFieldException |
                IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void update(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }
}
