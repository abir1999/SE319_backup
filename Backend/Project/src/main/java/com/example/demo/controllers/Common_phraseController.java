package com.example.demo.controllers;

import com.example.demo.helper.Phrase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.demo.models.Common_phrase;
import com.example.demo.repositories.Common_phraseRepo;
import com.example.demo.repositories.LoginRepo;

@RestController
public class Common_phraseController {
	
	@Autowired
	private Common_phraseRepo phraserepo;
	
	@Autowired
	private LoginRepo loginrepo;
	
	Common_phrase phrase_new = new Common_phrase();

//	@GetMapping(path = "/useword/{phrase}/{username}")
//	public void add_word(@PathVariable("phrase") String phrase, @PathVariable("username") String username) {
//
//		if(phraserepo.findPhrase(username, phrase)==null) {
//
//			//Integer id = loginrepo.findByUsername(username).getId();
//			Common_phrase phrase2 = new Common_phrase(username,phrase,1);
//			phraserepo.save(phrase2);
//
//		}
//		else {
//			phrase_new = phraserepo.findPhrase(username, phrase);
//			Integer count = phrase_new.getCount();
//			phrase_new.setCount(count+1);
//			phraserepo.save(phrase_new);
//		}
//
//	}

	@PostMapping(path = "/usephrase/{username}")
	public Map add_phrase(@RequestBody Phrase phrase,@PathVariable("username") String username) {

		if(phraserepo.findPhrase(username, phrase.getPhrase())==null) {

			//Integer id = loginrepo.findByUsername(username).getId();
			Common_phrase phrase2 = new Common_phrase(username,phrase.getPhrase(),1);
			phraserepo.save(phrase2);

		}
		else {
			phrase_new = phraserepo.findPhrase(username, phrase.getPhrase());
			Integer count = phrase_new.getCount();
			phrase_new.setCount(count+1);
			phraserepo.save(phrase_new);
		}
		Map<String, String> status = Collections.singletonMap("Status","OK");
		return status;

	}
	
	@GetMapping(path = "/findcommon/{username}")
	public Map<String, List<String>> common_phrases(@PathVariable("username") String name){
		//top 5 words
		List<Common_phrase> common = new ArrayList<Common_phrase>();
		List<String> frequent_string = new ArrayList<String>();
		common.addAll(phraserepo.findByUser(name));
		int size = common.size(); //number of elecments in the list
		for(int i = 0;i<size;i++) {
			
			for(int j = i+1;j<size;j++) {
				if(common.get(i).getCount()<common.get(j).getCount()) {
					Common_phrase temp = common.get(i);
					common.set(i, common.get(j));
					common.set(j, temp);
				}
			}
		}
		
		for(int i = 0;i<size;i++) {
			frequent_string.add(common.get(i).getPhrase());
			if(i==4) {
				break;
			}
		}

		Map<String, List<String>> output = Collections.singletonMap("Data",frequent_string);
		
		return output;
		
	}
}

