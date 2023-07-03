/**
 * Java pre-project. Практическая задача 1.1.2. Работа с базой данных c помощью JDBC (Java pre-project. Задача 1.1.4)
 *
 * ДЛЯ ВЫПОЛНЕНИЯ ЗАДАЧИ НЕОБХОДИМО:
 * Клонировать/скачать заготовку по ссылке https://github.com/KataAcademy/PP_1_1_3-4_JDBC_Hibernate.git
 * Установить на компьютер MySqlServer и Workbench. Создать подключение и схему через Workbench.
 * Протестировать возможность соединения с базой через идею с помощью встроенной утилиты.
 *
 * ОПИСАНИЕ ЗАДАЧИ:
 * Необходимо ознакомиться с заготовкой и доработать приложение,
 * которое взаимодействует с базой оперируя пользователем ( класс User )
 * и проверить свои методы заранее написанными JUnit тестами.
 * По итогу все тесты должны быть пройдены. Разрешается посмотреть реализацию тестов.
 * Для запуска теста необходимо найти класс в папке test ( показано в предыдущей лекции )
 * и при нажатии на него правой кнопкой мыши запустить, выбрав Run "Имя класса".
 * Класс UserHibernateDaoImpl в рамках этой задачи не затрагивается (остаётся пустой)
 *
 * User представляет из себя сущность с полями:
 *  Long id
 *  String name
 *  String lastName
 *  Byte age
 *
 * Архитектура приложения создана с опорой на паттерн проектирования MVC ( частично, у нас не WEB приложение).
 *
 * ТРЕБОВАНИЯ К КЛАССА ПРИЛОЖЕНИЯ:
 *  Классы dao/service должны реализовывать соответствующие интерфейсы
 *  Класс dao должен иметь конструктор пустой/по умолчанию
 *  Все поля должны быть private
 *  service переиспользует методы dao
 *  Обработка всех исключений, связанных с работой с базой данных должна находиться в dao
 *  Класс Util должен содержать логику настройки соединения с базой данных
 *
 * НЕОБХОДИМЫЕ ОПЕРАЦИИ:
 *  Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
 *  Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
 *  Очистка содержания таблицы
 *  Добавление User в таблицу
 *  Удаление User из таблицы ( по id )
 *  Получение всех User(ов) из таблицы
 *
 * АЛГОРИТМ РАБОТЫ ПРИЛОЖЕНИЯ:
 * В методе main класса Main должны происходить следующие операции:
 *  1. Создание таблицы User(ов)
 *  2. Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
 *  3. Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
 *  4. Очистка таблицы User(ов)
 *  5. Удаление таблицы
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
