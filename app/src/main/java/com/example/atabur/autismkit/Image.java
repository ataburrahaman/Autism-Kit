package com.example.atabur.autismkit;

public class Image {
    String dat,wrong,correct;

    public Image(String dat, String wrong, String correct) {
        this.dat = dat;
        this.wrong = wrong;
        this.correct = correct;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public Image(){
    }
}
