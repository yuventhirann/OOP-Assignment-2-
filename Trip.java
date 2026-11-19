public class Trip {
    private int tripId;
    private Car car;
    private Driver driver;
    private String destination;
    private double distanceKm;
    private double fuelUsed;
    private boolean completed;

    public Trip(int tripId, Car car, Driver driver, String destination, double distanceKm) {
        if (tripId <= 0 || car == null || driver == null || destination == null
                || destination.trim().isEmpty() || !Double.isFinite(distanceKm)
                || distanceKm <= 0) {
            throw new IllegalArgumentException("Invalid trip details.");
        }
        this.tripId = tripId;
        this.car = car;
        this.driver = driver;
        this.destination = destination.trim();
        this.distanceKm = distanceKm;
        this.fuelUsed = 0.0;
        this.completed = false;
    }

    public int getTripId() { return tripId; }
    public Car getCar() { return car; }
    public Driver getDriver() { return driver; }
    public String getDestination() { return destination; }
    public double getDistanceKm() { return distanceKm; }
    public double getFuelUsed() { return fuelUsed; }
    public boolean isCompleted() { return completed; }

    public boolean completeTrip() {
        if (completed) {
            System.out.println("This trip has already been completed.");
            return false;
        }
        if (!driver.hasValidLicense()) {
            System.out.println("Trip denied: driver has no registered licence.");
            return false;
        }
        double fuelBefore = car.getFuelLevel();
        if (!car.getIsEngineOn()) car.startEngine();
        if (!car.getIsEngineOn()) return false;
        if (car.getCurrentSpeed() == 0) car.accelerate(20);
        if (!car.getIsEngineOn() || car.getCurrentSpeed() == 0) {
            System.out.println("Trip could not begin: car did not accelerate.");
            car.stopEngine();
            return false;
        }

        boolean successful = car.drive(distanceKm);
        fuelUsed = Math.max(0, fuelBefore - car.getFuelLevel());
        car.stopEngine();
        if (successful) {
            completed = true;
            System.out.printf("Trip #%d to %s completed; total fuel used: %.2f%%%n", tripId, destination, fuelUsed);
        } else {
            System.out.println("Trip was not completed; no record will be saved.");
        }
        return successful;
    }

    public void displayInfo() {
        System.out.printf("Trip #%d | %s | %s %s | %s | %.1f km | Fuel used: %.2f%% | %s%n",
                tripId, driver.getName(), car.getBrand(), car.getModel(),
                destination, distanceKm, fuelUsed, completed ? "Completed" : "Incomplete");
    }
}
