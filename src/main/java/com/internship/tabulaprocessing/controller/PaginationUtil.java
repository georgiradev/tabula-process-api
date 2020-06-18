package com.internship.tabulaprocessing.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class PaginationUtil {
    private PaginationUtil() {
    }
    public static HttpHeaders generatePaginationHttpHeaders(Page<?> page, String baseUrl) {
        final int totalPages = page.getTotalPages();
        final int currentPageNumber = page.getNumber();
        final int pageSize = page.getSize();
        List<String> links = new ArrayList<>();
        links.add(generateRelLink(generateUri(baseUrl, 0, pageSize), "first"));
        if (currentPageNumber > 0) {
            links.add(generateRelLink(generateUri(baseUrl, currentPageNumber - 1, pageSize), "prev"));
        }
        if ((currentPageNumber + 1) < totalPages) {
            links.add(generateRelLink(generateUri(baseUrl, currentPageNumber + 1, pageSize), "next"));
        }
        int lastPage = totalPages > 0 ? totalPages - 1 : 0;
        links.add(generateRelLink(generateUri(baseUrl, lastPage, pageSize), "last"));
        String allLinks = links.stream().collect(Collectors.joining(","));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LINK, allLinks);
        headers.add("X-Total-Count", Integer.toString(totalPages));
        return headers;
    }
    private static String generateUri(String baseUrl, int page, int size) {
        return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size)
                .toUriString();
    }
    private static String generateRelLink(String url, String rel) {
        return "<" + url + ">; rel=\"" + rel + "\"";
    }
}