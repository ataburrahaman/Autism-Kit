package com.example.atabur.autismkit;


public class resultStore {
    int correct,incorrect,total,wordcount;
    int totalTime;

    public resultStore(int correct, int incorrect, int total, int wordcount, int totalTime, String date, String time) {
        this.correct = correct;
        this.incorrect = incorrect;
        this.total = total;
        this.wordcount = wordcount;
        this.totalTime = totalTime;
        this.date = date;
        this.time = time;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getWordcount() {
        return wordcount;
    }

    public void setWordcount(int wordcount) {
        this.wordcount = wordcount;
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

    String date;
    String time;

    public resultStore(int correct, int incorrect, int total, int wordcount, int totalTime, String date, String time, String prediction) {
        this.correct = correct;
        this.incorrect = incorrect;
        this.total = total;
        this.wordcount = wordcount;
        this.totalTime = totalTime;
        this.date = date;
        this.time = time;
        this.prediction = prediction;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    String prediction;

    public resultStore(int correct, int incorrect, int total, int wordcount, String date, String time) {
        this.correct = correct;
        this.incorrect = incorrect;
        this.total = total;
        this.wordcount = wordcount;
        this.date = date;
        this.time = time;
    }

    public resultStore(){
        
    }


}
