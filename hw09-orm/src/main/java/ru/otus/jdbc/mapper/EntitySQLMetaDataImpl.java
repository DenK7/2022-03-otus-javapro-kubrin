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
        return "select " + getFields(entityClassMetaData.getAllFields()) + " from " + name;
    }

    @Override
    public String getSelectByIdSql() {
        var name = entityClassMetaData.getName();
        return "select " + getFields(entityClassMetaData.getAllFields()) + " from " + name + " where " + entityClassMetaData.getIdField() + "= :id";
    }

    @Override
    public String getInsertSql() {
        var name = entityClassMetaData.getName();
        return "insert into " + name + " (" + getFields(entityClassMetaData.getFieldsWithoutId()) + ") values (" + ")";
    }

    @Override
    public String getUpdateSql() {
        var name = entityClassMetaData.getName();
        return "update " + name + " set " + getFields(entityClassMetaData.getFieldsWithoutId()) + ") values (" + ")";
    }

    private String getFields (List<Field> fields) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field: fields) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(field.getName().toLowerCase());
        }
        return stringBuilder.toString();
    }
}
