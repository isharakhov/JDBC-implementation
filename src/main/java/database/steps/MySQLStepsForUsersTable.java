package database.steps;

import database.models.usersModel;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс с реализацией MySQL шагов для Users таблицы
 */
@AllArgsConstructor
public class MySQLStepsForUsersTable {

    private static final String ID_FIELD = "id";
    private static final String NICKNAME_FIELD = "nickname";
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";
    private static final String BIRTHDAY_FIELD = "birthdate";
    private static final String IS_MALE_FIELD = "is_male";

    private static final String INSERT_SQL_QUERY_USERS = "INSERT INTO users (%s, %s, %s, %s, %s)" +
            "VALUES (\"%s\", \"%s\", \"%s\", \"%s\", %b)";

    private static final String UPDATE_SQL_REQUEST_USERS = "UPDATE users " +
            "SET %s = \"%s\", %s = \"%s\", %s = \"%s\", %s = \"%s\", %s = %b " +
            "WHERE %s = %d";

    private static final String DELETE_SQL_REQUEST_USERS = "DELETE FROM users WHERE %s = %d";
    private static final String DELETE_SQL_REQUEST_ALL_USERS = "DELETE FROM users";

    private static final String SELECT_ALL_SQL_REQUEST_USERS = "SELECT * FROM users";
    private static final String SELECT_BY_ID_SQL_REQUEST_USERS = "SELECT * FROM users WHERE %s = %d";

    /**
     * Переменная подключение к БД
     */
    private final Connection connection;

    /**
     * Метод создания users в БД
     *
     * @param user объект с параметрами для создания из usersModel
     */
    public void createUsers(usersModel user) {
        try (Statement stml = connection.createStatement()) {
            stml.executeUpdate(String.format(INSERT_SQL_QUERY_USERS,
                    NICKNAME_FIELD, EMAIL_FIELD, PASSWORD_FIELD, BIRTHDAY_FIELD, IS_MALE_FIELD,
                    user.getNickName(), user.getEmail(), user.getPassword(), user.getBirthDate(), user.isMale()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод обновления user в БД
     *
     * @param user объект с параметрами для обновления
     * @param id   идентификатор поля, которое обновляем
     */
    public void updateUser(usersModel user, int id) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(UPDATE_SQL_REQUEST_USERS,
                    NICKNAME_FIELD, user.getNickName(),
                    EMAIL_FIELD, user.getEmail(),
                    PASSWORD_FIELD, user.getPassword(),
                    BIRTHDAY_FIELD, user.getBirthDate(),
                    IS_MALE_FIELD, user.isMale(),
                    ID_FIELD, id)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод удаления user из БД
     *
     * @param id идентификатор поля, которое удаляем
     */
    public void deleteUser(int id) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(DELETE_SQL_REQUEST_USERS, ID_FIELD, id)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод запроса user по ID из БД
     *
     * @param id идентификатор поля
     * @return объект users
     */
    public usersModel getUserById(int id) {
        try (Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(String.format(SELECT_BY_ID_SQL_REQUEST_USERS, ID_FIELD, id));
            if (result.next()) {
                return usersModel.builder()
                        .id(result.getInt(ID_FIELD))
                        .nickName(result.getString(NICKNAME_FIELD))
                        .email(result.getString(EMAIL_FIELD))
                        .password(result.getString(PASSWORD_FIELD))
                        .birthDate(result.getObject(BIRTHDAY_FIELD, LocalDate.class))
                        .isMale(result.getBoolean(IS_MALE_FIELD))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Метод запроса всех user из БД
     *
     * @return список всех Qaa
     */
    public List<usersModel> getAllUsers() {
        List<usersModel> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(SELECT_ALL_SQL_REQUEST_USERS);
            while (result.next()) {
                list.add(usersModel.builder()
                        .id(result.getInt(ID_FIELD))
                        .nickName(result.getString(NICKNAME_FIELD))
                        .email(result.getString(EMAIL_FIELD))
                        .password(result.getString(PASSWORD_FIELD))
                        .birthDate(result.getObject(BIRTHDAY_FIELD, LocalDate.class))
                        .isMale(result.getBoolean(IS_MALE_FIELD))
                        .build());
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }
    }

    /**
     * Очищаем таблиц users
     */
    public void deleteAllUsers() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(DELETE_SQL_REQUEST_ALL_USERS));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}