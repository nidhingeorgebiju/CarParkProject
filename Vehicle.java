import java.time.LocalDateTime;

public sealed abstract class Vehicle implements Billable permits Car, ElectricCar {
    private final String licensePlate;
    private final ParkingZone zone;
    private final LocalDateTime entryTime;

    public Vehicle(String licensePlate) {
        this(licensePlate, ParkingZone.GROUND_FLOOR);
    }

    public Vehicle(String licensePlate, ParkingZone zone) {
        this.licensePlate = licensePlate;
        this.zone = zone;
        this.entryTime = LocalDateTime.now();
    }

    public String getLicensePlate() { return licensePlate; }
    public ParkingZone getZone() { return zone; }
    public LocalDateTime getEntryTime() { return entryTime; }

    public double calculateFee(int hours) {
        return hours * zone.getHourlyRate();
    }

    // Overloaded method
    public double calculateFee(int hours, double discountPercent) {
        double base = calculateFee(hours);
        return base - (base * (discountPercent / 100.0));
    }

    public String getVehicleSummary() {
        return "Plate: " + licensePlate + " | Zone: " + zone;
    }
}
