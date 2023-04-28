package com.example.trainerapi.controllers.responses;

import lombok.Data;

@Data
public class QuoteResponse {

    private String quote;

    public QuoteResponse(String quote){
        this.quote = quote;
    }
}
