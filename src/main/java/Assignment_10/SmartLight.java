/*
    Trần Quang Hiển
    202418893
*/
package Assignment_10;

public class SmartLight implements Device {
    private final String name;

    public SmartLight(String name) {
        this.name = name;
    }

    @Override
    public void turnOn() {
        System.out.println(name + ": ON");
    }

    @Override
    public void turnOff() {
        System.out.println(name + ": OFF");
    }
}