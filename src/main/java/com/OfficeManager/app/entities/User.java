package com.OfficeManager.app.entities;

import javax.persistence.*;

@Entity
@Table(name="USER")
public class User {

    private Integer id;
    private String username;
    private String password;
    private String role;

    public User(){}

    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    public Integer getId(){
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "USERNAME", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "ROLE", nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
