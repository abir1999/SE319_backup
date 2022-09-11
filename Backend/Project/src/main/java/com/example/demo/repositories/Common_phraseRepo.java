package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.models.Common_phrase;

@Repository
public interface Common_phraseRepo extends JpaRepository<Common_phrase, Integer> {
	
	List<Common_phrase> findAll();
	
	@SuppressWarnings("unchecked")
	Common_phrase save(Common_phrase phrase_new);
	
	@Query(value = "SELECT * FROM common_phrase WHERE username = ?1", nativeQuery = true)
	List<Common_phrase> findByUser(String user);
	
	@Query(value = "SELECT * FROM common_phrase WHERE username = ?1 AND phrase = ?2", nativeQuery = true)
	Common_phrase findPhrase(String username, String phrase);

}
