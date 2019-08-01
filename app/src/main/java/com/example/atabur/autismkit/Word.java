package com.example.atabur.autismkit;

public class Word {
    private String date,time,prediction;
      private long correct;
    private long incorrect;

    public Word(String date, String time, String prediction, long correct, long incorrect, long totalTime) {
        this.date = date;
        this.time = time;
        this.prediction = prediction;
        this.correct = correct;
        this.incorrect = incorrect;
        this.totalTime = totalTime;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public Word(String date, String time, long correct, long incorrect, long totalTime) {
        this.date = date;
        this.time = time;
        this.correct = correct;
        this.incorrect = incorrect;
        this.totalTime = totalTime;
    }

    private long totalTime;
    public Word(){

    }

    public Word(String date, String time, long correct, long incorrect) {
        this.date = date;
        this.time = time;
        this.correct = correct;
        this.incorrect = incorrect;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getCorrect() {
        return correct;
    }

    public void setCorrect(long correct) {
        this.correct = correct;
    }

    public long getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(long incorrect) {
        this.incorrect = incorrect;
    }
}

