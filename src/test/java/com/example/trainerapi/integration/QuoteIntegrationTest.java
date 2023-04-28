package com.example.trainerapi.integration;

import com.example.trainerapi.integration.mock.MockHttpServletRequestBuilderFactory;
import com.example.trainerapi.models.entities.*;
import com.example.trainerapi.models.repositories.ExerciseTypeRepository;
import com.example.trainerapi.models.repositories.QuoteRepository;
import com.example.trainerapi.models.repositories.UserRepository;
import com.example.trainerapi.models.repositories.WorkoutRepository;
import com.example.trainerapi.security.util.JwtTokenUtil;
import org.assertj.core.api.Condition;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class QuoteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private MockHttpServletRequestBuilderFactory requestFactory;

    @BeforeEach
    public void beforeEach() throws Exception{
        createQuote("testi", "en");
        createQuote("testi5", "en");
        createQuote("testi2", "fi");
        createQuote("testi3", "es");
        createQuote("testi4", "hy");

    }

    @AfterEach
    public void afterEach() {
        quoteRepository.deleteAll();
    }

    @Test
    public void qetsAllQuotes(){
        Iterable<Quote> quotes = quoteRepository.findAll();
        Iterator it = quotes.iterator();
        List<Quote> quoteList = new ArrayList<>();
        while (it.hasNext()){
            Quote q = (Quote) it.next();
            quoteList.add(q);
        }

        assertThat(quoteList.size()).isEqualTo(5);

    }

    @Test
    public void qetsQuotesByLang() throws Exception{

        mockMvc.perform(requestFactory.getQuotesByLangRequest("fi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quote", Matchers.is("testi2")));

        mockMvc.perform(requestFactory.getQuotesByLangRequest("en"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quote", Matchers.anyOf(Matchers.is("testi"), Matchers.is("testi5"))));




    }


    private void createQuote(String quote, String langCode) throws Exception {
        mockMvc.perform(requestFactory.createQuoteRequest(quote, langCode)).andExpect(status().isOk());
    }
}
