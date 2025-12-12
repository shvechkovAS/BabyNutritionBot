package com.telegramBot.nutricion.bot.handlers.profit;

import com.telegramBot.nutricion.bot.BotState;
import com.telegramBot.nutricion.bot.cache.BotCache;
import com.telegramBot.nutricion.bot.handlers.InputMessageHandler;
import com.telegramBot.nutricion.service.ButtonsService;
import com.telegramBot.nutricion.service.ReplyMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Component
public class ProfitHandler  implements InputMessageHandler {

    @Autowired
    private BotCache botCache;

    @Autowired
    private ButtonsService buttonsService;

    @Autowired
    private ReplyMessagesService messagesService;

    @Override
    public Object handle(Update update) {
        return processUsersInput(update);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.PRODUCT_PROFIT;
    }

    private Object processUsersInput(Update update){
        String usersAnswer;
        long userId;
        String chatId;

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

        BotState botState = botCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;
        SendPhoto replyPhoto = null;

        if (botState.equals(BotState.PRODUCT_PROFIT)) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askProductProfit");
            replyToUser.setReplyMarkup(buttonsService.setProfitButtons());
            botCache.saveUsersCurrentBotState(userId, BotState.CHOOSE_PRODUCT);
        }

        if (botState.equals(BotState.CHOOSE_PRODUCT)) {
            switch (usersAnswer){
                case "vegetablePuree":
                    replyPhoto = new SendPhoto(
                            chatId,
                            new InputFile(
                                    this.getClass().getResourceAsStream("/pics/products/vegetablePuree.jpg"),
                                    "vegetablePuree"));
                    replyPhoto.setCaption("Овощное пюре");
                    break;
                case "fruitPuree":
                    replyPhoto = new SendPhoto(
                            chatId,
                            new InputFile(
                                    this.getClass().getResourceAsStream("/pics/products/fruitPuree.jpg"),
                                    "fruitPuree"));
                    replyPhoto.setCaption("Фруктовое пюре");
                    break;
                case "porridge":
                    replyPhoto = new SendPhoto(
                            chatId,
                            new InputFile(
                                    this.getClass().getResourceAsStream("/pics/products/porridge.jpg"),
                                    "porridge"));
                    replyPhoto.setCaption("Каша");
                    break;
                case "meatPuree":
                    replyPhoto = new SendPhoto(
                            chatId,
                            new InputFile(
                                    this.getClass().getResourceAsStream("/pics/products/meatPuree.jpg"),
                                    "meatPuree"));
                    replyPhoto.setCaption("Мясное пюре");
                    break;
                case "fish":
                    replyPhoto = new SendPhoto(
                            chatId,
                            new InputFile(
                                    this.getClass().getResourceAsStream("/pics/products/fish.jpg"),
                                    "fish"));
                    replyPhoto.setCaption("Рыба");
                    break;
                case "butter":
                    replyPhoto = new SendPhoto(
                            chatId,
                            new InputFile(
                                    this.getClass().getResourceAsStream("/pics/products/butter.jpg"),
                                    "butter"));
                    replyPhoto.setCaption("Масло");
                    break;
                case "eggYolk":
                    replyPhoto = new SendPhoto(
                            chatId,
                            new InputFile(
                                    this.getClass().getResourceAsStream("/pics/products/eggYolk.jpg"),
                                    "eggYolk"));
                    replyPhoto.setCaption("Яичный желток");
                    break;
                case "fermentedMilkProducts":
                    replyPhoto = new SendPhoto(
                            chatId,
                            new InputFile(
                                    this.getClass().getResourceAsStream("/pics/products/fermentedMilkProducts.jpg"),
                                    "fermentedMilkProducts"));
                    replyPhoto.setCaption("Кисломолочные продуты");
                    break;
                case "juices":
                    replyPhoto = new SendPhoto(
                            chatId,
                            new InputFile(
                                    this.getClass().getResourceAsStream("/pics/products/juices.jpg"),
                                    "juices"));
                    replyPhoto.setCaption("Соки");
                    break;
                case "start":
                    botCache.saveUsersCurrentBotState(userId, BotState.SHOW_START_MENU);
            }
            if(Objects.nonNull(replyPhoto)) {
                replyPhoto.setReplyMarkup(buttonsService.setProfitButtons());
            }
            return replyPhoto;
        }
        return replyToUser;
    }
}
