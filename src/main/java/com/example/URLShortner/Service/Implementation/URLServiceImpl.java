package com.example.URLShortner.Service.Implementation;

import ch.qos.logback.core.util.StringUtil;
import com.example.URLShortner.Controller.ExceptionHandlerController;
import com.example.URLShortner.Domain.ShortURL;
import com.example.URLShortner.Domain.URLRequest;
import com.example.URLShortner.Domain.UrlResponse;
import com.example.URLShortner.Exception.APIException;
import com.example.URLShortner.Service.URLResponseRepo;
import com.example.URLShortner.Service.URLService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;


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

    Logger logger = LoggerFactory.getLogger(Controller.class);

    @Override
    public ShortURL execute(URLRequest urlRequest) {
        String longURL = urlRequest.getUrl();
        logger.info(urlRequest.toString());

        validateURL(urlRequest);

        if (checkIfDataExists(longURL) == true) {
            throw new APIException("Data already present in Database");
        }

        String shortUrl = "";
        try {
            shortUrl = toHexStr(hashAlgorithm(longURL));
        } catch (Exception e) {
            logger.info(String.valueOf(e));
        }
        return genrateReponse(shortUrl,longURL);
    }

    private ShortURL genrateReponse(String shortUrl, String longURL) {
        shortUrl = "https://example.com/".concat(shortUrl);
        saveData(longURL, shortUrl);
        logger.info(shortUrl);
        return new ShortURL(shortUrl);
    }

    private void saveData(String longUrl, String shortUrl) {
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setLongURL(longUrl);
        urlResponse.setShortURL(shortUrl);
        urlResponseRepo.save(urlResponse);

    }

    private byte[] hashAlgorithm(String urlLink) throws NoSuchAlgorithmException {

        MessageDigest msgDgst = MessageDigest.getInstance("SHA-256");
        logger.info(Arrays.toString(msgDgst.digest(urlLink.getBytes(StandardCharsets.UTF_8))));
        return msgDgst.digest(urlLink.getBytes(StandardCharsets.UTF_8));

    }


    private static String toHexStr(byte[] hash) {

        BigInteger no = new BigInteger(1, hash);
        StringBuilder hexStr = new StringBuilder(no.toString(16));
        while (hexStr.length() < 32) {
            hexStr.insert(0, '0');
        }
        return hexStr.substring(0, 7);
    }


    private boolean checkIfDataExists(String longUrl) {

        UrlResponse urlResponse = urlResponseRepo.findByLongUrl(longUrl);
        if (Objects.isNull(urlResponse)) return false;

        logger.info(urlResponse.toString());
        return true;
    }

    private void validateURL(URLRequest urlRequest) {
        if (StringUtil.isNullOrEmpty(urlRequest.getUrl())) {
            throw new APIException("Url provided is Empty. Please provide Legit URL");
        }
    }

}
