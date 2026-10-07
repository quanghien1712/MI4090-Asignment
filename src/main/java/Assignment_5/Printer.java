/*
    Trần Quang Hiển
    202418893
 */

package Assignment_5;

public class Printer extends Device {
    protected PrinterType type;
    protected int pagesPrinted;
    protected boolean isColor;

    public Printer(String id, String name, int yearInService, double purchasePrice,
                   PrinterType type, int pagesPrinted, boolean isColor) {
        super(id, name, yearInService, purchasePrice);
        if (type == null) {
            throw new IllegalArgumentException("Loai may in khong duoc null");
        }
        if (pagesPrinted < 0) {
            throw new IllegalArgumentException("So trang da in khong duoc am");
        }
        this.type = type;
        this.pagesPrinted = pagesPrinted;
        this.isColor = isColor;
    }

    public PrinterType getType() { return type; }

    public int getPagesPrinted() { return pagesPrinted; }

    public boolean isColor() { return isColor; }

    @Override
    public double calculateMaintenanceCost() {
        double cost = purchasePrice * 0.04;
        if (pagesPrinted > 100_000) cost += 500_000;
        if (isColor) cost += 300_000;
        return cost;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Printer: %s, %,d trang, %s",
              type, pagesPrinted, isColor ? "in mau" : "in den trang");
    }
}