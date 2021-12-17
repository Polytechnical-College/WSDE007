package com.example.demex;

public class User {
    String username;
    String firstname;
    String secondname;
    String email;
    int timeDrive;
    int cash;
    int token;

    public User(String username, String firstname, String secondname, String email, int cash, int timeDrive, int token) {
        this.username = username;
        this.firstname = firstname;
        this.secondname = secondname;
        this.timeDrive = timeDrive;
        this.cash = cash;
        this.email = email;
        this.token = token;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public int getTimeDrive() {
        return timeDrive;
    }

    public void setTimeDrive(int timeDrive) {
        this.timeDrive = timeDrive;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", email='" + email + '\'' +
                ", timeDrive=" + timeDrive +
                ", cash=" + cash +
                ", token=" + token +
                '}';
    }
}
