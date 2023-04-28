package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.Quote;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, UUID>{

    List<Quote> findByLangCode(String langCode);


}
