package com.example.URLShortner.Service.Implementation;

import com.example.URLShortner.Controller.ExceptionHandlerController;
import com.example.URLShortner.Controller.ExceptionHandlerController;
import com.example.URLShortner.Domain.URLRequest;
import com.example.URLShortner.Domain.UrlResponse;
import com.example.URLShortner.Exception.APIException;
import com.example.URLShortner.Service.URLResponseRepo;
import com.example.URLShortner.Service.URLService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;


@Service
public class URLServiceImpl implements URLService {

    @Autowired
    URLResponseRepo urlResponseRepo;

    @Autowired
    ExceptionHandlerController exceptionHandler;

    Logger logger = LoggerFactory.getLogger(Controller.class);

    @Override
    public String execute(URLRequest urlRequest) {
        String longURL = urlRequest.getUrl();
        logger.info(urlRequest.toString());

        if(StringUtils.isEmpty(longURL)){
            throw new APIException("Url provided is Empty. Please provide Legit URL");
        }

        if(checkIfDataExists(longURL) == true){
            throw new APIException("Data already present in Database");
        }

        String shortUrl = "";
        try {

            shortUrl = toHexStr(hashAlgorithm(longURL));

        } catch (Exception e) {
            logger.info(String.valueOf(e));
        }

        logger.info(shortUrl);
        shortUrl = "https://example.com/".concat(shortUrl);
        saveData(longURL, shortUrl);
        return shortUrl;
    }

    void saveData(String longUrl, String shortUrl) {
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setLongURL(longUrl);
        urlResponse.setShortURL(shortUrl);
        urlResponseRepo.save(urlResponse);

    }

    public byte[] hashAlgorithm(String urlLink) throws NoSuchAlgorithmException {

        MessageDigest msgDgst = MessageDigest.getInstance("SHA-256");
        logger.info(Arrays.toString(msgDgst.digest(urlLink.getBytes(StandardCharsets.UTF_8))));
        return msgDgst.digest(urlLink.getBytes(StandardCharsets.UTF_8));

    }


    public static String toHexStr(byte[] hash) {

        BigInteger no = new BigInteger(1, hash);
        StringBuilder hexStr = new StringBuilder(no.toString(16));
        while (hexStr.length() < 32) {
            hexStr.insert(0, '0');
        }
        return hexStr.substring(0,7);
    }


    public boolean checkIfDataExists(String longUrl){

        UrlResponse urlResponse = urlResponseRepo.findByLongUrl(longUrl);
        if (Objects.isNull(urlResponse))return false;

        logger.info(urlResponse.toString());
        return true;
    }


}
