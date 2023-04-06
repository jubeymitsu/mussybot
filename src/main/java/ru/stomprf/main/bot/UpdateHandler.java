package ru.stomprf.main.bot;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.stomprf.main.datastate.UserStateCache;

public class UpdateHandler {

    private final UserStateCache userStateCache = UserStateCache.getInstance();

    public SendMessage handleUpdate(Update update){
        SendMessage replyMessage = null;

        Message message = update.message();
        if (message != null && message.text() != null) {
            System.out.printf("New message from User:%s, chatId: %s,  with text: %s%n",
                    message.from().username(), message.chat().id(), message.text());
            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message){
        String inputMsg = message.text();
        long userId = message.chat().id();
        BotState botState;
        SendMessage replyMessage;

        if ("/start".equals(inputMsg))
            botState = BotState.ASK_TITLE;
        else
            botState = userStateCache.getUserCurrentState(userId);

        userStateCache.setUserCurrentState(userId, botState);
        System.out.println("Checking botState: " + botState);
        replyMessage = new MessageProccessor().proccessInputMessage(message, botState);

        return replyMessage;
    }


}
