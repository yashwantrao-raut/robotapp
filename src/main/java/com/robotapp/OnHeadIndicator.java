package com.robotapp;
public class OnHeadIndicator {
    Status status;

    public void indicateRead(){
        this.status=Status.RED;
    }
    public void indicateGreen(){
        this.status=Status.GREEN;
    }

    public Status getStatus() {
        return status;
    }

    public static enum Status{
       RED,GREEN
   }
}
