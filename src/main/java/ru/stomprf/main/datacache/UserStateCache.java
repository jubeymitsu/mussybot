package ru.stomprf.main.datacache;

import ru.stomprf.main.bot.BotState;

import java.util.HashMap;
import java.util.Map;

public class UserStateCache {

    private static UserStateCache instance;

    private UserStateCache(){

    }

    Map<Long, BotState> usersStates =  new HashMap<>();

    public void setUserCurrentState(Long userId, BotState state){
        usersStates.put(userId, state);
    }

    public BotState getUserCurrentState(Long userId) {
        BotState botState = usersStates.get(userId);
        if (botState == null)
            botState = BotState.ASK_TITLE;
        else
            botState = BotState.SHOW_TRACKLIST;
        return botState;
    }

    public static UserStateCache getInstance(){
        if (instance == null)
            instance = new UserStateCache();
        return instance;
    }


}
