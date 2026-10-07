/*
    Trần Quang Hiển
    202418893
 */

package Assignment_5;

public class NetworkPrinter extends Printer implements INetworkable {
    private String ipAddress;
    private boolean isConnected;

    public NetworkPrinter(String id, String name, int yearInService, double purchasePrice,
                          PrinterType type, int pagesPrinted, boolean isColor) {
        super(id, name, yearInService, purchasePrice, type, pagesPrinted, isColor);
    }

    @Override
    public void connect(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            throw new IllegalArgumentException("Dia chi IP khong duoc rong");
        }
        if (isConnected) {
            throw new IllegalStateException("May in " + id + " dang ket noi, khong the ket noi lai");
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
        return super.toString() + " | Mang: " + (isConnected ? "IP " + ipAddress : "chua ket noi");
    }
}
