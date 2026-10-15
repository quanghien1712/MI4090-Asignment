/*
    Trần Quang Hiển
    202418893
*/

package Assignment_6;

import Assignment_6.exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    @FunctionalInterface
    private interface ThrowingAction {
        void run() throws Exception;
    }

    // ===================== Tien ich in ket qua tung buoc =====================

    private static void step(String name, boolean success) {
        System.out.println("  [" + (success ? "THANH CONG" : "THAT BAI") + "] " + name);
    }

    private static void stepFail(String name, Exception e) {
        System.out.println("  [THAT BAI] " + name + " -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
    }

    // Buoc thanh cong khi action nem dung loai ngoai le mong doi
    private static void expectException(String name, Class<? extends Exception> expected, ThrowingAction action) {
        try {
            action.run();
            System.out.println("  [THAT BAI] " + name + " -> khong nem ngoai le nao");
        } catch (Exception e) {
            if (expected.isInstance(e)) {
                System.out.println("  [THANH CONG] " + name + " -> bat duoc "
                      + e.getClass().getSimpleName() + ": " + e.getMessage());
            } else {
                System.out.println("  [THAT BAI] " + name + " -> mong doi " + expected.getSimpleName()
                      + " nhung nhan " + e.getClass().getSimpleName());
            }
        }
    }

    private static Device newProjector(String id) {
        return new ProjectorRemote(id, "Dieu khien may chieu", 2023, 1_000_000);
    }

    private static Device newAirCon(String id) {
        return new AirConditionerRemote(id, "Dieu khien dieu hoa", 2023, 500_000);
    }

    private static Device newMic(String id) {
        return new WirelessMicrophone(id, "Micro khong day", 2023, 2_000_000);
    }

    // ===================== 11.1. Luong thanh cong =====================

    private static void testSuccessFlow() {
        System.out.println("=== 11.1. LUONG THANH CONG ===");
        LocalDate borrowDate = LocalDate.of(2026, 10, 1);

        try {
            // 1. Tao mot giang vien
            Lecturer lecturer = new Lecturer("GV001", "Nguyen Van A");
            step("1. Tao mot giang vien", lecturer.getBorrowerId().equals("GV001"));

            // 2. Tao dieu khien may chieu, dieu khien dieu hoa, micro khong day
            Device projector = newProjector("PJ01");
            Device airCon = newAirCon("AC01");
            Device mic = newMic("MIC01");
            step("2. Tao dieu khien may chieu, dieu khien dieu hoa, micro khong day",
                  projector.getStatus() == DeviceStatus.AVAILABLE
                        && airCon.getStatus() == DeviceStatus.AVAILABLE
                        && mic.getStatus() == DeviceStatus.AVAILABLE);

            // 3. Tao phieu muon cho mot phong day
            BorrowingSlip slip = new BorrowingSlip("S001", lecturer, "P.301", borrowDate);
            step("3. Tao phieu muon cho mot phong day", slip.getItems().isEmpty());

            // 4. Them dieu khien may chieu va micro vao phieu
            slip.addDevice(projector, 7, 1, 3);
            slip.addDevice(mic, 1, 1, 3);
            step("4. Them dieu khien may chieu va micro vao phieu",
                  slip.getItems().size() == 2
                        && projector.getStatus() == DeviceStatus.BORROWED
                        && mic.getStatus() == DeviceStatus.BORROWED);

            // 5. Tra dieu khien may chieu dung han
            double projectorFee = slip.returnDevice("PJ01", borrowDate.plusDays(7));
            step("5. Tra dieu khien may chieu dung han (phi tre = " + projectorFee + ")",
                  projectorFee == 0 && projector.getStatus() == DeviceStatus.AVAILABLE);

            // 6. Tra micro muon 2 ngay
            double micFee = slip.returnDevice("MIC01", borrowDate.plusDays(1 + 2));
            step("6. Tra micro muon 2 ngay (phi tre = " + micFee + ")",
                  micFee == 30000 && mic.getStatus() == DeviceStatus.AVAILABLE);

            // 7. Hien thi cac phieu tra muon, chua tra
            BorrowingSlip slip2 = new BorrowingSlip("S002", lecturer, "P.302", borrowDate);
            slip2.addDevice(airCon, 5, 4, 6); // chua tra

            BorrowingSlip slip3 = new BorrowingSlip("S003", lecturer, "P.303", borrowDate);
            Device projector2 = newProjector("PJ02");
            slip3.addDevice(projector2, 3, 1, 2);
            slip3.returnDevice("PJ02", borrowDate.plusDays(3)); // tra dung han

            List<BorrowingSlip> allSlips = List.of(slip, slip2, slip3);
            List<BorrowingSlip> problemSlips = getLateOrUnreturnedSlips(allSlips);

            System.out.println("  Cac phieu tra muon / chua tra:");
            for (BorrowingSlip s : problemSlips) {
                System.out.println("  " + s.toString().replace("\n", "\n  "));
            }
            step("7. Hien thi cac phieu tra muon, chua tra", problemSlips.size() == 2);
        } catch (Exception e) {
            stepFail("Luong thanh cong bi ngat", e);
        }
    }

    // Loc cac phieu co thiet bi tra muon hoac chua tra
    private static List<BorrowingSlip> getLateOrUnreturnedSlips(List<BorrowingSlip> slips) {
        List<BorrowingSlip> result = new ArrayList<>();
        for (BorrowingSlip s : slips) {
            if (s.hasLateOrUnreturnedItems()) {
                result.add(s);
            }
        }
        return result;
    }

    // ===================== 11.2. Luong phat sinh ngoai le =====================

    private static void testExceptionFlows() {
        System.out.println("\n=== 11.2. LUONG PHAT SINH NGOAI LE ===");
        LocalDate borrowDate = LocalDate.of(2026, 10, 1);
        Lecturer lecturer = new Lecturer("GV001", "Nguyen Van A");
        TeachingAssistant assistant = new TeachingAssistant("TG001", "Tran Thi B");

        try {
            // 1. Muon dieu khien dieu hoa dang UnderMaintenance
            Device maintAirCon = newAirCon("AC_M");
            maintAirCon.markAsUnderMaintenance();
            BorrowingSlip slip1 = new BorrowingSlip("E01", lecturer, "P.301", borrowDate);
            expectException("1. Muon dieu khien dieu hoa dang UnderMaintenance",
                  DeviceUnavailableException.class,
                  () -> slip1.addDevice(maintAirCon, 3, 1, 2));

            // 2. Tro giang muon thiet bi thu ba
            BorrowingSlip slip2 = new BorrowingSlip("E02", assistant, "P.302", borrowDate);
            slip2.addDevice(newProjector("PJ_1"), 3, 1, 2);
            slip2.addDevice(newAirCon("AC_1"), 3, 1, 2);
            Device third = newMic("MIC_1");
            expectException("2. Tro giang muon thiet bi thu ba",
                  BorrowLimitExceededException.class,
                  () -> slip2.addDevice(third, 1, 1, 2));

            // 3. Them cung mot micro hai lan
            BorrowingSlip slip3 = new BorrowingSlip("E03", lecturer, "P.303", borrowDate);
            Device mic = newMic("MIC_2");
            slip3.addDevice(mic, 1, 1, 2);
            expectException("3. Them cung mot micro hai lan",
                  DuplicateDeviceException.class,
                  () -> slip3.addDevice(mic, 1, 1, 2));

            // 4. Muon micro 3 ngay du gioi han la 1 ngay
            BorrowingSlip slip4 = new BorrowingSlip("E04", lecturer, "P.304", borrowDate);
            Device mic2 = newMic("MIC_3");
            expectException("4. Muon micro 3 ngay du gioi han la 1 ngay",
                  InvalidBorrowPeriodException.class,
                  () -> slip4.addDevice(mic2, 3, 1, 2));

            // 5. Tra mot dieu khien may chieu khong thuoc phieu
            BorrowingSlip slip5 = new BorrowingSlip("E05", lecturer, "P.305", borrowDate);
            Device other = newProjector("PJ_OTHER");
            expectException("5. Tra mot dieu khien may chieu khong thuoc phieu",
                  DeviceNotInSlipException.class,
                  () -> slip5.returnDevice(other.getId(), borrowDate.plusDays(1)));

            // 6. Tra cung mot thiet bi hai lan
            BorrowingSlip slip6 = new BorrowingSlip("E06", lecturer, "P.306", borrowDate);
            slip6.addDevice(newProjector("PJ_6"), 3, 1, 2);
            slip6.returnDevice("PJ_6", borrowDate.plusDays(2));
            expectException("6. Tra cung mot thiet bi hai lan",
                  DeviceAlreadyReturnedException.class,
                  () -> slip6.returnDevice("PJ_6", borrowDate.plusDays(2)));

            // 7. Tra thiet bi voi ngay tra truoc ngay muon
            BorrowingSlip slip7 = new BorrowingSlip("E07", lecturer, "P.307", borrowDate);
            slip7.addDevice(newProjector("PJ_7"), 3, 1, 2);
            expectException("7. Tra thiet bi voi ngay tra truoc ngay muon",
                  InvalidReturnDateException.class,
                  () -> slip7.returnDevice("PJ_7", borrowDate.minusDays(1)));
        } catch (Exception e) {
            stepFail("Chuan bi du lieu cho luong ngoai le bi loi", e);
        }
    }

    // ===================== main =====================

    public static void main(String[] args) {
        testSuccessFlow();
        testExceptionFlows();
    }
}