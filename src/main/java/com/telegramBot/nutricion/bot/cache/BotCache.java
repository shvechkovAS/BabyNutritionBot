package com.telegramBot.nutricion.bot.cache;

import com.telegramBot.nutricion.bot.BotState;
import com.telegramBot.nutricion.model.NutritionQuestionnaire;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Log4j
public class BotCache {
    private final Map<Long, BotState> botStates = new HashMap<>();
    private final Map<Long, NutritionQuestionnaire> questionnaireData = new HashMap<>();

    public BotState getUsersCurrentBotState(long userId){
        BotState botState = botStates.get(userId);
        if(botState == null){
            botState = BotState.SHOW_START_MENU;
        }
        return botState;
    }

    public void saveUsersCurrentBotState(long userId, BotState botState){
        botStates.put(userId, botState);
    }

    public NutritionQuestionnaire getNutritionQuestionnaireData(long userId){
        NutritionQuestionnaire nutritionQuestionnaire = questionnaireData.get(userId);
        if(nutritionQuestionnaire == null){
            nutritionQuestionnaire = new NutritionQuestionnaire();
        }
        return nutritionQuestionnaire;
    }

    public void saveNutritionQuestionnaireData(long userId, NutritionQuestionnaire nutritionQuestionnaire){
        questionnaireData.put(userId, nutritionQuestionnaire);
    }

    public void clearNutritionQuestionnaireData(long userId){
        NutritionQuestionnaire nutritionQuestionnaire = questionnaireData.get(userId);
        if(nutritionQuestionnaire != null){
            nutritionQuestionnaire.setBabyAge(null);
            nutritionQuestionnaire.setBirthBabyWeight(null);
            nutritionQuestionnaire.setBabyWeight(null);
            nutritionQuestionnaire.setMale(false);
            nutritionQuestionnaire.setBabyHeight(null);
            nutritionQuestionnaire.setIllness(0);
            nutritionQuestionnaire.setDmt(null);
            nutritionQuestionnaire.setDayVolume(null);
            nutritionQuestionnaire.setOneVolume(null);
            nutritionQuestionnaire.setMenu(null);
            nutritionQuestionnaire.setDescription(null);
            nutritionQuestionnaire.setTextMassBodyIndex(null);
            nutritionQuestionnaire.setCodeMassBodyIndex(0);
        }
    }
}