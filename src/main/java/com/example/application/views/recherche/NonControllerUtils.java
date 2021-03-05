package com.example.application.views.recherche;

public class NonControllerUtils {
    public static boolean startSearching(String searchText) {
        return searchText != null && searchText.length() >= 2;
    }
}
