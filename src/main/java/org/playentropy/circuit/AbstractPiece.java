package org.playentropy.circuit;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public abstract class AbstractPiece implements Piece {
    private final Vector size;
    private Collection<Connector> connectors =
        new HashSet<Connector>();

    public AbstractPiece(final Vector size) {
        this.size = size;
    }

    protected Connector addConnector(Vector offset, Vector direction) {
        assert offset != null;
        assert direction != null;
        assert offset.withinField(new Vector(0, 0),
                                  size.add(new Vector(-1, -1)));
        Connector connector = new Connector(offset, direction);

        assert !connectors.contains(connector);

        connectors.add(connector);
        return connector;
    }

    final public Vector getSize() {
        return size;
    }

    final public Collection<Connector> getConnectors() {
        return connectors;
    }

    private Collection<Connector> getInputConnectors() {
        return connectors.stream()
                         .filter(connector -> connector.isConnected())
                         .filter(connector -> connector.isUpdated())
                         .collect(Collectors.toList());
    }

    private Collection<Connector> getOutputConnectors() {
        return connectors.stream()
                         .filter(connector -> connector.isConnected())
                         .filter(connector -> !connector.isUpdated())
                         .collect(Collectors.toList());
    }

    public void update() {
        Collection<Connector> inputs = getInputConnectors();
        Collection<Connector> outputs = getOutputConnectors();
        update(inputs, outputs);
    }

    abstract public void update(Collection<Connector> inputConnectors,
                         Collection<Connector> outputConnectors);
}
