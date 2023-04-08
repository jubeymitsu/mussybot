package ru.stomprf.main.datacache;

import ru.stomprf.main.Track;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMusicCache {

    private static UserMusicCache instance;

    private UserMusicCache(){

    }
    private final Map<Long, List<Track>> usersMusic = new HashMap<>();

    public void setUserTrackList(Long id, List<Track> trackList){
        usersMusic.put(id, trackList);
    }

    public List<Track> getUserTracklist(Long id){
        return usersMusic.get(id);
    }

    public static UserMusicCache getInstance(){
        if (instance == null)
            instance = new UserMusicCache();
        return instance;
    }


}
