package Assignment_11;

public class Main {
    public static void main(String[] args) {
        // 1. Adapter
        System.out.println("=== ADAPTER ===");
        LegacySmokeDetector legacy = new LegacySmokeDetector(735);
        Sensor sensor = new SmokeSensorAdapter(legacy);
        System.out.println(sensor.readValue()); // 73.5
        System.out.println(sensor.getUnit());   // % smoke

        // 2. Mediator + thiết bị
        Alarm alarm = new Alarm();
        DoorController door = new DoorController();
        VentilationController ventilation = new VentilationController();
        IEmergencyMediator mediator = new EmergencyMediator(alarm, door, ventilation);

        // 3. Observer
        SensorMonitor monitor = new SensorMonitor();
        EmailAlertObserver email = new EmailAlertObserver("security@campus.edu");
        monitor.attach(new DashboardObserver());
        monitor.attach(email);
        monitor.attach(new AuditLogObserver());
        monitor.attach(new EmergencyBridgeObserver(mediator));

        System.out.println("\n=== Khoi 30% (binh thuong) ===");
        legacy.setSmokeLevel(300);
        monitor.checkSensor("Lab-A101", sensor);

        System.out.println("\n=== Khoi 55% (WARNING) ===");
        legacy.setSmokeLevel(550);
        monitor.checkSensor("Lab-A101", sensor);

        System.out.println("\n=== Khoi 80% (CRITICAL) ===");
        legacy.setSmokeLevel(800);
        monitor.checkSensor("Lab-A101", sensor);

        System.out.println("\n=== Huy dang ky Email, khoi 60% ===");
        monitor.detach(email);
        legacy.setSmokeLevel(600);
        monitor.checkSensor("Lab-A101", sensor);

        System.out.println("\n=== ALL CLEAR ===");
        mediator.notify(IEmergencyMediator.ALL_CLEAR);
    }
}