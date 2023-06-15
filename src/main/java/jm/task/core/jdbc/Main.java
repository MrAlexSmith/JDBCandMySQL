package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDaoJDBCImpl();

        // Создание таблицы User(ов)
        userDao.createUsersTable();

        // Добавление 4 User(ов) в таблицу
        User user1 = new User("John", "Doe", (byte) 25);
        User user2 = new User("Jane", "Smith", (byte) 30);
        User user3 = new User("David", "Johnson", (byte) 35);
        User user4 = new User("Sarah", "Williams", (byte) 28);

        userDao.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        System.out.println("User с именем " + user1.getName() + " добавлен в базу данных");

        userDao.saveUser(user2.getName(), user1.getLastName(), user1.getAge());
        System.out.println("User с именем " + user2.getName() + " добавлен в базу данных");

        userDao.saveUser(user3.getName(), user1.getLastName(), user1.getAge());
        System.out.println("User с именем " + user3.getName() + " добавлен в базу данных");

        userDao.saveUser(user4.getName(), user1.getLastName(), user1.getAge());
        System.out.println("User с именем " + user4.getName() + " добавлен в базу данных");

        // Получение всех User из базы и вывод в консоль
        System.out.println(userDao.getAllUsers());

        // Очистка таблицы User(ов)
        userDao.cleanUsersTable();

        // Удаление таблицы
        userDao.dropUsersTable();
    }
}
