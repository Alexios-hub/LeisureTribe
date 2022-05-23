package com.example.gamecenter.bean;

public class game {
    private String name;
    private float averageScore;
    private int numberOfPeople;
    private String description;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(float averageScore) {
        this.averageScore = averageScore;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "game{" +
                "name='" + name + '\'' +
                ", averageScore=" + averageScore +
                ", numberOfPeople=" + numberOfPeople +
                ", description='" + description + '\'' +
                '}';
    }
}
