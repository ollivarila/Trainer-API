package com.example.trainerapi.controllers.services;

import com.example.trainerapi.controllers.responses.QuoteResponse;
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

/**
 * handles methods called by quotecontroller
 */

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;

    @Autowired
    public QuoteService(QuoteRepository quoteRepository){
        this.quoteRepository = quoteRepository;
    }

    /**
     * gets a random quote from database matching in given language
     * @param langCode language code
     * @return QuoteResponce quote
     */
    public ResponseEntity<?> getRandomQuoteByLang(String langCode) {

        String lang = langCode.toLowerCase();

        List<Quote> quotes = quoteRepository.findByLangCode(lang);


        if(quotes.isEmpty()){
            quotes = quoteRepository.findByLangCode("en");
        }

        if(quotes.isEmpty()){
            return ResponseEntity.badRequest().body("No quotes found in database");
        }

        Quote quote = quotes.get((int) (Math.random() * quotes.size()));
        return ResponseEntity.ok(new QuoteResponse(quote.getQuote()));

    }

    /**
     * adds quote to database
     * @param quote Quote
     * @return added quote
     */

    public ResponseEntity<?> addQuote(Quote quote){
        return ResponseEntity.ok(quoteRepository.save(quote));
    }


}
