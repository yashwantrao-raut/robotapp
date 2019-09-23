package com.robotapp

import spock.lang.Specification

class RobotTest extends Specification {

    Robot robot
    Battery battery
    LEDDisplay display
    BarCodeScanner scanner
    OnHeadIndicator headIndicator

    void setup() {
        battery = new Battery()
        display = new LEDDisplay()
        scanner = new BarCodeScanner()
        headIndicator = new OnHeadIndicator()
        robot = new Robot(battery, display, scanner,headIndicator)
    }

    def "Robot can walk on battery 5 km per charge"() {
        given: "Battery already charged"
            battery.charge()

        when: "Robot walk 5 km"
            robot.walk(5)


        then: "Robot walk distance should be 5 km and battery percentage should be zero "
        robot.getWalkDistance() == 5
        battery.getPercentage() == 0
    }
    def "Robot walks for 3.5 KM"() {
        given:"Battery already charged"
            battery.charge()

        when:"Robot walk 3.5 km"
            robot.walk(3.5)

        then: "Battery percentage should be 30 % "
        battery.getPercentage() == 30
    }
    def "Robot walks for 2 Km carrying 3 Kg weight"() {
        given: "Battery already charged and robot carry 3 kg weight"
            battery.charge()
            robot.carry(new AnyObject("3 kg",3))


        when: "Robot walk 2 km"
        robot.walk(2)

        then: "Battery percentage should be 57.6 % "
        battery.getPercentage() ==  57.6
    }

    def "if less than 15 % battery remaining then red light on Robot head should lit up indicating low battery"() {
        given:"Battery 10 % remaining"
        battery.charge()
        battery.use(90)

        when:"Robot walk 0 km"
        robot.walk(0)

        then: "Head indicator status should be red"
        headIndicator.getStatus() == OnHeadIndicator.Status.RED
    }
    def "For every Kilogram carried by Robot, 2% extra [in addition to walking discharge] battery will be consumed that is 4 km 500 meter for 5 kg weight"() {
        given:"Battery already charged"
        battery.charge()

        when: "Robot walk 2 km with caring 5 kg weight"
        robot.carry(new AnyObject("5 kg",10))
        robot.walk(2)

        then: "Battery percentage should be 52 % "
        battery.getPercentage() == 52
    }

    def "If the weight of the object is more than 10 Kg, Robot display [LED display on chest] will show message 'Overweight'"() {
        given: "Battery already charged and robot has display"
        LEDDisplay display = Mock()
        robot.display=display
        battery.charge()

        when: "Robot carry object more than 10 kg"
        robot.carry(new AnyObject("more than 10 kg",12))

        then: "Overweight should be displayed on display"
        1 * display.show("Overweight")
    }

    def "Robot can scan any bar code and display it's price on Robot Display"() {
        given: "Robot is having display and scanner"
        LEDDisplay display = Mock()
        robot.display=display
        scanner.update(BarCodeScanner.Status.SUCCESS)


        when: "Robot do scan"
        robot.scan()

        then: "Product price 15 should be displayed on display"
        1 * display.show("Product price is 15 Rs.")
    }

    def "In case bar code is not clear enough for scanning, Robot display will show “Scan Failure”"() {

        given: "Robot is having display and scanner. Scanner status is failed"
        LEDDisplay display = Mock()
        robot.display=display
        scanner.update(BarCodeScanner.Status.FAILUR)


        when:"Robot do scan"
        robot.scan()

        then:" Scan failure message should be display on screen"
        1 * display.show("Scan Failure")

    }
}
