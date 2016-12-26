package com.robotapp

import spock.lang.Specification

class RobotTest extends Specification {

    Robot robot
    Battery battery
    LEDDisplay display
    BarCodeScanner scanner
    OnHeadIndicator headIndicator;

    void setup() {
        battery = new Battery()
        display = new LEDDisplay()
        scanner = new BarCodeScanner()
        headIndicator = new OnHeadIndicator()
        robot = new Robot(battery, display, scanner,headIndicator)
    }

    def "Robot can walk on battery 5 km per charge"() {
        given:
            battery.charge()

        when:
            robot.walk(5)


        then:
        robot.getWalkDistance() == 5
        battery.getPercentage() == 0
    }
    def "Robot walks for 3.5 KM"() {
        given:
            battery.charge()

        when:
            robot.walk(3.5)

        then:
        battery.getPercentage() == 30
    }
    def "Robot walks for 2 Km carrying 3 Kg weight"() {
        given:
            battery.charge()
            robot.carry(new AnyObject("3 kg",3))


        when:
        robot.walk(2)

        then:
        battery.getPercentage() ==  57.6
    }

    def "if less than 15 % battery remaining then red light on Robot head should lit up indicating low battery"() {
        given:
        battery.charge()
        battery.use(90)

        when:
        robot.walk(0)

        then:
        headIndicator.getStatus() == OnHeadIndicator.Status.RED
    }
    def "For every Kilogram carried by Robot, 2% extra [in addition to walking discharge] battery will be consumed that is 4 km 500 meter for 5 kg weight"() {
        given:
        battery.charge()

        when:
        robot.carry(new AnyObject("5 kg",10))
        robot.walk(2)

        then:
        battery.getPercentage() == 52
    }

    def "If the weight of the object is more than 10 Kg, Robot display [LED display on chest] will show message 'Overweight'"() {
        given:
        LEDDisplay display = Mock()
        robot.display=display
        battery.charge()

        when:
        robot.carry(new AnyObject("more than 10 kg",12))

        then:
        1 * display.show("Overweight")
    }

    def "Robot can scan any bar code and display it's price on Robot Display"() {
        given:
        LEDDisplay display = Mock()
        robot.display=display
        scanner.update(BarCodeScanner.Status.SUCCESS)


        when:
        robot.scan()

        then:
        1 * display.show("Product price is 15 Rs.")
    }

    def "In case bar code is not clear enough for scanning, Robot display will show “Scan Failure”"() {

        given:
        LEDDisplay display = Mock()
        robot.display=display
        scanner.update(BarCodeScanner.Status.FAILUR)


        when:
        robot.scan()

        then:
        1 * display.show("Scan Failure")

    }
}
