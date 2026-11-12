/*
    Trần Quang Hiển
    202418893
*/
package Assignment_10;

public class SmartFan implements Device {
    private final String name;

    public SmartFan(String name) {
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