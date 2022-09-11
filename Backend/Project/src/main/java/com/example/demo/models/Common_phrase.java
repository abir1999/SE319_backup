package com.example.demo.models;

import javax.persistence.*;

/*This class holds words/phrases used by every user and counts them, Initial implementation is not very efficient
 * but will do for now, Will probably use many-to-many mapping when best method is realized.*/
@Entity
@Table(name = "common_phrase")
public class Common_phrase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String username;
	
	private Integer count;
	
	private String phrase;
	
	public Common_phrase(){
		
	}
	public Common_phrase(String username, String phrase, int count) {
		this.username = username;
		this.phrase = phrase;
		this.count = count;
	}
	
    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public void setUsername(String username) {
    	this.username = username;
    }
    
    public Integer getCount() {
    	return count;
    }
    
    public void setCount(Integer count) {
    	this.count = count;
    }
    
    public String getPhrase() {
    	return phrase;
    }
    
    public void setPhrase(String phrase) {
    	this.phrase = phrase;
    }
	
}
