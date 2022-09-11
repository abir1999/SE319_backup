package com.example.demo.services;

/*
This service is used for mockito tests. It mocks the repository by having methods that act like mySQL queries.
 */

import com.example.demo.models.Common_phrase;
import com.example.demo.models.Custom_phrase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.*;

import java.util.List;

@Service
public class Custom_phraseService {

    @Autowired
    private Custom_phraseRepo repo;

    public List<Custom_phrase> findAllPhraseByUser(String user){
        return repo.findByUser(user);
    }

    public Custom_phrase findPhraseByUser(String user, String phrase){
        return repo.findPhrase(user,phrase);
    }

    public String deletePhraseByUser(String user, String phrase){
        try{
            repo.deletePhrase(user,phrase);
            return "Phrase Deleted";
        }
        catch (NullPointerException e){
            return "No Phrase found";
        }
    }


}
