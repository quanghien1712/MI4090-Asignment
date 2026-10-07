/*
    Trần Quang Hiển
    202418893
 */

package Assignment_5;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static void printRoom(LabRoom room) {
        System.out.println(room);
        if (room.getDevices().isEmpty()) {
            System.out.println("  (khong co thiet bi)");
        }
        for (Device d : room.getDevices()) {
            System.out.println("  - " + d);
        }
    }

    public static void main(String[] args) {
        // Khoi tao
        Computer pc1 = new Computer("PC01", "Dell Precision", 2018, 30000000, 32, "Intel i7", true);
        Computer pc2 = new Computer("PC02", "HP ProDesk", 2024, 15000000, 16, "Intel i5", false);

        NetworkPrinter pr1 = new NetworkPrinter("PR01", "Canon LBP", 2022, 8000000,
              PrinterType.Laser, 150000, true);
        Printer pr2 = new Printer("PR02", "Epson L3210", 2023, 4000000,
              PrinterType.Inkjet, 20000, false);

        Projector pj1 = new Projector("PJ01", "Epson EB-X06", 2019, 12000000, 3600, 3500);

        pc2.setStatus(DeviceStatus.UnderMaintenance);

        LabRoom room1 = new LabRoom("R101", "Phong Lab 1", 40);
        LabRoom room2 = new LabRoom("R102", "Phong Lab 2", 30);

        // 1. Them thiet bi vao phong
        room1.addDevice(pc1);
        room1.addDevice(pc2);
        room1.addDevice(pr1);
        room2.addDevice(pr2);
        room2.addDevice(pj1);

        // 2. Thu them thiet bi trung ma
        try {
            room1.addDevice(new Computer("PC01", "May trung ma", 2024, 10000000, 8, "Intel i3", false));
        } catch (IllegalArgumentException e) {
            System.out.println("Loi : " + e.getMessage());
        }

        // 3. In danh sach thiet bi tung phong
        printRoom(room1);
        printRoom(room2);

        // 4. Tinh tong chi phi bao tri du kien cua moi phong
        System.out.println(room1.getName() + ": " + room1.calculateAnnualMaintenanceCost() + " VND");
        System.out.println(room2.getName() + ": " + room2.calculateAnnualMaintenanceCost() + " VND");

        // 5. Liet ke thiet bi can bao tri
        for (LabRoom room : new LabRoom[]{room1, room2}) {
            System.out.println(room.getName() + ":");
            List<Device> need = room.getDevicesRequiringMaintenance();
            for (Device d : need) {
                System.out.println("  - " + d.getId() + " " + d.getName()
                      + " (" + d.getStatus() + ", " + d.getYearsInUse() + " nam)");
            }
        }

        // 6. Ket noi mang cho cac doi tuong implement INetworkable
        List<INetworkable> networkDevices = new ArrayList<>();
        for (LabRoom room : new LabRoom[]{room1, room2}) {
            for (Device d : room.getDevices()) {
                if (d instanceof INetworkable) {
                    networkDevices.add((INetworkable) d);
                }
            }
        }
        int ipCounter = 10;
        for (INetworkable n : networkDevices) {
            n.connect("192.168.1." + ipCounter++);
        }
        System.out.println("Da ket noi " + networkDevices.size() + " thiet bi mang");

        // 7. Duyet qua INetworkable
        for (INetworkable n : networkDevices) {
            System.out.println("IP: " + n.getIpAddress() + " | Connected: " + n.isConnected());
        }
    }
}
