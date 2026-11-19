public class EconomyCar extends Car {
    private boolean ecoMode;
    private int maximumEcoAcceleration;

    public EconomyCar(String brand, String model, int year, double price, double fuelLevel) {
        super(brand, model, year, price, false, 0, fuelLevel);
        this.ecoMode = true;
        this.maximumEcoAcceleration = 15;
    }

    public boolean isEcoMode() { return ecoMode; }
    public int getMaximumEcoAcceleration() { return maximumEcoAcceleration; }
    public void setEcoMode(boolean ecoMode) { this.ecoMode = ecoMode; }
    public void setMaximumEcoAcceleration(int maximumEcoAcceleration) {
        if (maximumEcoAcceleration > 0 && maximumEcoAcceleration <= 40) {
            this.maximumEcoAcceleration = maximumEcoAcceleration;
        }
    }

    @Override
    protected double getAccelerationFuelRate() {
        return ecoMode ? 0.06 : 0.10;
    }

    @Override
    protected double getDistanceFuelRate() {
        return ecoMode ? 0.05 : 0.08;
    }

    @Override
    public void accelerate(int speedIncrease) {
        if (!getIsEngineOn() || speedIncrease <= 0) {
            super.accelerate(speedIncrease);
            return;
        }
        int adjusted = ecoMode ? Math.min(speedIncrease, maximumEcoAcceleration) : speedIncrease;
        System.out.println(ecoMode ? "ECONOMY MODE: Fuel-saving acceleration." : "ECONOMY MODE: Standard acceleration.");
        super.accelerate(adjusted);
    }
}
