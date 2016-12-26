package com.robotapp;

import java.util.Optional;

public class Robot {

    Battery battery;
    LEDDisplay display;
    BarCodeScanner scanner;
    double walkDistance;
    OnHeadIndicator onHeadIndicator;
    Optional<Carriable> carringObject;

    public Robot(Battery battery, LEDDisplay display, BarCodeScanner scanner,OnHeadIndicator onHeadIndicator) {
        this.battery = battery;
        this.display = display;
        this.scanner = scanner;
        this.onHeadIndicator=onHeadIndicator;
        this.carringObject=Optional.empty();
    }

    public void walk(double kilometer){
        double requestedKilometer=kilometer;
        if(kilometer ==0){
            requestedKilometer=5;
        }
        double requiredBatteryUsage = calculateRequiredBatteryUsage(requestedKilometer);

        if(battery.getPercentage()>=requiredBatteryUsage){
                battery.use(requiredBatteryUsage);
                walkDistance+=requestedKilometer;
            }
            else {
                display.show("not enough battery");
            }

        indicateRedForLowBattery();

    }

    private double calculateRequiredBatteryUsage(double requestedKilometer) {
        double requiredBatteryUsage=requestedKilometer*0.02*1000;
        if(carringObject.isPresent())
            requiredBatteryUsage=requiredBatteryUsage+requiredBatteryUsage*0.02*carringObject.get().weight();

        return requiredBatteryUsage;
    }

    private void indicateRedForLowBattery() {
        if(battery.getStatus()== Battery.Status.LOW_BATTERY){
            onHeadIndicator.indicateRead();
        }
    }

    public void carry( Carriable carriable){
        double weight = carriable.weight();
        if(weight>10){
            display.show("Overweight");
        }
        this.carringObject =Optional.of(carriable);
    }
    public void unCarry( ){
        this.carringObject =Optional.empty();
    }


    public void scan( ){
        String scannedMessage = scanner.scan();
        if (scanner.getStatus()==BarCodeScanner.Status.SUCCESS)
            display.show(scannedMessage);
        else
            display.show("Scan Failure");
    }

    public double getWalkDistance() {
        return walkDistance;
    }
}
