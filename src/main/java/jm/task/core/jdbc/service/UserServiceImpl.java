package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

/**
 * Класс 'UserServiceImpl' выполняет роль сервиса-посредника, с помощью которого выбирается способ подключения
 * и управления БД MySQL:
 *  1. Direct-SQL-запросы посредством драйвера JDBC;
 *  2. Посредством фреймворка 'Hibernate'.
 * Для работы первым способом, в конструкторе класса 'UserServiceImpl' создаётся объект 'UserDaoJDBCImpl',
 * который имплементирует интерфейс 'userDao'.
 * Для работы вторым способом, в конструкторе класса 'UserServiceImpl' создаётся объект 'UserDaoHibernateImpl',
 * который имплементирует интерфейс 'userDao'.
 * Все остальные переопределённые методы, непосредственно перенаправляют команды от исполняемого класса 'Main' в
 * соответствующие классы, имплементирующие интерфейс 'UserDao'.
 */
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoJDBCImpl();
    }

    @Override
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
