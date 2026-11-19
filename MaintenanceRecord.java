public class MaintenanceRecord {
    private int recordId;
    private Car car;
    private String serviceType;
    private String serviceDate;
    private double cost;
    private boolean completed;

    public MaintenanceRecord(int recordId, Car car, String serviceType, String serviceDate, double cost) {
        if (recordId <= 0 || car == null || serviceType == null
                || serviceType.trim().isEmpty() || serviceDate == null
                || serviceDate.trim().isEmpty() || !Double.isFinite(cost)
                || cost < 0) {
            throw new IllegalArgumentException("Invalid service record details.");
        }
        this.recordId = recordId;
        this.car = car;
        this.serviceType = serviceType.trim();
        this.serviceDate = serviceDate.trim();
        this.cost = cost;
        this.completed = false;
    }

    public int getRecordId() { return recordId; }
    public Car getCar() { return car; }
    public String getServiceType() { return serviceType; }
    public String getServiceDate() { return serviceDate; }
    public double getCost() { return cost; }
    public boolean isCompleted() { return completed; }

    public boolean completeService() {
        if (completed) {
            System.out.println("Service already recorded as completed.");
            return false;
        }
        if (car.getIsEngineOn()) {
            System.out.println("Please stop the engine before servicing.");
            return false;
        }
        completed = true;
        System.out.printf("Service #%d completed for %s %s (RM %.2f).%n",
                recordId, car.getBrand(), car.getModel(), cost);
        return true;
    }

    public void displayInfo() {
        System.out.printf("Service #%d | %s %s | %s | %s | RM %.2f | %s%n",
                recordId, car.getBrand(), car.getModel(), serviceType,
                serviceDate, cost, completed ? "Completed" : "Pending");
    }
}
