package com.example.URLShortner.Service.Implementation;

import ch.qos.logback.core.util.StringUtil;
import com.example.URLShortner.Domain.URLRequest;
import com.example.URLShortner.Domain.UrlResponse;
import com.example.URLShortner.Service.URLResponseRepo;
import com.example.URLShortner.Service.URLService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

@Service
public class URLServiceImpl implements URLService {

    @Autowired
    URLResponseRepo urlResponseRepo;

    Logger logger = LoggerFactory.getLogger(Controller.class);
    HashMap<String, String> urlList = new HashMap<>();

    @Override
    public String execute(URLRequest urlRequest) {
        logger.info(urlRequest.toString());
        String shortUrl = hashAlgorithm(urlRequest.getUrl());
        urlList.put(urlRequest.getUrl(),shortUrl);
        logger.info(shortUrl);
        shortUrl = "https://example.com/".concat(shortUrl);
        saveData(urlRequest.getUrl(),shortUrl);
        return shortUrl;
    }

    void saveData(String longUrl, String shortUrl){
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setLongURL(longUrl);
        urlResponse.setShortURL(shortUrl);
        urlResponseRepo.save(urlResponse);

    }

    public String hashAlgorithm(String urlLink){

        byte[] id = urlLink.getBytes();
        String encodedId = Base64.getEncoder().encodeToString(id);
        return encodedId.substring(0, 7);

    }


}
