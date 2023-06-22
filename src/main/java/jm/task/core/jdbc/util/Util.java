/*package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/db_1_1_4";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1111dba";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
*/

package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/db_1_1_4";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "1111dba";

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.url", JDBC_URL)
                    .setProperty("hibernate.connection.username", JDBC_USERNAME)
                    .setProperty("hibernate.connection.password", JDBC_PASSWORD)
                    // Дополнительные настройки Hibernate...
                    .configure();

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
