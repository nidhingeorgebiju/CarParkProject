import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class CarParkSystem {
    private final List<Vehicle> parkedVehicles = new ArrayList<>();
    private final int maxCapacity;

    public CarParkSystem(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.maxCapacity = maxCapacity;
    }

    public void parkVehicles(Vehicle... vehicles) throws CarParkFullException {
        for (var v : vehicles) {
            if (parkedVehicles.size() >= maxCapacity) {
                throw new CarParkFullException("Cannot park " + v.getLicensePlate() + ": Car park is full!");
            }
            parkedVehicles.add(v);
        }
    }

    public void printFilteredVehicles(ParkingZone targetZone) {
        Predicate<Vehicle> zoneFilter = v -> v.getZone() == targetZone;

        System.out.println("\n--- Vehicles in " + targetZone + " ---");
        parkedVehicles.stream()
                      .filter(zoneFilter)
                      .map(Vehicle::getVehicleSummary)
                      .forEach(System.out::println);
    }

    public String inspectVehicleType(Vehicle v) {
        return switch (v) {
            case ElectricCar ev -> "Eco-friendly EV (" + ev.getLicensePlate() + ")";
            case Car c          -> "Standard Combustion Car (" + c.getLicensePlate() + ")";
        };
    }

    // Java 26/27 primitive switch pattern matching (JEP 532)
    public String categorizeStayDuration(int hours) {
        return switch (hours) {
            case int h when h <= 0 -> "Invalid duration";
            case 1                 -> "Quick drop-off (1 hour)";
            case int h when h <= 4 -> "Short stay (" + h + " hours)";
            case int h             -> "Long stay (" + h + " hours - eligible for day rate)";
        };
    }

    public String generateDailyReport(ParkingTicket[] tickets) {
        var sb = new StringBuilder("\n=== DAILY TICKET REPORT ===\n");
        for (ParkingTicket t : tickets) {
            sb.append("Ticket #").append(t.ticketId())
              .append(" | Plate: ").append(t.licensePlate())
              .append(" | Paid: EUR ").append(String.format("%.2f", t.totalPaid()))
              .append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Billable.printTaxNotice();

        var carPark = new CarParkSystem(5);

        Date rawDate = new Date();
        OwnerProfile owner = new OwnerProfile("Alex Murphy", rawDate);
        rawDate.setTime(0); // Verify immutability
        System.out.println("Immutable Owner Registered: " + owner.getRegistrationDate());

        Vehicle v1 = new Car("261-WH-101", ParkingZone.GROUND_FLOOR);
        Vehicle v2 = new ElectricCar("262-D-999", ParkingZone.VIP_UNDERGROUND, true);
        Vehicle v3 = new Car("251-G-404", ParkingZone.ROOFTOP);

        try {
            carPark.parkVehicles(v1, v2, v3);
        } catch (CarParkFullException e) {
            System.err.println("Alert: " + e.getMessage());
        }

        System.out.println("\n--- Vehicle Inspections ---");
        for (Vehicle v : carPark.parkedVehicles) {
            System.out.println(carPark.inspectVehicleType(v) + " -> 3hr Fee: " + v.getFormattedReceipt(3));
        }

        carPark.printFilteredVehicles(ParkingZone.VIP_UNDERGROUND);

        System.out.println("\nStay check (6 hrs): " + carPark.categorizeStayDuration(6));

        ParkingTicket[] completedTickets = {
            new ParkingTicket("T01", v1.getLicensePlate(), 3, v1.calculateFee(3)),
            new ParkingTicket("T02", v2.getLicensePlate(), 3, v2.calculateFee(3))
        };
        System.out.println(carPark.generateDailyReport(completedTickets));
    }
}
