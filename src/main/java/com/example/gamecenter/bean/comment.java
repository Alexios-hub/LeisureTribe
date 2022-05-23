package com.example.gamecenter.bean;

public class comment {
    private String email;
    private String userName;
    private String description;
    private String gameName;
    private int score;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "comment{" +
                "email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", description='" + description + '\'' +
                ", gameName='" + gameName + '\'' +
                ", score=" + score +
                '}';
    }
}
