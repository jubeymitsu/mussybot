package ru.stomprf.main;

import java.nio.file.Path;

public class Track {

    public Track(String title, Path location) {
        this.title = title;
        this.location = location;
    }

    private String title;
    private Path location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Path getLocation() {
        return location;
    }

    public void setLocation(Path location) {
        this.location = location;
    }
}
