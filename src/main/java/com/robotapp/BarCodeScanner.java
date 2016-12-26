package com.robotapp;
public class BarCodeScanner {
    Status status;

    static enum Status{
        FAILUR,
        SUCCESS
    }

    public Status getStatus() {
        return status;
    }

    public void update(Status status) {
        this.status = status;
    }

    public String scan(){
        if(status==Status.SUCCESS)
        return "Product price is 15 Rs.";
        else
            return "DDDDXXXHHHHHHH";
    }
}
