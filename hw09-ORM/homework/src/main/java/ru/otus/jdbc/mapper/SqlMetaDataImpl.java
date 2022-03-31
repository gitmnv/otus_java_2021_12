package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.reflection.EntityClassMetaDataImpl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public class SqlMetaDataImpl<T> implements EntitySQLMetaData<T> {

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
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select * from ")
                .append(toLowerCase(entityClassMetaData.getName()))
                .append(" where ")
                .append(toLowerCase(entityClassMetaData.getIdField().getName()))
                .append(" = ?");
        return queryBuilder.toString();
    }

    @Override
    public String getInsertSql() {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        StringBuilder firstPart = new StringBuilder();
        StringBuilder endPart = new StringBuilder();
        firstPart.append("insert into ").append(toLowerCase(aClass.getSimpleName()))
                .append(" (");
        endPart.append("values (");
        for (Field field : fields) {
            // tableListString.append(field.getName() +", ");
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
        //"UPDATE Manager SET label = ?, param1 = ? WHERE no = ?";
        List<Field> fieldList = entityClassMetaData.getFieldsWithoutId();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("update ")
                .append(toLowerCase(entityClassMetaData.getName()))
                .append(" set ");

        for (Field field : fieldList) {
            queryBuilder.append(toLowerCase(field.getName()))
                    .append(" = ?, ");
        }
        queryBuilder.setLength(queryBuilder.length() - 2);
        queryBuilder.append(" where ")
                .append(toLowerCase(entityClassMetaData.getIdField().getName()))
                .append(" = ?");
        return queryBuilder.toString();
    }

    private String toLowerCase(String data) {
        return data.toLowerCase(Locale.ROOT);
    }
}
