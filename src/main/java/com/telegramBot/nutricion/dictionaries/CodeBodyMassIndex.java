package com.telegramBot.nutricion.dictionaries;

public interface CodeBodyMassIndex {

    int CODE_NORMAL = 0;
    int CODE_VERY_THIN = 1;
    int CODE_THIN = 2;
    int CODE_THICK = 3;
    int CODE_VERY_THICK = 4;

    String TEXT_NORMAL = "Масса тела вашего ребёнка соответствует норме.";
    String TEXT_VERY_THIN = "\uD83C\uDD98 У вашего ребёнка дефицит массы тела.\n\n ❗Для предотвращения негативных последствий, необходима коррекция рациона только совместно с врачом-педиатром. *Проконсультируйтесь со специалистом.*";
    String TEXT_THIN = "У вашего ребёнка пониженное питание.";
    String TEXT_THICK = "У вашего ребёнка избыточная масса тела.";
    String TEXT_VERY_THICK = "\uD83C\uDD98 У вашего ребёнка ожирение.\n\n ❗Для предотвращения негативных последствий, необходима коррекция рациона только совместно с врачом-педиатром. *Проконсультируйтесь со специалистом.*";
}
