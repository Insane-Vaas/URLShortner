package com.example.URLShortner.Service;

import com.example.URLShortner.Domain.URLRequest;
import com.example.URLShortner.Domain.UrlResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GetURLService {
    UrlResponse execute(URLRequest urlLink);

    List<UrlResponse> getAllURLs();
}
