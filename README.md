# JDBC-implementation

### JDBC-implementation for MySql DB

***
**Technology Stack**

```
Java, Gradle, TestNG, JUnit, Allure, Owner, Allure, JDBC, MySQL, Lombok, AssertJ, Faker 
```

***
**Реализация:**

+ DB с 2 таблицами users и posts.
+ Подключение к БД посредством JDBC.
+ Написаны тесты на CRUD к обеим таблицам.
    + Create (Создание сущности) insert + select (для проверки результата).
    + Read (Чтение сущности) select + insert (для заведения тестовых данных).
    + Update (Обновление сущности) update + insert (для заведения тестовых данных) + select (для проверки результата)
    + Delete (Удаление сущности) delete + insert (для заведения тестовых данных) + select (для проверки результата)
+ В тестах только необходимые шаги и проверки (Соединение к БД делаются в отдельном классе)
+ Формируется адекватный Allure-отчет

***