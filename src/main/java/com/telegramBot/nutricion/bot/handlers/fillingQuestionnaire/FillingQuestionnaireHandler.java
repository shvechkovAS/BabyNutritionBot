package com.telegramBot.nutricion.bot.handlers.fillingQuestionnaire;

import com.telegramBot.nutricion.bot.BotState;
import com.telegramBot.nutricion.bot.handlers.InputMessageHandler;
import com.telegramBot.nutricion.bot.cache.BotCache;
import com.telegramBot.nutricion.dictionaries.CodeBodyMassIndex;
import com.telegramBot.nutricion.model.NutritionQuestionnaire;
import com.telegramBot.nutricion.service.ButtonsService;
import com.telegramBot.nutricion.service.NutritionQuestionnaireService;
import com.telegramBot.nutricion.service.ReplyMessagesService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Log4j
public class FillingQuestionnaireHandler implements InputMessageHandler {

    @Autowired
    private BotCache botCache;

    @Autowired
    private ReplyMessagesService messagesService;

    @Autowired
    private NutritionQuestionnaireService nutritionQuestionnaireService;

    @Autowired
    private ButtonsService buttonsService;

    @Override
    public Object handle(Update update) {
        if (update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            long userId = callbackQuery.getFrom().getId();
            if(botCache.getUsersCurrentBotState(userId)
                    .equals(BotState.FILLING_QUESTIONNAIRE)){
                botCache.clearNutritionQuestionnaireData(userId);
                botCache.saveUsersCurrentBotState(userId, BotState.ASK_BABY_AGE);

            }
        } else if (update.hasMessage()){
            Message message = update.getMessage();
            long userId = message.getFrom().getId();
            if(botCache.getUsersCurrentBotState(userId)
                    .equals(BotState.FILLING_QUESTIONNAIRE)){
                botCache.clearNutritionQuestionnaireData(userId);
                botCache.saveUsersCurrentBotState(userId, BotState.ASK_BABY_AGE);
            }
        }

        return processUsersInput(update);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_QUESTIONNAIRE;
    }

    private List<SendMessage> processUsersInput(Update update){
        String usersAnswer;
        long userId;
        String chatId;
        List<SendMessage> messages = new ArrayList<>();
        if (update.hasCallbackQuery()){
            CallbackQuery callback = update.getCallbackQuery();
            usersAnswer = callback.getData();
            userId = callback.getFrom().getId();
            chatId = callback.getMessage().getChatId().toString();
        } else {
            Message inputMsg = update.getMessage();
            usersAnswer = inputMsg.getText();
            userId = inputMsg.getFrom().getId();
            chatId = inputMsg.getChatId().toString();
        }

        NutritionQuestionnaire questionnaireData = botCache.getNutritionQuestionnaireData(userId);
        BotState botState = botCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser;

        //если пользователь нажал "Показать пример меню", устанавливается статус "ASK_BABY_AGE"
        if (botState.equals(BotState.ASK_BABY_AGE)) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askBabyAge");
            replyToUser.setReplyMarkup(buttonsService.setMenuButtons());
            replyToUser.setParseMode(ParseMode.MARKDOWN);
            botCache.saveUsersCurrentBotState(userId, BotState.ASK_BIRTH_BABY_WEIGHT);
            messages.add(replyToUser);
        }

        //если пользователь выбрал возраст в месяцах, устанавливается статус "ASK_BIRTH_BABY_WEIGHT"
        if (botState.equals(BotState.ASK_BIRTH_BABY_WEIGHT)) {
            if (update.hasCallbackQuery()){
                usersAnswer = update.getCallbackQuery().getData();
            } else {
                botCache.saveUsersCurrentBotState(userId, BotState.ASK_BABY_AGE);
                messages.add(new SendMessage(chatId, "Выберите возраст с помощью предлагаемых кнопок"));
                return messages;
            }
            questionnaireData.setBabyAge(usersAnswer);
            botCache.saveUsersCurrentBotState(userId, BotState.ASK_BABY_HEIGHT);
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askBirthBabyWeight");
            replyToUser.setParseMode(ParseMode.MARKDOWN);
            messages.add(replyToUser);
        }

