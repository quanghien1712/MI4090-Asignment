/*
    Trần Quang Hiển
    202418893
 */

package Assignment_5;

public abstract class  Device {
    protected final String id;
    protected String name;
    protected int yearInService;
    protected double purchasePrice;
    protected DeviceStatus status;

    protected Device(String id, String name, int yearInService, double purchasePrice) {
        this.id = id;
        this.name = name;
        this.yearInService = yearInService;
        this.purchasePrice = purchasePrice;
        this.status = DeviceStatus.Active;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public int getYearInService() { return yearInService; }

    public double getPurchasePrice() { return purchasePrice; }

    public DeviceStatus getStatus() { return status; }

    public void setStatus(DeviceStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Trang thai khong duoc null");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Nam SD: %d | Gia mua: %,.0f VND | Trang thai: %s",
              id, name, yearInService, purchasePrice, status);
    }
}
