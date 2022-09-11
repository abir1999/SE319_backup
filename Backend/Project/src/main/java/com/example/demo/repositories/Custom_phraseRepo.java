package com.example.demo.repositories;

import com.example.demo.models.Common_phrase;
import com.example.demo.models.Custom_phrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface Custom_phraseRepo extends JpaRepository<Custom_phrase,Integer> {

    List<Custom_phrase> findAll();

    @SuppressWarnings("unchecked")
    Custom_phrase save(Custom_phrase phrase_new);

    @Query(value ="SELECT * FROM custom_phrase WHERE username = ?1", nativeQuery = true)
    List<Custom_phrase> findByUser(String user);

    @Query(value = "SELECT * FROM custom_phrase WHERE username = ?1 AND phrase = ?2", nativeQuery = true)
    Custom_phrase findPhrase(String username, String phrase);

    @Modifying
    @Query(value = "DELETE FROM custom_phrase WHERE username = :username AND phrase = :phrase",nativeQuery = true)
    void deletePhrase(String username, String phrase);

}
