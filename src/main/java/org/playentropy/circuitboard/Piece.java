package org.playentropy.circuitboard;

import java.util.Collection;

public interface Piece {
    public Vector getSize();
    public Collection<Connector> getConnectors();

    public void update();

    public String toString();
}
