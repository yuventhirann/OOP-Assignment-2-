import java.util.Scanner;

public class Main {
    private static final int MAX_RECORDS = 50;

    public static void main(String[] args) {
        Car myCar = new Car("Toyota", "Camry", 2024, 150000.0, false, 0, 50.0);
        Car customCar = new Car();
        customCar.setBrand("Honda");
        customCar.setModel("Civic");
        customCar.setYear(2025);
        customCar.setPrice(130000.0);
        customCar.setFuelLevel(30.0);

        Car sportsCar = new SportsCar("Porsche", "911", 2025, 850000.0, 65.0, 280);
        Car economyCar = new EconomyCar("Perodua", "Bezza", 2024, 42000.0, 70.0);

        // Base-type array demonstrates polymorphism in each for-each loop.
        Car[] fleet = {myCar, customCar, sportsCar, economyCar};

        // Create supporting objects and arrays for associated records.
        Driver[] drivers = {
            new Driver("D01", "Aiman", "TEST-LIC-101", 5),
            new Driver("D02", "Priya", "TEST-LIC-202", 2),
            new Driver("D03", "New Driver", "", 0)
        };
        Trip[] tripHistory = new Trip[MAX_RECORDS];
        MaintenanceRecord[] maintenanceHistory = new MaintenanceRecord[MAX_RECORDS];
        int tripCount = 0;
        int maintenanceCount = 0;

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        System.out.println("=== CAR MANAGEMENT & SIMULATION SYSTEM ===");
        System.out.println("Demo driver details and car fuel rules are fictional.");

        // While loop keeps the menu available until the user selects Exit.
        while (running) {
            showMenu();
            int choice = readInt(scanner, "Select an option: ");

            // Switch selects the correct interaction. If-else checks validity.
            switch (choice) {
                case 1: {
                    showFleet(fleet);
                    break;
                }
                case 2: {
                    Car car = selectCar(scanner, fleet);
                    if (car != null) car.startEngine();
                    break;
                }
                case 3: {
                    Car car = selectCar(scanner, fleet);
                    if (car != null) {
                        int amount = readInt(scanner, "Speed increase (km/h): ");
                        car.accelerate(amount); // Virtual dispatch to actual subtype.
                    }
                    break;
                }
                case 4: {
                    Car car = selectCar(scanner, fleet);
                    if (car != null) car.stopEngine();
                    break;
                }
                case 5: {
                    Car car = selectCar(scanner, fleet);
                    if (car != null) {
                        double amount = readDouble(scanner, "Add fuel percentage points: ");
                        car.refuel(amount);
                    }
                    break;
                }
                case 6: {
                    if (tripCount >= MAX_RECORDS) {
                        System.out.println("Trip history is full.");
                        break;
                    }
                    Car car = selectCar(scanner, fleet);
                    if (car == null) break;
                    Driver driver = selectDriver(scanner, drivers);
                    if (driver == null) break;
                    System.out.print("Destination: ");
                    String destination = scanner.nextLine().trim();
                    double distance = readDouble(scanner, "Trip distance (km): ");
                    if (destination.isEmpty() || !Double.isFinite(distance)
                            || distance <= 0) {
                        System.out.println("Invalid trip details.");
                        break;
                    }
                    Trip trip = new Trip(tripCount + 1, car, driver,
                                         destination, distance);
                    if (trip.completeTrip()) {
                        tripHistory[tripCount] = trip;
                        tripCount++;
                    }
                    break;
                }
                case 7: {
                    if (maintenanceCount >= MAX_RECORDS) {
                        System.out.println("Maintenance history is full.");
                        break;
                    }
                    Car car = selectCar(scanner, fleet);
                    if (car == null) break;
                    System.out.print("Service type (e.g. Oil Change): ");
                    String serviceType = scanner.nextLine().trim();
                    System.out.print("Service date (YYYY-MM-DD): ");
                    String date = scanner.nextLine().trim();
                    double cost = readDouble(scanner, "Service cost (RM): ");
                    if (serviceType.isEmpty() || date.isEmpty()
                            || !Double.isFinite(cost) || cost < 0) {
                        System.out.println("Invalid maintenance details.");
                        break;
                    }
                    MaintenanceRecord record = new MaintenanceRecord( maintenanceCount + 1, car, serviceType, date, cost);
                    if (record.completeService()) {
                        maintenanceHistory[maintenanceCount] = record;
                        maintenanceCount++;
                    }
                    break;
                }
                case 8: {
                    showHistories(tripHistory, tripCount, maintenanceHistory, maintenanceCount);
                    break;
                }
                case 9: {
                    Car car = selectCar(scanner, fleet);
                    if (car instanceof SportsCar) {
                        SportsCar sports = (SportsCar) car;
                        sports.setTurboMode(!sports.isTurboMode());
                        System.out.println("Turbo mode is now "
                                + (sports.isTurboMode() ? "ON" : "OFF"));
                    } else if (car instanceof EconomyCar) {
                        EconomyCar economy = (EconomyCar) car;
                        economy.setEcoMode(!economy.isEcoMode());
                        System.out.println("Eco mode is now "
                                + (economy.isEcoMode() ? "ON" : "OFF"));
                    } else if (car != null) {
                        System.out.println("Standard cars have no special mode.");
                    }
                    break;
                }
                case 10: {
                    demonstratePolymorphism(fleet);
                    break;
                }
                case 0: {
                    running = false;
                    System.out.println("Thank you for using the Car Simulation System.");
                    break;
                }
                default: {
                    System.out.println("Invalid menu option. Try again.");
                }
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println(" 1. View all cars");
        System.out.println(" 2. Start an engine");
        System.out.println(" 3. Accelerate a car");
        System.out.println(" 4. Stop an engine");
        System.out.println(" 5. Refuel a car");
        System.out.println(" 6. Complete a trip (Driver + Car + Trip)");
        System.out.println(" 7. Record car maintenance");
        System.out.println(" 8. View trip and maintenance records");
        System.out.println(" 9. Toggle turbo or economy mode");
        System.out.println("10. Demonstrate polymorphism");
        System.out.println(" 0. Exit");
    }

    private static void showFleet(Car[] fleet) {
        System.out.println("\n--- FLEET ---");
        for (int i = 0; i < fleet.length; i++) {
            System.out.print((i + 1) + ". ");
            fleet[i].displayInfo();
        }
    }

    private static Car selectCar(Scanner scanner, Car[] fleet) {
        System.out.println("\nChoose a car:");
        for (int i = 0; i < fleet.length; i++) {
            System.out.printf("%d. %s %s (%s)%n", i + 1,
                    fleet[i].getBrand(), fleet[i].getModel(),
                    fleet[i].getClass().getSimpleName());
        }
        int selected = readInt(scanner, "Car number: ");
        if (selected < 1 || selected > fleet.length) {
            System.out.println("Invalid car selection.");
            return null;
        }
        return fleet[selected - 1];
    }

    private static Driver selectDriver(Scanner scanner, Driver[] drivers) {
        System.out.println("\nChoose a driver:");
        for (int i = 0; i < drivers.length; i++) {
            System.out.print((i + 1) + ". ");
            drivers[i].displayInfo();
        }
        int selected = readInt(scanner, "Driver number: ");
        if (selected < 1 || selected > drivers.length) {
            System.out.println("Invalid driver selection.");
            return null;
        }
        return drivers[selected - 1];
    }

    private static void showHistories(Trip[] trips, int tripCount, MaintenanceRecord[] records, int maintenanceCount) {
        System.out.println("\n--- COMPLETED TRIPS ---");
        if (tripCount == 0) System.out.println("No completed trips yet.");
        for (int i = 0; i < tripCount; i++) trips[i].displayInfo();

        System.out.println("\n--- MAINTENANCE RECORDS ---");
        if (maintenanceCount == 0) System.out.println("No maintenance records yet.");
        for (int i = 0; i < maintenanceCount; i++) records[i].displayInfo();
    }

    private static void demonstratePolymorphism(Car[] fleet) {
        System.out.println("\n--- POLYMORPHISM DEMONSTRATION ---");
        System.out.println("The same accelerate(20) call is made on every Car reference.");
        for (Car car : fleet) {
            System.out.println("\nActual object type: " + car.getClass().getSimpleName() 
            + " (" + car.getBrand() + " " + car.getModel() + ")");
            double fuelBefore = car.getFuelLevel();
            if (!car.getIsEngineOn()) car.startEngine();
            car.accelerate(20); 
            System.out.printf("Fuel used in this test: %.2f%%%n",
                    Math.max(0, fuelBefore - car.getFuelLevel()));
            car.stopEngine();
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid integer input.");
            return -1;
        }
    }

    private static double readDouble(Scanner scanner, String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number input.");
            return Double.NaN;
        }
    }
}
