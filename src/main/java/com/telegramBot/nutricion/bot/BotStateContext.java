package com.telegramBot.nutricion.bot;

import com.telegramBot.nutricion.bot.handlers.InputMessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public Object processInputMessage(BotState currentState, Update update){
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(update);
    }

    private InputMessageHandler findMessageHandler(BotState currentState){
        if(isNutritionMenuState(currentState)){
            return messageHandlers.get(BotState.FILLING_QUESTIONNAIRE);
        }
        if(isProductProfitState(currentState)){
            return messageHandlers.get(BotState.PRODUCT_PROFIT);
        }
        return messageHandlers.get(currentState);
    }

    private boolean isNutritionMenuState(BotState currentState){
        return switch (currentState) {
            case ASK_BABY_AGE,
                    ASK_BABY_HEIGHT,
                    ASK_BABY_SEX,
                    ASK_ILLNESS,
                    ASK_BABY_WEIGHT,
                    ASK_BIRTH_BABY_WEIGHT,
                    FILLING_QUESTIONNAIRE,
                    QUESTIONNAIRE_FILLED -> true;
            default -> false;
        };
    }

    private boolean isProductProfitState(BotState currentState){
        return switch (currentState) {
            case PRODUCT_PROFIT,
                    CHOOSE_PRODUCT -> true;
            default -> false;
        };
    }
}
