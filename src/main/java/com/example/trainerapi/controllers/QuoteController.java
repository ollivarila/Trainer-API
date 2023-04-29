package com.example.trainerapi.controllers;

import com.example.trainerapi.controllers.services.QuoteService;
import com.example.trainerapi.models.entities.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * controller handling requests to /api/quotes
 * any action doesnt require authentication
 */

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteService quoteService;


    @Autowired
    public QuoteController(QuoteService quoteService){
        this.quoteService = quoteService;
    }

    /**
     * gets a single quote from database based on language conde
     * @param langCode language  code as path e.g. "en"
     * @return a single guote as json object {"quote" : "fetchedQuote" }
     */
    @GetMapping("/{langCode}")
    public ResponseEntity<?> getQuotesByLanguage(@PathVariable("langCode") String langCode) {
        return quoteService.getRandomQuoteByLang(langCode);
    }


    /**
     * add a guote to database,
     * @param quote Quote object
     * @return quote that was added as json
     */
    @PostMapping()
    public ResponseEntity<?> addQuotes(@RequestBody Quote quote) {
        return quoteService.addQuote(quote);
    }
}
