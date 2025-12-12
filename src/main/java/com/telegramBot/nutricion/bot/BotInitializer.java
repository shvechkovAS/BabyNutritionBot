package com.telegramBot.nutricion.bot;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Log4j
public class BotInitializer {

    private final NutritionTelegramBot nutritionTelegramBot;

    @Autowired
    public BotInitializer(NutritionTelegramBot nutritionTelegramBot) {
        this.nutritionTelegramBot = nutritionTelegramBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try{
            telegramBotsApi.registerBot(nutritionTelegramBot);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
