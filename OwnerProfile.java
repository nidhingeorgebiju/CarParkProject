import java.util.Date;

// Custom immutable class
public final class OwnerProfile {
    private final String ownerName;
    private final Date registrationDate;

    public OwnerProfile(String ownerName, Date registrationDate) {
        this.ownerName = ownerName;
        this.registrationDate = new Date(registrationDate.getTime()); // Defensive copy
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Date getRegistrationDate() {
        return new Date(registrationDate.getTime()); // Defensive copy
    }
}
