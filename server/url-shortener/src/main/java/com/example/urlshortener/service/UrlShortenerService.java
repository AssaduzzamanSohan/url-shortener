package com.example.urlshortener.service;

import java.net.UnknownHostException;

public interface UrlShortenerService {

    String createLongUrlToShortUrl(String longUrl) throws UnknownHostException;

    String getLongUrlFromShortUrl(String shortUrl) throws Exception;
}
