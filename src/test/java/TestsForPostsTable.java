import database.models.postsModel;
import database.models.usersModel;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.TestsObjectBuilder.getRandomPostModel;
import static utils.TestsObjectBuilder.getRandomUserModel;

/**
 * Класс для Posts Table, внешний ключ user_id
 */
public class TestsForPostsTable extends BaseTest {

    /**
     * Модель объекта user & post
     */
    private usersModel user;
    private postsModel post;

    /**
     * Переменные для userID & postID
     */
    private Integer userId;
    private Integer postId;

    /**
     * Перед каждым тестом создаем random user в таблице users,
     * без него не создать post в таблице posts
     */
    @BeforeEach
    public void setUpClassForUserTable() {

        step("Создание рандомной модели user", () -> {
            user = getRandomUserModel();
        });

        step("Создаем запись в БД с данными user", () -> stepsForUsersTable.createUsers(user));

        step("Переводим созданных и имеющихся user в виде List," +
                "выполняем их перебор, пока nickname = nickname созданного пользователя, получаем id этого элемента", () -> {
            List<usersModel> users = stepsForUsersTable.getAllUsers();
            for (usersModel element : users) {
                if (element.getNickName().equals(user.getNickName())) {
                    userId = element.getId();
                }
            }
        });

        step("Создание рандомной модели post", () -> {
            post = getRandomPostModel();
        });

        step("Создаем запись в БД с данными post", () -> {
            stepsForPostsTable.createPost(post, userId);
            List<postsModel> posts = stepsForPostsTable.getAllPosts();
            for (postsModel element : posts) {
                if (element.getUserId() == userId) {
                    postId = element.getId();
                }
            }
        });
    }

    @Test
    @Feature("Создаем post в posts table, для этого нужен user в таблице users, создаем в Before Each")
    @DisplayName("Создание post в БД")
    public void creatingPostInDataBase() {

        //Выкл., так как post by user создается в Before Each
//        step("Создание рандомной модели post", () -> {
//            post = getRandomPostModel();
//        });
//        step("Создаем запись в БД с данными post", () -> stepsForPostsTable.createPost(post, userId));

        step("Проверяем, что post создался в posts table", () -> {
            boolean isCreated = false;
            List<postsModel> posts = stepsForPostsTable.getAllPosts();
            for (postsModel element : posts) {

                isCreated = true;
                if (element.getUserId() == user.getId()) {
                    postId = element.getId();
                    assertThat(element)
                            .withFailMessage("user_ID post & ID user don't match")
                            .isEqualTo(post);
                }
            }
            assertThat(isCreated)
                    .withFailMessage("Post by User wasn't create")
                    .isTrue();
        });

        step("Удаляем созданный post", () -> stepsForPostsTable.deletePost(postId));
    }

    @Test
    @Feature("Обновляем post в posts table")
    @DisplayName("Обновление post в БД")
    public void updatePostInDataBase() {

        step("Проверяем, что post создался в posts table", () -> {
            boolean isCreated = false;
            List<postsModel> posts = stepsForPostsTable.getAllPosts();
            for (postsModel element : posts) {
                postId = element.getId();
                isCreated = true;
                if (element.getUserId() == user.getId()) {
                    assertThat(element)
                            .withFailMessage("user_ID post & ID user don't match")
                            .isEqualTo(post);
                }
            }
            assertThat(isCreated)
                    .withFailMessage("Post wasn't create")
                    .isTrue();
        });

        step("Создание рандомной модели post", () -> {
            post = getRandomPostModel();
        });

        step("Обновляем post в БД с новыми данными post", () -> stepsForPostsTable.updatePost(post, userId, postId));

        step("Обновляем post в БД с новыми данными post", () -> {
            postsModel getPostFromTable = stepsForPostsTable.getPostById(postId);
            assertThat(getPostFromTable.getPostText())
                    .withFailMessage("Post text wasn't update")
                    .isEqualTo(post.getPostText());
            assertThat(getPostFromTable.getPostDate())
                    .withFailMessage("Post data wasn't update")
                    .isEqualTo(post.getPostDate());
            assertThat(getPostFromTable.getTitle())
                    .withFailMessage("Post title wasn't update")
                    .isEqualTo(post.getTitle());
        });

//        step("Удаляем созданный post", () -> stepsForPostsTable.deletePost(postId));
    }

    @Test
    @Feature("Удаляем создаваемый post из posts table")
    @DisplayName("Удаление post из БД")
    public void deletePostInDataBase() {

        step("Проверяем, что post создался в posts table", () -> {
            boolean isCreated = false;
            List<postsModel> posts = stepsForPostsTable.getAllPosts();
            for (postsModel element : posts) {
                postId = element.getId();
                isCreated = true;
                if (element.getUserId() == user.getId()) {
                    assertThat(element)
                            .withFailMessage("user_ID post & ID user don't match")
                            .isEqualTo(post);
                }
            }
            assertThat(isCreated)
                    .withFailMessage("Post wasn't create")
                    .isTrue();
        });

        step("Обновляем post в БД с новыми данными post", () -> stepsForPostsTable.deletePost(postId));

        step("Проверяем, что post создался в posts table", () -> {
            boolean isDeleted = true;
            List<postsModel> tableAfterDelete = stepsForPostsTable.getAllPosts();
            for (postsModel element : tableAfterDelete) {
                if (element.getUserId() == user.getId()) {
                    isDeleted = false;
                    break;
                }
            }
            assertThat(isDeleted)
                    .withFailMessage("Post wasn't delete")
                    .isTrue();
        });
    }

    /**
     * Очищаем все таблицы(ВЫКЛ по умолч.)
     */
    @AfterEach
    public void deleteAllFromTablePosts() {
        stepsForPostsTable.deleteAllPosts();
        stepsForUsersTable.deleteAllUsers();
    }
}