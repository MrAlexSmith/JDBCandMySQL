package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

/**
 * Класс 'Util' содержит логику настройки соединения с БД MySQL.
 * Имеет статический метод 'getConnection()', который возвращает соединение с БД MySQL.
 * С данным классом работают два класса: 'UserDaoJDBCImpl' и 'UserDaoHibernateImpl',
 * в которых реализованы обработки исключений.
 * Обращения из сервиса 'UserServiceImpl' к методам класса 'Util' реализованы за счёт полиформизма интерфейса 'UserDao'.
 */
public class Util {
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/db_1_1_4";
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "root";
    private static final String MYSQL_DIALECT = "org.hibernate.dialect.MySQL5Dialect";
    private static final String MYSQL_SHOW_SQL = "true";
    private static final String MYSQL_CURRENT_SESSION_CONTEXT_CLASS = "thread";
    private static final String MYSQL_HBM2DDL_AUTO = "create-drop";

    private static SessionFactory sessionFactory;

    /**
     * Статический метод посзволяет получить общее соединение с БД MySQL без создания объекта,
     * с целью отсутствия зависимости от состояния объекта класса 'Util'
     * и предоставления общего доступа к соединению с БД MySQL.
     * @return connection - соединение с БД MySQL.
     */
    public static Connection getConnectionMySQL() throws SQLException, ClassNotFoundException {

//        Class.forName("com.mysql.jdbc.Driver"); // Данную инструкцию нужно использовать только со старыми версиями драйвера JDBC.
        return DriverManager.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
    }

    /**
     * Статический метод посзволяет получить общее подключение к БД MySQL без создания объекта,
     * с целью отсутствия зависимости от состояния объекта класса 'Util'
     * и предоставления общего доступа к соединению с БД MySQL.
     * В методе создаётся фабрика сессий 'SessionFactory'.
     * К методу идёт обращение из класса 'UserDaoHibernateImpl', который имплементирует интерфейс 'UserDao'.
     * @return - объект 'SessionFactory'
     * @throws Exception
     */
    public static SessionFactory getSessionFactory() throws Exception {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            Properties properties = new Properties();
            properties.put(Environment.DRIVER, MYSQL_DRIVER);
            properties.put(Environment.URL, MYSQL_URL);
            properties.put(Environment.USER, MYSQL_USERNAME);
            properties.put(Environment.PASS, MYSQL_PASSWORD);
            properties.put(Environment.DIALECT, MYSQL_DIALECT);
            properties.put(Environment.SHOW_SQL, MYSQL_SHOW_SQL);
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, MYSQL_CURRENT_SESSION_CONTEXT_CLASS);
            properties.put(Environment.HBM2DDL_AUTO, MYSQL_HBM2DDL_AUTO);

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration
                                                                                  .getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
