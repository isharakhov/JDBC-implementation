package utils;

import com.github.javafaker.Faker;

import java.time.LocalDate;

public class RandomDataHelper {

    private static final Faker faker = new Faker();

    /**
     * Метода генерации случайного nickname
     *
     * @return случайную nickname
     */
    public static String getRandomNickName() {
        return faker.name().username();
    }

    private static final String GET_EMAIL_REGEXP = "[a-z]{10}\\@[a-z]{5}\\.[a-z]{2}";

    /**
     * Метода генерации случайного email
     *
     * @return случайный email
     */
    public static String getRandomEmail() {
        return faker.regexify(GET_EMAIL_REGEXP);
    }

    /**
     * Метода генерации случайного имени
     *
     * @return случайное имя
     */
    public static String getRandomPassWord() {
        return faker.internet().password(3, 12,
                true, true, true);
    }

    /**
     * Метода генерации случайной даты рождения
     *
     * @return случайная дата рождения
     */
    public static LocalDate getRandomBirthDate() {
        int year = faker.number().numberBetween(1980, 2005);
        int month = faker.number().numberBetween(1, 12);
        int day = faker.number().numberBetween(1, 28);
        return LocalDate.of(year, month, day);
    }

    /**
     * Метода генерации случайного пола
     *
     * @return случайный пол
     */
    public static boolean getMaleOrNot() {
        return faker.random().nextBoolean();
    }

    /**
     * Метода генерации случайного title
     *
     * @return случайный title
     */
    public static String getRandomTitle() {
        return faker.name().title();
    }

    /**
     * Метода генерации случайного text
     *
     * @return случайный text
     */
    public static String getRandomText() {
        return faker.lorem().sentence();
    }

    /**
     * Метода генерации случайной post даты
     *
     * @return случайная post дата
     */
    public static LocalDate getRandomPostData() {
        int year = faker.number().numberBetween(2010, 2022);
        int month = faker.number().numberBetween(1, 12);
        int day = faker.number().numberBetween(1, 28);
        return LocalDate.of(year, month, day);
    }
}
