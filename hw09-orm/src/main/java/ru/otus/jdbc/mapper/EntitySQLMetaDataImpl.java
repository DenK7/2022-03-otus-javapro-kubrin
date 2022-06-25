package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData{

    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        var name = entityClassMetaData.getName();
        return "select " + getFields(entityClassMetaData.getAllFields(), Boolean.TRUE) + " from " + name;
    }

    @Override
    public String getSelectByIdSql() {
        var name = entityClassMetaData.getName();
        return "select " + getFields(entityClassMetaData.getAllFields(), Boolean.TRUE) +
                " from " + name + " where " + entityClassMetaData.getIdField().getName().toLowerCase() + "= ?";
    }

    @Override
    public String getInsertSql() {
        var name = entityClassMetaData.getName();
        var fields = entityClassMetaData.getFieldsWithoutId();
        return "insert into " + name + " (" + getFields(fields, Boolean.TRUE) + ") values (" + ",?".repeat(fields.size()).substring(1) + ")";
    }

    @Override
    public String getUpdateSql() {
        var name = entityClassMetaData.getName();
        var fields = entityClassMetaData.getFieldsWithoutId();

        return "update " + name +
                " set " + getFields(fields, Boolean.FALSE) +
                " where " + entityClassMetaData.getIdField().getName().toLowerCase() + " = ?";
    }

    private String getFields (List<Field> fields, Boolean notForUpdate) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field: fields) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            if (notForUpdate) {
                stringBuilder.append(field.getName().toLowerCase());
            } else {
                stringBuilder.append(field.getName().toLowerCase()).append(" = ? ");
            }
        }
        return stringBuilder.toString();
    }
}
