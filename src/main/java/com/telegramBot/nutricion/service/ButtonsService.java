package com.telegramBot.nutricion.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class ButtonsService {

    public InlineKeyboardMarkup setStartButtons() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonMenu = new InlineKeyboardButton("Показать пример меню");
        buttonMenu.setCallbackData("menu");
        InlineKeyboardButton buttonProfit = new InlineKeyboardButton("Узнать о пользе продуктов");
        buttonProfit.setCallbackData("profit");
        InlineKeyboardButton buttonCalendar = new InlineKeyboardButton("Календарь и правила введения прикормов");
        buttonCalendar.setCallbackData("calendar");

        List<InlineKeyboardButton> keyboardButtonsFirstRowList = new ArrayList<>();
        keyboardButtonsFirstRowList.add(buttonMenu);

        List<InlineKeyboardButton> keyboardButtonsSecondRowList = new ArrayList<>();
        keyboardButtonsSecondRowList.add(buttonCalendar);

        List<InlineKeyboardButton> keyboardButtonsThirdRowList = new ArrayList<>();
        keyboardButtonsThirdRowList.add(buttonProfit);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsFirstRowList);
        rowList.add(keyboardButtonsSecondRowList);
        rowList.add(keyboardButtonsThirdRowList);

        keyboardMarkup.setKeyboard(rowList);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup setMenuButtons(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton fourMonths = new InlineKeyboardButton("4");
        fourMonths.setCallbackData("4");
        InlineKeyboardButton fiveMonths = new InlineKeyboardButton("5");
        fiveMonths.setCallbackData("5");
        InlineKeyboardButton sixMonths = new InlineKeyboardButton("6");
        sixMonths.setCallbackData("6");
        InlineKeyboardButton sevenMonths = new InlineKeyboardButton("7");
        sevenMonths.setCallbackData("7");
        InlineKeyboardButton eightMonths = new InlineKeyboardButton("8");
        eightMonths.setCallbackData("8");
        InlineKeyboardButton nineMonths = new InlineKeyboardButton("9");
        nineMonths.setCallbackData("9");
        InlineKeyboardButton tenMonths = new InlineKeyboardButton("10");
        tenMonths.setCallbackData("10");
        InlineKeyboardButton elevenMonths = new InlineKeyboardButton("11");
        elevenMonths.setCallbackData("11");
        InlineKeyboardButton twelveMonths = new InlineKeyboardButton("12");
        twelveMonths.setCallbackData("12");

        List<InlineKeyboardButton> keyboardButtonsFirstRowList = new ArrayList<>();
        keyboardButtonsFirstRowList.add(fourMonths);
        keyboardButtonsFirstRowList.add(fiveMonths);
        keyboardButtonsFirstRowList.add(sixMonths);

        List<InlineKeyboardButton> keyboardButtonsSecondRowList = new ArrayList<>();
        keyboardButtonsSecondRowList.add(sevenMonths);
        keyboardButtonsSecondRowList.add(eightMonths);
        keyboardButtonsSecondRowList.add(nineMonths);

        List<InlineKeyboardButton> keyboardButtonsThirdRowList = new ArrayList<>();
        keyboardButtonsThirdRowList.add(tenMonths);
        keyboardButtonsThirdRowList.add(elevenMonths);
        keyboardButtonsThirdRowList.add(twelveMonths);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsFirstRowList);
        rowList.add(keyboardButtonsSecondRowList);
        rowList.add(keyboardButtonsThirdRowList);

        keyboardMarkup.setKeyboard(rowList);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup setProfitButtons(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton vegetablePuree = new InlineKeyboardButton("Овощное пюре");
        vegetablePuree.setCallbackData("vegetablePuree");
        InlineKeyboardButton fruitPuree = new InlineKeyboardButton("Фруктовое пюре");
        fruitPuree.setCallbackData("fruitPuree");
        InlineKeyboardButton porridge = new InlineKeyboardButton("Каша");
        porridge.setCallbackData("porridge");
        InlineKeyboardButton meatPuree = new InlineKeyboardButton("Мясное пюре");
        meatPuree.setCallbackData("meatPuree");
        InlineKeyboardButton fish = new InlineKeyboardButton("Рыба");
        fish.setCallbackData("fish");
        InlineKeyboardButton butter = new InlineKeyboardButton("Масло");
        butter.setCallbackData("butter");
        InlineKeyboardButton eggYolk = new InlineKeyboardButton("Яичный желток");
        eggYolk.setCallbackData("eggYolk");
        InlineKeyboardButton fermentedMilkProducts = new InlineKeyboardButton("Кисломолочные продуты");
        fermentedMilkProducts.setCallbackData("fermentedMilkProducts");
        InlineKeyboardButton juices = new InlineKeyboardButton("Соки");
        juices.setCallbackData("juices");
        InlineKeyboardButton start = new InlineKeyboardButton("В начало");
        start.setCallbackData("start");

        List<InlineKeyboardButton> keyboardButtonsFirstRowList = new ArrayList<>();
        keyboardButtonsFirstRowList.add(vegetablePuree);
        keyboardButtonsFirstRowList.add(fruitPuree);

        List<InlineKeyboardButton> keyboardButtonsSecondRowList = new ArrayList<>();
        keyboardButtonsSecondRowList.add(porridge);
        keyboardButtonsSecondRowList.add(fish);
        keyboardButtonsSecondRowList.add(butter);

        List<InlineKeyboardButton> keyboardButtonsThirdRowList = new ArrayList<>();
        keyboardButtonsThirdRowList.add(meatPuree);
        keyboardButtonsThirdRowList.add(eggYolk);

        List<InlineKeyboardButton> keyboardButtonsFourthRowList = new ArrayList<>();
        keyboardButtonsFourthRowList.add(fermentedMilkProducts);
        keyboardButtonsFourthRowList.add(juices);

        List<InlineKeyboardButton> keyboardButtonsFifthRowList = new ArrayList<>();
        keyboardButtonsFourthRowList.add(start);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsFirstRowList);
        rowList.add(keyboardButtonsSecondRowList);
        rowList.add(keyboardButtonsThirdRowList);
        rowList.add(keyboardButtonsFourthRowList);
        rowList.add(keyboardButtonsFifthRowList);

        keyboardMarkup.setKeyboard(rowList);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup setSexButtons(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton male = new InlineKeyboardButton("Мужской");
        male.setCallbackData("male");
        InlineKeyboardButton female = new InlineKeyboardButton("Женский");
        female.setCallbackData("female");

        List<InlineKeyboardButton> keyboardButtonsFirstRowList = new ArrayList<>();
        keyboardButtonsFirstRowList.add(male);
        keyboardButtonsFirstRowList.add(female);

        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(keyboardButtonsFirstRowList);

        keyboardMarkup.setKeyboard(row);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup setIllnessButtons(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton constipation = new InlineKeyboardButton("Склонность к запорам");
        constipation.setCallbackData("1");
        InlineKeyboardButton diarrhea = new InlineKeyboardButton("Срыгивания");
        diarrhea.setCallbackData("2");
        InlineKeyboardButton noIllness = new InlineKeyboardButton("Нет");
        noIllness.setCallbackData("0");

        List<InlineKeyboardButton> keyboardButtonsFirstRowList = new ArrayList<>();
        keyboardButtonsFirstRowList.add(constipation);
        List<InlineKeyboardButton> keyboardButtonsSecondRowList = new ArrayList<>();
        keyboardButtonsSecondRowList.add(diarrhea);
        List<InlineKeyboardButton> keyboardButtonsThirdRowList = new ArrayList<>();
        keyboardButtonsThirdRowList.add(noIllness);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsFirstRowList);
        rowList.add(keyboardButtonsSecondRowList);
        rowList.add(keyboardButtonsThirdRowList);

        keyboardMarkup.setKeyboard(rowList);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup setBacktrackButton(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton start = new InlineKeyboardButton("В начало");
        start.setCallbackData("start");


        List<InlineKeyboardButton> keyboardButtonsFirstRowList = new ArrayList<>();
        keyboardButtonsFirstRowList.add(start);

        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(keyboardButtonsFirstRowList);

        keyboardMarkup.setKeyboard(row);
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup setCountButtons(){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton count = new InlineKeyboardButton("Рассчитать");
        count.setCallbackData("count");


        List<InlineKeyboardButton> keyboardButtonsFirstRowList = new ArrayList<>();
        keyboardButtonsFirstRowList.add(count);

        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(keyboardButtonsFirstRowList);

        keyboardMarkup.setKeyboard(row);
        return keyboardMarkup;
    }
}
