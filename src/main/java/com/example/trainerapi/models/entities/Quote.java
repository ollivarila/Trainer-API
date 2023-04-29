package com.example.trainerapi.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

/**
 * Quote entity to store quotes in the database
 * String quote quote
 * String langCode language code for the quote
 */
@Data
@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String quote;

    private String langCode;

    public Quote() {
    }

    public Quote(String quote, String langCode) {

        this.quote = quote;
        this.langCode = langCode;
    }

    public String getQuote(){
        return quote;
    }

    @Override
    public String toString(){
        return String.format("Quote[id=%s, quote='%s', langCode='%s']", id, quote, langCode);
    }

}
