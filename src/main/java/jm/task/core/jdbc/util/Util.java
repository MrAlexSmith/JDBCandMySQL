package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс 'Util' содержит логику настройки соединения с БД MySQL.
 * Имеет статический метод 'getConnection()', который возвращает соединение с БД MySQL.
 * В методе 'getConnection()' используется класс 'DriverManager',
 * с помощью которого выполняется настройка соединения с БД MySQL (установка URL, имени пользователя, пароля и т.д.).
 * С данным классом работает класс 'UserDaoJDBCImpl', в котором реализована обработка исключения 'SQLException'.
 */
public class Util {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/db_1_1_4";
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "root";

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
}
