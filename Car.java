public class Car {
    // Seven private attributes
    private String brand;
    private String model;
    private int year;
    private double price;
    private boolean isEngineOn;
    private int currentSpeed;
    private double fuelLevel;

    // Default constructor
    public Car() {
        this.brand = "Unknown";
        this.model = "Unknown";
        this.year = 2000;
        this.price = 0.0;
        this.isEngineOn = false;
        this.currentSpeed = 0;
        this.fuelLevel = 0.0;
    }

    // Parameterized constructor
    public Car(String brand, String model, int year, double price, boolean isEngineOn, int currentSpeed, double fuelLevel) {

        this();
        setBrand(brand);
        setModel(model);
        setYear(year);
        setPrice(price);
        setFuelLevel(fuelLevel);
        setIsEngineOn(isEngineOn);
        setCurrentSpeed(currentSpeed);
    }

    // Getter methods to access private attributes
    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public boolean getIsEngineOn() {
        return isEngineOn;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    // Setter methods to modify private attributes
    public void setBrand(String brand) {
        if (brand != null && !brand.trim().isEmpty()) {
            this.brand = brand;
        }
    }

    public void setModel(String model) {
        if (model != null && !model.trim().isEmpty()) {
            this.model = model;
        }
    }

    public void setYear(int year) {
        if (year >= 1886) {
            this.year = year;
        }
    }

    public void setPrice(double price) {
        if (Double.isFinite(price) && price >= 0) {
            this.price = price;
        }
    }

    public void setIsEngineOn(boolean isEngineOn) {
        if (isEngineOn && fuelLevel <= 0) {
            System.out.println("Cannot turn on engine. Out of fuel.");
            return;
        }

        this.isEngineOn = isEngineOn;

        if (!isEngineOn) {
            this.currentSpeed = 0;
        }
    }

    public void setCurrentSpeed(int currentSpeed) {
        if (currentSpeed >= 0 &&
            (currentSpeed == 0 || (isEngineOn && fuelLevel > 0))) {
            this.currentSpeed = currentSpeed;
        }
    }

    public void setFuelLevel(double fuelLevel) {
        if (Double.isFinite(fuelLevel) &&
            fuelLevel >= 0 && fuelLevel <= 100) {
            this.fuelLevel = fuelLevel;

            if (fuelLevel == 0) {
                this.isEngineOn = false;
                this.currentSpeed = 0;
            }
        }
    }

    // Additional Method 1: Start the engine
    public void startEngine() {
        if (isEngineOn) {
            System.out.println("Engine is already running.");
        } else if (fuelLevel > 0) {
            isEngineOn = true;
            System.out.println("Engine started successfully.");
        } else {
            System.out.println("Cannot start engine. Out of fuel.");
        }
    }

    // Additional Method 2: Accelerate the car
    public void accelerate(int speedIncrease) {
        if (!isEngineOn) {
            System.out.println("Please start the engine first!");
        } else if (speedIncrease <= 0) {
            System.out.println("Speed increase must be positive.");
        } else {
            // Dynamic binding
            double fuelNeeded = speedIncrease * getAccelerationFuelRate();

            if (fuelLevel >= fuelNeeded) {
                // Prevent integer overflow during repeated acceleration.
                if (currentSpeed > Integer.MAX_VALUE - speedIncrease) {
                    System.out.println("Speed increase is too large.");
                    return;
                }
                currentSpeed += speedIncrease;
                fuelLevel -= fuelNeeded;

                System.out.println("Accelerating. Current speed: " + currentSpeed + " km/h");

                if (fuelLevel == 0) {
                    isEngineOn = false;
                    currentSpeed = 0;
                    System.out.println("Fuel empty. Engine stopped.");
                }
            } else {
                System.out.println("Not enough fuel to accelerate.");
            }
        }
    }

    // Additional Method 3: Stop the car and engine
    public void stopEngine() {
        if (isEngineOn) {
            isEngineOn = false;
            currentSpeed = 0;
            System.out.println("Car stopped and engine turned off.");
        } else {
            System.out.println("Engine is already off.");
        }
    }

    // Fuel consumption for each km/h added. Overridden in the child classes.
    protected double getAccelerationFuelRate() {
        return 0.10;
    }

    // Simplified fuel percentage used per kilometre driven

    protected double getDistanceFuelRate() {
        return 0.08;
    }

    public boolean drive(double distanceKm) {
        if (!isEngineOn || currentSpeed <= 0) {
            System.out.println("Start and accelerate the car before driving.");
            return false;
        }
        if (!Double.isFinite(distanceKm) || distanceKm <= 0) {
            System.out.println("Distance must be a positive number.");
            return false;
        }
        double fuelNeeded = distanceKm * getDistanceFuelRate();
        if (fuelNeeded > fuelLevel) {
            System.out.println("Insufficient fuel to complete the trip.");
            return false;
        }
        setFuelLevel(Math.max(0, fuelLevel - fuelNeeded));
        System.out.printf("Travelled %.1f km; fuel remaining: %.1f%%%n", distanceKm, fuelLevel);
        return true;
    }

    // Adds fuel while keeping the fuel level within the 0-100 range. 
    public void refuel(double amount) {
        if (!Double.isFinite(amount) || amount <= 0) {
            System.out.println("Please enter a positive fuel amount.");
            return;
        }
        setFuelLevel(Math.min(100.0, fuelLevel + amount));
        System.out.printf("Refuelled. Current fuel: %.1f%%%n", fuelLevel);
    }

    // Displays shared car details; used for standard and inherited car objects. 
    public void displayInfo() {
        System.out.printf("%-11s | %-7s %-8s | %d | RM %,.2f | Engine: %-3s | %3d km/h | Fuel: %5.1f%%%n", getClass().getSimpleName(), 
        brand, model, year, price, isEngineOn ? "ON" : "OFF", currentSpeed, fuelLevel);
    }
}
