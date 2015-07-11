package org.playentropy.user;

import java.io.Serializable;
import javax.persistence.*;

public class User implements Serializable {
    private String id;

    private String username;

    private String email;

    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }
}
