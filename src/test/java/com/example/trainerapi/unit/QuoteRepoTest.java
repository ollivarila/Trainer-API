package com.example.trainerapi.unit;


import com.example.trainerapi.models.entities.Quote;
import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.entities.Workout;
import com.example.trainerapi.models.repositories.QuoteRepository;
import com.example.trainerapi.models.repositories.UserRepository;
import com.example.trainerapi.models.repositories.WorkoutRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QuoteRepoTest {

    @Autowired
    private QuoteRepository quoteRepository;



    @Test
    public void repositoryLoads() {
        assertThat(quoteRepository).isNotNull();
    }


    @Test
    public void getsQuotesByLang(){

        quoteRepository.save(new Quote("test", "en"));
        quoteRepository.save(new Quote("test2", "en"));

        List<Quote> quotes = quoteRepository.findByLangCode("en");
        assertThat(quotes.iterator().hasNext()).isTrue();
        assertThat(quotes.get(1).getQuote()).isEqualTo("test2");

    }

}
