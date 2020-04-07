package com.Telegram.component;

import com.Telegram.entity.City;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SendMessageBuilder {
    private static final String ALL_CITIES_MESSAGE = "<b>%d. %s</b> \n<i>%s</i> \n\n";
    private final SendMessage sendMessage;

    public SendMessageBuilder(Long chatID){
        this.sendMessage = new SendMessage()
                .setChatId(chatID)
                .setParseMode(ParseMode.HTML)
                .enableWebPagePreview();
    }

    public SendMessage getMessage(){
        return this.sendMessage;
    }

    public void sendStartMessage(String message){
        this.sendMessage.setText(message).setReplyMarkup(new InlineButtonBuilder().sendInlineKeyboardMessage());
    }

    public void sendDefaultMessage(String message){
        this.sendMessage.setText(message).setParseMode(ParseMode.HTML);
    }

    public void sendAllCitiesMessage(List<City> cities){
        StringBuilder message = new StringBuilder();
        for(City city: cities){
            message.append(String.format(ALL_CITIES_MESSAGE, cities.indexOf(city) + 1, city.getName(), city.getDescription()));
        }
        sendMessage.setText(message.toString()).setReplyMarkup(new InlineButtonBuilder().sendInlineKeyboardMessage());
    }

    public void sendCityMessage(City city){
        InlineKeyboardButton buttonRemove = new InlineButtonBuilder("", 0x274C, "/deleteCty")
                .getButton();
        InlineKeyboardButton buttonEdit = new InlineButtonBuilder("", 0x270F, "/editCity")
                .getButton();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        inlineKeyboardButtons.add(buttonEdit);
        inlineKeyboardButtons.add(buttonRemove);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(inlineKeyboardButtons);
        this.sendMessage
                .setText(String.format(ALL_CITIES_MESSAGE, 1, city.getName(), city.getDescription()))
                .setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(rowList));
    }
}
