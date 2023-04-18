package ru.stomprf.main.scrap.m3u8;

public class SegmentDao {

    private boolean hasMethod;
    private String methodUri;

    public SegmentDao(boolean hasMethod, String methodUri) {
        this.hasMethod = hasMethod;
        this.methodUri = methodUri;
    }

    public boolean isHasMethod() {
        return hasMethod;
    }

    public void setHasMethod(boolean hasMethod) {
        this.hasMethod = hasMethod;
    }

    public String getMethodUri() {
        return methodUri;
    }

    public void setMethodUri(String methodUri) {
        this.methodUri = methodUri;
    }
}
