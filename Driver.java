public class Driver {
    private String driverId;
    private String name;
    private String licenseNumber;
    private int yearsExperience;

    public Driver(String driverId, String name, String licenseNumber, int yearsExperience) {
        this.driverId = "Unknown";
        this.name = "Unknown";
        this.licenseNumber = "";
        this.yearsExperience = 0;
        setDriverId(driverId);
        setName(name);
        setLicenseNumber(licenseNumber);
        setYearsExperience(yearsExperience);
    }

    public String getDriverId() { return driverId; }
    public String getName() { return name; }
    public String getLicenseNumber() { return licenseNumber; }
    public int getYearsExperience() { return yearsExperience; }

    public void setDriverId(String driverId) {
        if (driverId != null && !driverId.trim().isEmpty()) this.driverId = driverId;
    }
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) this.name = name;
    }
    public void setLicenseNumber(String licenseNumber) {
        if (licenseNumber != null) this.licenseNumber = licenseNumber.trim();
    }
    public void setYearsExperience(int yearsExperience) {
        if (yearsExperience >= 0 && yearsExperience <= 70) {
            this.yearsExperience = yearsExperience;
        }
    }

    public boolean hasValidLicense() {
        return !licenseNumber.isEmpty();
    }

    public void displayInfo() {
        System.out.printf("%s - %s (Licence: %s, Experience: %d years)%n", driverId, name,
                hasValidLicense() ? licenseNumber : "Not registered", yearsExperience);
    }
}
