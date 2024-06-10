package com.example.URLShortner.Service;

import com.example.URLShortner.Domain.URLRequest;
import com.example.URLShortner.Domain.UrlResponse;
import org.springframework.stereotype.Service;

public interface GetURLService {
    UrlResponse execute(URLRequest urlLink);
}
