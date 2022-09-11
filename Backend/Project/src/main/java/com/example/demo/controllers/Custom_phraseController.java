package com.example.demo.controllers;

import com.example.demo.helper.Phrase;
import com.example.demo.models.Custom_phrase;
import com.example.demo.repositories.Custom_phraseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class Custom_phraseController {

    @Autowired
    private Custom_phraseRepo repo;

    @PostMapping(path = "/add_custom/{username}")
    public Map add_custom(@RequestBody Phrase phrase, @PathVariable("username") String username){

        Custom_phrase p = new Custom_phrase(username,phrase.getPhrase());
        Map<String, String> status = Collections.singletonMap("Status","Custom Phrase added");
        Map<String, String> status_exists = Collections.singletonMap("Status","Custom Phrase exists");

        if(repo.findPhrase(username,phrase.getPhrase())==null) {
            repo.save(p);
            return status;
        }
        else{
            return status_exists;
        }
    }

    @PostMapping(path = "/delete_custom/{username}")
    public Map delete_custom(@RequestBody Phrase phrase, @PathVariable("username") String username){

        Map<String, String> status = Collections.singletonMap("Status","Custom Phrase deleted");
        Map<String, String> status_exists = Collections.singletonMap("Status","Custom Phrase doesn't exist!");

        if(repo.findPhrase(username,phrase.getPhrase())!=null) {
            repo.deletePhrase(username,phrase.getPhrase());
            return status;
        }
        else{
            return status_exists;
        }
    }

    @GetMapping(path = "retrieve_custom/{username}")
    public Map<String, List<String>> retrieve_custom(@PathVariable("username") String username){

        List<Custom_phrase> custom = new ArrayList<Custom_phrase>();
        List<String> custom_string = new ArrayList<String>();
        custom.addAll(repo.findByUser(username));

        for(int i = 0;i<custom.size();i++){
            custom_string.add(custom.get(i).getPhrase());
        }
        Map<String, List<String>> output = Collections.singletonMap("Data",custom_string);
        return output;
    }


}
