package com.example.urlshortener.controller;


import com.example.urlshortener.dto.UrlDto;
import com.example.urlshortener.service.UrlShortenerService;
import com.example.urlshortener.utils.UrlValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.UnknownHostException;

@Slf4j
@RestController
@CrossOrigin("*")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    //TODO: do not throws those exception, return as custom exception like "Not Found"
    @PostMapping("/create-short-url")
    public String create(@Valid @RequestBody UrlDto urlDto) throws UnknownHostException {
        log.info("Request received to create short url for long url [{}]", urlDto.getLongUrl());

        UrlValidator.validateUrl(urlDto.getLongUrl());

        return urlShortenerService.createLongUrlToShortUrl(urlDto.getLongUrl());
    }

    @GetMapping("/{shortUrl}")
    public RedirectView localRedirect(@NotNull @PathVariable String shortUrl) throws Exception {
        log.info("Request received to redirect to long url for short url [{}]", shortUrl);

        String longUrl = urlShortenerService.getLongUrlFromShortUrl(shortUrl);

        log.info("Redirecting to [{}] for url [{}]", longUrl, shortUrl);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(longUrl);

        return redirectView;
    }
}
