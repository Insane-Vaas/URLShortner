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

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

@Service
public class URLServiceImpl implements URLService {

    @Autowired
    URLResponseRepo urlResponseRepo;

    Logger logger = LoggerFactory.getLogger(Controller.class);

    @Override
    public String execute(URLRequest urlRequest) {
        String longURL = urlRequest.getUrl();
        logger.info(urlRequest.toString());
        String shortUrl = "";
        try {

            shortUrl = toHexStr(hashAlgorithm(longURL)).substring(0,7);

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

        return hexStr.toString();
    }


}
