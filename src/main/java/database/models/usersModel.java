package database.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Модель сущности users БД
 */
@Data
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class usersModel {

    /**
     * Индентификатор
     */
    private final int id;

    /**
     * НикНэйм
     */
    private final String nickName;

    /**
     * Email
     */
    private final String email;

    /**
     * Пароль
     */
    private final String password;

    /**
     * День рождение
     */
    private final LocalDate birthDate;

    /**
     * Мужчина или нет
     */
    private final boolean isMale;
}