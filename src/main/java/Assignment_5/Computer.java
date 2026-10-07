package Assignment_5;

import java.time.Year;

public class Computer extends Device implements INetworkable {
    private int ramGb;
    private String processor;
    private boolean hasDedicatedGpu;
    private String ipAddress;
    private boolean isConnected;

    public Computer(String id, String name, int yearInService, double purchasePrice,
                    int ramGb, String processor, boolean hasDedicatedGpu) {
        super(id, name, yearInService, purchasePrice);
        if (ramGb <= 0) {
            throw new IllegalArgumentException("Dung luong RAM phai > 0");
        }
        if (processor == null || processor.isEmpty()) {
            throw new IllegalArgumentException("Loai bo xu ly khong duoc rong");
        }
        this.ramGb = ramGb;
        this.processor = processor;
        this.hasDedicatedGpu = hasDedicatedGpu;
    }

    public int getRamGb() { return ramGb; }

    public String getProcessor() { return processor; }

    public boolean hasDedicatedGpu() { return hasDedicatedGpu; }

    @Override
    public double calculateMaintenanceCost() {
        double rate = 0.05;
        if (hasDedicatedGpu) rate += 0.02;
        if (getYearsInUse() > 5) rate += 0.01;
        return purchasePrice * rate;
    }

    public int getYearsInUse() {
        return Year.now().getValue() - yearInService;
    }

    @Override
    public void connect(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            throw new IllegalArgumentException("Dia chi IP khong duoc rong");
        }
        if (isConnected) {
            throw new IllegalStateException("May tinh " + id + " dang ket noi, khong the ket noi lai");
        }
        this.ipAddress = ipAddress;
        this.isConnected = true;
    }

    @Override
    public void disconnect() {
        this.isConnected = false;
        this.ipAddress = null;
    }

    @Override
    public String getIpAddress() { return ipAddress; }

    @Override
    public boolean isConnected() { return isConnected; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Computer: RAM %dGB, CPU %s, GPU roi: %s, Mang: %s",
              ramGb, processor, hasDedicatedGpu ? "co" : "khong",
              isConnected ? "IP " + ipAddress : "chua ket noi");
    }
}