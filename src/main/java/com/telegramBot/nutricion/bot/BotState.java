package com.telegramBot.nutricion.bot;

import lombok.Getter;

@Getter
public enum BotState {
    SHOW_START_MENU("Показать стартовое меню"),
    FILLING_QUESTIONNAIRE("Опросник по меню заполняется"),
    ASK_BABY_AGE("Вопрос про возраст"),
    ASK_BIRTH_BABY_WEIGHT("Вопрос про вес при рождении"),
    ASK_ILLNESS("Вопрос про болезни"),
    ASK_BABY_WEIGHT("Вопрос про вес"),
    ASK_BABY_SEX("Вопрос про пол"),
    ASK_BABY_HEIGHT("Вопрос про рост"),
    QUESTIONNAIRE_FILLED("Опросник по меню заполнен"),
    FEEDING_UP_CALENDAR("Календать прикормов"),
    PRODUCT_PROFIT("Узнать больше о пользе продуктов"),
    CHOOSE_PRODUCT("Выбрать продукт");

    private final String title;

    BotState(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return "BotState{" +
                "title='" + title + '\'' +
                '}';
    }
}
