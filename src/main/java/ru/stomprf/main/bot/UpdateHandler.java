package ru.stomprf.main.bot;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class UpdateHandler {

    public SendMessage handleUpdate(Update update){
        SendMessage replyMessage = null;

        Message message = update.message();
        if (message != null && message.text() != null) {
            System.out.println(String.format("New message from User:%s, chatId: %s,  with text: %s",
                    message.from().username(), message.chat().id(), message.text()));
            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message){
        String inputMsg = message.text();
        long userId = message.chat().id();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.ASK_TITLE;
                break;
            case "Получить предсказание":
                botState = BotState.FILLING_PROFILE;
                break;
            case "Помощь":
                botState = BotState.SHOW_HELP_MENU;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }
    }

}
