package com.ewallet.EWallet.Model;

import javax.persistence.*;

@Entity
@Table(name="userauth")
public class UserAuth {

    @Id
    @Column(name="userid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(name="username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name="role")
    private String role;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
