package com.booking.model;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private String message;
    private String token;
    private List<Cardlist> cards = new ArrayList<Cardlist>();

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public List<Cardlist> getCard() {
        return cards;
    }
}
