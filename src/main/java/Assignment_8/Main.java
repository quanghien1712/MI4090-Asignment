/*
    Trần Quang Hiển
    202418893
*/
package Assignment_8;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    interface TestBody { boolean run() throws Exception; }

    private static int counter = 0;

    private static void test(String name, TestBody body) {
        counter++;
        String label = String.format("Test %02d: %s", counter, name);
        try {
            System.out.println((body.run() ? "[ĐẠT]       " : "[KHÔNG ĐẠT] ") + label);
        } catch (Throwable ex) {
            System.out.println("[KHÔNG ĐẠT] " + label + " -> ngoại lệ: " + ex);
        }
    }

    /** Chạy r và ẩn stdout/stderr để màn hình chỉ còn kết quả test. */
    private static void quiet(Runnable r) {
        PrintStream o = System.out, e = System.err;
        PrintStream nul = new PrintStream(new ByteArrayOutputStream());
        System.setOut(nul);
        System.setErr(nul);
        try { r.run(); } finally { System.setOut(o); System.setErr(e); }
    }

    private static boolean throwsInvalid(Runnable r, String id, int value) {
        try {
            r.run();
            return false;
        } catch (InvalidDeviceReadingException ex) {
            return ex.getDeviceId().equals(id) && ex.getInvalidValue() == value;
        }
    }

    /** Ghi lại các cảnh báo của thiết bị vào một danh sách. */
    private static List<DeviceAlertEvent> alerts(TeachingDevice d) {
        List<DeviceAlertEvent> list = new ArrayList<>();
        d.alertRaised().subscribe((s, e) -> list.add(e));
        return list;
    }

    public static void main(String[] args) {
        System.out.println("===== TEST DELEGATE & EVENT =====");

        // ---------- TeachingDevice: StatusChanged ----------
        test("Đổi trạng thái -> phát 1 event, đúng DeviceId/Old/New, sender là thiết bị", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            List<DeviceStatusChangedEvent> got = new ArrayList<>();
            Object[] sender = new Object[1];
            p.statusChanged().subscribe((s, e) -> { got.add(e); sender[0] = s; });
            p.changeStatus(DeviceStatus.BORROWED);
            return got.size() == 1 && sender[0] == p
                  && got.get(0).deviceId().equals("P1")
                  && got.get(0).oldStatus() == DeviceStatus.AVAILABLE
                  && got.get(0).newStatus() == DeviceStatus.BORROWED
                  && got.get(0).changedAt() != null;
        });

        test("Trạng thái mới trùng trạng thái cũ -> KHÔNG phát event", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            int[] n = {0};
            p.statusChanged().subscribe((s, e) -> n[0]++);
            p.changeStatus(DeviceStatus.AVAILABLE);
            return n[0] == 0;
        });

        test("Trạng thái được cập nhật TRƯỚC khi phát event", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            DeviceStatus[] seen = new DeviceStatus[1];
            p.statusChanged().subscribe((s, e) -> seen[0] = p.getStatus());
            p.changeStatus(DeviceStatus.UNDER_MAINTENANCE);
            return seen[0] == DeviceStatus.UNDER_MAINTENANCE;
        });

        // ---------- Event: subscribe / unsubscribe / ổn định ----------
        test("Nhiều subscriber cùng nhận event; đăng ký trùng handler chỉ nhận 1 lần", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            int[] a = {0}, b = {0};
            EventHandler<DeviceStatusChangedEvent> h1 = (s, e) -> a[0]++;
            p.statusChanged().subscribe(h1);
            p.statusChanged().subscribe(h1);
            p.statusChanged().subscribe((s, e) -> b[0]++);
            p.changeStatus(DeviceStatus.BORROWED);
            return a[0] == 1 && b[0] == 1;
        });

        test("Unsubscribe -> handler không nhận event nữa; unsubscribe handler lạ không lỗi", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            int[] n = {0};
            EventHandler<DeviceStatusChangedEvent> h = (s, e) -> n[0]++;
            p.statusChanged().subscribe(h);
            p.changeStatus(DeviceStatus.BORROWED);
            p.statusChanged().unsubscribe(h);
            p.statusChanged().unsubscribe((s, e) -> { });
            p.changeStatus(DeviceStatus.AVAILABLE);
            return n[0] == 1;
        });

        test("Subscribe handler null -> IllegalArgumentException", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            try { p.alertRaised().subscribe(null); return false; }
            catch (IllegalArgumentException ex) { return true; }
        });

        test("Handler ném lỗi -> handler khác vẫn chạy, thiết bị vẫn hoạt động", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            int[] n = {0};
            p.statusChanged().subscribe((s, e) -> { throw new RuntimeException("giả lập"); });
            p.statusChanged().subscribe((s, e) -> n[0]++);
            quiet(() -> p.changeStatus(DeviceStatus.BORROWED));
            return n[0] == 1 && p.getStatus() == DeviceStatus.BORROWED;
        });

        // ---------- ProjectorRemote ----------
        test("Projector: 50->19 Warning; 19->10 không lặp; 10->0 Critical; 0->0 không lặp", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            List<DeviceAlertEvent> a = alerts(p);
            p.updateBatteryLevel(19);
            p.updateBatteryLevel(10);
            p.updateBatteryLevel(0);
            p.updateBatteryLevel(0);
            return a.size() == 2
                  && a.get(0).severity() == AlertSeverity.WARNING
                  && a.get(1).severity() == AlertSeverity.CRITICAL;
        });

        test("Projector: biên 20 và vùng an toàn/hồi phục -> không cảnh báo", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 80);
            List<DeviceAlertEvent> a = alerts(p);
            p.updateBatteryLevel(50);
            p.updateBatteryLevel(20);
            p.updateBatteryLevel(100);
            return a.isEmpty() && p.getBatteryLevel() == 100;
        });

        test("Projector: 50->0 phát đúng 1 cảnh báo Critical", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            List<DeviceAlertEvent> a = alerts(p);
            p.updateBatteryLevel(0);
            return a.size() == 1 && a.get(0).severity() == AlertSeverity.CRITICAL;
        });

        test("Projector: pin -1 / 101 ném InvalidDeviceReadingException, giá trị cũ giữ nguyên", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            List<DeviceAlertEvent> a = alerts(p);
            boolean low = throwsInvalid(() -> p.updateBatteryLevel(-1), "P1", -1);
            boolean high = throwsInvalid(() -> p.updateBatteryLevel(101), "P1", 101);
            return low && high && p.getBatteryLevel() == 50 && a.isEmpty();
        });

        test("Projector: khởi tạo với pin ngoài 0-100 -> ném ngoại lệ", () ->
              throwsInvalid(() -> new ProjectorRemote("P2", "Bad", 120), "P2", 120));

        // ---------- AirConditionerRemote ----------
        test("AC: phản hồi -> không phản hồi = 1 Critical (có mã phòng); lặp lại không cảnh báo", () -> {
            AirConditionerRemote ac = new AirConditionerRemote("AC1", "AC", "A101");
            List<DeviceAlertEvent> a = alerts(ac);
            ac.updateResponseState(false);
            ac.updateResponseState(false);
            return a.size() == 1 && a.get(0).severity() == AlertSeverity.CRITICAL
                  && a.get(0).message().contains("A101") && !ac.isResponsive();
        });

        test("AC: đang phản hồi mà set true, hoặc phục hồi lại -> không cảnh báo; mất lần 2 -> cảnh báo lại", () -> {
            AirConditionerRemote ac = new AirConditionerRemote("AC1", "AC", "A101");
            List<DeviceAlertEvent> a = alerts(ac);
            ac.updateResponseState(true);
            ac.updateResponseState(false);
            ac.updateResponseState(true);
            ac.updateResponseState(false);
            return a.size() == 2 && !ac.isResponsive();
        });

        // ---------- WirelessMicrophone ----------
        test("Mic pin: 50->30 không cảnh báo; ->29 Warning; ->15 không lặp; ->0 Critical", () -> {
            WirelessMicrophone m = new WirelessMicrophone("M1", "Mic", 50, 80);
            List<DeviceAlertEvent> a = alerts(m);
            m.updateBatteryLevel(30);
            boolean none = a.isEmpty();
            m.updateBatteryLevel(29);
            m.updateBatteryLevel(15);
            m.updateBatteryLevel(0);
            return none && a.size() == 2
                  && a.get(0).severity() == AlertSeverity.WARNING
                  && a.get(1).severity() == AlertSeverity.CRITICAL;
        });

        test("Mic tín hiệu: 80->40 không cảnh báo; ->39 Warning; ->20 không lặp; ->0 Critical", () -> {
            WirelessMicrophone m = new WirelessMicrophone("M1", "Mic", 80, 80);
            List<DeviceAlertEvent> a = alerts(m);
            m.updateSignalQuality(40);
            boolean none = a.isEmpty();
            m.updateSignalQuality(39);
            m.updateSignalQuality(20);
            m.updateSignalQuality(0);
            return none && a.size() == 2
                  && a.get(0).severity() == AlertSeverity.WARNING
                  && a.get(1).severity() == AlertSeverity.CRITICAL;
        });

        test("Mic: pin và tín hiệu cảnh báo độc lập nhau", () -> {
            WirelessMicrophone m = new WirelessMicrophone("M1", "Mic", 80, 80);
            List<DeviceAlertEvent> a = alerts(m);
            m.updateBatteryLevel(10);
            m.updateSignalQuality(10);
            return a.size() == 2
                  && a.get(0).message().toLowerCase().contains("battery")
                  && a.get(1).message().toLowerCase().contains("signal");
        });

        test("Mic: pin/tín hiệu ngoài 0-100 ném ngoại lệ, giá trị cũ giữ nguyên", () -> {
            WirelessMicrophone m = new WirelessMicrophone("M1", "Mic", 60, 70);
            boolean b = throwsInvalid(() -> m.updateBatteryLevel(-5), "M1", -5);
            boolean s = throwsInvalid(() -> m.updateSignalQuality(101), "M1", 101);
            boolean c = throwsInvalid(() -> new WirelessMicrophone("M2", "Bad", 50, 300), "M2", 300);
            return b && s && c && m.getBatteryLevel() == 60 && m.getSignalQuality() == 70;
        });

        // ---------- Subscribers ----------
        test("ConsoleNotificationService in đúng định dạng [Severity] DeviceId: Message", () -> {
            PrintStream old = System.out;
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            System.setOut(new PrintStream(buf, true));
            try {
                new ConsoleNotificationService().handleAlert(null,
                      new DeviceAlertEvent("D1", "Pin yếu", AlertSeverity.WARNING, java.time.LocalDateTime.now()));
            } finally { System.setOut(old); }
            return buf.toString().trim().equals("[WARNING] D1: Pin yếu");
        });

        test("DeviceAuditLogger ghi lịch sử (bỏ qua thay đổi trùng) và danh sách chỉ đọc", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            DeviceAuditLogger log = new DeviceAuditLogger();
            p.statusChanged().subscribe(log::handleStatusChanged);
            p.changeStatus(DeviceStatus.BORROWED);
            p.changeStatus(DeviceStatus.BORROWED);
            p.changeStatus(DeviceStatus.UNDER_MAINTENANCE);
            boolean readOnly;
            try { log.getEntries().add("x"); readOnly = false; }
            catch (UnsupportedOperationException ex) { readOnly = true; }
            return log.getEntries().size() == 2
                  && log.getEntries().get(0).contains("P1")
                  && log.getEntries().get(0).contains("AVAILABLE -> BORROWED")
                  && readOnly;
        });

        test("MaintenanceService chỉ tạo yêu cầu cho Critical; Warning/Information bị bỏ qua", () -> {
            AirConditionerRemote ac = new AirConditionerRemote("AC1", "AC", "A101");
            WirelessMicrophone m = new WirelessMicrophone("M1", "Mic", 80, 80);
            MaintenanceService ms = new MaintenanceService();
            ac.alertRaised().subscribe(ms::handleCriticalAlert);
            m.alertRaised().subscribe(ms::handleCriticalAlert);
            m.updateSignalQuality(30);       // Warning -> bỏ qua
            ac.updateResponseState(false);   // Critical -> tạo yêu cầu
            return ms.getRequests().size() == 1 && ms.getRequests().get(0).contains("AC1");
        });

        // ---------- Delegate / FilteredAlertSubscriber ----------
        test("FilteredAlertSubscriber: filter 'chỉ Critical' lọc đúng, filter true/false hoạt động", () -> {
            int[] crit = {0}, all = {0}, none = {0};
            FilteredAlertSubscriber onlyCrit = new FilteredAlertSubscriber(
                  a -> a.severity() == AlertSeverity.CRITICAL, a -> crit[0]++);
            FilteredAlertSubscriber everything = new FilteredAlertSubscriber(a -> true, a -> all[0]++);
            FilteredAlertSubscriber nothing = new FilteredAlertSubscriber(a -> false, a -> none[0]++);
            WirelessMicrophone m = new WirelessMicrophone("M1", "Mic", 80, 80);
            m.alertRaised().subscribe(onlyCrit::handleAlert);
            m.alertRaised().subscribe(everything::handleAlert);
            m.alertRaised().subscribe(nothing::handleAlert);
            m.updateBatteryLevel(10);   // Warning
            m.updateBatteryLevel(0);    // Critical
            return crit[0] == 1 && all[0] == 2 && none[0] == 0;
        });

        test("FilteredAlertSubscriber: filter hoặc action null -> ném ngoại lệ", () -> {
            boolean f, a;
            try { new FilteredAlertSubscriber(null, x -> { }); f = false; }
            catch (NullPointerException ex) { f = true; }
            try { new FilteredAlertSubscriber(x -> true, null); a = false; }
            catch (NullPointerException ex) { a = true; }
            return f && a;
        });

        // ---------- DeviceMonitoringCoordinator ----------
        test("Coordinator: chưa Subscribe -> không subscriber nào nhận event", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            DeviceAuditLogger log = new DeviceAuditLogger();
            MaintenanceService ms = new MaintenanceService();
            new DeviceMonitoringCoordinator(List.of(p), new ConsoleNotificationService(), log, ms);
            quiet(() -> { p.updateBatteryLevel(0); p.changeStatus(DeviceStatus.BORROWED); });
            return log.getEntries().isEmpty() && ms.getRequests().isEmpty();
        });

        test("Coordinator: Subscribe nhiều lần không đăng ký trùng; nhận event từ nhiều thiết bị", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            AirConditionerRemote ac = new AirConditionerRemote("AC1", "AC", "A101");
            WirelessMicrophone m = new WirelessMicrophone("M1", "Mic", 80, 80);
            DeviceAuditLogger log = new DeviceAuditLogger();
            MaintenanceService ms = new MaintenanceService();
            DeviceMonitoringCoordinator c = new DeviceMonitoringCoordinator(
                  List.of(p, ac, m), new ConsoleNotificationService(), log, ms);
            c.subscribe();
            c.subscribe();
            quiet(() -> {
                p.updateBatteryLevel(0);          // Critical
                ac.updateResponseState(false);    // Critical
                m.updateSignalQuality(30);        // Warning
                p.changeStatus(DeviceStatus.BORROWED);
                m.changeStatus(DeviceStatus.BORROWED);
            });
            return ms.getRequests().size() == 2 && log.getEntries().size() == 2;
        });

        test("Coordinator: Unsubscribe hủy đúng đăng ký của nó, handler bên ngoài vẫn còn", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            DeviceAuditLogger log = new DeviceAuditLogger();
            DeviceMonitoringCoordinator c = new DeviceMonitoringCoordinator(
                  List.of(p), new ConsoleNotificationService(), log, new MaintenanceService());
            int[] external = {0};
            p.statusChanged().subscribe((s, e) -> external[0]++);
            c.subscribe();
            p.changeStatus(DeviceStatus.BORROWED);
            c.unsubscribe();
            c.unsubscribe();                               // gọi lại không lỗi
            p.changeStatus(DeviceStatus.AVAILABLE);
            return log.getEntries().size() == 1 && external[0] == 2;
        });

        test("Coordinator: Dispose()/close() hủy đăng ký (try-with-resources), gọi lại không lỗi", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            DeviceAuditLogger log = new DeviceAuditLogger();
            DeviceMonitoringCoordinator c;
            try (DeviceMonitoringCoordinator cc = new DeviceMonitoringCoordinator(
                  List.of(p), new ConsoleNotificationService(), log, new MaintenanceService())) {
                c = cc;
                cc.subscribe();
                p.changeStatus(DeviceStatus.BORROWED);
            }
            c.close();
            p.changeStatus(DeviceStatus.AVAILABLE);
            return log.getEntries().size() == 1;
        });

        // ---------- Sửa danh sách handler trong lúc đang publish ----------
        test("Handler tự unsubscribe khi đang publish -> không ném ngoại lệ, handler còn lại vẫn nhận event", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            int[] h2 = {0}, h3 = {0};
            EventHandler<DeviceStatusChangedEvent>[] self = new EventHandler[1];
            self[0] = (s, e) -> p.statusChanged().unsubscribe(self[0]);
            p.statusChanged().subscribe(self[0]);
            p.statusChanged().subscribe((s, e) -> h2[0]++);
            p.statusChanged().subscribe((s, e) -> h3[0]++);
            p.changeStatus(DeviceStatus.BORROWED);          // không được ném ConcurrentModificationException
            p.changeStatus(DeviceStatus.AVAILABLE);         // lần 2: handler đầu đã hủy, hai handler kia vẫn chạy
            return h2[0] == 2 && h3[0] == 2;
        });

        test("Handler hủy một handler khác (chưa chạy) khi đang publish -> không ném ngoại lệ", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            int[] last = {0};
            EventHandler<DeviceStatusChangedEvent> victim = (s, e) -> { };
            EventHandler<DeviceStatusChangedEvent> last3 = (s, e) -> last[0]++;
            p.statusChanged().subscribe((s, e) -> p.statusChanged().unsubscribe(victim));
            p.statusChanged().subscribe(victim);
            p.statusChanged().subscribe(last3);
            p.changeStatus(DeviceStatus.BORROWED);
            return last[0] == 1;
        });

        test("Handler subscribe thêm handler mới khi đang publish -> không ném ngoại lệ, handler mới nhận từ lần sau", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            int[] added = {0}, other = {0};
            boolean[] done = {false};
            p.statusChanged().subscribe((s, e) -> {
                if (!done[0]) {
                    done[0] = true;
                    p.statusChanged().subscribe((s2, e2) -> added[0]++);
                }
            });
            p.statusChanged().subscribe((s, e) -> other[0]++);
            p.changeStatus(DeviceStatus.BORROWED);          // không được ném ngoại lệ
            p.changeStatus(DeviceStatus.AVAILABLE);
            return other[0] == 2 && added[0] >= 1;
        });

        test("Lỗi do sửa danh sách handler không được lọt ra làm hỏng thiết bị (trạng thái vẫn cập nhật)", () -> {
            ProjectorRemote p = new ProjectorRemote("P1", "Projector", 50);
            EventHandler<DeviceAlertEvent>[] self = new EventHandler[1];
            self[0] = (s, e) -> p.alertRaised().unsubscribe(self[0]);
            p.alertRaised().subscribe(self[0]);
            p.alertRaised().subscribe((s, e) -> { });
            p.alertRaised().subscribe((s, e) -> { });
            try {
                p.updateBatteryLevel(0);
            } catch (RuntimeException ex) {
                return false;                               // publish làm lỗi lọt ra ngoài publisher
            }
            return p.getBatteryLevel() == 0;
        });

        // ---------- Thiết kế ----------
        test("Thiết kế: raiseAlert/onAlertRaised/onStatusChanged là protected; TeachingDevice không giữ tham chiếu tới service", () -> {
            int prot = 0;
            for (java.lang.reflect.Method m : TeachingDevice.class.getDeclaredMethods()) {
                String n = m.getName();
                if ((n.equals("raiseAlert") || n.equals("onAlertRaised") || n.equals("onStatusChanged"))
                      && java.lang.reflect.Modifier.isProtected(m.getModifiers())) {
                    prot++;
                }
            }
            boolean noDeps = true;
            for (java.lang.reflect.Field f : TeachingDevice.class.getDeclaredFields()) {
                Class<?> t = f.getType();
                if (t == DeviceAuditLogger.class || t == MaintenanceService.class
                      || t == ConsoleNotificationService.class) {
                    noDeps = false;
                }
            }
            return prot == 3 && noDeps;
        });
    }
}