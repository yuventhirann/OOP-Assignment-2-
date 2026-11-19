public class SportsCar extends Car {
    private boolean turboMode;
    private int maximumSpeed;

    public SportsCar(String brand, String model, int year, double price, double fuelLevel, int maximumSpeed) {
        // Constructor chaining to the base class using super(...).
        super(brand, model, year, price, false, 0, fuelLevel);
        setMaximumSpeed(maximumSpeed);
        this.turboMode = false;
    }

    public boolean isTurboMode() { return turboMode; }
    public int getMaximumSpeed() { return maximumSpeed; }
    public void setTurboMode(boolean turboMode) { this.turboMode = turboMode; }
    public void setMaximumSpeed(int maximumSpeed) {
        if (maximumSpeed >= 60 && maximumSpeed <= 400) {
            this.maximumSpeed = maximumSpeed;
        } else {
            this.maximumSpeed = 240;
        }
    }

    @Override
    protected double getAccelerationFuelRate() {
        return turboMode ? 0.18 : 0.13;
    }

    @Override
    protected double getDistanceFuelRate() {
        return turboMode ? 0.14 : 0.12;
    }

    @Override
    public void accelerate(int speedIncrease) {
        if (!getIsEngineOn() || speedIncrease <= 0) {
            super.accelerate(speedIncrease);
            return;
        }
        long requested = turboMode ? 2L * speedIncrease : speedIncrease;
        int available = maximumSpeed - getCurrentSpeed();
        if (available <= 0) {
            System.out.println("Sports car has reached its speed limit.");
            return;
        }
        int adjusted = (int) Math.min(requested, available);
        System.out.println(turboMode ? "SPORTS MODE: Turbo acceleration!" : "SPORTS MODE: Normal acceleration.");
        super.accelerate(adjusted);
    }
}
