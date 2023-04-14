package ru.stomprf.main.scrap;

import ru.stomprf.main.Track;

import java.util.List;

public interface Scraper {

    List<Track> scrapTracks(String searchText, int preferNumberOfTracks);

}
