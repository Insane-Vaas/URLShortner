package com.example.URLShortner.Domain;

public class URLRequest {
    private String url;


    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "URLRequest{" +
                "url='" + url + '\'' +
                '}';
    }
}
