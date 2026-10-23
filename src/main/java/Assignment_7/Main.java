/*
    Trần Quang Hiển
    202418893
 */

package Assignment_7;

import Assignment_7.exception.DeviceNotReadyException;
import Assignment_7.exception.DuplicateIdException;
import Assignment_7.exception.NoSuitableItemException;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Sample Data
        ProjectorRemote p1 = new ProjectorRemote("PRJ01", "May chieu Epson", "Tu A", 55);
        ProjectorRemote p2 = new ProjectorRemote("PRJ02", "May chieu Sony", "Tu A", 90);
        ProjectorRemote p3 = new ProjectorRemote("PRJ03", "May chieu BenQ", "Tu B", 15);

        AirConditionerRemote ac1 = new AirConditionerRemote("AC01", "Daikin", "Tu C", Set.of("A101", "A102"));
        AirConditionerRemote ac2 = new AirConditionerRemote("AC02", "Panasonic", "Tu C", Set.of("B201"));

        WirelessMicrophone m1 = new WirelessMicrophone("MIC01", "Shure", "Tu D", 20, 80);
        WirelessMicrophone m2 = new WirelessMicrophone("MIC02", "Sennheiser", "Tu D", 80, 75);
        WirelessMicrophone m3 = new WirelessMicrophone("MIC03", "Audio-Technica", "Tu D", 60, 50);

        InMemoryRepository<ProjectorRemote> projectorRepo = new InMemoryRepository<>();
        InMemoryRepository<AirConditionerRemote> acRepo = new InMemoryRepository<>();
        InMemoryRepository<WirelessMicrophone> micRepo = new InMemoryRepository<>();

        // 1 Thêm thiết bị vào repository.
        System.out.println("===== Test 1: Them thiet bi vao repository =====");
        try {
            projectorRepo.add(p1);
            projectorRepo.add(p2);
            projectorRepo.add(p3);
            acRepo.add(ac1);
            acRepo.add(ac2);
            micRepo.add(m1);
            micRepo.add(m2);
            micRepo.add(m3);
        } catch (DuplicateIdException e) {
            System.out.println("Loi: " + e.getMessage());
        }
        System.out.println("Projector repo: " + projectorRepo.getAll());
        System.out.println("AirConditioner repo: " + acRepo.getAll());
        System.out.println("Microphone repo: " + micRepo.getAll());

        // 2. Thử thêm hai thiết bị trùng mã.
        System.out.println("\n===== Test 2: Them hai thiet bi trung ma =====");
        try {
            projectorRepo.add(new ProjectorRemote("PRJ01", "May chieu trung ma", "Tu E", 70));
            System.out.println("Them thanh cong (khong mong doi)");
        } catch (DuplicateIdException e) {
            System.out.println("Bat duoc DuplicateIdException: " + e.getMessage()
                  + " | ma trung = " + e.getDuplicateId());
        }
        System.out.println("So luong projector sau khi thu them: " + projectorRepo.getAll().size());

        // 3. Tìm thiết bị theo mã.
        System.out.println("\n===== Test 3: Tim thiet bi theo ma =====");
        System.out.println("findById(\"AC02\")    = " + acRepo.findById("AC02"));
        System.out.println("findById(\"MIC03\")   = " + micRepo.findById("MIC03"));
        System.out.println("findById(\"XXX\")     = " + projectorRepo.findById("XXX"));
        System.out.println("findById(null)      = " + projectorRepo.findById(null));

        // 4. Lọc các thiết bị sẵn sàng.
        System.out.println("\n===== Test 4: Loc cac thiet bi san sang =====");
        System.out.println("Projector san sang: " + projectorRepo.findAll(TeachingDevice::isReadyForUse));
        System.out.println("AirConditioner san sang: " + acRepo.findAll(TeachingDevice::isReadyForUse));
        System.out.println("Microphone san sang: " + micRepo.findAll(TeachingDevice::isReadyForUse));

        // 5. Chọn điều khiển máy chiếu có điểm cao nhất.
        System.out.println("\n===== Test 5: Chon dieu khien may chieu co diem cao nhat =====");
        AllocationService<ProjectorRemote> projectorService = new AllocationService<>(
              projectorRepo, new HighestPriorityStrategy<>(TeachingDevice::getDefaultPriorityScore));
        try {
            ProjectorRemote chosen = projectorService.allocate(null);
            System.out.println("Duoc chon: " + chosen + " | diem = " + chosen.getDefaultPriorityScore());
        } catch (NoSuitableItemException | DeviceNotReadyException e) {
            System.out.println("Loi: " + e.getMessage());
        }

        // 6. Chọn điều khiển điều hòa tương thích với một phòng.
        System.out.println("\n===== Test 6: Chon dieu khien dieu hoa tuong thich voi mot phong =====");
        AllocationService<AirConditionerRemote> acService = new AllocationService<>(
              acRepo, new HighestPriorityStrategy<>(TeachingDevice::getDefaultPriorityScore));
        final String room1 = "A102";
        try {
            AirConditionerRemote chosen = acService.allocate(ac -> ac.isCompatibleWith(room1));
            System.out.println("Phong " + room1 + " -> duoc chon: " + chosen
                  + " | phong tuong thich = " + chosen.getCompatibleRooms().stream().sorted().toList());
        } catch (NoSuitableItemException | DeviceNotReadyException e) {
            System.out.println("Loi: " + e.getMessage());
        }
        final String room2 = "B201";
        try {
            AirConditionerRemote chosen = acService.allocate(ac -> ac.isCompatibleWith(room2));
            System.out.println("Phong " + room2 + " -> duoc chon: " + chosen
                  + " | phong tuong thich = " + chosen.getCompatibleRooms().stream().sorted().toList());
        } catch (NoSuitableItemException | DeviceNotReadyException e) {
            System.out.println("Loi: " + e.getMessage());
        }

        // 7. Chọn micro đầu tiên sẵn sàng.
        System.out.println("\n===== Test 7: Chon micro dau tien san sang =====");
        AllocationService<WirelessMicrophone> micService = new AllocationService<>(
              micRepo, new FirstAvailableStrategy<>());
        System.out.println("MIC01 san sang? " + m1.isReadyForUse() + " (pin=" + m1.getBatteryLevel()
              + ", tin hieu=" + m1.getSignalQuality() + ") -> bi bo qua");
        try {
            WirelessMicrophone chosen = micService.allocate(null);
            System.out.println("Duoc chon: " + chosen);
        } catch (NoSuitableItemException | DeviceNotReadyException e) {
            System.out.println("Loi: " + e.getMessage());
        }

        // 8. Đổi chiến lược trong thời gian chạy và quan sát kết quả.
        System.out.println("\n===== Test 8: Doi chien luoc trong thoi gian chay =====");
        p2.returnDevice(); // PRJ02 da bi muon o Test 5
        System.out.println("Trang thai ban dau: " + p1 + " ; " + p2);
        try {
            projectorService.changeStrategy(new FirstAvailableStrategy<>());
            ProjectorRemote a = projectorService.allocate(null);
            System.out.println("[FirstAvailableStrategy]  -> " + a);
            a.returnDevice();

            projectorService.changeStrategy(new HighestPriorityStrategy<>(TeachingDevice::getDefaultPriorityScore));
            ProjectorRemote b = projectorService.allocate(null);
            System.out.println("[HighestPriorityStrategy] -> " + b);
            b.returnDevice();
        } catch (NoSuitableItemException | DeviceNotReadyException e) {
            System.out.println("Loi: " + e.getMessage());
        }
        try {
            projectorService.changeStrategy(null);
        } catch (IllegalArgumentException e) {
            System.out.println("changeStrategy(null) -> IllegalArgumentException: " + e.getMessage());
        }

        // 9. Thử cấp phát khi không có ứng viên phù hợp.
        System.out.println("\n===== Test 9: Cap phat khi khong co ung vien phu hop =====");
        try {
            AirConditionerRemote chosen = acService.allocate(ac -> ac.isCompatibleWith("Z999"));
            System.out.println("Duoc chon: " + chosen + " (khong mong doi)");
        } catch (NoSuitableItemException e) {
            System.out.println("Bat duoc NoSuitableItemException: " + e.getMessage());
        } catch (DeviceNotReadyException e) {
            System.out.println("Loi khac: " + e.getMessage());
        }

        // 10. Chứng minh thiết bị được chọn chuyển sang Borrowed.
        System.out.println("\n===== Test 10: Thiet bi duoc chon chuyen sang Borrowed =====");
        WirelessMicrophone target = micRepo.findById("MIC03");
        System.out.println("Truoc khi cap phat: " + target + " | status = " + target.getStatus());
        try {
            WirelessMicrophone chosen = micService.allocate(null);
            System.out.println("Duoc chon: " + chosen.getId() + " | cung doi tuong voi MIC03? " + (chosen == target));
            System.out.println("Sau khi cap phat:   " + target + " | status = " + target.getStatus());
        } catch (NoSuitableItemException | DeviceNotReadyException e) {
            System.out.println("Loi: " + e.getMessage());
        }
        System.out.println("Micro san sang con lai: " + micRepo.findAll(TeachingDevice::isReadyForUse));
        try {
            target.borrow();
            System.out.println("Muon lai MIC03 thanh cong (khong mong doi)");
        } catch (DeviceNotReadyException e) {
            System.out.println("Muon lai MIC03 -> DeviceNotReadyException: " + e.getMessage());
        }

        // 11. Chứng minh repository tổng quát có thể dùng cho ít nhất hai loại thiết bị mà không
        // sao chép mã.
        System.out.println("\n===== Test 11: Repository tong quat dung cho nhieu loai thiet bi =====");
        System.out.println("projectorRepo : " + projectorRepo.getClass().getSimpleName()
              + "<ProjectorRemote>, so luong = " + projectorRepo.getAll().size());
        System.out.println("acRepo        : " + acRepo.getClass().getSimpleName()
              + "<AirConditionerRemote>, so luong = " + acRepo.getAll().size());
        System.out.println("micRepo       : " + micRepo.getClass().getSimpleName()
              + "<WirelessMicrophone>, so luong = " + micRepo.getAll().size());
        System.out.println("Cung mot class InMemoryRepository: "
              + (projectorRepo.getClass() == acRepo.getClass() && acRepo.getClass() == micRepo.getClass()));

        InMemoryRepository<TeachingDevice> mixedRepo = new InMemoryRepository<>();
        try {
            for (TeachingDevice d : projectorRepo.getAll()) mixedRepo.add(d);
            for (TeachingDevice d : acRepo.getAll()) mixedRepo.add(d);
            for (TeachingDevice d : micRepo.getAll()) mixedRepo.add(d);
        } catch (DuplicateIdException e) {
            System.out.println("Loi: " + e.getMessage());
        }
        System.out.println("mixedRepo<TeachingDevice> chua " + mixedRepo.getAll().size() + " thiet bi:");
        for (TeachingDevice d : mixedRepo.getAll()) {
            System.out.println("  - " + d);
        }
    }
}