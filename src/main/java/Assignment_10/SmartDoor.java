/*
    Trần Quang Hiển
    202418893
*/
package Assignment_10;

public class SmartDoor implements Device, Lockable {
    private final String name;

    public SmartDoor(String name) {
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

    @Override
    public void lock() {
        System.out.println(name + ": LOCKED");
    }

    @Override
    public void unlock() {
        System.out.println(name + ": UNLOCKED");
    }
}