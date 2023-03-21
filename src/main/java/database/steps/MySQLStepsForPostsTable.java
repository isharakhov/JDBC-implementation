package database.steps;

import database.models.postsModel;
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
 * Класс с реализацией MySQL шагов для Posts таблицы
 */
@AllArgsConstructor
public class MySQLStepsForPostsTable {

    private static final String ID_FIELD = "id";
    private static final String USER_ID_FIELD = "user_id";
    private static final String TITLE_FIELD = "title";
    private static final String POST_TEXT_FIELD = "post_text";
    private static final String POST_DATE_FIELD = "post_date";

    private static final String INSERT_SQL_QUERY_POSTS = "INSERT INTO posts (%s, %s, %s, %s)" +
            "VALUES (%d, \"%s\", \"%s\", \"%s\")";

    private static final String UPDATE_SQL_REQUEST_POSTS = "UPDATE posts " +
            "SET %s = %d , %s = \"%s\", %s = \"%s\", %s = \"%s\"" +
            "WHERE %s = %d";

    private static final String DELETE_SQL_REQUEST_POSTS = "DELETE FROM posts WHERE %s = %d";
    private static final String DELETE_SQL_REQUEST_ALL_POSTS = "DELETE FROM posts";
    private static final String SELECT_ALL_SQL_REQUEST_POSTS = "SELECT * FROM posts";
    private static final String SELECT_BY_ID_SQL_REQUEST_POSTS = "SELECT * FROM posts WHERE %s = %d";

    /**
     * Переменная подключение к БД
     */
    private final Connection connection;

    /**
     * Метод создания posts в БД
     *
     * @param post объект с параметрами для создания из usersModel
     */
    public void createPost(postsModel post, int id) {
        try (Statement stml = connection.createStatement()) {
            stml.executeUpdate(String.format(INSERT_SQL_QUERY_POSTS,
                    USER_ID_FIELD, TITLE_FIELD, POST_TEXT_FIELD, POST_DATE_FIELD,
                    id, post.getTitle(), post.getPostText(), post.getPostDate()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод обновления post в БД
     *
     * @param post   объект с параметрами для обновления
     * @param userId идентификатор поля, которое обновляем
     * @param postId идентификатор поля, которое обновляем
     */
    public void updatePost(postsModel post, int userId, int postId) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(UPDATE_SQL_REQUEST_POSTS,
                    USER_ID_FIELD, userId,
                    TITLE_FIELD, post.getTitle(),
                    POST_TEXT_FIELD, post.getPostText(),
                    POST_DATE_FIELD, post.getPostDate(),
                    ID_FIELD, postId)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод удаления post в БД
     *
     * @param id идентификатор поля, которое удаляем
     */
    public void deletePost(int id) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(DELETE_SQL_REQUEST_POSTS, ID_FIELD, id)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод запроса post по ID из БД
     *
     * @param id идентификатор поля
     * @return объект posts
     */
    public postsModel getPostById(int id) {
        try (Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(String.format(SELECT_BY_ID_SQL_REQUEST_POSTS, ID_FIELD, id));
            if (result.next()) {
                return postsModel.builder()
                        .id(result.getInt(ID_FIELD))
                        .userId(result.getInt(USER_ID_FIELD))
                        .title(result.getString(TITLE_FIELD))
                        .postText(result.getString(POST_TEXT_FIELD))
                        .postDate(result.getObject(POST_DATE_FIELD, LocalDate.class))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Метод запроса всех posts из БД
     *
     * @return список всех posts
     */
    public List<postsModel> getAllPosts() {
        List<postsModel> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(SELECT_ALL_SQL_REQUEST_POSTS);
            while (result.next()) {
                list.add(postsModel.builder()
                        .id(result.getInt(ID_FIELD))
                        .userId(result.getInt(USER_ID_FIELD))
                        .title(result.getString(TITLE_FIELD))
                        .postText(result.getString(POST_TEXT_FIELD))
                        .postDate(result.getObject(POST_DATE_FIELD, LocalDate.class))
                        .build());
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }
    }

    /**
     * Очищаем таблицу posts
     */
    public void deleteAllPosts() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(DELETE_SQL_REQUEST_ALL_POSTS));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}