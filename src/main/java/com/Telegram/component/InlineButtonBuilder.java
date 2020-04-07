package com.Telegram.component;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineButtonBuilder {
    private final InlineKeyboardButton inlineKeyboardButton;

    public InlineButtonBuilder() {
        this.inlineKeyboardButton = new InlineKeyboardButton();
    }

    public InlineButtonBuilder(String text, String callbackData) {
        this();
        setText(text);
        setCallbackData(callbackData);
    }

    public InlineButtonBuilder(String text, Integer emoji, String callbackData) {
        this();
        setText(text, emoji);
        setCallbackData(callbackData);
    }

    public InlineButtonBuilder setText(String text){
        inlineKeyboardButton.setText(text);
        return this;
    }

    public InlineButtonBuilder setText(String text, Integer emoji){
        inlineKeyboardButton.setText(text + " " + new String(Character.toChars(emoji)));
        return this;
    }

    public InlineButtonBuilder setCallbackData(String callbackData){
        inlineKeyboardButton.setCallbackData(callbackData);
        return this;
    }

    public InlineKeyboardButton getButton(){
        return inlineKeyboardButton;
    }

    public InlineKeyboardMarkup sendInlineKeyboardMessage() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineButtonBuilder allCitiesButton = new InlineButtonBuilder("Города", "/getallcities");
        InlineButtonBuilder searchCity = new InlineButtonBuilder("Поиск города", "/findcity");
        InlineButtonBuilder addCity = new InlineButtonBuilder("Добавить город", "/addcity");


        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<InlineKeyboardButton>();
        keyboardButtonsRow.add(allCitiesButton.getButton());
        keyboardButtonsRow.add(searchCity.getButton());

        List<InlineKeyboardButton> keyboardButtonsColumn = new ArrayList<>();
        keyboardButtonsColumn.add(addCity.getButton());

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        rowList.add(keyboardButtonsColumn);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
