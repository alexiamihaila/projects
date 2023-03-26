package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     *
     * @param field
     * @return
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     *
     * @param id
     * @return
     */
    private String createInsertQuery(int id){
        String query = "INSERT INTO " + type.getSimpleName() + "(";

        int i = 0;
        for(Field f : type.getDeclaredFields()){
            if(i != 0){
                query = query + f.getName() + ", ";
            }
            i++;
        }
        query = query.substring(0, query.length()-1);
        query += ") + VALUES + (";

        i = 0;
        for(Field f : type.getDeclaredFields()){
            if(i != 0){
                query = query  + "?, ";
            }
            i++;
        }
        query = query.substring(0, query.length()-1);
        query += ")";

        return query;
    }

    /**
     *
     * @return
     */
    public List<T> findAll() {
        String query = "SELECT * FROM " + type.getSimpleName();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return Collections.emptyList();
    }

    /**
     *
     * @param id
     * @return
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     *
     * @param resultSet
     * @return
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * @param t
     * @param id
     * @return
     */
    public T insert(T t, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery(id);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 0;

            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                if(i != 0){
                    statement.setObject(i, field.get(t));
                }

                i++;
            }
        }catch  (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }


        return t;
    }

    /**
     *
     * @param t
     * @param id
     * @return
     */
    public T update(T t, int id) {

        Connection connection = null;
        PreparedStatement statement = null;
        String query = "UPDATE " + type.getSimpleName() + " SET ";

        int i = 0;
        for(Field f : type.getDeclaredFields()){
            if(i != 0){
                query = query + f.getName() + " = ?, ";
            }
            i++;
        }
        query = query.substring(0, query.length()-1);
        query = query + "WHERE id = " + id;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            i = 0;

            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                if(i != 0){
                        statement.setObject(i, field.get(t));
                }

                i++;
            }
        }catch  (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: update " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }


    /**
     *
     * @param id
     * @return
     */
    public boolean delete(int id){

        Connection connection = null;
        PreparedStatement statement = null;

        String query = "DELETE FROM " + type.getSimpleName() + " WHERE id = " + id;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return false;
    }
}
