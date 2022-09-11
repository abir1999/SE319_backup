package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "custom_phrase")
public class Custom_phrase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String phrase;

    public Custom_phrase(){

    }
    public Custom_phrase(String username, String phrase) {
        this.username = username;
        this.phrase = phrase;
    }

    public Custom_phrase(int id, String username, String phrase) {
        this.id = id;
        this.username = username;
        this.phrase = phrase;
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

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
