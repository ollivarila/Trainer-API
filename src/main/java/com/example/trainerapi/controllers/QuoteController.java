package com.example.trainerapi.controllers;

import com.example.trainerapi.controllers.services.QuoteService;
import com.example.trainerapi.models.entities.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteService quoteService;


    @Autowired
    public QuoteController(QuoteService quoteService){
        this.quoteService = quoteService;
    }

    @GetMapping("/{langCode}")
    public ResponseEntity<?> getQuotesByLanguage(@PathVariable("langCode") String langCode) {
        return quoteService.getRandomQuoteByLang(langCode);
    }

    @PostMapping()
    public ResponseEntity<?> addQuotes(@RequestBody Quote quote) {
        return quoteService.addQuote(quote);
    }
}
