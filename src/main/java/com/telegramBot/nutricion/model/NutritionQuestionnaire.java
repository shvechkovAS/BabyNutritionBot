package com.telegramBot.nutricion.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NutritionQuestionnaire {
    private String babyAge;//Возраст ребёнка
    private String birthBabyWeight;//Вес ребёнка при рождении (кг)
    private String babyWeight;//Текущий вес ребёнка (кг)
    private boolean male;//Пол мужской?
    private String babyHeight;//Рост ребёнка
    private int illness;//Заболевание
    private Double dmt;//Долженствующая масса тела
    private Double dayVolume;//Суточный объём
    private Double oneVolume;//Разовый объём
    private String menu;//Меню
    private String description;//Описание
    private String textMassBodyIndex;//Текст индекса массы тела
    private int codeMassBodyIndex;//Код индекса массы тела
}
