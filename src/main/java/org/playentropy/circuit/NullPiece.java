package org.playentropy.circuit;

import java.util.Collection;
import java.util.HashSet;

public class NullPiece implements Piece {
    @Override
    public Vector getSize() {
        return new Vector(1, 1);
    }

    @Override
    public Collection<Connector> getConnectors() {
        return new HashSet<Connector>();
    }

    @Override
    public void update() {
    }

    @Override
    public String getIdentifier() {
        return "NullPiece";
    }

    @Override
    public String toString() {
        return " ";
    }

    public boolean equals(Object object) {
        if(object == this) return true;
        if(object instanceof NullPiece) return true;
        return false;
    }

}
