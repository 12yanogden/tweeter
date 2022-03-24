package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * Represents an auth token in the system.
 */
public class AuthToken implements Serializable {
    /**
     * Value of the auth token.
     */
    public String token;
    /**
     * String representation of date/time at which the auth token was created.
     */
    public String datetime;

    public AuthToken() {
        token = UUID.randomUUID().toString();
        datetime = calcDateTime();
    }

    private String calcDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy h:mm aaa");
        LocalDateTime now = LocalDateTime.now();

        return formatter.format(now);
    }

    public AuthToken(String token) {
        this.token = token;
        datetime = new Date().toString();
    }

    public AuthToken(String token, String datetime) {
        this.token = token;
        this.datetime = datetime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDatetime() {
        return datetime;
    }
}