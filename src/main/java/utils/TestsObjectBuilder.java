package utils;

import database.models.postsModel;
import database.models.usersModel;
import lombok.experimental.UtilityClass;

import static utils.RandomDataHelper.*;

/**
 * Класс с методами создания тестовых данных
 */
@UtilityClass
public class TestsObjectBuilder {

    private usersModel user;
    /**
     * Метод генерации случайной users модели
     *
     * @return случайная user модель
     */
    public static usersModel getRandomUserModel() {
        return usersModel.builder()
                .nickName(getRandomNickName())
                .email(getRandomEmail())
                .password(getRandomPassWord())
                .birthDate(getRandomBirthDate())
                .isMale(getMaleOrNot())
                .build();
    }

    /**
     * Метод генерации случайной post модели
     *
     * @return случайная post модель
     */
    public static postsModel getRandomPostModel() {
        return postsModel.builder()
                .title(getRandomTitle())
                .postText(getRandomText())
                .postDate(getRandomPostData())
                .build();
    }
}