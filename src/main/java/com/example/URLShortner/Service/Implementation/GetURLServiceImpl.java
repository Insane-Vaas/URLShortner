package com.example.URLShortner.Service.Implementation;

import com.example.URLShortner.Domain.URLRequest;
import com.example.URLShortner.Domain.UrlResponse;
import com.example.URLShortner.Service.GetURLService;
import com.example.URLShortner.Service.URLResponseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GetURLServiceImpl implements GetURLService {


    @Autowired
    URLResponseRepo urlResponseRepo;

    @Override
    public UrlResponse execute(URLRequest urlLink) {
        UrlResponse urlResponse = new UrlResponse();

        urlResponse = urlResponseRepo.findByShortUrl(urlLink.getUrl());

        return urlResponse;

    }

    @Override
    public List<UrlResponse> getAllURLs() {
        return urlResponseRepo.getAllURLs();
    }
}
