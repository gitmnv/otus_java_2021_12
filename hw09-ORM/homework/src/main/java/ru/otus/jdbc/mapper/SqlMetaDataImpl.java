package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.reflection.EntityClassMetaDataImpl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public class SqlMetaDataImpl implements EntitySQLMetaData {

    private final Class aClass;
    private final EntityClassMetaData entityClassMetaData;

    public SqlMetaDataImpl(Class aClass) {
        this.aClass = aClass;
        this.entityClassMetaData = new EntityClassMetaDataImpl(aClass);
    }

    @Override
    public String getSelectAllSql() {
        return "select * from " + toLowerCase(entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return "select * from " + toLowerCase(entityClassMetaData.getName()) + " where id = ?";
    }

    @Override
    public String getInsertSql() {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        // List<Field> fields = Arrays.stream(aClass.getDeclaredFields()).toList();
        String q = "insert into " + toLowerCase(entityClassMetaData.getName())
                + "(" + "name" + ")" + " values (?)";
        StringBuilder firstPart = new StringBuilder();
        StringBuilder endPart = new StringBuilder();
        firstPart.append("insert into ").append(toLowerCase(aClass.getSimpleName()))
                .append(" (");
        endPart.append("values (");
        for (Field field : fields) {
            firstPart.append(field.getName());
            firstPart.append(",");
            endPart.append("?,");
        }

        firstPart.setLength(firstPart.length() - 1);
        endPart.setLength(endPart.length() - 1);

        firstPart.append(") ");
        endPart.append(")");

        return firstPart.toString() + endPart.toString();
    }

    @Override
    public String getUpdateSql() {
        String q = "update client set name=? where id= ?";
        StringBuilder firstPart = new StringBuilder();
        firstPart.append("update ")
                .append(toLowerCase(entityClassMetaData.getName()))
                .append(" set ");
        return null;
    }

    private String toLowerCase(String data) {
        return data.toLowerCase(Locale.ROOT);
    }
}
