package com.robotapp;

public class Battery {

    private double percentage;
    private Status status;

  public  enum Status{
        CHARGED,
        DISCHARGED,
        PARTIALLY_CHARGED,
        LOW_BATTERY
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void charge(){
        status =Status.CHARGED;
        percentage= 100;
    }

    public double use(double requestedPercentage){
        double returnPercentage=0;
        if(status==Status.DISCHARGED || this.percentage==0)
            return returnPercentage;

        if(requestedPercentage >=this.percentage)
        {
            returnPercentage=this.percentage;
            this.percentage =0;
            this.status=Status.DISCHARGED;
        }
        else {
            returnPercentage = requestedPercentage;
            double remainingPercentage= this.percentage-requestedPercentage;
            if(remainingPercentage <=15)
                this.status=Status.LOW_BATTERY;
            else
                this.status= Status.PARTIALLY_CHARGED;

            this.percentage =remainingPercentage;
        }
        return returnPercentage;

    }
}
