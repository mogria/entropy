package org.playentropy.circuitboard;

public class Energy {
    final Vector energyVector;

    public Energy(final Vector energyVector) {
        this.energyVector = energyVector;
    }

    public Energy(int energyMass, int energySpeed) {
        this(new Vector(energyMass, energySpeed));
    }

    public int getEnergyMass() {
        return energyVector.getX();
    }

    public int getEnergySpeed() {
        return energyVector.getY();
    }

    public Vector getEnergyVector() {
        return energyVector;
    }

    public Energy merge(Energy other) {
        return new Energy(getEnergyVector().add(other.getEnergyVector()));
    }

    public Energy split(int parts) {
        return new Energy(getEnergyMass() / parts, getEnergySpeed() / parts);
    }

    @Override
    public String toString() {
        return String.format("E%+d>%+d", getEnergyMass(), getEnergySpeed());
    }

}
