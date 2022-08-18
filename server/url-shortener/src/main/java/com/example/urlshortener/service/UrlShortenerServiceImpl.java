package com.example.urlshortener.service;

import com.example.urlshortener.utils.EnvUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.UnknownHostException;

@Service
@Slf4j
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private static final String REDIS_KEY_SHORT_URL_TO_LONG_URL = "SHORT_URL_TO_LONG_URL";
    private static final String REDIS_KEY_LONG_URL_TO_SHORT_URL = "LONG_URL_TO_SHORT_URL";
    private static final String REDIS_KEY_LAST_CREATED_SHORT_URL = "LAST_CREATED_SHORT_URL";
    private final RedisService redisService;
    private final EnvUtil envUtil;
    char[] allowedChar = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
            'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
            '2', '3', '4', '5', '6', '7', '8', '9', '+'
    };

    public UrlShortenerServiceImpl(RedisService redisService, EnvUtil envUtil) {
        this.redisService = redisService;
        this.envUtil = envUtil;
    }

    @Override
    public String createLongUrlToShortUrl(String longUrl) throws UnknownHostException {
        Object url = redisService.get(REDIS_KEY_LONG_URL_TO_SHORT_URL, longUrl);
        if (url != null) {
            log.info("Url [{}] found in database for url [{}]", url.toString(), longUrl);
            return envUtil.getBaseUrl() + "/" + url.toString();
        }
        return envUtil.getBaseUrl() + "/" + createShortUrl(longUrl);
    }

    @Override
    public String getLongUrlFromShortUrl(String shortUrl) throws Exception {
        String url = shortUrl.replace(envUtil.getBaseUrl() + "/", "");
        Object value = redisService.get(REDIS_KEY_SHORT_URL_TO_LONG_URL, shortUrl);
        if (value == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found [" + shortUrl + "]");
        }

        return value.toString();
    }

    private synchronized String createShortUrl(String longUrl) {
        log.info("Creating new short Url for url [{}]", longUrl);
        String newUrl = "";
        String lastShortUrl = getLastCreatedShortUrl();
        log.info("Last short Url is [{}], creating next one", lastShortUrl);
        if (isLastString(lastShortUrl)) {
            newUrl = generateFirstUrlOfLength(lastShortUrl.length() + 1);
        } else {
            newUrl = getNextShortUrl(lastShortUrl);
        }

        log.info("New url create for [{}] is [{}]", longUrl, newUrl);

        redisService.set(REDIS_KEY_LONG_URL_TO_SHORT_URL, longUrl, newUrl);
        redisService.set(REDIS_KEY_SHORT_URL_TO_LONG_URL, newUrl, longUrl);
        redisService.set(REDIS_KEY_LAST_CREATED_SHORT_URL, newUrl);

        return newUrl;
    }

    private String getNextShortUrl(String url) {
        for (int i = url.length() - 1; i >= 0; i--) {
            if (url.charAt(i) != allowedChar[allowedChar.length - 1]) {
                int index = findIndexOfChar(url.charAt(i));
                return url.substring(0, i) + allowedChar[index + 1] + generateFirstUrlOfLength(url.length() - i - 1);
            }
        }
        return "Could not create";
    }

    private String getLastCreatedShortUrl() {
        Object url = redisService.get(REDIS_KEY_LAST_CREATED_SHORT_URL);
        if (url == null) return "";
        return url.toString();
    }

    private boolean isLastString(String url) {
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) != allowedChar[allowedChar.length - 1]) return false;
        }
        return true;
    }

    private String generateFirstUrlOfLength(int length) {
        String newUrl = "";
        for (int i = 0; i < length; i++) {
            newUrl += allowedChar[0];
        }
        return newUrl;
    }

    private int findIndexOfChar(char c) {
        for (int i = 0; i < allowedChar.length; i++) {
            if (allowedChar[i] == c) return i;
        }
        return -2;
    }
}
