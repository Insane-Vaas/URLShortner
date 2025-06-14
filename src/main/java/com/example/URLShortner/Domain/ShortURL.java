package com.example.URLShortner.Domain;

public class ShortURL {
    private String shortURL;

    public ShortURL() {
    }

    public ShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }
}
