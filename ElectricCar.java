public final class ElectricCar extends Vehicle {
    private final boolean needsCharging;

    public ElectricCar(String licensePlate, ParkingZone zone, boolean needsCharging) {
        super(licensePlate, zone);
        this.needsCharging = needsCharging;
    }

    @Override
    public double calculateFee(int hours) {
        double baseFee = super.calculateFee(hours);
        return needsCharging ? baseFee + 3.00 : baseFee;
    }

    @Override
    public String getVehicleSummary() {
        return super.getVehicleSummary() + " | EV Charging: " + needsCharging;
    }
}
