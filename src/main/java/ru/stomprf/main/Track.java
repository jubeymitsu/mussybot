package ru.stomprf.main;

import java.nio.file.Path;

public class Track {

    public Track(String title, String downloadLink, Path location) {
        this.title = title;
        this.downloadLink = downloadLink;
        this.location = location;
    }

    public Track(){

    }

    private String title;
    private String downloadLink;
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

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    @Override
    public String toString() {
        return "Track{" +
                "title='" + title + '\'' +
                ", downloadLink='" + downloadLink + '\'' +
                ", location=" + location +
                '}';
    }
}
