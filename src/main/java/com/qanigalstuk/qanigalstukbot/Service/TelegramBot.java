package com.qanigalstuk.qanigalstukbot.Service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.qanigalstuk.qanigalstukbot.config.BotConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

final BotConfig config;

public TelegramBot(BotConfig config){
this.config = config;
}

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default: senMessage(chatId, "Uzur, command was not recognized");
            }
        }
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    private void startCommandReceived(long chatId, String name) {
            String answer = "Assalom aleykum, " + name +", qani galstuk?";
            log.info("Replied to user :" + name);
            senMessage(chatId, answer);
    }

    private void senMessage(long chatId, String texToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(texToSend);

        try{
            execute(message);
        }
        catch(TelegramApiException e){
            log.error("Error occured :" + e.getMessage());
        }
    }
    
}
