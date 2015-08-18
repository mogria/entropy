package org.playentropy.circuit;

import java.util.Collection;
import java.io.Serializable;

public interface Piece extends Serializable {
    public Vector getSize();
    public Collection<Connector> getConnectors();

    public void update();

    public String getIdentifier();

    public String toString();
}
