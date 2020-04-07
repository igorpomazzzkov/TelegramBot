package com.Telegram.component;

import com.Telegram.entity.City;
import com.Telegram.service.CityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    private final CityService cityService;
    private Long chatId;
    private City city;
    private String previousMessage;

    @Value("${token}")
    private String telegramToken;

    @Value("${bot.username}")
    private String botUsername;

    public Bot(CityService cityService) {
        this.cityService = cityService;
        this.city = new City();
    }


    @Override
    public void onUpdateReceived(Update update) {
        String message = null;
        if (update.hasMessage()) {
            this.chatId = update.getMessage().getChatId();
            if (update.getMessage().getText() != null) {
                message = update.getMessage().getText();
            }
        }
        if(update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getData();
        }
        try {
            execute(this.setMessage(message));
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }

    private SendMessage setMessage(String message) {
        SendMessageBuilder sendMessageBuilder = new SendMessageBuilder(this.chatId);
        if (!message.substring(0, 1).equals("/")) {
            message = this.getOrderMessage(message);
            switch (this.previousMessage){
                case "/findcity": {
                    City city = cityService.findByCityName(message);
                    if (city == null) {
                        sendMessageBuilder.sendStartMessage(MessageResponse.NO_DATABASE_CITY);
                    } else {
                        this.city = city;
                        sendMessageBuilder.sendCityMessage(city);
                    }
                    break;
                }
                case "/editCity":
                case "/addcity":{
                    if(this.city.getName() == null){
                        City city = cityService.findByCityName(message);
                        if(city != null) {
                            sendMessageBuilder.sendDefaultMessage(MessageResponse.DATABASE_CITY + "\n" + MessageResponse.GET_NAME_CITY_MESSAGE);
                        } else {
                            this.city.setName(message);
                            sendMessageBuilder.sendDefaultMessage(MessageResponse.GET_DESCRIPTION_CITY_MESSAGE);
                        }
                    } else {
                        this.city.setDescription(message);
                        if(this.previousMessage.equals("/editCity")){
                            this.cityService.updateCity(city);
                            sendMessageBuilder.sendStartMessage(MessageResponse.EDIT_MESSAGE);
                        }
                        if(this.previousMessage.equals("/addcity")){
                            this.cityService.updateCity(city);
                            sendMessageBuilder.sendStartMessage(MessageResponse.ADD_MESSAGE);
                        }
                    }
                    break;
                }
                case "/removecity": {
                    City city = cityService.findByCityName(message);
                    if (city == null) {
                        sendMessageBuilder.sendStartMessage(MessageResponse.NO_DATABASE_CITY);
                    } else {
                        cityService.deleteCity(city);
                        sendMessageBuilder.sendStartMessage(MessageResponse.REMOVE_MESSAGE);
                    }
                    break;
                }
            }
        } else {
            switch (message) {
                case "/start": {
                    sendMessageBuilder.sendStartMessage(MessageResponse.START_MESSAGE);
                }
                case "/getallcities": {
                    List<City> cityList = cityService.findAllCities();
                    System.out.println(cityList.size());
                    if (cityList.size() != 0) {
                        sendMessageBuilder.sendAllCitiesMessage(cityService.findAllCities());
                    } else {
                        sendMessageBuilder.sendStartMessage("База данных городов пуста! \n" + MessageResponse.DEFAULT_MESSAGE);
                    }
                    break;
                }
                case "/findcity":
                case "/addcity":
                case "/removecity": {
                    sendMessageBuilder.sendDefaultMessage(MessageResponse.GET_NAME_CITY_MESSAGE);
                    break;
                }

                case "/deleteCty": {
                    cityService.deleteCity(this.city);
                    sendMessageBuilder.sendStartMessage(MessageResponse.REMOVE_MESSAGE);
                    break;
                }
                case "/editCity": {
                    sendMessageBuilder.sendDefaultMessage(String.format(MessageResponse.EDIT_NAME_CITY, this.city.getName()));
                    break;
                }
            }
            this.city.setName(null);
            this.city.setDescription(null);
            this.previousMessage = message;
        }
        return sendMessageBuilder.getMessage();
    }

    private String getOrderMessage(String message) {
        return (message.substring(0, 1).toUpperCase() + message.substring(1).toLowerCase()).trim();
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.telegramToken;
    }
}
