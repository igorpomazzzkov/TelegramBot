package com.Telegram.component;

public class MessageResponse {
    public static final String START_MESSAGE =
            "<b>" +
                    "Доброго времени суток." + new String(Character.toChars(0x270B)) +
                    "Вас приветствует бот тестовое задание.\nВеберите следующие действие:" +
                    "</b>";

    public static final String GET_NAME_CITY_MESSAGE = "Введите название города:";
    public static final String GET_DESCRIPTION_CITY_MESSAGE = "Введите описание города:";
    public static final String NO_DATABASE_CITY = "Город не найден";
    public static final String DATABASE_CITY = "Данный город уже существует в базе данных";
    public static final String EDIT_NAME_CITY = "Изменить имя %s на:";
    public static final String REMOVE_MESSAGE = String.format(MessageResponse.SUCCESS_MESSAGE, "удален");
    public static final String EDIT_MESSAGE = String.format(MessageResponse.SUCCESS_MESSAGE, "обновлен");
    public static final String ADD_MESSAGE = String.format(MessageResponse.SUCCESS_MESSAGE, "добавлен");
    public static final String SUCCESS_MESSAGE = "Город успешно %s";
    public static final String DEFAULT_MESSAGE = "Выберите следующие действие:";
}
