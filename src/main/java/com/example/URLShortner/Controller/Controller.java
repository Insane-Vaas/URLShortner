package com.example.URLShortner.Controller;


import com.example.URLShortner.Domain.ShortURL;
import com.example.URLShortner.Domain.URLRequest;
import com.example.URLShortner.Domain.UrlResponse;
import com.example.URLShortner.Service.GetURLService;
import com.example.URLShortner.Service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    URLService urlService;
    @Autowired
    GetURLService getURLService;

    @PostMapping("/urlshortner")
    public ShortURL urlShortner(@RequestBody URLRequest urlRequest){
        return urlService.execute(urlRequest);
    }


    @PostMapping("/geturlshortner")
    public UrlResponse getUrlShortner(@RequestBody URLRequest urlRequest){
        return getURLService.execute(urlRequest);
    }

    @GetMapping("/getallurls")
    public List<UrlResponse> getAllUrls(){
        return getURLService.getAllURLs();
    }

}
