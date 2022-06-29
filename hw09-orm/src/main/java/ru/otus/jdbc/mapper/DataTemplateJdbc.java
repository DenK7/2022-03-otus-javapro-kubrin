package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.jdbc.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохраняет объект в базу, читает объект из базы
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
        try {
            String sql = entitySQLMetaData.getSelectByIdSql();
            return (Optional<T>) dbExecutor.executeSelect(connection, sql, List.of(id), rs -> {
                try {
                    if (rs.next()) {
                        Constructor<?> constructor = entityClassMetaData.getConstructor();
                        var objectFromClazz = constructor.newInstance();
                        setDataIntoFieldsObject((T) objectFromClazz, rs);

                        return objectFromClazz;
                    }
                    return null;
                } catch (Exception e) {
                    throw new DataTemplateException(e);
                }
            });
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private void setDataIntoFieldsObject(T object, ResultSet rs) {
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                if (field.getAnnotation(PrimaryKey.class) != null || field.getAnnotation(ColumnData.class) != null) {
                    field.setAccessible(true);
                    field.set(object, rs.getObject(field.getName()));
                }
            }
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            List<T> listResults = new ArrayList<>();
            try {
                while (rs.next()) {
                    Constructor<?> constructor = entityClassMetaData.getConstructor();
                    var objectFromClazz = constructor.newInstance();
                    setDataIntoFieldsObject((T) objectFromClazz, rs);
                    listResults.add((T) objectFromClazz);
                }
                return listResults;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            ArrayList<Object> objects = new ArrayList<>();
            for (Object field: entityClassMetaData.getFieldsWithoutId()) {
                ((Field)field).setAccessible(true);
                objects.add(((Field)field).get(client));
            }

            String sql = entitySQLMetaData.getInsertSql();
            return dbExecutor.executeStatement(connection, sql, objects);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            ArrayList<Object> objects = new ArrayList<>();
            for (Object field: entityClassMetaData.getFieldsWithoutId()) {
                ((Field)field).setAccessible(true);
                objects.add(((Field)field).get(client));
            }

            var field = entityClassMetaData.getIdField();
            field.setAccessible(true);
            objects.add(field.get(client));

            String sql = entitySQLMetaData.getUpdateSql();
            dbExecutor.executeStatement(connection, sql, objects);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
