package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс 'UserDaoHibernateImpl' имплементирует класс 'UserDao', т.о. обеспечивая подключение и управление БД MySQL.
 * Использование полиморфии с помощью интерфейса 'UserDao' позволяет использовать два различных метода управления
 * БД MySQL:
 *  1. С помощью драйвера JDBC;
 *  2. С помощью фреймворка 'Hibernate'.
 * Выбор способа производится в классе 'UserServiceImpl'.
 * Класс создан для одного из двух способов управления БД MySQL, а именно c применением фреймворка Hibernate.
 * При таком варианте используется объект 'SessionFactory', с помощью которого получается объект 'Session',
 * который в свою очередь позволяет осуществлять SQL-запросы к БД MySQl.
 * В методах класса обрабатываются исключения 'SQLException' и 'Exception'.
 * Методы выводят в консоль сообщения только об ошибках,
 * т.к. 'Hibernate' самостоятельно выводит сообщения об успешных операциях (по условию задачи
 * исключение составляет метод добавления пользователя).
 * Строковые переменные stringSQL в методах класса вынесены в начало методов для читабельности
 * и удобной возможности внесения изменений в SQL-запросы.
 */
public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;
    private Transaction transaction;

    public UserDaoHibernateImpl() {
        try {
            this.sessionFactory = Util.getSessionFactory();
        }
        catch (Exception e) {
            System.out.println("HIBERNATE: Не удалось создать сессию подключения к БД MySQL! <ERROR>");
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        String stringSQL =  "CREATE TABLE IF NOT EXISTS users (" +
                            "   id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                            "   name VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ," +
                            "   last_name VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL," +
                            "   age TINYINT NOT NULL" +
                            ")" +
                            "ENGINE = InnoDB, DEFAULT CHARACTER SET = utf8, COLLATE = utf8_unicode_ci";

        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();
                session.createSQLQuery(stringSQL).executeUpdate();
                transaction.commit();
            }
            catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.out.println("HIBERNATE: Ошибка при создании таблицы 'Users': " + e.getMessage());
            }
        }
        catch (Exception e) {
            System.out.println("HIBERNATE: Ошибка при создании таблицы 'Users': " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String stringSQL = "DROP TABLE IF EXISTS users";

        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();
                session.createSQLQuery(stringSQL).executeUpdate();
                transaction.commit();
            }
            catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.out.println("HIBERNATE: Ошибка при удалении таблицы 'Users': " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("HIBERNATE: Ошибка при удалении таблицы 'Users': " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();
                session.save(new User(name, lastName, age));
                transaction.commit();
                System.out.println("HIBERNATE: Пользователь '" + name + " " +
                                                                 lastName + " (" +
                                                                 age + ")' успешно добавлен в таблицу 'Users'. <OK>");
            }
            catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.out.println("HIBERNATE: Не удалось добавить пользователя '" + name + " " +
                                                                                     lastName + " (" +
                                                                                     age + ")' в таблицу 'Users'!" +
                                                                                     e.getMessage());
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            System.out.println("HIBERNATE: Не удалось добавить пользователя '" + name + " " +
                                                                                 lastName + " (" +
                                                                                 age + ")' в таблицу 'Users'!" +
                                                                                 e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();
                User user = session.get(User.class, id);
                if (user != null) {
                    session.delete(user);
                    transaction.commit();
                    System.out.println("HIBERNATE: Пользователь с ID='" + id + "' удалён из таблицы 'Users'. <OK>");
                }
                else {
                    System.out.println("HIBERNATE: Пользователь с ID='" + id + "' не найден в таблице 'Users'.");
                }
            }
            catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.out.println("HIBERNATE: Не удалось удалить пользователя с ID='" + id + "' из таблицы 'Users'!" +
                                                                                         e.getMessage());
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            System.out.println("HIBERNATE: Не удалось удалить пользователя с ID='" + id + "' из таблицы 'Users'!" +
                                                                                     e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String stringSQL = "SELECT * FROM users";

        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createNativeQuery(stringSQL, User.class);
            users = query.list();
        }
        catch (Exception e) {
            System.out.println("HIBERNATE: Не удалось вывести список всех пользователей из таблицы 'Users'! <ERROR>");
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        String stringSQL = "TRUNCATE TABLE users";
        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();
                session.createSQLQuery(stringSQL).executeUpdate();
                transaction.commit();
            }
            catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.out.println("HIBERNATE: Не удалось очистить таблицу 'Users'. <ERROR>");
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            System.out.println("HIBERNATE: Не удалось очистить таблицу 'Users'. <ERROR>");
            e.printStackTrace();
        }
    }
}
