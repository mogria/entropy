package org.playentropy.circuit;

public class Connector {
    private final Vector offset;
    private final Vector direction;

    boolean connected;
    boolean updated;
    private Energy energy;

    public Connector(final Vector offsetInPiece,
                               final Vector connectionDirection) {
        assert offsetInPiece != null;
        assert connectionDirection != null;
        double expectedDistance = 1.0;
        double e = Math.ulp(expectedDistance);
        assert Math.abs(connectionDirection.distance() - 1.0) < e;

        this.offset = offsetInPiece;
        this.direction = connectionDirection;
        this.updated = false;
        this.connected = false;
        resetEnergy();
    }

    public Vector getOffset() {
        return offset;
    }

    public Vector getDirection() {
        return direction;
    }

    public Vector getConnectionOffset() {
        return offset.add(direction);
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Connector)) return false;

        Connector connector = (Connector)other;
        return offset.equals(connector.getOffset())
            && direction.equals(connector.getDirection());
    }

    public Energy readEnergy() {
        this.updated = false;
        return energy;
    }

    public void writeEnergy(Energy energy) {
        this.updated = true;
        this.energy = energy;
    }

    public Energy getEnergy() {
        return energy;
    }

    public void resetEnergy() {
        this.energy = new Energy(0, 0);
    }

    public boolean isUpdated() {
        return this.updated;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

}
