import database.MySQLConnector;
import database.steps.MySQLStepsForPostsTable;
import database.steps.MySQLStepsForUsersTable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

public class BaseTest {

    /**
     * Переменная с шагами SQL для users & posts table
     */
    protected MySQLStepsForUsersTable stepsForUsersTable;
    protected MySQLStepsForPostsTable stepsForPostsTable;

    /**
     * Переменная с соединением к БД
     */
    protected Connection connection;

    /**
     * Экземпляр класса SQL коннектора
     */
    private final MySQLConnector connector = new MySQLConnector();

    /**
     * Перед каждым тестом создаем подключение к БД
     */
    @BeforeEach
    public void setUp() {

        // Открытие подключения к БД
        connection = connector.openConnection();

        // Создание объекта MySQLSteps для users table
        stepsForUsersTable = new MySQLStepsForUsersTable(connection);

        // Создание объекта MySQLSteps для posts table
        stepsForPostsTable = new MySQLStepsForPostsTable(connection);
    }

    /**
     * Закрытие подключения к БД после тестов
     */
    @AfterEach
    public void tearDown() {
        // Закрытие подключения к БД
        connector.closeConnection(connection);
    }
}