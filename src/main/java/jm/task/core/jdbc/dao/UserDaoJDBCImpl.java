package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс 'UserDaoJDBCImpl' имплементирует класс 'UserDao', т.о. обеспечивая подключение и управление БД MySQL.
 * Использование полиморфии с помощью интерфейса 'UserDao' позволяет использовать два различных метода управления
 * БД MySQL:
 *  1. С помощью драйвера JDBC;
 *  2. С помощью фреймворка 'Hibernate'.
 * Выбор способа производится в классе 'UserServiceImpl'.
 * Класс создан для одного из двух способов управления БД MySQL, а именно напрямую с помощью драйвера JDBC.
 * При таком варианте используется объект 'Connection', с помощью которого получается объект 'Statement'.
 * Объект 'Statement' позволяет осуществлять direct-SQL-запросы к БД MySQl.
 * В методах класса обрабатываются исключения 'SQLException' и 'ClassNotFoundException' (для старых версий драйвера).
 * Методы выводят в консоль сообщения не только об ошибках, но и об успешных операциях с целью логирования действий.
 */
public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        try {
            this.connection = Util.getConnectionMySQL();
            System.out.println("JDBC: Подключение к БД MySQL выполнено успешно. <OK>");
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println("JDBC: Не удалось подключиться к БД MySQL! <ERROR>");
            e.printStackTrace();
        }
    }

    /**
     * Создаётся таблица 'users' в БД MySQL, если такая таблица ещё не существует в БД MySQl.
     * В таблицу добавлена кодировка 'utf8' для работы с кириллицей.
     * Для полей 'name' и 'last_name' установлена кодировка 'utf8' для строк с символами кириллицы.
     */
    @Override
    public void createUsersTable() {
        String query =  "CREATE TABLE IF NOT EXISTS users (" +
                        "   id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                        "   name VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ," +
                        "   last_name VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL," +
                        "   age TINYINT NOT NULL" +
                        ")" +
                        "ENGINE = InnoDB, DEFAULT CHARACTER SET = utf8, COLLATE = utf8_unicode_ci";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("JDBC: Таблица 'Users' успешно создана, либо она уже существует в БД. <OK>");
        }
        catch (SQLException e) {
            System.out.println("JDBC: Не удалось создать таблицу 'Users'! <ERROR>");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("JDBC: Таблица 'Users' успешно удалена, либо она уже была удалена ранее. <OK>");
        }
        catch (SQLException e) {
            System.out.println("JDBC: Не удалось удалить таблицу 'Users'! <ERROR>");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("JDBC: Пользователь '" + name + " " +
                                                        lastName + " (" +
                                                        age + ")' успешно добавлен в таблицу 'Users'. <OK>");
        }
        catch (SQLException e) {
            System.out.println("JDBC: Не удалось добавить пользователя '" + name + " " +
                                                                            lastName + " (" +
                                                                            age + ")' в таблицу 'Users'! <ERROR>");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("JDBC: Пользователь с ID='" + id + "' удалён из таблицы 'Users'. <OK>");
        }
        catch (SQLException e) {
            System.out.println("JDBC: Не удалось удалить пользователя с ID='" + id + "' из таблицы 'Users'! <ERROR>");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM users";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        }
        catch (SQLException e) {
            System.out.println("JDBC: Не удалось вывести список всех пользователей из таблицы 'Users'! <ERROR>");
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("JDBC: Таблица 'Users' успешно очищена. <OK>");
        }
        catch (SQLException e) {
            System.out.println("JDBC: Не удалось очистить таблицу 'Users'. <ERROR>");
            e.printStackTrace();
        }
    }
}
