package com.example.URLShortner.Service;

import com.example.URLShortner.Domain.ShortURL;
import com.example.URLShortner.Domain.URLRequest;
import com.example.URLShortner.Domain.UrlResponse;

public interface URLService {

    ShortURL execute(URLRequest urlRequest);

}
