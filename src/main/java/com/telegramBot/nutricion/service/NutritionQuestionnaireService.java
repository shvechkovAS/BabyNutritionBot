package com.telegramBot.nutricion.service;

import com.telegramBot.nutricion.dictionaries.*;
import com.telegramBot.nutricion.model.NutritionQuestionnaire;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class NutritionQuestionnaireService {

    public void countValues(NutritionQuestionnaire nutritionQuestionnaire, String userName){
        double dmt;
        int babyAge = Integer.parseInt(nutritionQuestionnaire.getBabyAge());
        double birthWeight = Double.parseDouble(nutritionQuestionnaire.getBirthBabyWeight());
        double weight = Double.parseDouble(nutritionQuestionnaire.getBabyWeight());
        int height = Integer.parseInt(nutritionQuestionnaire.getBabyHeight());
        log.info("Пользователь: name = " + userName + ". Данные: Возраст = " + babyAge +
                "; Вес при рождении = "+ birthWeight + " кг; Текущий вес = " + weight +
                " кг; Рост = "+ height + " см;");

        if (babyAge <= 6) {
            dmt = birthWeight * 1000 + (800 * babyAge);
        } else {
            dmt = birthWeight * 1000 + 4800 + 400 * (babyAge - 6);
        }
        nutritionQuestionnaire.setDmt(dmt);

        double dayVolume = getDayVolume(babyAge, dmt);
        nutritionQuestionnaire.setDayVolume(dayVolume);

        double oneVolume = dayVolume / 5.00;
        nutritionQuestionnaire.setOneVolume(oneVolume);

        double heightInMeters = (double) height / 100;
        double massBodyIndex = weight / (heightInMeters * heightInMeters);
        log.info("Пользователь: name = " + userName + ". Индекс массы тела = " + massBodyIndex);
        generateCodeMassBodyIndex(nutritionQuestionnaire, massBodyIndex);
        generateTextMassBodyIndex(nutritionQuestionnaire);
    }

    private static double getDayVolume(int babyAge, double dmt) {
        double dayVolume = 0.0;
        if(babyAge >= 4 && babyAge<6){
            dayVolume = dmt / 7.0;
        } else if (babyAge >= 6 && babyAge<8){
            dayVolume = dmt / 8.0;
        } else if (babyAge >= 8 && babyAge<=12) {
            dayVolume = dmt / 9.0;
        }
        if(dayVolume > 1000){
            dayVolume = 1000;
        }
        return dayVolume;
    }

    private void generateCodeMassBodyIndex(NutritionQuestionnaire nutritionQuestionnaire, double massBodyIndex){
        nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_NORMAL);
        int babyAge = Integer.parseInt(nutritionQuestionnaire.getBabyAge());
        boolean sexMale = nutritionQuestionnaire.isMale();
        if (sexMale){
            if(((babyAge == 4 || babyAge == 11) && massBodyIndex < 14.5)
                    || ((babyAge == 5 || babyAge == 6 || babyAge == 8 || babyAge == 9) && massBodyIndex < 14.7)
                    || (babyAge == 7 && massBodyIndex < 14.8)
                    || (babyAge == 10 && massBodyIndex < 14.6)
                    || (babyAge == 12 && massBodyIndex < 14.4)) {
                 nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_VERY_THIN);
            } else if((babyAge == 4 && massBodyIndex >= 14.5 && massBodyIndex <= 15.7)
                    ||(babyAge == 5 && massBodyIndex >= 14.7 && massBodyIndex <= 15.8)
                    ||(babyAge == 6 && massBodyIndex >= 14.7 && massBodyIndex <= 15.9)
                    ||(babyAge == 7 && massBodyIndex >= 14.8 && massBodyIndex <= 15.9)
                    ||(babyAge == 8 && massBodyIndex >= 14.7 && massBodyIndex <= 15.8)
                    ||(babyAge == 9 && massBodyIndex >= 14.7 && massBodyIndex <= 15.7)
                    ||(babyAge == 10 && massBodyIndex >= 14.6 && massBodyIndex <= 15.6)
                    ||(babyAge == 11 && massBodyIndex >= 14.5 && massBodyIndex <= 15.5)
                    ||(babyAge == 12 && massBodyIndex >= 14.4 && massBodyIndex <= 15.4)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_THIN);
            } else if((babyAge == 4 && massBodyIndex >= 18.8 && massBodyIndex <= 20.3)
                    ||((babyAge == 5 || babyAge == 6 || babyAge == 7) && massBodyIndex >= 18.9 && massBodyIndex <= 20.5)
                    ||(babyAge == 8 && massBodyIndex >= 18.8 && massBodyIndex <= 20.4)
                    ||(babyAge == 9 && massBodyIndex >= 18.7 && massBodyIndex <= 20.3)
                    ||(babyAge == 10 && massBodyIndex >= 18.6 && massBodyIndex <= 20.1)
                    ||(babyAge == 11 && massBodyIndex >= 18.5 && massBodyIndex <= 20.0)
                    ||(babyAge == 12 && massBodyIndex >= 18.3 && massBodyIndex <= 19.8)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_THICK);
            } else if(((babyAge == 4 || babyAge == 9) && massBodyIndex >20.3)
                    || ((babyAge == 5 || babyAge == 6 || babyAge == 7) && massBodyIndex >20.5)
                    || (babyAge == 8 && massBodyIndex >20.4) || (babyAge == 10 && massBodyIndex >20.1)
                    || (babyAge == 11 && massBodyIndex >20.0) || (babyAge == 12 && massBodyIndex >19.8)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_VERY_THICK);
            }
        } else {
            if(((babyAge == 4 || babyAge == 11) && massBodyIndex < 13.9)
                    || ((babyAge == 5 || babyAge == 6 || babyAge == 8 || babyAge == 9) && massBodyIndex < 14.1)
                    || (babyAge == 7 && massBodyIndex < 14.2)
                    || (babyAge == 10 && massBodyIndex < 14.0)
                    || (babyAge == 12 && massBodyIndex < 13.8)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_VERY_THIN);
            } else if((babyAge == 4 && massBodyIndex >= 13.9 && massBodyIndex <= 15.1)
                    ||((babyAge == 5 || babyAge == 8) && massBodyIndex >= 14.1 && massBodyIndex <= 15.3)
                    ||(babyAge == 6 && massBodyIndex >= 14.1 && massBodyIndex <= 15.4)
                    ||(babyAge == 7 && massBodyIndex >= 14.2 && massBodyIndex <= 15.4)
                    ||(babyAge == 9 && massBodyIndex >= 14.1 && massBodyIndex <= 15.2)
                    ||(babyAge == 10 && massBodyIndex >= 14.0 && massBodyIndex <= 15.1)
                    ||(babyAge == 11 && massBodyIndex >= 13.9 && massBodyIndex <= 15.0)
                    ||(babyAge == 12 && massBodyIndex >= 13.8 && massBodyIndex <= 14.9)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_THIN);
            } else if((babyAge == 4 && massBodyIndex >= 18.4 && massBodyIndex <= 20.0)
                    ||((babyAge == 5 || babyAge == 8) && massBodyIndex >= 18.5 && massBodyIndex <= 20.2)
                    ||((babyAge == 6 || babyAge == 7) && massBodyIndex >= 18.6 && massBodyIndex <= 20.3)
                    ||(babyAge == 9 && massBodyIndex >= 18.4 && massBodyIndex <= 20.1)
                    ||(babyAge == 10 && massBodyIndex >= 18.3 && massBodyIndex <= 19.9)
                    ||(babyAge == 11 && massBodyIndex >= 18.1 && massBodyIndex <= 19.8)
                    ||(babyAge == 12 && massBodyIndex >= 18.0 && massBodyIndex <= 19.6)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_THICK);
            } else if((babyAge == 4 && massBodyIndex >20.0)
                    || ((babyAge == 5 || babyAge == 8) && massBodyIndex >20.2)
                    || (babyAge == 6 || babyAge == 7 && massBodyIndex >20.3)
                    || (babyAge == 9 && massBodyIndex >20.1)
                    || (babyAge == 10 && massBodyIndex >19.9)
                    || (babyAge == 11 && massBodyIndex >19.8)
                    || (babyAge == 12 && massBodyIndex >19.6)) {
                nutritionQuestionnaire.setCodeMassBodyIndex(CodeBodyMassIndex.CODE_VERY_THICK);
            }
        }
    }

    private void generateTextMassBodyIndex(NutritionQuestionnaire nutritionQuestionnaire){
        switch (nutritionQuestionnaire.getCodeMassBodyIndex()) {
            case CodeBodyMassIndex.CODE_NORMAL -> nutritionQuestionnaire.setTextMassBodyIndex(CodeBodyMassIndex.TEXT_NORMAL);
            case CodeBodyMassIndex.CODE_VERY_THIN -> nutritionQuestionnaire.setTextMassBodyIndex(CodeBodyMassIndex.TEXT_VERY_THIN);
            case CodeBodyMassIndex.CODE_THIN -> nutritionQuestionnaire.setTextMassBodyIndex(CodeBodyMassIndex.TEXT_THIN);
            case CodeBodyMassIndex.CODE_THICK -> nutritionQuestionnaire.setTextMassBodyIndex(CodeBodyMassIndex.TEXT_THICK);
            case CodeBodyMassIndex.CODE_VERY_THICK -> nutritionQuestionnaire.setTextMassBodyIndex(CodeBodyMassIndex.TEXT_VERY_THICK);
        }
    }

    public void menuText(NutritionQuestionnaire nutritionQuestionnaire){
        int illness =  nutritionQuestionnaire.getIllness();
        int codeIMT = nutritionQuestionnaire.getCodeMassBodyIndex();
        int babyAge = Integer.parseInt(nutritionQuestionnaire.getBabyAge());
        double oneVolume = nutritionQuestionnaire.getOneVolume();
        switch (codeIMT) {
            case CodeBodyMassIndex.CODE_NORMAL:
                if (illness == Illness.NO_ILLNESS) {
                    switch (babyAge) {
                        case 1, 2, 3, 4:
                            nutritionQuestionnaire.setDescription(TextNormal.DESCRIPTION_FOURTH);
                            break;
                        case 5:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormal.DESCRIPTION_FIFTH_HEALTHY,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormal.MENU_FIFTH_HEALTHY,
                                            oneVolume, oneVolume-11, oneVolume, oneVolume, oneVolume,
                                            oneVolume, oneVolume-13, oneVolume-77, oneVolume-77, oneVolume,
                                            oneVolume, oneVolume-81, oneVolume-77, oneVolume-77, oneVolume-77,
                                            oneVolume, oneVolume-101, oneVolume-77, oneVolume-100, oneVolume-77)
                            );
                            break;
                        case 6:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormal.DESCRIPTION_SIXTH_HEALTHY,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormal.MENU_SIXTH_HEALTHY,
                                            oneVolume, oneVolume-122, oneVolume-102, oneVolume-80, oneVolume,
                                            oneVolume, oneVolume-124, oneVolume-135, oneVolume-110, oneVolume)
                            );
                            break;
                        case 7:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormal.DESCRIPTION_SEVENTH_HEALTHY,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormal.MENU_SEVENTH_HEALTHY,
                                            oneVolume, oneVolume-134, oneVolume-140, oneVolume-90, oneVolume)
                            );
                            break;
                        case 8:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormal.DESCRIPTION_EIGHTH_HEALTHY,
                                    nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormal.MENU_EIGHTH_HEALTHY,
                                            oneVolume, oneVolume-90, oneVolume-140, oneVolume-140, oneVolume,
                                            oneVolume, oneVolume-140, oneVolume-140, oneVolume-90,
                                            oneVolume, oneVolume-135, oneVolume-136, oneVolume-90,
                                            oneVolume, oneVolume-140, oneVolume-146, oneVolume-95)
                            );
                            break;
                        case 9, 10, 11, 12:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormal.DESCRIPTION_NINTH_TO_TWELFTH_HEALTHY,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormal.MENU_NINTH_TO_TWELFTH_HEALTHY,
                                            oneVolume)
                            );
                            break;
                        default:
                            nutritionQuestionnaire.setDescription(null);
                            nutritionQuestionnaire.setMenu(null);
                            break;
                    }
                } else if (illness == Illness.CONSTIPATION) {
                    switch (babyAge) {
                        case 1, 2, 3, 4:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormalConstipation.DESCRIPTION_FOURTH,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormalConstipation.MENU_FOURTH,
                                            oneVolume, oneVolume-10, oneVolume/3, oneVolume, oneVolume/3,
                                            oneVolume, oneVolume, oneVolume/3,
                                            oneVolume, oneVolume-75, oneVolume, oneVolume, oneVolume)
                            );
                            break;
                        case 5:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormalConstipation.DESCRIPTION_FIFTH,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormalConstipation.MENU_FIFTH,
                                            oneVolume, oneVolume-11, oneVolume-76, oneVolume, oneVolume-76,
                                            oneVolume, oneVolume-81, oneVolume-76, oneVolume-76, oneVolume-76,
                                            oneVolume, oneVolume-100, oneVolume-76, oneVolume-100, oneVolume-76)
                            );
                            break;
                        case 6:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormalConstipation.DESCRIPTION_SIXTH,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormalConstipation.MENU_SIXTH,
                                            oneVolume, oneVolume-122, oneVolume-102, oneVolume-92, oneVolume,
                                            oneVolume, oneVolume-122, oneVolume-125, oneVolume-112, oneVolume)
                            );
                            break;
                        case 7:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormalConstipation.DESCRIPTION_SEVENTH,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormalConstipation.MENU_SEVENTH,
                                            oneVolume, oneVolume-140, oneVolume-123, oneVolume-82, oneVolume)
                            );
                            break;
                        case 8:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormalConstipation.DESCRIPTION_EIGHTH,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormalConstipation.MENU_EIGHTH,
                                            oneVolume, oneVolume-90, oneVolume-140, oneVolume-140, oneVolume,
                                            oneVolume-70, oneVolume-90, oneVolume-70,
                                            oneVolume, oneVolume-135, oneVolume-90, oneVolume-140,
                                            oneVolume, oneVolume-130, oneVolume-95, oneVolume-100)
                            );
                            break;
                        case 9, 10, 11, 12:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormalConstipation.DESCRIPTION_NINTH_TO_TWELFTH,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormalConstipation.MENU_NINTH_TO_TWELFTH,
                                            oneVolume, oneVolume-130, oneVolume-100)
                            );
                            break;
                        default:
                            nutritionQuestionnaire.setDescription(null);
                            nutritionQuestionnaire.setMenu(null);
                            break;
                    };
                } else if (illness == Illness.REGURGITATION) {
                    switch (babyAge) {
                        case 1, 2, 3, 4:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormalRegurgitation.DESCRIPTION_FOURTH,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormalRegurgitation.MENU_FOURTH,
                                            oneVolume, oneVolume - 7, oneVolume - 7, oneVolume - 7, oneVolume)
                            );
                            break;
                        case 5:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormalRegurgitation.DESCRIPTION_FIFTH,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormalRegurgitation.MENU_FIFTH,
                                            oneVolume-5, oneVolume-11, oneVolume-7, oneVolume, oneVolume-7,
                                            oneVolume, oneVolume-77, oneVolume-13, oneVolume-66, oneVolume-10,
                                            oneVolume-7, oneVolume-82, oneVolume-77, oneVolume-71, oneVolume-77,
                                            oneVolume-10, oneVolume-97, oneVolume-77, oneVolume-97, oneVolume-77)
                            );
                            break;
                        case 6:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormalRegurgitation.DESCRIPTION_SIXTH,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormalRegurgitation.MENU_SIXTH,
                                            oneVolume-5, oneVolume-112, oneVolume-102, oneVolume-80, oneVolume,
                                            oneVolume-5, oneVolume-119, oneVolume-135, oneVolume-100, oneVolume)
                            );
                            break;
                        case 7:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormalRegurgitation.DESCRIPTION_SEVENTH,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormalRegurgitation.MENU_SEVENTH,
                                            oneVolume-5, oneVolume-140, oneVolume-140, oneVolume-95, oneVolume)
                            );
                            break;
                        case 8:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormalRegurgitation.DESCRIPTION_EIGHTH,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormalRegurgitation.MENU_EIGHTH,
                                            oneVolume-5, oneVolume-90, oneVolume-130, oneVolume-140, oneVolume,
                                            oneVolume-5, oneVolume-125, oneVolume-161, oneVolume-90,
                                            oneVolume-5, oneVolume-130, oneVolume-136, oneVolume-90,
                                            oneVolume-5, oneVolume-125, oneVolume-146, oneVolume-95)
                            );
                            break;
                        case 9, 10, 11, 12:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextNormalRegurgitation.DESCRIPTION_NINTH_TO_TWELFTH,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextNormalRegurgitation.MENU_NINTH_TO_TWELFTH,
                                            oneVolume-7, oneVolume-130, oneVolume-146, oneVolume-100)
                            );
                            break;
                        default:
                            nutritionQuestionnaire.setDescription(null);
                            nutritionQuestionnaire.setMenu(null);
                            break;
                    };
                }
                break;
            case CodeBodyMassIndex.CODE_THIN:
                if (illness == Illness.NO_ILLNESS) {
                    switch (babyAge) {
                        case 1, 2, 3, 4:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextThin.DESCRIPTION_FOURTH_THIN,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextThin.MENU_FOURTH_THIN,
                                            oneVolume, oneVolume - 10, oneVolume, oneVolume, oneVolume,
                                            oneVolume, oneVolume - 35, oneVolume - 40, oneVolume, oneVolume)
                            );
                            break;
                        case 5:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextThin.DESCRIPTION_FIFTH_THIN,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextThin.MENU_FIFTH_THIN,
                                            oneVolume, oneVolume - 76, oneVolume, oneVolume - 76, oneVolume,
                                            oneVolume, oneVolume - 76, oneVolume - 5, oneVolume - 76, oneVolume,
                                            oneVolume, oneVolume - 76, oneVolume - 41, oneVolume - 76, oneVolume)
                            );
                            break;
                        case 6:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextThin.DESCRIPTION_SIXTH_THIN,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextThin.MENU_SIXTH_THIN,
                                            oneVolume, oneVolume - 81, oneVolume - 81, oneVolume - 76, oneVolume,
                                            oneVolume, oneVolume - 30, oneVolume - 76, oneVolume - 81, oneVolume - 96,
                                            oneVolume, oneVolume - 97, oneVolume - 84, oneVolume - 78, oneVolume - 70)
                            );
                            break;
                        case 7:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextThin.DESCRIPTION_SEVENTH_THIN,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextThin.MENU_SEVENTH_THIN,
                                            oneVolume, oneVolume - 102, oneVolume - 84, oneVolume - 78, oneVolume - 70)
                            );
                            break;
                        case 8:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextThin.DESCRIPTION_EIGHTH_THIN,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextThin.MENU_EIGHTH_THIN,
                                            oneVolume, oneVolume - 102, oneVolume - 84, oneVolume - 70,
                                            oneVolume, oneVolume - 140, oneVolume - 140, oneVolume - 90,
                                            oneVolume, oneVolume - 140, oneVolume - 136, oneVolume - 90)
                            );
                            break;
                        case 9, 10, 11, 12:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextThin.DESCRIPTION_NINTH_TO_TWELFTH_THIN,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextThin.MENU_NINTH_TO_TWELFTH_THIN,
                                            oneVolume, oneVolume - 140, oneVolume - 100)
                            );
                            break;
                        default:
                            nutritionQuestionnaire.setDescription(null);
                            nutritionQuestionnaire.setMenu(null);
                            break;
                    }
                } else if (illness == Illness.CONSTIPATION) {
                    nutritionQuestionnaire.setDescription(
                            String.format(TextNotNormalIllness.DESCRIPTION_NOT_NORMAL_CONSTIPATION)
                    );
                } else if (illness == Illness.REGURGITATION) {
                    nutritionQuestionnaire.setDescription(
                            String.format(TextNotNormalIllness.DESCRIPTION_NOT_NORMAL_REGURGITATION)
                    );
                }
                break;
            case CodeBodyMassIndex.CODE_THICK:
                if (illness == Illness.NO_ILLNESS) {
                    switch (babyAge) {
                        case 1, 2, 3, 4:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextThick.DESCRIPTION_FOURTH_THICK,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextThick.MENU_FOURTH_THICK,
                                            oneVolume, oneVolume - 5, oneVolume, oneVolume, oneVolume - 5,
                                            oneVolume, oneVolume - 75, oneVolume, oneVolume, oneVolume - 75)
                            );
                            break;
                        case 5:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextThick.DESCRIPTION_FIFTH_THICK,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextThick.MENU_FIFTH_THICK,
                                            oneVolume, oneVolume - 5, oneVolume - 76, oneVolume - 5, oneVolume - 75,
                                            oneVolume, oneVolume - 50, oneVolume - 78, oneVolume - 20, oneVolume - 75,
                                            oneVolume, oneVolume - 110, oneVolume - 77, oneVolume - 20, oneVolume - 75)
                            );
                            break;
                        case 6:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextThick.DESCRIPTION_SIXTH_THICK,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextThick.MENU_SIXTH_THICK,
                                            oneVolume, oneVolume - 130, oneVolume - 108, oneVolume - 50, oneVolume,
                                            oneVolume, oneVolume - 130, oneVolume - 103, oneVolume - 80, oneVolume)
                            );
                            break;
                        case 7:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextThick.DESCRIPTION_SEVENTH_THICK,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextThick.MENU_SEVENTH_THICK,
                                            oneVolume, oneVolume - 140, oneVolume - 123, oneVolume - 80, oneVolume)
                            );
                            break;
                        case 8:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextThick.DESCRIPTION_EIGHTH_THICK,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextThick.MENU_EIGHTH_THICK,
                                            oneVolume, oneVolume - 90, oneVolume - 137, oneVolume - 140, oneVolume,
                                            oneVolume, oneVolume - 140, oneVolume - 140, oneVolume - 90,
                                            oneVolume, oneVolume - 140, oneVolume - 136, oneVolume - 90,
                                            oneVolume, oneVolume - 140, oneVolume - 140, oneVolume - 90)
                            );
                            break;
                        case 9, 10, 11, 12:
                            nutritionQuestionnaire.setDescription(
                                    String.format(TextThick.DESCRIPTION_NINTH_TO_TWELFTH_THICK,
                                            nutritionQuestionnaire.getTextMassBodyIndex(), oneVolume)
                            );
                            nutritionQuestionnaire.setMenu(
                                    String.format(TextThick.MENU_NINTH_TO_TWELFTH_THICK,
                                            oneVolume, oneVolume - 140, oneVolume - 140, oneVolume - 110)
                            );
                            break;
                        default:
                            nutritionQuestionnaire.setDescription(null);
                            nutritionQuestionnaire.setMenu(null);
                            break;
                    }
                } else if (illness == Illness.CONSTIPATION) {
                    nutritionQuestionnaire.setDescription(
                            String.format(TextNotNormalIllness.DESCRIPTION_NOT_NORMAL_CONSTIPATION)
                    );
                } else if (illness == Illness.REGURGITATION) {
                    nutritionQuestionnaire.setDescription(
                            String.format(TextNotNormalIllness.DESCRIPTION_NOT_NORMAL_REGURGITATION)
                    );
                }
                break;
            case CodeBodyMassIndex.CODE_VERY_THIN:
            case CodeBodyMassIndex.CODE_VERY_THICK:
                if (illness == Illness.NO_ILLNESS) {
                    //Подставляем текст индекса массы тела в описание
                    nutritionQuestionnaire.setDescription(nutritionQuestionnaire.getTextMassBodyIndex());
                } else if (illness == Illness.CONSTIPATION) {
                    nutritionQuestionnaire.setDescription(
                            String.format(TextNotNormalIllness.DESCRIPTION_NOT_NORMAL_CONSTIPATION)
                    );
                } else if (illness == Illness.REGURGITATION) {
                    nutritionQuestionnaire.setDescription(
                            String.format(TextNotNormalIllness.DESCRIPTION_NOT_NORMAL_REGURGITATION)
                    );
                }
                break;
        }
    }
}
