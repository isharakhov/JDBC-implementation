package database.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Модель сущности post БД
 */
@Data
@Builder
public class postsModel {

    /**
     * Индентификатор
     */
    private final int id;

    /**
     * user id
     */
    private final int userId;

    /**
     * Заголовок
     */
    private final String title;

    /**
     * Содержание post
     */
    private final String postText;

    /**
     * Дата публикации
     */
    private final LocalDate postDate;
}