/**
 * Java pre-project. Практическая задача 1.1.2. Работа с базой данных c помощью JDBC (Java pre-project. Задача 1.1.4)
 *
 * ДЛЯ ВЫПОЛНЕНИЯ ЗАДАЧИ НЕОБХОДИМО:
 *  1. Ознакомиться с Hibernate. Подробнее узнать о Hibernate можно здесь https://hibernate.org/orm/
 *  2. Готовая прошлая задача ( работа будет происходить в этом же проекте )
 *
 * ОПИСАНИЕ ЗАДАЧИ:
 * В прошлой задаче мы познакомились с Maven и JDBC, доработали приложение, взаимодействующее с базой данных.
 * На этот раз обратим внимание на класс UserHibernateDaoImpl, который реализует тот же интерефейс,
 * что и UserDaoJdbcImpl.
 * В рамках этой задачи необходимо реализовать взаимодействие с базой данных с помощью Hibernate
 * и дописать методы в классе UserHibernateDaoImpl, проверить свои методы заранее написанными JUnit тестами из заготовки.
 *
 * ТРЕБОВАНИЯ К КЛАССА ПРИЛОЖЕНИЯ:
 *  1. UserHibernateDaoImpl должен реализовывать интерефейс UserDao
 *  2. В класс Util должна быть добавлена конфигурация для Hibernate ( рядом с JDBC), без использования xml.
 *  3. Service на этот раз использует реализацию dao через Hibernate
 *  4. Методы создания и удаления таблицы пользователей в классе UserHibernateDaoImpl должны быть реализованы с помощью SQL.
 *
 * Алгоритм приложения и операции не меняются в сравнении с предыдущим заданием.
 */
package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        // Создание таблицы 'users' в БД MySQL.
        userService.createUsersTable();

        // Добавление четырёх пользователей User в таблицу 'Users' БД MySQL.
        User user1 = new User("Alex",    "Smith",    (byte) 50);
        User user2 = new User("Иван",    "Иванов",   (byte) 25);
        User user3 = new User("Василий", "Петров",   (byte) 35);
        User user4 = new User("Sarah",   "Williams", (byte) 28);

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        // Получение списка всех пользователей (User) из таблицы 'users' БД MySQL и вывод списка в консоль.
        System.out.println("________________________________________________________________________________");
        System.out.println("Список пользователей из таблицы 'users' БД MySQL:");
        System.out.println(userService.getAllUsers()
                .toString()
                .replace("[", "")
                .replace("]", ""));
        System.out.println("________________________________________________________________________________");

        // Очистка таблицы пользователей 'users' БД MySQL.
        userService.cleanUsersTable();

        // Удаление таблицы пользователей 'users' из БД MySQL.
        userService.dropUsersTable();
    }
}
