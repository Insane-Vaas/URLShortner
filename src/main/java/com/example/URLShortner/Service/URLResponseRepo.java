package com.example.URLShortner.Service;

import com.example.URLShortner.Domain.UrlResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface URLResponseRepo extends JpaRepository<UrlResponse, Integer> {

    @Query("From #{#entityName} WHERE shortURL = ?1")
    UrlResponse findByShortUrl(String urlLink);


    @Query("From #{#entityName} WHERE longURL = ?1")
    UrlResponse findByLongUrl(String urlLink);

}
