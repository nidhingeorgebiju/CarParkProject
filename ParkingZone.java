public enum ParkingZone {
    GROUND_FLOOR(2.50),
    ROOFTOP(1.50),
    VIP_UNDERGROUND(5.00);

    private final double hourlyRate;

    ParkingZone(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}
