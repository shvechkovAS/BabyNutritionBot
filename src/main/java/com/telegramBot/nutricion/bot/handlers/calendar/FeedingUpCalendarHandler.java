package com.telegramBot.nutricion.bot.handlers.calendar;

import com.telegramBot.nutricion.bot.BotState;
import com.telegramBot.nutricion.bot.cache.BotCache;
import com.telegramBot.nutricion.bot.handlers.InputMessageHandler;
import com.telegramBot.nutricion.service.ButtonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeedingUpCalendarHandler implements InputMessageHandler {

    @Autowired
    private BotCache botCache;

    @Autowired
    private ButtonsService buttonsService;

    @Override
    public Object handle(Update update) {
        return processUsersInput(update);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FEEDING_UP_CALENDAR;
    }

    private List<SendPhoto> processUsersInput(Update update){
        long userId;
        String chatId;

        if (update.hasCallbackQuery()){
            CallbackQuery callback = update.getCallbackQuery();
            userId = callback.getFrom().getId();
            chatId = callback.getMessage().getChatId().toString();
        } else {
            Message inputMsg = update.getMessage();
            userId = inputMsg.getFrom().getId();
            chatId = inputMsg.getChatId().toString();
        }

        SendPhoto calendar = new SendPhoto(
                chatId,
                new InputFile(
                        this.getClass().getResourceAsStream("/pics/feeding/calendar.jpg"),
                        "calendar"));
        calendar.setCaption("Календарь введения прикормов");

        SendPhoto rules = new SendPhoto(
                chatId,
                new InputFile(
                        this.getClass().getResourceAsStream("/pics/feeding/rules.jpg"),
                        "rules"));
        rules.setCaption("Правила введения прикормов");

        SendPhoto dayVolume = new SendPhoto(
                chatId,
                new InputFile(
                        this.getClass().getResourceAsStream("/pics/feeding/dayVolume.jpg"),
                        "dayVolume"));
        dayVolume.setCaption("Суточный объём прикормов");

        dayVolume.setReplyMarkup(buttonsService.setBacktrackButton());

        List<SendPhoto> photos = new ArrayList<>();
        photos.add(calendar);
        photos.add(rules);
        photos.add(dayVolume);

        botCache.saveUsersCurrentBotState(userId, BotState.SHOW_START_MENU);

        return photos;
    }
}
