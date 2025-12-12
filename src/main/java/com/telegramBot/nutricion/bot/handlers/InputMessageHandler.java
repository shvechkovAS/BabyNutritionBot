package com.telegramBot.nutricion.bot.handlers;

import com.telegramBot.nutricion.bot.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Обработчик сообщений
 */
public interface InputMessageHandler {
    Object handle(Update update);

    BotState getHandlerName();
}
