package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "loginpage")
public class Login_page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    //setters and getters

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getUsername(){

        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword(){

        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
