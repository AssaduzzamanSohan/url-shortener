package com.example.urlshortener.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator {

    public static final String REGEX = "^(https?:\\/\\/)?" + // protocol
            "((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|" + // domain name
            "((\\d{1,3}\\.){3}\\d{1,3}))" + // OR ip (v4) address
            "(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*" + // port and path
            "(\\?[;&a-z\\d%_.~+=-]*)?" + // query string
            "(\\#[-a-z\\d_]*)?$";
    public static Pattern pattern = Pattern.compile(REGEX);

    public static void validateUrl(String url) {
        if (isProtocolMissing(url)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Protocol Missing, please add https:// or http:// in your url");
        }
        if (!isValidUrl(url)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL is not valid");
        }
    }

    public static boolean isValidUrl(String url) {
        Matcher matcher = pattern.matcher(url);
        return matcher.find();
    }

    public static boolean isProtocolMissing(String url) {
        return !url.startsWith("https://") && !url.startsWith("http://");
    }
}