        if (botState.equals(BotState.ASK_BABY_HEIGHT)) {
            String birthBabyWeight = usersAnswer.replaceAll(",", ".").replaceAll("[^0-9.]", "");
            //Вес при рождении проверить на количество знаков перед запятой, если 3 и больше - выдавать текст "Укажите в килограммах" и оставлять тот же статус
            questionnaireData.setBirthBabyWeight(birthBabyWeight);
            botCache.saveUsersCurrentBotState(userId, BotState.ASK_BABY_WEIGHT);
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askBabyHeight");
            replyToUser.setParseMode(ParseMode.MARKDOWN);
            messages.add(replyToUser);
        }

        if (botState.equals(BotState.ASK_BABY_WEIGHT)) {
            questionnaireData.setBabyHeight(usersAnswer.replaceAll(",", ".").replaceAll("[^0-9.]", ""));
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askBabyWeight");
            replyToUser.setParseMode(ParseMode.MARKDOWN);
            messages.add(replyToUser);
            botCache.saveUsersCurrentBotState(userId, BotState.ASK_BABY_SEX);
        }

        if (botState.equals(BotState.ASK_BABY_SEX)) {
            String babyWeight = usersAnswer.replaceAll(",", ".").replaceAll("[^0-9.]", "");
            //Вес при рождении проверить на количество знаков перед запятой, если 3 и больше - выдавать текст "Укажите в килограммах" и оставлять тот же статус
            questionnaireData.setBabyWeight(babyWeight);
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askBabySex");
            replyToUser.setReplyMarkup(buttonsService.setSexButtons());
            replyToUser.setParseMode(ParseMode.MARKDOWN);

            messages.add(replyToUser);
            botCache.saveUsersCurrentBotState(userId, BotState.ASK_ILLNESS);
        }

        if (botState.equals(BotState.ASK_ILLNESS)) {
            if (update.hasCallbackQuery()){
                usersAnswer = update.getCallbackQuery().getData();
            } else {
                botCache.saveUsersCurrentBotState(userId, BotState.ASK_BABY_SEX);
                messages.add(new SendMessage(chatId, "Выберите пол с помощью предлагаемых кнопок."));
                return messages;
            }
            if (usersAnswer.equals("male")){
                questionnaireData.setMale(true);
            } else if (usersAnswer.equals("female")) {
                questionnaireData.setMale(false);
            }
            nutritionQuestionnaireService.countValues(questionnaireData, update.getCallbackQuery().getFrom().getUserName());

            replyToUser = messagesService.getReplyMessage(chatId, "reply.askIllness");
            replyToUser.setReplyMarkup(buttonsService.setIllnessButtons());
            replyToUser.setParseMode(ParseMode.MARKDOWN);

            messages.add(replyToUser);
            botCache.saveUsersCurrentBotState(userId, BotState.QUESTIONNAIRE_FILLED);
        }

        if (botState.equals(BotState.QUESTIONNAIRE_FILLED)) {
            if (update.hasCallbackQuery()){
                usersAnswer = update.getCallbackQuery().getData();
            }  else {
                botCache.saveUsersCurrentBotState(userId, BotState.ASK_ILLNESS);
                messages.add(new SendMessage(chatId, "Выберите болезни с помощью предлагаемых кнопок."));
                return messages;
            }
            questionnaireData.setIllness(Integer.parseInt(usersAnswer));
            nutritionQuestionnaireService.menuText(questionnaireData);
            botCache.saveUsersCurrentBotState(userId, BotState.SHOW_START_MENU);

            replyToUser = new SendMessage(chatId, questionnaireData.getDescription());

            if(Objects.isNull(questionnaireData.getMenu()) || questionnaireData.getMenu().isEmpty()){
                replyToUser.setReplyMarkup(buttonsService.setBacktrackButton());
                replyToUser.setParseMode(ParseMode.MARKDOWN);
            }
            messages.add(replyToUser);

            if(Objects.nonNull(questionnaireData.getMenu()) && !questionnaireData.getMenu().isEmpty()){
                replyToUser = new SendMessage(chatId, questionnaireData.getMenu());
                replyToUser.setReplyMarkup(buttonsService.setBacktrackButton());
                replyToUser.setParseMode(ParseMode.MARKDOWN);
                messages.add(replyToUser);
            }
        }
        botCache.saveNutritionQuestionnaireData(userId, questionnaireData);
        return messages;
    }
}
