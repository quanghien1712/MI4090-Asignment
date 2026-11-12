/*
    Trần Quang Hiển
    202418893
*/
package Assignment_10;

public class Main {
    public static void main(String[] args) {
        // T1: Tạo light, fan, door bằng Factory -> kiểm tra đúng kiểu đối tượng
        System.out.println("=== T1: Factory tạo light, fan, door ===");
        Device light = DeviceFactory.create("light", "Living room light");
        Device fan = DeviceFactory.create("fan", "Living room fan");
        Device door = DeviceFactory.create("door", "Main door");
        System.out.println("light -> " + light.getClass().getSimpleName()
              + " | instanceof SmartLight: " + (light instanceof SmartLight));
        System.out.println("fan   -> " + fan.getClass().getSimpleName()
              + " | instanceof SmartFan: " + (fan instanceof SmartFan));
        System.out.println("door  -> " + door.getClass().getSimpleName()
              + " | instanceof SmartDoor: " + (door instanceof SmartDoor)
              + " | instanceof Lockable: " + (door instanceof Lockable));
        // Chuỗi đầu vào không phân biệt hoa/thường và có khoảng trắng
        Device upper = DeviceFactory.create("  LIGHT ", "Bedroom light");
        System.out.println("\"  LIGHT \" -> " + upper.getClass().getSimpleName());

        // Hiển thị trạng thái mẫu của các thiết bị
        light.turnOn();
        fan.turnOff();
        ((Lockable) door).lock();
        System.out.println();

        // T2: Tạo loại camera khi chưa hỗ trợ -> phát sinh IllegalArgumentException
        System.out.println("=== T2: Tạo thiết bị camera (chưa hỗ trợ) ===");
        try {
            DeviceFactory.create("camera", "Front camera");
            System.out.println("Không có exception được ném ra");
        } catch (IllegalArgumentException e) {
            System.out.println("Bắt được IllegalArgumentException: " + e.getMessage());
        }
        System.out.println();

        // T3: Lấy HomeConfiguration.getInstance() hai lần -> cùng tham chiếu
        System.out.println("=== T3: Singleton HomeConfiguration ===");
        HomeConfiguration c1 = HomeConfiguration.getInstance();
        HomeConfiguration c2 = HomeConfiguration.getInstance();
        System.out.println("c1 == c2: " + (c1 == c2));
        System.out.println();

        // Tạo facade từ các thiết bị đã tạo bằng Factory
        SmartHomeFacade facade = new SmartHomeFacade(light, fan, (Lockable) door);

        // T4: Gọi goToWork() -> đèn tắt, quạt tắt, cửa khóa
        System.out.println("=== T4: goToWork() ===");
        facade.goToWork();
        System.out.println();

        // T5: Gọi goToSleep() -> quạt tắt, cửa khóa, đèn bật, hiện mức sáng
        System.out.println("=== T5: goToSleep() ===");
        facade.goToSleep();
    }
}