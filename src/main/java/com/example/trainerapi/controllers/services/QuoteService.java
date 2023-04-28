package com.example.trainerapi.controllers.services;

import com.example.trainerapi.models.entities.Quote;
import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.repositories.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.html.HTMLQuoteElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;

    @Autowired
    public QuoteService(QuoteRepository quoteRepository){
        this.quoteRepository = quoteRepository;
    }

    public ResponseEntity<?> getRandomQuoteByLang(String langCode) {

        String lang = langCode.toLowerCase();

        List<Quote> quotes = quoteRepository.findByLangCode(lang);


        if(!quotes.isEmpty()){
            Quote quote = quotes.get((int) (Math.random() * quotes.size()));
            return ResponseEntity.ok(quote.getQuote());
        }
        return  ResponseEntity.badRequest().body("No quotes found for language code: " + langCode);

    }

    public ResponseEntity<?> addQuote(Quote quote){
        return ResponseEntity.ok(quoteRepository.save(quote));
    }


}
