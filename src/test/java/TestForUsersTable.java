import database.models.usersModel;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.TestsObjectBuilder.getRandomUserModel;

/**
 * Класс для Users Table
 */
public class TestForUsersTable extends BaseTest {

    /**
     * Модель объекта user
     */
    private usersModel user;
    private Integer id;

    /**
     * Перед каждым тестом создаем random user в таблице,
     * представляем строки в таблице в виде List, и получаем id, созданной строки
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
                    id = element.getId();
                }
            }
        });
    }

    @Test
    @Feature("Создание сущности user в таблице users")
    @DisplayName("Создание user в базе данных")
    public void creatingUserInDataBase() {

        //выключено, т.к. user создается в Before Each
//        user = getRandomUserModel();
//        stepsForUsersTable.createUsers(user);

        step("Получаем id user, проверяем результаты, а после удаляем созданный user по id", () -> {
            boolean isCreated = false;
            List<usersModel> users = stepsForUsersTable.getAllUsers();
            for (usersModel element : users) {
                id = element.getId();
                isCreated = true;
                if (element.getNickName().equals(user.getNickName())) {
                    assertThat(element)
                            .isEqualTo(user);
                }
            }
            assertThat(isCreated)
                    .withFailMessage("User wasn't create")
                    .isTrue();

            stepsForUsersTable.deleteUser(id);
        });
    }

    @Test
    @Feature("Обновление данных у созданного раннее user")
    @DisplayName("Обновление user в БД")
    public void updateUserInDataBase() {

        step("Создание рандомной модели user", () -> {
            user = getRandomUserModel();
        });

        step("Обновляем user,проверяем что он обновился", () -> {
            stepsForUsersTable.updateUser(user, id);
            usersModel getUserFromTable = stepsForUsersTable.getUserById(id);
            assertThat(getUserFromTable)
                    .withFailMessage("User wasn't update")
                    .isEqualTo(user);
        });

        stepsForUsersTable.deleteUser(id);
    }

    @Test
    @Feature("Удаление сущности user из users table")
    @DisplayName("Удаление user из БД")
    public void deleteUserInDataBase() {

        step("Удаляем user, созданный в Before Each по id", () -> stepsForUsersTable.deleteUser(id));

        step("Проверяем, что user действительно удалился", () -> {
            boolean isDeleted = true;
            List<usersModel> tableAfterDelete = stepsForUsersTable.getAllUsers();
            for (usersModel element : tableAfterDelete) {
                if (element.getNickName().equals(user.getNickName())) {
                    isDeleted = false;
                    break;
                }
            }
            assertThat(isDeleted)
                    .withFailMessage("User wasn't delete")
                    .isTrue();
        });
    }

    /**
     * Очищает users table полностью, выключено по умолч.
     */
    @AfterEach
    public void deleteAllFromTableUsers(){
        stepsForUsersTable.deleteAllUsers();
    }
}