/*
    Trần Quang Hiển
    202418893
*/
package Assignment_11;

public final class VentilationController {
    private boolean running;

    public void startExhaust() {
        if (!running) {
            running = true;
            System.out.println("[VENTILATION] Smoke exhaust STARTED");
        }
    }

    public void stop() {
        if (running) {
            running = false;
            System.out.println("[VENTILATION] Exhaust STOPPED");
        }
    }

    public boolean isRunning() {
        return running;
    }
}